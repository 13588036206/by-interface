package com.youzan.youzanservice.impl;

import com.youzan.dao.mapper.*;
import com.youzan.dao.model.*;
import com.youzan.youzanservice.IGoodsItemsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("goodsItemsService")
public class GoodsItemsServiceImpl implements IGoodsItemsService {

    @Resource
    private GoodsItemsMapper goodsItemsMapper;

    @Resource
    private GoodsQuantityMapper goodsQuantityMapper;

    @Resource
    private DeliveryTemplateMapper deliveryTemplateMapper;

    @Resource
    private ItemImgsMapper itemImgsMapper;

    @Resource
    private GoodsSkusMapper goodsSkusMapper;

    @Override
    public void saveGoodsItems(GoodsItems goodsItems) {
        if (Optional.ofNullable(goodsItemsMapper.selectByItemId(goodsItems.getItemId())).isPresent()) {
            goodsItemsMapper.updateByItemId(goodsItems);
        } else {
            goodsItemsMapper.insert(goodsItems);
        }
    }

    @Override
    public void saveDeliveryTemplate(DeliveryTemplate deliveryTemplate) {
        if (Optional.ofNullable(deliveryTemplateMapper.selectByItemId(deliveryTemplate.getDeliveryTemplateId(),deliveryTemplate.getItemId())).isPresent()) {
            deliveryTemplateMapper.updateByItemId(deliveryTemplate);
        } else {
            deliveryTemplateMapper.insert(deliveryTemplate);
        }
    }

    @Override
    public void saveItemImgs(ItemImgs itemImgs) {
        if (Optional.ofNullable(itemImgsMapper.selectByItemId(itemImgs.getId(),itemImgs.getItemId())).isPresent()) {
            itemImgsMapper.updateByItemId(itemImgs);
        } else {
            itemImgsMapper.insert(itemImgs);
        }
    }
    @Override
    public void saveGoodsSkus(GoodsSkus goodsSkus) {
        if (Optional.ofNullable(goodsSkusMapper.selectBySkuId(goodsSkus.getSkuId())).isPresent()) {
            goodsSkusMapper.updateBySkuId(goodsSkus);
        } else {
            goodsSkusMapper.insert(goodsSkus);
        }
    }

    @Override
    public List<SimpleGoodsItems> getByCondition(String authorityId, String anyField, String goodsId, String corresponding, Integer start, Integer end) {
        return goodsItemsMapper.selectByCondition(authorityId, anyField, goodsId, corresponding, start, end);
    }

    @Override
    public Integer count(String authorityId, String anyField, String goodsId, String corresponding) {
        return goodsItemsMapper.count(authorityId, anyField, goodsId, corresponding);
    }

    @Override
    public void updateGoodsItems(SimpleGoodsItems simpleGoodsItems) {
        goodsItemsMapper.updateSimpleGoodsItems(simpleGoodsItems);
    }

    @Override
    public List<GoodsQuantity> getByCompanycode(String companycode) {
        return goodsQuantityMapper.selectByCompanycode(companycode);
    }

    @Override
    public void saveAll(List<GoodsQuantity> list) {
        goodsQuantityMapper.insertAll(list);
    }

    @Override
    public List<GoodsQuantityReq> getChangeList() {
        return goodsItemsMapper.selectChangeList();
    }

    @Override
    public List<GoodsQuantityReq> getSavedList(String items) {
        return goodsItemsMapper.selectSavedList(items);
    }

    @Override
    public List<GoodsQuantityReq> getByAuthorityIds(String authorityIds) {
        return goodsItemsMapper.selectByAuthorityIds(authorityIds);
    }

    @Override
    public List<GoodsQuantity> getQuantityList(String goodsids, String bycode) {
        return goodsQuantityMapper.selectQuantityList(goodsids,bycode);
    }

    @Override
    public List<GoodsQuantity> getTrueSignListByBycodes(String bycodes) {
        return goodsQuantityMapper.selectByBycodes(bycodes);
    }

    @Override
    public List<GoodsQuantity> getQuantityListByBycodes(String goodsids, String bycodes) {
        return goodsQuantityMapper.selectQuantityListByBycodes(goodsids,bycodes);
    }

    @Override
    public void deleteOldChangeList() {
        goodsQuantityMapper.deleteOldChangeList();
    }

    @Override
    public String getBycode(String skuUniqueCode) {
        return goodsItemsMapper.selectBycode(skuUniqueCode);
    }

    @Override
    public void createGoodsRelationship(String authorityId) {
        goodsItemsMapper.createGoodsRelationship(authorityId);
    }

    @Override
    public boolean checkValid(List<SimpleGoodsItems> list) {
        if(Optional.ofNullable(list).isPresent()){
            String authorityId = goodsItemsMapper.selectAuthorityId(list.get(0).getSkuUniqueCode());
            StringBuffer sb= new StringBuffer();
            List<String> allGoodsid = new ArrayList<>();
            for(SimpleGoodsItems simpleGoodsItems:list){
                sb.append("'").append(simpleGoodsItems.getSkuUniqueCode()).append("',");
                if(Optional.ofNullable(simpleGoodsItems.getGoodsId()).isPresent()&&!"".equals(simpleGoodsItems.getGoodsId())){
                    allGoodsid.add(simpleGoodsItems.getGoodsId());
                }
            }
            String skuUniqueCodes = sb.substring(0,sb.toString().length()-1);
            List<String> goodsids= goodsItemsMapper.selcectGoodsids(skuUniqueCodes,authorityId);
            if(Optional.ofNullable(goodsids).isPresent()){
                allGoodsid.addAll(goodsids);
            }
            if(allGoodsid.size()>0) {
                Map<String, Long> map = allGoodsid.stream().
                        collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                Collection<Long> collection = map.values();
                for (Long count : collection) {
                    if (count > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
