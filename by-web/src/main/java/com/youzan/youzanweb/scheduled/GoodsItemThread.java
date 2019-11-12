package com.youzan.youzanweb.scheduled;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanItemQuantityUpdate;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanItemQuantityUpdateParams;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanItemQuantityUpdateResult;
import com.youzan.dao.model.GoodsQuantityReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodsItemThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(GoodsItemThread.class);
    private GoodsQuantityReq goodsQuantityReq;

    public GoodsItemThread(GoodsQuantityReq goodsQuantityReq) {
        super();
        this.goodsQuantityReq = goodsQuantityReq;
    }

    @Override
    public void run() {
        YouZanClient yzClient = new DefaultYZClient();
        Token token = new Token(goodsQuantityReq.getAccessToken());
        YouzanItemQuantityUpdate youzanItemQuantityUpdate = new YouzanItemQuantityUpdate();
        YouzanItemQuantityUpdateParams youzanItemQuantityUpdateParams = new YouzanItemQuantityUpdateParams();
        youzanItemQuantityUpdateParams.setItemId(goodsQuantityReq.getItemId());
        youzanItemQuantityUpdateParams.setQuantity(goodsQuantityReq.getQuantity());
        youzanItemQuantityUpdateParams.setSkuId(goodsQuantityReq.getSkuId());
        youzanItemQuantityUpdate.setAPIParams(youzanItemQuantityUpdateParams);
        try {
            YouzanItemQuantityUpdateResult result = yzClient.invoke(youzanItemQuantityUpdate, token, YouzanItemQuantityUpdateResult.class);
            if(200==result.getCode()){
                LOG.info("商品id:"+goodsQuantityReq.getItemId()+",规格id:"+goodsQuantityReq.getSkuId()+"更新库存成功!");
            }else{
                LOG.error("商品id:"+goodsQuantityReq.getItemId()+",规格id:"+goodsQuantityReq.getSkuId()+"更新库存失败!失败原因:"+result.getMessage());
            }
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    public GoodsQuantityReq getGoodsQuantityReq() {
        return goodsQuantityReq;
    }

    public void setGoodsQuantityReq(GoodsQuantityReq goodsQuantityReq) {
        this.goodsQuantityReq = goodsQuantityReq;
    }
}
