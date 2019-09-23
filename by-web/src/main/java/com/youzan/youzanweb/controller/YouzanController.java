package com.youzan.youzanweb.controller;


import com.alibaba.fastjson.JSON;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.oauth.model.OAuthToken;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanItemQuantityUpdate;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanItemQuantityUpdateParams;
import com.youzan.dao.model.CodeRecord;
import com.youzan.dao.model.TokenInfo;
import com.youzan.dao.model.UserInfo;
import com.youzan.dao.model.YouzanMessage;
import com.youzan.dao.reqBean.GetTokenBean;
import com.youzan.dao.reqBean.UserReqBean;
import com.youzan.dao.respBean.*;
import com.youzan.youzanUtil.AES;
import com.youzan.youzanservice.IUserInfoService;
import com.youzan.youzanservice.IYouzanMessageService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
public class YouzanController {

    @Autowired
    private IYouzanMessageService youzanMessageService;
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 有赞推送接口
     * @param httpServletRequest
     */
    @RequestMapping(value="/youzan*",method = RequestMethod.GET)
    public void handleRequest(HttpServletRequest httpServletRequest){
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String companyCode = requestUrl.substring(requestUrl.length()-5);
        try{
            String key = userInfoService.getKeyByCompanyCode(companyCode);
            String message = httpServletRequest.getParameter("message");
            String code = httpServletRequest.getParameter("code");
            if(Optional.ofNullable(message).isPresent()){
                message = AES.decrypt(message,key);
                YouzanMessage youzanMessage=JSON.parseObject(message,YouzanMessage.class);
                if(Optional.ofNullable(youzanMessage).isPresent()){
                    youzanMessage.setCompanycode(companyCode);
                    youzanMessageService.saveMessage(youzanMessage);
                }
            }else if(Optional.ofNullable(code).isPresent()){
                CodeRecord codeRecord = new CodeRecord();
                codeRecord.setCode(code);
                codeRecord.setCreatetime(System.currentTimeMillis()+"");
                codeRecord.setCompanycode(companyCode);
                youzanMessageService.saveCode(codeRecord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取/刷新token信息
     * @param reqJson
     * @return
     * {"code":"200","data":{"accessToken":"dd875a48635a764c059fa918015ce93","authorityId":"44120002","companycode":"36610","createtime":"2019-09-21 14:27:55","expires":"1569652082853","id":0,"modifytime":"2019-09-21 14:27:55","refreshToken":"3b2bf083bfbc7c10d06e57d0140024f","scope":"multi_store shop item trade logistics coupon_advanced user pay_qrcode trade_virtual reviews item_category storage retail_goods open_market crm_advanced trade-delivery retail_trade retail-scrm beauty_staff tag beauty_item beauty_appointment beauty_member beauty_order beauty_cashier beauty_stock beauty_shop mei_promoter item_hotel item_standard circle selffetchcode virtualticket tags trade_advanced retail_product retail_stock retail_shop retail_supplier"},"message":"","state":"SUCCESS"}
     */
    @RequestMapping(value="/byGetToken",method = RequestMethod.POST)
    @ResponseBody
    public String  byGetToken(@RequestBody String reqJson){
        TokenRespBean tokenRespBean =null;
        try {
            GetTokenBean getTokenBean = JSON.parseObject(reqJson,GetTokenBean.class);
            DefaultYZClient yzClient = new DefaultYZClient();
            TokenParameter tokenParameter = null;
            if(Optional.ofNullable(getTokenBean.getCode()).isPresent()) {
                tokenParameter=TokenParameter.code()
                        .clientId(getTokenBean.getClientId())
                        .clientSecret(getTokenBean.getClientSecret())
                        .code(getTokenBean.getCode())
                        .build();
            }else{
                tokenParameter=TokenParameter.refresh()
                        .clientId(getTokenBean.getClientId())
                        .clientSecret(getTokenBean.getClientSecret())
                        .refreshToken(getTokenBean.getRefreshToken())
                        .build();
            }
            OAuthToken codeToken = yzClient.getOAuthToken(tokenParameter);
            TokenInfo tokenInfo = new TokenInfo();
            BeanUtils.copyProperties(tokenInfo,codeToken);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String currentDate = df.format(new Date());
            tokenInfo.setModifytime(currentDate);
            tokenInfo.setCreatetime(currentDate);
            tokenInfo.setCompanycode(getTokenBean.getCompanyCode());
            userInfoService.saveTokenInfo(tokenInfo);
            tokenRespBean = new TokenRespBean();
            tokenRespBean.setData(tokenInfo);
        }catch (Exception e){
            tokenRespBean = new TokenRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return tokenRespBean.toString();
    }

    /**
     *获取最新code信息
     * @param httpServletRequest
     * @return
     * {"code":"200","data":"0eaad1dee4079cf356c6f822edadc489","message":"","state":"SUCCESS"}
     */
    @RequestMapping(value="/byGetCode",method = RequestMethod.POST)
    @ResponseBody
    public String byGetCode(HttpServletRequest httpServletRequest){
        CodeRespBean codeRespBean = null;
        try{
            String companyCode=httpServletRequest.getParameter("companyCode");
            String code = youzanMessageService.getCode(companyCode);
            if(Optional.ofNullable(code).isPresent()){
                codeRespBean = new CodeRespBean();
                codeRespBean.setData(code);
            }else{
                codeRespBean = new CodeRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,"code不存在");
            }
        }catch (Exception e){
            codeRespBean = new CodeRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return codeRespBean.toString();
    }

    /**
     * 获取用户信息及店铺token信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value="/byGetUserData",method = RequestMethod.POST)
    @ResponseBody
    public String byGetUserInfo(HttpServletRequest httpServletRequest){
        UserDataRespBean userDataRespBean = null;
        try {
            String companyCode = httpServletRequest.getParameter("companyCode");
            UserData userData = userInfoService.getUserDataByCompanyCode(companyCode);
            if(Optional.ofNullable(userData).isPresent()){
                userDataRespBean = new UserDataRespBean();
                userDataRespBean.setData(userData);
            }else{
                userDataRespBean= new UserDataRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,"未知错误");
            }
        }catch (Exception e){
            userDataRespBean = new UserDataRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return userDataRespBean.toString();
    }

    /**
     * 保存用户基本信息
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/bySaveUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String bySaveUserInfo(@RequestBody String reqJson){
        BaseRespBean baseRespBean = null;
        try{
            UserReqBean userReqBean = JSON.parseObject(reqJson,UserReqBean.class);
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfo,userReqBean);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String currentDate = df.format(new Date());
            userInfo.setCreatetime(currentDate);
            userInfo.setModifytime(currentDate);
            userInfoService.saveUserInfo(userInfo);
            baseRespBean=new BaseRespBean();
        }catch (Exception e){
            baseRespBean = new BaseRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return baseRespBean.toString();
    }



    public static void main(String[] args) {
        try {
            Token token = new Token("token");
            YouzanItemQuantityUpdateParams youzanItemQuantityUpdateParams = null;
            YouzanItemQuantityUpdate youzanItemQuantityUpdate = null;
            DefaultYZClient yzClient = new DefaultYZClient();
            TokenParameter tokenParameter = TokenParameter.code()
                    .clientId("7c8755a2a81bda002c")
                    .clientSecret("b407352e0488e80c386897337ceb8d4e")
                    .code("0eaad1dee4079cf356c6f822edadc489")
                    .build();
            OAuthToken codeToken = yzClient.getOAuthToken(tokenParameter);
            System.out.println(codeToken.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
