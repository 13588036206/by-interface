package com.youzan.youzanweb.scheduled;

import com.alibaba.fastjson.JSON;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanItemGet;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanItemGetParams;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanItemGetResult;
import com.youzan.dao.model.GoodsSkus;
import com.youzan.dao.model.SkuMap;
import com.youzan.youzanUtil.CglibBeanCopierUtils;
import com.youzan.youzanservice.IGoodsItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SkuThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(SkuThread.class);

    private Long itemId;

    private String accessToken;

    private IGoodsItemsService goodsItemsService;

    public SkuThread(Long itemId,String accessToken,IGoodsItemsService goodsItemsService) {
        super();
        this.itemId = itemId;
        this.accessToken = accessToken;
        this.goodsItemsService = goodsItemsService;
    }

    @Override
    public void run() {
        YouZanClient yzClient = new DefaultYZClient();
        Token token = new Token(accessToken);
        YouzanItemGet youzanItemGet = new YouzanItemGet();
        //创建参数对象,并设置参数
        YouzanItemGetParams youzanItemGetParams = new YouzanItemGetParams();
        youzanItemGetParams.setItemId(itemId);
        youzanItemGet.setAPIParams(youzanItemGetParams);
        try {
            YouzanItemGetResult result = yzClient.invoke(youzanItemGet, token, YouzanItemGetResult.class);
            if(200==result.getCode()){
                LOG.info("商品id:"+itemId+"获取规格属性成功!");
                if(Optional.ofNullable(result.getData().getItem().getSkus()).isPresent()&&result.getData().getItem().getSkus().size()>0){
                    for(YouzanItemGetResult.YouzanItemGetResultSkus sku:result.getData().getItem().getSkus()){
                        GoodsSkus goodsSkus = new GoodsSkus();
                        CglibBeanCopierUtils.copyProperties(sku,goodsSkus);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String currentDate = df.format(new Date());
                        goodsSkus.setPropertiesNameJson(getSkus(goodsSkus.getPropertiesNameJson()));
                        goodsSkus.setCreatetime(currentDate);
                        goodsSkus.setModifytime(currentDate);
                        goodsItemsService.saveGoodsSkus(goodsSkus);
                    }
                }
            }else{
                LOG.error("商品id:"+itemId+"获取规格属性失败:"+result.getMessage());
            }
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSkus(String skuJson){
        StringBuffer skus = new StringBuffer();
        List<SkuMap> list = JSON.parseArray(skuJson,SkuMap.class);
        if(list.size()>0){
            for (SkuMap skuMap:list){
                skus.append(skuMap.getV()).append(" ");
            }
        }
        return  skus.toString();
    }
}
