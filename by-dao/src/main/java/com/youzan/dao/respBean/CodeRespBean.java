package com.youzan.dao.respBean;

public class CodeRespBean extends BaseRespBean {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CodeRespBean() {
    }

    public CodeRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }
}
