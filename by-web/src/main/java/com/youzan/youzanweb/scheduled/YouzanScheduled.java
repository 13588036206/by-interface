package com.youzan.youzanweb.scheduled;

import com.by.byconfig.dynamicDS.DynamicDataSourceContextHolder;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.dao.model.GoodsQuantity;
import com.youzan.dao.model.GoodsQuantityReq;
import com.youzan.dao.model.RefreshToken;
import com.youzan.dao.model.TokenInfo;
import com.youzan.youzanUtil.CglibBeanCopierUtils;
import com.youzan.youzanservice.IGoodsItemsService;
import com.youzan.youzanservice.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class YouzanScheduled {
    private static final Logger LOG = LoggerFactory.getLogger(YouzanScheduled.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IGoodsItemsService goodsItemsService;

    /**
     * 刷新token信息
     * @param
     * @return
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void  refreshQuantity(){
        LOG.info("开始刷新库存信息:"+System.currentTimeMillis());
        try {
            DynamicDataSourceContextHolder.setDataSourceKey("main");
            List<GoodsQuantity> listAll = new ArrayList<GoodsQuantity>();
            List<String> list = userInfoService.getAllCompanycode();
            if(Optional.ofNullable(list).isPresent()&&list.size()>0){
                for(String companyCode:list){
                    DynamicDataSourceContextHolder.setDataSourceKey(companyCode);
                    List<GoodsQuantity> goodsList = goodsItemsService.getByCompanycode(companyCode);
                    if(Optional.ofNullable(goodsList).isPresent()&&goodsList.size()>0){
                        listAll.addAll(goodsList);
                    }
                }
            }
            DynamicDataSourceContextHolder.setDataSourceKey("main");
            goodsItemsService.deleteOldChangeList();
            if(listAll.size()>0){
                goodsItemsService.saveAll(listAll);
            }
            List<GoodsQuantityReq> changeList = goodsItemsService.getChangeList();
            if(Optional.ofNullable(changeList).isPresent()&&changeList.size()>0){
                for(GoodsQuantityReq goodsQuantityReq:changeList) {
                    GoodsItemThread goodsItemThread = new GoodsItemThread(goodsQuantityReq);
                    goodsItemThread.start();
                }
            }
            LOG.info("刷新库存信息结束:"+System.currentTimeMillis());
        }catch (Exception e) {
            LOG.error("刷新库存失败！失败原因:"+e.getMessage());
        }
    }


    /**
     * 刷新token信息
     * @param
     * @return
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void  refreshToken(){
        LOG.info("开始刷新token:"+System.currentTimeMillis());
        try {
            DynamicDataSourceContextHolder.setDataSourceKey("main");
            List<RefreshToken> list = userInfoService.getAlltokenInfo();
            if(Optional.ofNullable(list).isPresent()&&list.size()>0){
                for(RefreshToken refreshToken:list){
                    DefaultYZClient yzClient = new DefaultYZClient();
                    TokenParameter tokenParameter = TokenParameter.refresh()
                            .clientId(refreshToken.getClientId())
                            .clientSecret(refreshToken.getClientSecret())
                            .refreshToken(refreshToken.getRefreshToken())
                            .build();
                    TokenInfo tokenInfo = new TokenInfo();
                    //BeanUtils.copyProperties(tokenInfo,yzClient.getOAuthToken(tokenParameter));
                    CglibBeanCopierUtils.copyProperties(yzClient.getOAuthToken(tokenParameter),tokenInfo);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String currentDate = df.format(new Date());
                    tokenInfo.setModifytime(currentDate);
                    userInfoService.saveTokenInfo(tokenInfo);
                }
            }
            LOG.info("刷新token结束:"+System.currentTimeMillis());
        }catch (Exception e) {
            LOG.error("刷新token失败！失败原因:"+e.getMessage());
        }
    }
}
