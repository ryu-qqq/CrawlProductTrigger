package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class MustItCrawlRawData implements CrawlRawData {

    private MustItItems items;

    public MustItCrawlRawData.MustItItems getItems() {
        return items;
    }

    public void setItems(MustItItems items) {
        this.items = items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MustItItems {
        private long itemNo;

        private String name;

        public long getItemNo() {
            return itemNo;
        }

        public void setItemNo(long itemNo) {
            this.itemNo = itemNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public CrawlProductInsertRequestDto crawlProductInsertRequestDto(long siteId){
            return new CrawlProductInsertRequestDto(
                    siteId,
                    SiteName.MUSTIT,
                    itemNo,
                    name
            );
        }
    }



}
