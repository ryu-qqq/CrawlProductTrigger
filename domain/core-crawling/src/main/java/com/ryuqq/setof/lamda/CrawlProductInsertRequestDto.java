package com.ryuqq.setof.lamda;

public record CrawlProductInsertRequestDto(
        long siteId,
        SiteName siteName,
        long siteProductId,
        String productName
) {}
