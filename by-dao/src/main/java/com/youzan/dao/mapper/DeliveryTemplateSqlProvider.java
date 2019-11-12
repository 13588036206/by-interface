package com.youzan.dao.mapper;

import com.youzan.dao.model.DeliveryTemplate;
import org.apache.ibatis.jdbc.SQL;

public class DeliveryTemplateSqlProvider {

    public String insertSelective(DeliveryTemplate record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("delivery_template");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getDeliveryTemplateFee() != null) {
            sql.VALUES("delivery_template_fee", "#{deliveryTemplateFee,jdbcType=VARCHAR}");
        }
        
        if (record.getDeliveryTemplateName() != null) {
            sql.VALUES("delivery_template_name", "#{deliveryTemplateName,jdbcType=VARCHAR}");
        }
        
        if (record.getDeliveryTemplateValuationType() != null) {
            sql.VALUES("delivery_template_valuation_type", "#{deliveryTemplateValuationType,jdbcType=INTEGER}");
        }
        
        if (record.getDeliveryTemplateId() != null) {
            sql.VALUES("delivery_template_id", "#{deliveryTemplateId,jdbcType=BIGINT}");
        }
        
        if (record.getItemId() != null) {
            sql.VALUES("item_id", "#{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createTime", "#{createtime,jdbcType=VARCHAR}");
        }
        
        if (record.getModifytime() != null) {
            sql.VALUES("modifyTime", "#{modifytime,jdbcType=VARCHAR}");
        }
        
        if (record.getCompanycode() != null) {
            sql.VALUES("companyCode", "#{companycode,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorityId() != null) {
            sql.VALUES("authority_id", "#{authorityId,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DeliveryTemplate record) {
        SQL sql = new SQL();
        sql.UPDATE("delivery_template");
        
        if (record.getDeliveryTemplateFee() != null) {
            sql.SET("delivery_template_fee = #{deliveryTemplateFee,jdbcType=VARCHAR}");
        }
        
        if (record.getDeliveryTemplateName() != null) {
            sql.SET("delivery_template_name = #{deliveryTemplateName,jdbcType=VARCHAR}");
        }
        
        if (record.getDeliveryTemplateValuationType() != null) {
            sql.SET("delivery_template_valuation_type = #{deliveryTemplateValuationType,jdbcType=INTEGER}");
        }
        
        if (record.getDeliveryTemplateId() != null) {
            sql.SET("delivery_template_id = #{deliveryTemplateId,jdbcType=BIGINT}");
        }
        
        if (record.getItemId() != null) {
            sql.SET("item_id = #{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getCreatetime() != null) {
            sql.SET("createTime = #{createtime,jdbcType=VARCHAR}");
        }
        
        if (record.getModifytime() != null) {
            sql.SET("modifyTime = #{modifytime,jdbcType=VARCHAR}");
        }
        
        if (record.getCompanycode() != null) {
            sql.SET("companyCode = #{companycode,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorityId() != null) {
            sql.SET("authority_id = #{authorityId,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}