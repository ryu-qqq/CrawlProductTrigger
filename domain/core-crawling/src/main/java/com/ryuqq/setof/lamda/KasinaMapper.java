package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class KasinaMapper implements Mapper<KasinaCrawlRawData>{


    @Override
    public List<CrawlProductInsertRequestDto> parseData(long siteId, JsonNode dataNode) {
        List<KasinaCrawlRawData> kasinaCrawlRawData = JsonUtils.fromJsonList(dataNode.toString(), KasinaCrawlRawData.class);
        return kasinaCrawlRawData.stream()
                .map(k -> k.getItems()
                        .crawlProductInsertRequestDto(siteId))
                .toList();

    }
}
