package com.ryuqq.setof.lamda;

public class KasinaCrawlRawData implements CrawlRawData {

    private KasinaItems items;

    public KasinaItems getItems() {
        return items;
    }

    public void setItems(KasinaItems items) {
        this.items = items;
    }

    public static class KasinaItems {
        private long productNo;

        private String productName;

        public long getProductNo() {
            return productNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductNo(long productNo) {
            this.productNo = productNo;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public CrawlProductInsertRequestDto crawlProductInsertRequestDto(long siteId){
            return new CrawlProductInsertRequestDto(
                    siteId,
                    SiteName.KASINA,
                    productNo,
                    productName
            );
        }
    }


}
