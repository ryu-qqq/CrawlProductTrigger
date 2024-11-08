package com.ryuqq.setof.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CrawlingTriggerHandler implements RequestHandler<S3Event, String> {

    private final S3Client s3Client = S3Client.builder()
            .region(Region.of("ap-northeast-2"))
            .build();

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String handleRequest(S3Event event, Context context) {
        try {

            String bucketName = event.getRecords().getFirst().getS3().getBucket().getName();
            String objectKey = event.getRecords().getFirst().getS3().getObject().getUrlDecodedKey();

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);

            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(object))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line);
                }
            }
            JsonNode rootNode = objectMapper.readTree(fileContent.toString());


            JsonNode metadataNode = rootNode.path("metadata");
            String siteName = metadataNode.path("site_name").asText();
            long siteId = metadataNode.path("site_id").asLong();


            Mapper<CrawlRawData> mapper = MapperProvider.getMapper(SiteName.of(siteName));
            JsonNode dataNode = rootNode.path("data");

            List<CrawlProductInsertRequestDto> requests = mapper.parseData(siteId, dataNode);
            sendToServer(requests, context);

            return "Success";

        } catch (Exception e) {
            context.getLogger().log("Error processing S3 event: " + e.getMessage());
            return "Error";
        }
    }


    private void sendToServer(List<CrawlProductInsertRequestDto> requestDtos, Context context) throws Exception {

        String serverUri = "https://your-server-host/api/v1/site/crawl/product";

        String jsonPayload = objectMapper.writeValueAsString(requestDtos);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverUri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();


        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            context.getLogger().log("Data sent successfully. Response code: " + response.statusCode());
        } else {
            context.getLogger().log("Failed to send data. HTTP response code: " + response.statusCode());
            throw new Exception("Failed to send data to server. HTTP response code: " + response.statusCode());
        }
    }

}
