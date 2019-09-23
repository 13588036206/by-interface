package com.youzan.youzanservice;

import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;
import com.youzan.dao.respBean.UserData;

public interface IUserInfoService {
    public String getKeyByCompanyCode(String companycode);

    public void saveTokenInfo(TokenInfo tokenInfo) throws Exception;

    public void saveUserInfo(UserInfo userInfo) throws Exception;

    public UserData getUserDataByCompanyCode(String companycode);

}
