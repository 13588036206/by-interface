package com.youzan.dao.model;

public class GoodsSkus {
    private Integer id;

    private String modified;

    private String skuUniqueCode;

    private String outerId;

    private Long skuId;

    private String oneItemMultiCode;

    private Long soldNum;

    private String itemNo;

    private Long price;

    private Long itemId;

    private Long costPrice;

    private String propertiesNameJson;

    private String created;

    private Long withHoldQuantity;

    private Long quantity;

    private String createtime;

    private String modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified == null ? null : modified.trim();
    }

    public String getSkuUniqueCode() {
        return skuUniqueCode;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.skuUniqueCode = skuUniqueCode == null ? null : skuUniqueCode.trim();
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId == null ? null : outerId.trim();
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getOneItemMultiCode() {
        return oneItemMultiCode;
    }

    public void setOneItemMultiCode(String oneItemMultiCode) {
        this.oneItemMultiCode = oneItemMultiCode == null ? null : oneItemMultiCode.trim();
    }

    public Long getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Long soldNum) {
        this.soldNum = soldNum;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public String getPropertiesNameJson() {
        return propertiesNameJson;
    }

    public void setPropertiesNameJson(String propertiesNameJson) {
        this.propertiesNameJson = propertiesNameJson == null ? null : propertiesNameJson.trim();
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created == null ? null : created.trim();
    }

    public Long getWithHoldQuantity() {
        return withHoldQuantity;
    }

    public void setWithHoldQuantity(Long withHoldQuantity) {
        this.withHoldQuantity = withHoldQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime == null ? null : modifytime.trim();
    }
}