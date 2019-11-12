package com.youzan.dao.model;

public class Company {

    private Long authorityId;

    private String authorityName;

    private  String bycode;

    private String corresponding;

    private String modifytime;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getBycode() {
        return bycode;
    }

    public void setBycode(String bycode) {
        this.bycode = bycode;
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