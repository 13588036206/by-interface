package com.youzan.dao.respBean;

import net.sf.json.JSONObject;

public class BaseRespBean {
    protected String code;
    protected String state;
    protected String message;
    public static String SUCCESSCODE="200";
    public static String ERRORCODE="-1";
    public static String SUCCESS="SUCCESS";
    public static String ERROR="ERROR";

    public BaseRespBean() {
        this.code = BaseRespBean.SUCCESSCODE;
        this.state = BaseRespBean.SUCCESS;
    }

    public BaseRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
