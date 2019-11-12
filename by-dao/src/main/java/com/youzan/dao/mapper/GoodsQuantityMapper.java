package com.youzan.dao.mapper;

import com.youzan.dao.model.GoodsQuantity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GoodsQuantityMapper {

    @Select({
        "CALL update_youzan_kcsl(#{companycode,jdbcType=VARCHAR})"
    })
    @Results({
        @Result(column="goodsid", property="goodsid", jdbcType=JdbcType.VARCHAR),
        @Result(column="bycode", property="bycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantity> selectByCompanycode(String companycode);

    @InsertProvider(type = GoodsQuantitySqlProvider.class, method = "insertAll")
    int insertAll(@Param("list") List<GoodsQuantity> list);


    @Select({
            "TRUNCATE goods_quantity"
    })
    void deleteOldChangeList();

    @Select({
            "select",
            "spdm goodsid,sum(sl) quantity from wl_spkc",
            "where spdm in (${goodsids}) and gsdm =#{bycode,jdbcType=VARCHAR} group by spdm"
    })
    @Results({
            @Result(column="goodsid", property="goodsid", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantity> selectQuantityList(String goodsids,String bycode);

    @Select({
            "select",
            "spdm goodsid,sum(sl) quantity,gsdm bycode from wl_spkc",
            "where spdm in (${goodsids}) and gsdm in (${bycodes}) group by spdm,gsdm"
    })
    @Results({
            @Result(column="goodsid", property="goodsid", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.BIGINT),
            @Result(column="bycode", property="bycode", jdbcType=JdbcType.VARCHAR)
    })
    List<GoodsQuantity> selectQuantityListByBycodes(String goodsids,String bycodes);

    @Select({
            "select",
            "a.sku_unique_code,a.goods_id,b.byCode",
            "from goods_relationship a",
            "INNER JOIN token_info b on a.authority_id = b.authority_id",
            "where b.bycode in(${bycodes}) and a.corresponding ='Y'"
    })
    @Results({
            @Result(column="sku_unique_code", property="skuUniqueCode", jdbcType=JdbcType.BIGINT),
            @Result(column="goods_id", property="goodsid", jdbcType=JdbcType.VARCHAR),
            @Result(column="byCode", property="bycode", jdbcType=JdbcType.BIGINT)
    })
    List<GoodsQuantity> selectByBycodes(String bycodes);

}