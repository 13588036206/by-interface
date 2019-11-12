package com.youzan.youzanservice;

import com.youzan.dao.model.Company;
import com.youzan.dao.model.RefreshToken;
import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;

import java.util.List;

public interface IUserInfoService {
    String getKeyByCompanyCode(String companycode);

    String getAuthorityId(String companycode,String bycode);

    void saveTokenInfo(TokenInfo tokenInfo) throws Exception;

    void saveUserInfo(UserInfo userInfo) throws Exception;

    UserInfo getUserInfoByCompanyCode(String companycode);

    List<Company> getByCondition(String companycode,String bycode, String anyField, String corresponding, Integer start, Integer end);

    Integer count(String companycode,String bycode,String anyField,String corresponding);

    void updateCompany(Company company);

    void updateAuthorityName(String authorityName,String authorityId);

    TokenInfo selectByBycode(String companycode,String bycode);

    List<RefreshToken> getAlltokenInfo();

    List<String> getAllCompanycode();

    boolean checkValid(List<Company> list,String companyCode);

}
