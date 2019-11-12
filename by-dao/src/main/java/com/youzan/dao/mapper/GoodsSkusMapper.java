package com.youzan.dao.mapper;

import com.youzan.dao.model.GoodsSkus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface GoodsSkusMapper {
    @Delete({
        "delete from goods_skus",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into goods_skus (modified, ",
        "sku_unique_code, outer_id, ",
        "sku_id, one_item_multi_code, ",
        "sold_num, item_no, ",
        "price, item_id, cost_price, ",
        "properties_name_json, created, ",
        "with_hold_quantity, quantity, ",
        "createTime, modifyTime)",
        "values (#{modified,jdbcType=VARCHAR},#{skuUniqueCode,jdbcType=VARCHAR}, #{outerId,jdbcType=VARCHAR}, ",
        "#{skuId,jdbcType=BIGINT}, #{oneItemMultiCode,jdbcType=VARCHAR}, ",
        "#{soldNum,jdbcType=BIGINT}, #{itemNo,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=BIGINT}, #{itemId,jdbcType=BIGINT}, #{costPrice,jdbcType=BIGINT}, ",
        "#{propertiesNameJson,jdbcType=VARCHAR}, #{created,jdbcType=VARCHAR}, ",
        "#{withHoldQuantity,jdbcType=BIGINT}, #{quantity,jdbcType=BIGINT}, ",
        "#{createtime,jdbcType=VARCHAR}, #{modifytime,jdbcType=VARCHAR})"
    })
    int insert(GoodsSkus record);

    @Select({
        "select",
        "id, modified, sku_unique_code, outer_id, sku_id, one_item_multi_code, sold_num, ",
        "item_no, price, item_id, cost_price, properties_name_json, created, with_hold_quantity, ",
        "quantity, createTime, modifyTime",
        "from goods_skus",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="modified", property="modified", jdbcType=JdbcType.VARCHAR),
        @Result(column="sku_unique_code", property="skuUniqueCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="outer_id", property="outerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="sku_id", property="skuId", jdbcType=JdbcType.BIGINT),
        @Result(column="one_item_multi_code", property="oneItemMultiCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="sold_num", property="soldNum", jdbcType=JdbcType.BIGINT),
        @Result(column="item_no", property="itemNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.BIGINT),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
        @Result(column="cost_price", property="costPrice", jdbcType=JdbcType.BIGINT),
        @Result(column="properties_name_json", property="propertiesNameJson", jdbcType=JdbcType.VARCHAR),
        @Result(column="created", property="created", jdbcType=JdbcType.VARCHAR),
        @Result(column="with_hold_quantity", property="withHoldQuantity", jdbcType=JdbcType.BIGINT),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR)
    })
    GoodsSkus selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "sku_id",
            "from goods_skus",
            "where sku_id = #{skuId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="sku_id", property="skuId", jdbcType=JdbcType.BIGINT),
    })
    Long selectBySkuId(Long skuId);

    @Update({
        "update goods_skus",
        "set modified = #{modified,jdbcType=VARCHAR},",
          "sku_unique_code = #{skuUniqueCode,jdbcType=VARCHAR},",
          "outer_id = #{outerId,jdbcType=VARCHAR},",
          "one_item_multi_code = #{oneItemMultiCode,jdbcType=VARCHAR},",
          "sold_num = #{soldNum,jdbcType=BIGINT},",
          "item_no = #{itemNo,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=BIGINT},",
          "item_id = #{itemId,jdbcType=BIGINT},",
          "cost_price = #{costPrice,jdbcType=BIGINT},",
          "properties_name_json = #{propertiesNameJson,jdbcType=VARCHAR},",
          "created = #{created,jdbcType=VARCHAR},",
          "with_hold_quantity = #{withHoldQuantity,jdbcType=BIGINT},",
          "quantity = #{quantity,jdbcType=BIGINT},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where sku_id = #{skuId,jdbcType=BIGINT}"
    })
    int updateBySkuId(GoodsSkus record);
}