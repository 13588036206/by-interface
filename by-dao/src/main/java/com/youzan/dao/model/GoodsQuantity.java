package com.youzan.dao.model;

public class GoodsQuantity {
    private Integer id;

    private String goodsid;

    private String bycode;

    private String skuUniqueCode;

    private Long quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public String getBycode() {
        return bycode;
    }

    public void setBycode(String bycode) {
        this.bycode = bycode == null ? null : bycode.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSkuUniqueCode() {
        return skuUniqueCode;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.skuUniqueCode = skuUniqueCode;
    }
}