package com.youzan.dao.model;

public class DeliveryTemplate {
    private Long id;

    private String deliveryTemplateFee;

    private String deliveryTemplateName;

    private Integer deliveryTemplateValuationType;

    private Long deliveryTemplateId;

    private Long itemId;

    private String createtime;

    private String modifytime;

    private String companycode;

    private String authorityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryTemplateFee() {
        return deliveryTemplateFee;
    }

    public void setDeliveryTemplateFee(String deliveryTemplateFee) {
        this.deliveryTemplateFee = deliveryTemplateFee == null ? null : deliveryTemplateFee.trim();
    }

    public String getDeliveryTemplateName() {
        return deliveryTemplateName;
    }

    public void setDeliveryTemplateName(String deliveryTemplateName) {
        this.deliveryTemplateName = deliveryTemplateName == null ? null : deliveryTemplateName.trim();
    }

    public Integer getDeliveryTemplateValuationType() {
        return deliveryTemplateValuationType;
    }

    public void setDeliveryTemplateValuationType(Integer deliveryTemplateValuationType) {
        this.deliveryTemplateValuationType = deliveryTemplateValuationType;
    }

    public Long getDeliveryTemplateId() {
        return deliveryTemplateId;
    }

    public void setDeliveryTemplateId(Long deliveryTemplateId) {
        this.deliveryTemplateId = deliveryTemplateId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId == null ? null : authorityId.trim();
    }
}