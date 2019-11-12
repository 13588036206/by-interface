package com.youzan.youzanservice.impl;

import com.youzan.dao.mapper.TokenInfoMapper;
import com.youzan.dao.mapper.UserInfoMapper;
import com.youzan.dao.model.Company;
import com.youzan.dao.model.RefreshToken;
import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;
import com.youzan.youzanservice.IUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public String getAuthorityId(String companycode, String bycode) {
        return tokenInfoMapper.selectAuthorityId(companycode,bycode);
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
    public UserInfo getUserInfoByCompanyCode(String companycode) {
        UserInfo userInfo = null;
        try {
             userInfo = userInfoMapper.selectByCompanyCode(companycode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    public List<Company> getByCondition(String companycode,String bycode, String anyField, String corresponding, Integer start, Integer end) {
       return tokenInfoMapper.selectByCondition(companycode,bycode,anyField,corresponding,start,end);
    }

    @Override
    public Integer count(String companycode,String bycode, String anyField, String corresponding) {
        return tokenInfoMapper.count(companycode,bycode,anyField,corresponding);
    }

    @Override
    public void updateCompany(Company company) {
        tokenInfoMapper.updateCompany(company);
    }

    @Override
    public List<RefreshToken> getAlltokenInfo() {
        return tokenInfoMapper.selcectAllToken();
    }

    @Override
    public void updateAuthorityName(String authorityName,String authorityId) {
        tokenInfoMapper.updateAuthorityName(authorityName,authorityId);
    }

    @Override
    public TokenInfo selectByBycode(String companycode, String bycode) {
        return tokenInfoMapper.selectByBycode(companycode,bycode);
    }

    @Override
    public List<String> getAllCompanycode() {
        return tokenInfoMapper.selcectAllCompanycode();
    }

    @Override
    public boolean checkValid(List<Company> list,String companyCode) {
        if(Optional.ofNullable(list).isPresent()){
            StringBuffer sb= new StringBuffer();
            List<String> allBycodes = new ArrayList<>();
            for(Company company:list){
                sb.append("'").append(company.getAuthorityId()).append("',");
                if(Optional.ofNullable(company.getBycode()).isPresent()&&!"".equals(company.getBycode())){
                    allBycodes.add(company.getBycode());
                }
            }
            String authorityIds = sb.substring(0,sb.toString().length()-1);
            List bycodes = tokenInfoMapper.selcectBycodes(authorityIds,companyCode);
            if(Optional.ofNullable(bycodes).isPresent()){
                allBycodes.addAll(bycodes);
            }
            if(allBycodes.size()>0) {
                Map<String, Long> map = allBycodes.stream().
                        collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                for (Long count : map.values()) {
                    if (count > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
