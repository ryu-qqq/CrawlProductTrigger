package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface Mapper<T extends CrawlRawData> {

    List<CrawlProductInsertRequestDto> parseData(long siteId, JsonNode dataNode);


}
