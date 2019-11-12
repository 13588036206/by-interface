package com.youzan.youzanservice;

import com.youzan.dao.model.*;

import java.util.List;

public interface IGoodsItemsService {
    void saveGoodsItems(GoodsItems goodsItems);
    void saveDeliveryTemplate(DeliveryTemplate deliveryTemplate);
    void saveItemImgs(ItemImgs itemImgs);
    void saveGoodsSkus(GoodsSkus goodsSkus);
    void updateGoodsItems(SimpleGoodsItems simpleGoodsItems);
    void saveAll(List<GoodsQuantity> list);
    void deleteOldChangeList();
    void createGoodsRelationship(String authorityId);
    String getBycode(String skuUniqueCode);
    List<SimpleGoodsItems> getByCondition(String authorityId, String anyField, String goodsId, String corresponding, Integer start, Integer end);
    Integer count(String authorityId, String anyField, String goodsId, String corresponding);
    List<GoodsQuantity> getByCompanycode(String companycode);
    List<GoodsQuantity> getTrueSignListByBycodes(String bycodes);
    List<GoodsQuantityReq> getChangeList();
    List<GoodsQuantityReq> getSavedList(String items);
    List<GoodsQuantityReq> getByAuthorityIds(String authorityIds);
    List<GoodsQuantity> getQuantityList(String goodsids,String bycode);
    List<GoodsQuantity> getQuantityListByBycodes(String goodsids,String bycodes);
    boolean checkValid(List<SimpleGoodsItems> list);

}
