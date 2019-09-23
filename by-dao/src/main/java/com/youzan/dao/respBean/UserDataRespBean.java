package com.youzan.dao.respBean;

public class UserDataRespBean extends BaseRespBean{
    private UserData data;

    public UserDataRespBean() {
    }

    public UserDataRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
