package com.youzan.youzanweb.controller;


import com.alibaba.fastjson.JSON;
import com.by.byconfig.dynamicDS.DynamicDataSourceContextHolder;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanItemSearch;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanItemsOnsaleGet;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanShopGet;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.*;
import com.youzan.dao.model.*;
import com.youzan.dao.reqBean.*;
import com.youzan.dao.respBean.*;
import com.youzan.youzanUtil.AES;
import com.youzan.youzanUtil.CglibBeanCopierUtils;
import com.youzan.youzanUtil.StringUtil;
import com.youzan.youzanservice.IGoodsItemsService;
import com.youzan.youzanservice.IUserInfoService;
import com.youzan.youzanservice.IYouzanMessageService;
import com.youzan.youzanweb.scheduled.GoodsItemThread;
import com.youzan.youzanweb.scheduled.SkuThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class YouzanController {
    private static final Logger LOG = LoggerFactory.getLogger(YouzanController.class);

    @Autowired
    private IYouzanMessageService youzanMessageService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IGoodsItemsService goodsItemsService;

    /**
     * 有赞推送接口
     * @param httpServletRequest
     */
    @RequestMapping(value="/youzan/{companyCode}",method = RequestMethod.GET)
    @ResponseBody
    public String handleRequest(HttpServletRequest httpServletRequest,@PathVariable("companyCode") String companyCode){
        LOG.info("访问开始时间:"+System.currentTimeMillis());
        String requestUrl = httpServletRequest.getRequestURL().toString();
        LOG.info("---访问地址:"+requestUrl+"||公司代码:"+companyCode);
        try{
            String key = userInfoService.getKeyByCompanyCode(companyCode);
            String message = httpServletRequest.getParameter("message");
            String code = httpServletRequest.getParameter("code");
            LOG.info("---message:"+message+"||code:"+code);
            if(Optional.ofNullable(message).isPresent()){
                message = AES.decrypt(message,key);
                YouzanMessage youzanMessage=JSON.parseObject(message,YouzanMessage.class);
                if(Optional.ofNullable(youzanMessage).isPresent()){
                    youzanMessage.setCompanycode(companyCode);
                    youzanMessageService.saveMessage(youzanMessage);
                }
            }else if(Optional.ofNullable(code).isPresent()){
                //记录code
                CodeRecord codeRecord = new CodeRecord();
                codeRecord.setCode(code);
                codeRecord.setCreatetime(System.currentTimeMillis()+"");
                codeRecord.setCompanycode(companyCode);
                youzanMessageService.saveCode(codeRecord);

                //获取token
                UserInfo userInfo = userInfoService.getUserInfoByCompanyCode(companyCode);
                if(Optional.ofNullable(userInfo).isPresent()) {
                    DefaultYZClient yzClient = new DefaultYZClient();
                    TokenParameter tokenParameter = TokenParameter.code()
                            .clientId(userInfo.getClientId())
                            .clientSecret(userInfo.getClientSecret())
                            .code(code)
                            .build();
                    TokenInfo tokenInfo = new TokenInfo();
                    CglibBeanCopierUtils.copyProperties(yzClient.getOAuthToken(tokenParameter),tokenInfo);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String currentDate = df.format(new Date());
                    tokenInfo.setModifytime(currentDate);
                    tokenInfo.setCreatetime(currentDate);
                    tokenInfo.setCompanycode(companyCode);
                    userInfoService.saveTokenInfo(tokenInfo);

                    //获取店铺名字
                    Token token = new Token(tokenInfo.getAccessToken());
                    YouzanShopGet youzanShopGet = new YouzanShopGet();
                    YouzanShopGetParams youzanShopGetParams = new YouzanShopGetParams();
                    youzanShopGet.setAPIParams(youzanShopGetParams);
                    YouzanShopGetResult result = yzClient.invoke(youzanShopGet, token, YouzanShopGetResult.class);
                    if(result.getCode()==200&&result.getSuccess()) {
                        userInfoService.updateAuthorityName(result.getData().getName(),tokenInfo.getAuthorityId());
                    }
                }
            }
        }catch (Exception e){
            LOG.info("访问出现异常,异常原因:"+e.getMessage());
            return "ERROR";
        }
        LOG.info("访问结束时间:"+System.currentTimeMillis());
        return "SUCCESS";
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
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/byGetUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String byGetUserInfo(@RequestBody String reqJson){
        UserInfoRespBean userInfoRespBean = null;
        try {
            BaseReqBean baseReqBean =  JSON.parseObject(reqJson,BaseReqBean.class);
            UserInfo userInfo = userInfoService.getUserInfoByCompanyCode(baseReqBean.getCompanycode());
            if(Optional.ofNullable(userInfo).isPresent()){
                userInfoRespBean = new UserInfoRespBean();
                userInfoRespBean.setData(userInfo);
            }else{
                userInfoRespBean = new UserInfoRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,"未知错误");
            }
        }catch (Exception e){
            userInfoRespBean = new UserInfoRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return userInfoRespBean.toString();
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
            //BeanUtils.copyProperties(userInfo,userReqBean);
            CglibBeanCopierUtils.copyProperties(userReqBean,userInfo);
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

    /**
     * 获取商品信息
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/byGetGoodsItems",method = RequestMethod.POST)
    @ResponseBody
    public String byGetGoodsItems(@RequestBody String reqJson){
        GoodsItemsRespBean goodsItemsRespBean = null;
        try {
            GoodsItemsReq goodsItemsReq =  JSON.parseObject(reqJson,GoodsItemsReq.class);
            String authorityId = userInfoService.getAuthorityId(goodsItemsReq.getCompanycode(),goodsItemsReq.getBycode());
            String anyfield = goodsItemsReq.getAnyField()==null?null:"%"+goodsItemsReq.getAnyField()+"%";
            Integer count = goodsItemsService.count(authorityId,anyfield,goodsItemsReq.getGoodsId(),goodsItemsReq.getCorresponding());
            List<SimpleGoodsItems> goodsItems = goodsItemsService.getByCondition(authorityId,anyfield,
                    goodsItemsReq.getGoodsId(),goodsItemsReq.getCorresponding(),goodsItemsReq.getStart(),goodsItemsReq.getEnd());
            goodsItemsRespBean = new GoodsItemsRespBean();
            goodsItemsRespBean.setData(goodsItems);
            goodsItemsRespBean.setCount(count);
        }catch (Exception e){
            goodsItemsRespBean = new GoodsItemsRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return goodsItemsRespBean.toString();
    }

    /**
     * 保存商品信息
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/bySaveGoodsItems",method = RequestMethod.POST)
    @ResponseBody
    public String bySaveGoodsItems(@RequestBody String reqJson){
        BaseRespBean baseRespBean = null;
        try {
            GoodsSaveReq goodsSaveReq =  JSON.parseObject(reqJson,GoodsSaveReq.class);
            Map<String,String> trueSignMap = new HashMap<>();//有对应的商品id列表
            List<String> allList = new ArrayList<>();//所有的商品id列表
            if(Optional.ofNullable(goodsSaveReq.getData()).isPresent()&&goodsSaveReq.getData().size()>0){
                List<SimpleGoodsItems> list = goodsSaveReq.getData();
                if(!goodsItemsService.checkValid(list)){
                    return new BaseRespBean(BaseRespBean.UNIQUEERRORCODE, BaseRespBean.UNIQUEERROR, "博远商品与有赞商品只能一一对应！").toString();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String currentDate = df.format(new Date());
                for (SimpleGoodsItems simpleGoodsItems:list){
                    if("".equals(simpleGoodsItems.getGoodsId())){
                        simpleGoodsItems.setGoodsId(null);
                    }
                    allList.add(simpleGoodsItems.getSkuUniqueCode());
                    if("true".equals(simpleGoodsItems.getCorresponding())){
                        //创建skuUniqueCode-goodsId映射
                        trueSignMap.put(simpleGoodsItems.getSkuUniqueCode(),simpleGoodsItems.getGoodsId());
                    }
                    simpleGoodsItems.setModifytime(currentDate);
                    simpleGoodsItems.setCorresponding("true".equals(simpleGoodsItems.getCorresponding())?"Y":"N");
                    goodsItemsService.updateGoodsItems(simpleGoodsItems);
                }
            }
            if(allList.size()>0){
                StringBuffer stringBuffer = new StringBuffer();
                for(String skuUniqueCode:allList){
                    stringBuffer.append("'").append(skuUniqueCode).append("',");
                }
                String items = stringBuffer.substring(0,stringBuffer.toString().length()-1);
                LOG.info("-------items="+items+"--------");
                List<GoodsQuantityReq> list = goodsItemsService.getSavedList(items);
                if (trueSignMap.size()>0){
                    String bycode = goodsItemsService.getBycode((String)trueSignMap.keySet().toArray()[0]);
                    DynamicDataSourceContextHolder.setDataSourceKey(goodsSaveReq.getCompanycode());//设置数据源为erp数据库
                    StringBuffer sb = new StringBuffer();
                    for(String goodsid:trueSignMap.values()){
                        sb.append(goodsid).append(",");
                    }
                    String goodsids = sb.substring(0,sb.toString().length()-1);
                    List<GoodsQuantity> listGoods = goodsItemsService.getQuantityList(goodsids,bycode);
                    if(Optional.ofNullable(listGoods).isPresent()&&listGoods.size()>0){
                        Map<String,Long> quantityMap= new HashMap<>();
                        for(GoodsQuantity goodsQuantity:listGoods){
                            quantityMap.put(goodsQuantity.getGoodsid(),goodsQuantity.getQuantity());
                        }
                        for(GoodsQuantityReq goodsQuantityReq:list){
                            if (quantityMap.containsKey(trueSignMap.get(goodsQuantityReq.getSkuUniqueCode()))){
                                goodsQuantityReq.setQuantity(quantityMap.get(trueSignMap.get(goodsQuantityReq.getSkuUniqueCode())));
                            }
                        }
                    }
                    DynamicDataSourceContextHolder.setDataSourceKey("main");
                }
                for(GoodsQuantityReq goodsQuantityReq:list){
                    GoodsItemThread goodsItemThread = new GoodsItemThread(goodsQuantityReq);
                    goodsItemThread.start();
                }
            }
            baseRespBean = new BaseRespBean();
        }catch (Exception e){
            baseRespBean = new BaseRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return baseRespBean.toString();
    }

    /**
     * 获取公司信息
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/byGetCompany",method = RequestMethod.POST)
    @ResponseBody
    public String byGetCompany(@RequestBody String reqJson){
        CompanyRespBean companyRespBean = null;
        try {
            CompanyReq companyReq =  JSON.parseObject(reqJson,CompanyReq.class);
            String anyfield = companyReq.getAnyField()==null?null:"%"+companyReq.getAnyField()+"%";
            Integer count = userInfoService.count(companyReq.getCompanycode(),companyReq.getBycode(),anyfield,companyReq.getCorresponding());
            List<Company> companies = userInfoService.getByCondition(companyReq.getCompanycode(),companyReq.getBycode(),anyfield,companyReq.getCorresponding(),
                    companyReq.getStart(),companyReq.getEnd());
            companyRespBean = new CompanyRespBean();
            companyRespBean.setData(companies);
            companyRespBean.setCount(count);
        }catch (Exception e){
            companyRespBean = new CompanyRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return companyRespBean.toString();
    }

    /**
     * 保存公司信息
     * @param reqJson
     * @return
     */
    @RequestMapping(value="/bySaveCompany",method = RequestMethod.POST)
    @ResponseBody
    public String bySaveCompany(@RequestBody String reqJson){
        BaseRespBean baseRespBean = null;
        try {
            CompanySaveReq companySaveReq =  JSON.parseObject(reqJson,CompanySaveReq.class);
            List<Long> allAuthorityIdList = new ArrayList<>();//所有修改的有赞门店id
            List<String> trueSignList = new ArrayList<>(); //修改后有对应的公司代码
            if(Optional.ofNullable(companySaveReq.getData()).isPresent()&&companySaveReq.getData().size()>0){
                List<Company> list = companySaveReq.getData();
                if(!userInfoService.checkValid(list,companySaveReq.getCompanycode())){
                    return new BaseRespBean(BaseRespBean.UNIQUEERRORCODE, BaseRespBean.UNIQUEERROR, "公司与门店只能一一对应").toString();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String currentDate = df.format(new Date());
                for (Company company:list){
                    allAuthorityIdList.add(company.getAuthorityId());
                    if("true".equals(company.getCorresponding())){
                        trueSignList.add(company.getBycode());
                    }
                    if("".equals(company.getBycode())){
                        company.setBycode(null);
                    }
                    company.setModifytime(currentDate);
                    company.setCorresponding("true".equals(company.getCorresponding())?"Y":"N");
                    userInfoService.updateCompany(company);
                }
            }
            if(allAuthorityIdList.size()>0){
                StringBuffer stringBuffer = new StringBuffer();
                for (Long authorityId:allAuthorityIdList){
                    stringBuffer.append("'").append(authorityId).append("',");
                }
                String authorityIds = stringBuffer.substring(0,stringBuffer.toString().length()-1);
                //所有需要刷新的商品库存
                List<GoodsQuantityReq> list = goodsItemsService.getByAuthorityIds(authorityIds);
                //勾上对应标志的公司列表
                if (trueSignList.size()>0){
                    StringBuffer sb = new StringBuffer();
                    for(String trueSignBycode:trueSignList){
                        sb.append("'").append(trueSignBycode).append("',");
                    }
                    String trueSignBycodes=sb.substring(0,sb.toString().length()-1);
                    //根据对应标志为Y的公司，找到勾上对应标志的商品列表
                    List<GoodsQuantity> listGq = goodsItemsService.getTrueSignListByBycodes(trueSignBycodes);
                    if(Optional.ofNullable(listGq).isPresent()&&listGq.size()>0){
                        Map<String,String> mapIG = new HashMap<>();
                        StringBuffer goodsidSb=new StringBuffer();
                        for(GoodsQuantity goodsQuantity:listGq){
                            mapIG.put(goodsQuantity.getSkuUniqueCode(),goodsQuantity.getGoodsid()+"_"+goodsQuantity.getBycode());
                            goodsidSb.append(goodsQuantity.getGoodsid()).append(",");
                        }
                        String goodsids = goodsidSb.substring(0,goodsidSb.toString().length()-1);
                        DynamicDataSourceContextHolder.setDataSourceKey(companySaveReq.getCompanycode());//设置数据源为erp数据库
                        //获取erp数据库中对应商品的库存列表
                        List<GoodsQuantity> erpGoodsList = goodsItemsService.getQuantityListByBycodes(goodsids,trueSignBycodes);
                        if(Optional.ofNullable(erpGoodsList).isPresent()&&erpGoodsList.size()>0){
                            Map<String,Long> erpGoodsMap = new HashMap<>();
                            for (GoodsQuantity goodsQuantity:erpGoodsList){
                                erpGoodsMap.put(goodsQuantity.getGoodsid()+"_"+goodsQuantity.getBycode(),goodsQuantity.getQuantity());
                            }
                            for (GoodsQuantityReq goodsQuantityReq:list){
                                if(erpGoodsMap.containsKey(mapIG.get(goodsQuantityReq.getSkuUniqueCode()))){
                                    goodsQuantityReq.setQuantity(erpGoodsMap.get(mapIG.get(goodsQuantityReq.getSkuUniqueCode())));
                                }
                            }
                        }
                        DynamicDataSourceContextHolder.setDataSourceKey("main");
                    }
                }
                for(GoodsQuantityReq goodsQuantityReq:list){
                    GoodsItemThread goodsItemThread = new GoodsItemThread(goodsQuantityReq);
                    goodsItemThread.start();
                }
            }
            baseRespBean = new BaseRespBean();
        }catch (Exception e){
            baseRespBean = new BaseRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return baseRespBean.toString();
    }



    /**
     * 根据token获取有赞商品
     *
     * @param
     */
    @RequestMapping(value="/byGetYouzanGoods",method = RequestMethod.POST)
    @ResponseBody
    public String byGetYouzanGoods(@RequestBody String reqJson){
        BaseRespBean baseRespBean = null;
        try {
            CompanyReq companyReq =  JSON.parseObject(reqJson,CompanyReq.class);
            TokenInfo tokenInfo = userInfoService.selectByBycode(companyReq.getCompanycode(),companyReq.getBycode());
            YouZanClient yzClient = new DefaultYZClient();
            Token token = new Token(tokenInfo.getAccessToken());
            YouzanItemsOnsaleGet youzanItemsOnsaleGet = new YouzanItemsOnsaleGet();
            YouzanItemsOnsaleGetParams youzanItemsOnsaleGetParams = new YouzanItemsOnsaleGetParams();
            int i=1;
            int pageSize=50;
            //获取所有商品item
            List<Long> listItemid = new ArrayList();//记录所有itemid
            while(true) {
                LOG.info("i=="+i+"-----");
                youzanItemsOnsaleGetParams.setPageNo(i++);
                youzanItemsOnsaleGetParams.setPageSize(pageSize);
                youzanItemsOnsaleGet.setAPIParams(youzanItemsOnsaleGetParams);
                YouzanItemsOnsaleGetResult result = yzClient.invoke(youzanItemsOnsaleGet, token, YouzanItemsOnsaleGetResult.class);
                if (result.getCode() == 200 && result.getSuccess()) {
                    if (Optional.ofNullable(result.getData()).isPresent() && result.getData().getCount() > 0) {
                        List<YouzanItemsOnsaleGetResult.YouzanItemsOnsaleGetResultItems> listItems = result.getData().getItems();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String currentDate = df.format(new Date());
                        for (YouzanItemsOnsaleGetResult.YouzanItemsOnsaleGetResultItems item : listItems) {
                            try {
                                listItemid.add(item.getItemId());
                                GoodsItems goodsItems = new GoodsItems();
                                //BeanUtils.copyProperties(goodsItems, item);
                                CglibBeanCopierUtils.copyProperties(item,goodsItems);
                                goodsItems.setTitle(StringUtil.getSafeString(goodsItems.getTitle()));
                                goodsItems.setCreatetime(currentDate);
                                goodsItems.setModifytime(currentDate);
                                goodsItems.setAuthorityId(tokenInfo.getAuthorityId());
                                goodsItems.setCompanycode(companyReq.getCompanycode());
                                goodsItemsService.saveGoodsItems(goodsItems);
                                if (Optional.ofNullable(item.getDeliveryTemplate().getDeliveryTemplateId()).isPresent()) {
                                    DeliveryTemplate deliveryTemplate = new DeliveryTemplate();
                                    //BeanUtils.copyProperties(deliveryTemplate, item.getDeliveryTemplate());
                                    CglibBeanCopierUtils.copyProperties(item.getDeliveryTemplate(),deliveryTemplate);
                                    deliveryTemplate.setItemId(item.getItemId());
                                    deliveryTemplate.setCreatetime(currentDate);
                                    deliveryTemplate.setModifytime(currentDate);
                                    deliveryTemplate.setAuthorityId(tokenInfo.getAuthorityId());
                                    deliveryTemplate.setCompanycode(companyReq.getCompanycode());
                                    goodsItemsService.saveDeliveryTemplate(deliveryTemplate);
                                }
                                if (Optional.ofNullable(item.getItemImgs()).isPresent() && item.getItemImgs().size() > 0) {
                                    for (YouzanItemsOnsaleGetResult.YouzanItemsOnsaleGetResultItemimgs itemImg : item.getItemImgs()) {
                                        ItemImgs itemImgs = new ItemImgs();
                                        //BeanUtils.copyProperties(itemImgs, itemImg);
                                        CglibBeanCopierUtils.copyProperties(itemImg,itemImgs);
                                        itemImgs.setItemId(item.getItemId());
                                        itemImgs.setCreatetime(currentDate);
                                        itemImgs.setModifytime(currentDate);
                                        itemImgs.setAuthorityId(tokenInfo.getAuthorityId());
                                        itemImgs.setCompanycode(companyReq.getCompanycode());
                                        goodsItemsService.saveItemImgs(itemImgs);
                                    }
                                }
                            } catch (Exception e) {
                                baseRespBean = new BaseRespBean(BaseRespBean.ERRORCODE, BaseRespBean.ERROR, e.getMessage());
                                return  baseRespBean.toString();
                            }
                        }
                        if(listItems.size()<pageSize){
                            break;
                        }
                    }
                }
            }
            //根据itemid获取所有商品规格
            List<SkuThread> workers = new ArrayList<>();
            if(listItemid.size()>0){
                for(Long l:listItemid){
                    SkuThread skuThread = new SkuThread(l,tokenInfo.getAccessToken(),goodsItemsService);
                    workers.add(skuThread);
                    skuThread.start();
                }
            }
            for (SkuThread skuThread:workers){
                try {
                    skuThread.join();
                } catch (InterruptedException e) {
                    LOG.error("线程执行join方法异常："+e.getMessage());
                }
            }
            LOG.info("--开始生成商品关系映射---");
            goodsItemsService.createGoodsRelationship(tokenInfo.getAuthorityId());
            baseRespBean = new BaseRespBean();
        } catch (SDKException e) {
            e.printStackTrace();
            baseRespBean = new BaseRespBean(BaseRespBean.ERRORCODE,BaseRespBean.ERROR,e.getMessage());
        }
        return  baseRespBean.toString();

    }


    public static void main(String[] args) {

        YouZanClient yzClient = new DefaultYZClient();

        Token token = new Token("token");

        YouzanItemSearch youzanItemSearch = new YouzanItemSearch();
//创建参数对象,并设置参数
        YouzanItemSearchParams youzanItemSearchParams = new YouzanItemSearchParams();
        youzanItemSearch.setAPIParams(youzanItemSearchParams);

        try {
            YouzanItemSearchResult result = yzClient.invoke(youzanItemSearch, token, YouzanItemSearchResult.class);
        } catch (SDKException e) {
            e.printStackTrace();
        }

    }
}
