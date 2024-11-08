package com.ryuqq.setof.lamda;

import java.util.HashMap;
import java.util.Map;

public class MapperProvider {

    private static final Map<SiteName, Mapper<? extends CrawlRawData>> mappers = new HashMap<>();

    static {
        mappers.put(SiteName.KASINA, new KasinaMapper());
        mappers.put(SiteName.MUSTIT, new MustItMapper());
    }

    public static <T extends CrawlRawData> Mapper<T> getMapper(SiteName siteName) {
        @SuppressWarnings("unchecked")
        Mapper<T> mapper = (Mapper<T>) mappers.get(siteName);
        if (mapper == null) {
            throw new IllegalArgumentException("No mapper found for site: " + siteName);
        }
        return mapper;
    }

}
