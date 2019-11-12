package com.youzan.dao.mapper;

import com.youzan.dao.model.GoodsItems;
import com.youzan.dao.model.GoodsQuantityReq;
import com.youzan.dao.model.SimpleGoodsItems;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GoodsItemsMapper {

    @Insert({
        "insert into goods_items (item_id, ",
        "title, created_time, ",
        "quantity, item_no, ",
        "price, update_time, ",
        "classid, post_type, ",
        "image, origin, sub_title, ",
        "alias, page_url, ",
        "post_fee, share_detail, ",
        "item_type, share_title, ",
        "share_icon, detail_url, ",
        "num, createTime, ",
        "modifyTime, companyCode, ",
        "authority_id)",
        "values (#{itemId,jdbcType=BIGINT}, ",
        "#{title,jdbcType=VARCHAR}, #{createdTime,jdbcType=VARCHAR}, ",
        "#{quantity,jdbcType=BIGINT}, #{itemNo,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=BIGINT}, #{updateTime,jdbcType=VARCHAR}, ",
        "#{classid,jdbcType=VARCHAR}, #{postType,jdbcType=INTEGER}, ",
        "#{image,jdbcType=VARCHAR}, #{origin,jdbcType=VARCHAR}, #{subTitle,jdbcType=VARCHAR}, ",
        "#{alias,jdbcType=VARCHAR}, #{pageUrl,jdbcType=VARCHAR}, ",
        "#{postFee,jdbcType=BIGINT}, #{shareDetail,jdbcType=BIGINT}, ",
        "#{itemType,jdbcType=INTEGER}, #{shareTitle,jdbcType=VARCHAR}, ",
        "#{shareIcon,jdbcType=VARCHAR}, #{detailUrl,jdbcType=VARCHAR}, ",
        "#{num,jdbcType=BIGINT}, #{createtime,jdbcType=VARCHAR}, ",
        "#{modifytime,jdbcType=VARCHAR}, #{companycode,jdbcType=VARCHAR}, ",
        "#{authorityId,jdbcType=VARCHAR})"
    })
    int insert(GoodsItems record);



    @Select({
            "select",
            "c.sku_unique_code,CONCAT(a.title,ifnull(b.properties_name_json,'')) title, a.companyCode, c.goods_id, case when c.corresponding ='Y' then 'true' else 'false' end corresponding",
            "from goods_items a",
            "LEFT JOIN goods_skus b on a.item_id = b.item_id",
            "LEFT JOIN goods_relationship c on CONCAT(a.item_id,ifnull(b.sku_id,'')) = c.sku_unique_code",
            "where a.authority_id = #{authorityId,jdbcType=VARCHAR} ",
            "and (#{anyField,jdbcType=VARCHAR} is null or CONCAT(a.title,b.properties_name_json) like #{anyField,jdbcType=VARCHAR})",
            "and (#{goodsId,jdbcType=VARCHAR} is null or c.goods_id = #{goodsId,jdbcType=VARCHAR})",
            "and (#{corresponding,jdbcType=VARCHAR} is null or ifnull(c.corresponding,'N') = #{corresponding,jdbcType=VARCHAR})",
            "order by CONCAT(a.title,ifnull(b.properties_name_json,'')) asc limit ${start},${end}"

    })
    @Results({
            @Result(column="sku_unique_code", property="skuUniqueCode", jdbcType=JdbcType.BIGINT),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
            @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.VARCHAR),
            @Result(column="corresponding", property="corresponding", jdbcType=JdbcType.VARCHAR)
    })
    List<SimpleGoodsItems> selectByCondition(String authorityId, String anyField, String goodsId, String corresponding, Integer start, Integer end);

    @Select({
            "select",
            "count(*) count",
            "from goods_items a",
            "LEFT JOIN goods_skus b on a.item_id = b.item_id",
            "LEFT JOIN goods_relationship c on CONCAT(a.item_id,ifnull(b.sku_id,'')) = c.sku_unique_code",
            "where a.authority_id = #{authorityId,jdbcType=VARCHAR} ",
            "and (#{anyField,jdbcType=VARCHAR} is null or CONCAT(a.title,b.properties_name_json) like #{anyField,jdbcType=VARCHAR})",
            "and (#{goodsId,jdbcType=VARCHAR} is null or c.goods_id = #{goodsId,jdbcType=VARCHAR})",
            "and (#{corresponding,jdbcType=VARCHAR} is null or ifnull(c.corresponding,'N') = #{corresponding,jdbcType=VARCHAR})"
    })
    @Results({
            @Result(column="count", property="count", jdbcType=JdbcType.INTEGER)
    })
    Integer count(String authorityId, String anyField, String goodsId, String corresponding);


    @Select({
            "select",
            "item_id",
            "from goods_items",
            "where item_id = #{itemId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
    })
    Long selectByItemId(Long itemId);


    @Update({
        "update goods_items",
	      "set title = #{title,jdbcType=VARCHAR},",
          "created_time = #{createdTime,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=BIGINT},",
          "item_no = #{itemNo,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=VARCHAR},",
          "classid = #{classid,jdbcType=VARCHAR},",
          "post_type = #{postType,jdbcType=INTEGER},",
          "image = #{image,jdbcType=VARCHAR},",
          "origin = #{origin,jdbcType=VARCHAR},",
          "sub_title = #{subTitle,jdbcType=VARCHAR},",
          "alias = #{alias,jdbcType=VARCHAR},",
          "page_url = #{pageUrl,jdbcType=VARCHAR},",
          "post_fee = #{postFee,jdbcType=BIGINT},",
          "share_detail = #{shareDetail,jdbcType=BIGINT},",
          "item_type = #{itemType,jdbcType=INTEGER},",
          "share_title = #{shareTitle,jdbcType=VARCHAR},",
          "share_icon = #{shareIcon,jdbcType=VARCHAR},",
          "detail_url = #{detailUrl,jdbcType=VARCHAR},",
          "num = #{num,jdbcType=BIGINT},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",

        "where item_id = #{itemId,jdbcType=BIGINT}"
    })
    int updateByItemId(GoodsItems record);

    @Update({
            "update goods_relationship",
            "set goods_id = #{goodsId,jdbcType=VARCHAR},",
            "corresponding = #{corresponding,jdbcType=VARCHAR},",
            "modifyTime = #{modifytime,jdbcType=VARCHAR}",
            "where sku_unique_code = #{skuUniqueCode,jdbcType=VARCHAR}"
    })
    int updateSimpleGoodsItems(SimpleGoodsItems record);


    @Select({
            "select",
            "a.item_id,a.sku_id,b.access_token,c.quantity",
            "from goods_relationship a",
            "INNER JOIN token_info b on a.authority_id = b.authority_id",
            "INNER JOIN goods_quantity c on a.goods_id =c.goodsid and b.byCode = c.bycode",
            "where a.corresponding='Y' order by b.access_token"
    })
    @Results({
            @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
            @Result(column="sku_id", property="skuId", jdbcType=JdbcType.BIGINT),
            @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantityReq> selectChangeList();


    @Select({
            "select",
            "a.item_id,a.sku_id,a.sku_unique_code,b.access_token,0 quantity",
            "from goods_relationship a",
            "INNER JOIN token_info b on a.authority_id = b.authority_id",
            "where a.sku_unique_code in(${items})"
    })
    @Results({
            @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
            @Result(column="sku_id", property="skuId", jdbcType=JdbcType.BIGINT),
            @Result(column="sku_unique_code", property="skuUniqueCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantityReq> selectSavedList(String items);

    @Select({
            "select",
            "a.item_id,a.sku_id,a.sku_unique_code,b.access_token,0 quantity",
            "from goods_relationship a",
            "INNER JOIN token_info b on a.authority_id = b.authority_id",
            "where b.authority_id in(${authorityIds})"
    })
    @Results({
            @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
            @Result(column="sku_id", property="skuId", jdbcType=JdbcType.BIGINT),
            @Result(column="sku_unique_code", property="skuUniqueCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantityReq> selectByAuthorityIds(String authorityIds);

    @Select({
            "select",
            "b.byCode",
            "from goods_relationship a",
            "INNER JOIN token_info b on a.authority_id = b.authority_id",
            "where a.sku_unique_code = #{skuUniqueCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="byCode", property="byCode", jdbcType=JdbcType.VARCHAR)
    })
    String selectBycode(String skuUniqueCode);

    @Select({
            "select",
            "authority_id",
            "from goods_relationship",
            "where sku_unique_code = #{skuUniqueCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR)
    })
    String selectAuthorityId(String skuUniqueCode);

    @Select({
            "select",
            "goods_id",
            "from goods_relationship",
            "where sku_unique_code not in (${skuUniqueCodes})",
            "and authority_id = #{authorityId,jdbcType=VARCHAR} and goods_id is not null"
    })
    @Results({
            @Result(column="goods_id", property="goods_id", jdbcType=JdbcType.VARCHAR),
    })
    List<String>  selcectGoodsids(String skuUniqueCodes,String authorityId);

    @Insert({
            "insert into goods_relationship (sku_unique_code,sku_id,item_id,authority_id,createTime)",
            "SELECT CONCAT(a.item_id,ifnull(b.sku_id,'')) sku_unique_code,b.sku_id,a.item_id,a.authority_id,a.createTime from",
            "goods_items a",
            "LEFT JOIN goods_skus b on a.item_id = b.item_id",
            "LEFT JOIN goods_relationship c on CONCAT(a.item_id,ifnull(b.sku_id,'')) = c.sku_unique_code",
            "where a.authority_id = #{authorityId,jdbcType=VARCHAR} and c.sku_unique_code is null"
    })
    int createGoodsRelationship(String authorityId);

}