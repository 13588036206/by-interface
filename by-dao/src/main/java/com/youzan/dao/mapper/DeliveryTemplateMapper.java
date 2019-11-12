package com.youzan.dao.mapper;

import com.youzan.dao.model.DeliveryTemplate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DeliveryTemplateMapper {
    @Delete({
        "delete from delivery_template",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into delivery_template (delivery_template_fee, ",
        "delivery_template_name, delivery_template_valuation_type, ",
        "delivery_template_id, item_id, ",
        "createTime, modifyTime, ",
        "companyCode, authority_id)",
        "values (#{deliveryTemplateFee,jdbcType=VARCHAR}, ",
        "#{deliveryTemplateName,jdbcType=VARCHAR}, #{deliveryTemplateValuationType,jdbcType=INTEGER}, ",
        "#{deliveryTemplateId,jdbcType=BIGINT}, #{itemId,jdbcType=BIGINT}, ",
        "#{createtime,jdbcType=VARCHAR}, #{modifytime,jdbcType=VARCHAR}, ",
        "#{companycode,jdbcType=VARCHAR}, #{authorityId,jdbcType=VARCHAR})"
    })
    int insert(DeliveryTemplate record);

    @InsertProvider(type=DeliveryTemplateSqlProvider.class, method="insertSelective")
    int insertSelective(DeliveryTemplate record);

    @Select({
        "select",
        "id, delivery_template_fee, delivery_template_name, delivery_template_valuation_type, ",
        "delivery_template_id, item_id, createTime, modifyTime, companyCode, authority_id",
        "from delivery_template",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="delivery_template_fee", property="deliveryTemplateFee", jdbcType=JdbcType.VARCHAR),
        @Result(column="delivery_template_name", property="deliveryTemplateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="delivery_template_valuation_type", property="deliveryTemplateValuationType", jdbcType=JdbcType.INTEGER),
        @Result(column="delivery_template_id", property="deliveryTemplateId", jdbcType=JdbcType.BIGINT),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR),
        @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR)
    })
    DeliveryTemplate selectByPrimaryKey(Long id);

    @Select({
            "select",
            "delivery_template_id",
            "from delivery_template",
            "where delivery_template_id = #{deliveryTemplateId,jdbcType=BIGINT} and item_id = #{itemId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="delivery_template_id", property="deliveryTemplateId", jdbcType=JdbcType.BIGINT),
    })
    Long selectByItemId(Long deliveryTemplateId,Long itemId);

    @UpdateProvider(type=DeliveryTemplateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DeliveryTemplate record);

    @Update({
        "update delivery_template",
        "set delivery_template_fee = #{deliveryTemplateFee,jdbcType=VARCHAR},",
          "delivery_template_name = #{deliveryTemplateName,jdbcType=VARCHAR},",
          "delivery_template_valuation_type = #{deliveryTemplateValuationType,jdbcType=INTEGER},",
	  "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where delivery_template_id = #{deliveryTemplateId,jdbcType=BIGINT} and item_id = #{itemId,jdbcType=BIGINT}"
    })
    int updateByItemId(DeliveryTemplate record);
}