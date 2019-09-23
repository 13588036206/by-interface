package com.youzan.dao.respBean;

import com.youzan.dao.model.TokenInfo;

public class TokenRespBean extends BaseRespBean{
    private TokenInfo data;
    public TokenRespBean() {
    }

    public TokenInfo getData() {
        return data;
    }

    public void setData(TokenInfo data) {
        this.data = data;
    }

    public TokenRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }
}
