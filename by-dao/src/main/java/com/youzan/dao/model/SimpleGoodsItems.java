package com.youzan.dao.model;

public class SimpleGoodsItems {

    private String skuUniqueCode;

    private String title;

    private String goodsId;

    private  String companycode;

    private String corresponding;

    private String modifytime;




    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getSkuUniqueCode() {
        return skuUniqueCode;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.skuUniqueCode = skuUniqueCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getCorresponding() {
        return corresponding;
    }

    public void setCorresponding(String corresponding) {
        this.corresponding = corresponding;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }
}