package com.youzan.youzanservice.impl;

import com.youzan.dao.mapper.TokenInfoMapper;
import com.youzan.dao.mapper.UserInfoMapper;
import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;
import com.youzan.dao.respBean.UserData;
import com.youzan.youzanservice.IUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private TokenInfoMapper tokenInfoMapper;

    @Override
    public String getKeyByCompanyCode(String companycode) {
        return userInfoMapper.selectKeyByCompanyCode(companycode);
    }

    @Override
    public void saveTokenInfo(TokenInfo tokenInfo) {
        if (Optional.ofNullable(tokenInfoMapper.selectByAuthorityId(tokenInfo.getAuthorityId())).isPresent()) {
            tokenInfoMapper.updateByAuthorityId(tokenInfo);
        } else {
            tokenInfoMapper.insert(tokenInfo);
        }
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        if (Optional.ofNullable(userInfoMapper.selectByCompanyCode(userInfo.getCompanycode())).isPresent()) {
            userInfoMapper.updateByCompanyCode(userInfo);
        } else {
            userInfoMapper.insert(userInfo);
        }
    }

    @Override
    public UserData getUserDataByCompanyCode(String companycode) {
        UserData userData = null;
        try {
            List<TokenInfo> listTokenInfo = tokenInfoMapper.selectByCompanyCode(companycode);
            UserInfo userInfo = userInfoMapper.selectByCompanyCode(companycode);
            userData = new UserData(userInfo, listTokenInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }
}
