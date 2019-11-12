package com.youzan.dao.respBean;

import com.youzan.dao.model.UserInfo;

public class UserInfoRespBean extends BaseRespBean{
    private UserInfo data;

    public UserInfoRespBean() {
    }

    public UserInfoRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
