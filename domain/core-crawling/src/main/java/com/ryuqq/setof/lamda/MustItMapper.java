package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class MustItMapper implements Mapper<MustItCrawlRawData>{

    @Override
    public List<CrawlProductInsertRequestDto> parseData(long siteId, JsonNode dataNode) {
        List<MustItCrawlRawData> mustItCrawlRawData = JsonUtils.fromJsonList(dataNode.toString(), MustItCrawlRawData.class);
            return mustItCrawlRawData.stream()
                .map(k -> k.getItems()
                            .crawlProductInsertRequestDto(siteId))
                .toList();
    }
}
