package com.youzan.dao.respBean;

import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;

import java.util.List;

public class UserData {
    private UserInfo userInfo;
    private List<TokenInfo> listToken;

    public UserData(UserInfo userInfo, List<TokenInfo> listToken) {
        this.userInfo = userInfo;
        this.listToken = listToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<TokenInfo> getListToken() {
        return listToken;
    }

    public void setListToken(List<TokenInfo> listToken) {
        this.listToken = listToken;
    }
}
