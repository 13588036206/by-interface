package com.youzan.dao.model;

public class YouzanMessage {
    private Integer id;

    private String type;

    private String orderno;

    private String skuversiontext;

    private Integer skuintervaltext;

    private String buyerphone;

    private String paytime;

    private String kdtid;

    private String shopdisplayno;

    private Integer price;

    private String appid;

    private String buyerid;

    private String status;

    private String env;

    private String expiretime;

    private String effecttime;

    private String companycode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getSkuversiontext() {
        return skuversiontext;
    }

    public void setSkuversiontext(String skuversiontext) {
        this.skuversiontext = skuversiontext == null ? null : skuversiontext.trim();
    }

    public Integer getSkuintervaltext() {
        return skuintervaltext;
    }

    public void setSkuintervaltext(Integer skuintervaltext) {
        this.skuintervaltext = skuintervaltext;
    }

    public String getBuyerphone() {
        return buyerphone;
    }

    public void setBuyerphone(String buyerphone) {
        this.buyerphone = buyerphone == null ? null : buyerphone.trim();
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime == null ? null : paytime.trim();
    }

    public String getKdtid() {
        return kdtid;
    }

    public void setKdtid(String kdtid) {
        this.kdtid = kdtid == null ? null : kdtid.trim();
    }

    public String getShopdisplayno() {
        return shopdisplayno;
    }

    public void setShopdisplayno(String shopdisplayno) {
        this.shopdisplayno = shopdisplayno == null ? null : shopdisplayno.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid == null ? null : buyerid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime == null ? null : expiretime.trim();
    }

    public String getEffecttime() {
        return effecttime;
    }

    public void setEffecttime(String effecttime) {
        this.effecttime = effecttime == null ? null : effecttime.trim();
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }
}