package com.youzan.dao.mapper;

import com.youzan.dao.model.GoodsSkus;
import org.apache.ibatis.jdbc.SQL;

public class GoodsSkusSqlProvider {

    public String insertSelective(GoodsSkus record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("goods_skus");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getModified() != null) {
            sql.VALUES("modified", "#{modified,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuUniqueCode() != null) {
            sql.VALUES("sku_unique_code", "#{skuUniqueCode,jdbcType=VARCHAR}");
        }
        
        if (record.getOuterId() != null) {
            sql.VALUES("outer_id", "#{outerId,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuId() != null) {
            sql.VALUES("sku_id", "#{skuId,jdbcType=BIGINT}");
        }
        
        if (record.getOneItemMultiCode() != null) {
            sql.VALUES("one_item_multi_code", "#{oneItemMultiCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSoldNum() != null) {
            sql.VALUES("sold_num", "#{soldNum,jdbcType=BIGINT}");
        }
        
        if (record.getItemNo() != null) {
            sql.VALUES("item_no", "#{itemNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=BIGINT}");
        }
        
        if (record.getItemId() != null) {
            sql.VALUES("item_id", "#{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getCostPrice() != null) {
            sql.VALUES("cost_price", "#{costPrice,jdbcType=BIGINT}");
        }
        
        if (record.getPropertiesNameJson() != null) {
            sql.VALUES("properties_name_json", "#{propertiesNameJson,jdbcType=VARCHAR}");
        }
        
        if (record.getCreated() != null) {
            sql.VALUES("created", "#{created,jdbcType=VARCHAR}");
        }
        
        if (record.getWithHoldQuantity() != null) {
            sql.VALUES("with_hold_quantity", "#{withHoldQuantity,jdbcType=BIGINT}");
        }
        
        if (record.getQuantity() != null) {
            sql.VALUES("quantity", "#{quantity,jdbcType=BIGINT}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createTime", "#{createtime,jdbcType=VARCHAR}");
        }
        
        if (record.getModifytime() != null) {
            sql.VALUES("modifyTime", "#{modifytime,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(GoodsSkus record) {
        SQL sql = new SQL();
        sql.UPDATE("goods_skus");
        
        if (record.getModified() != null) {
            sql.SET("modified = #{modified,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuUniqueCode() != null) {
            sql.SET("sku_unique_code = #{skuUniqueCode,jdbcType=VARCHAR}");
        }
        
        if (record.getOuterId() != null) {
            sql.SET("outer_id = #{outerId,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuId() != null) {
            sql.SET("sku_id = #{skuId,jdbcType=BIGINT}");
        }
        
        if (record.getOneItemMultiCode() != null) {
            sql.SET("one_item_multi_code = #{oneItemMultiCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSoldNum() != null) {
            sql.SET("sold_num = #{soldNum,jdbcType=BIGINT}");
        }
        
        if (record.getItemNo() != null) {
            sql.SET("item_no = #{itemNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=BIGINT}");
        }
        
        if (record.getItemId() != null) {
            sql.SET("item_id = #{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getCostPrice() != null) {
            sql.SET("cost_price = #{costPrice,jdbcType=BIGINT}");
        }
        
        if (record.getPropertiesNameJson() != null) {
            sql.SET("properties_name_json = #{propertiesNameJson,jdbcType=VARCHAR}");
        }
        
        if (record.getCreated() != null) {
            sql.SET("created = #{created,jdbcType=VARCHAR}");
        }
        
        if (record.getWithHoldQuantity() != null) {
            sql.SET("with_hold_quantity = #{withHoldQuantity,jdbcType=BIGINT}");
        }
        
        if (record.getQuantity() != null) {
            sql.SET("quantity = #{quantity,jdbcType=BIGINT}");
        }
        
        if (record.getCreatetime() != null) {
            sql.SET("createTime = #{createtime,jdbcType=VARCHAR}");
        }
        
        if (record.getModifytime() != null) {
            sql.SET("modifyTime = #{modifytime,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}