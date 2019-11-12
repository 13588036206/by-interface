package com.youzan.dao.model;

public class GoodsQuantityReq {
    private Long itemId;

    private Long skuId;

    private String skuUniqueCode;

    private Long quantity;

    private String accessToken;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuUniqueCode() {
        return skuUniqueCode;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.skuUniqueCode = skuUniqueCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}