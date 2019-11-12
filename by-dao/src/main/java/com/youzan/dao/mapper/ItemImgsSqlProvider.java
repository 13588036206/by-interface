package com.youzan.dao.mapper;

import com.youzan.dao.model.ItemImgs;
import org.apache.ibatis.jdbc.SQL;

public class ItemImgsSqlProvider {

    public String insertSelective(ItemImgs record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("item_imgs");
        
        if (record.getTid() != null) {
            sql.VALUES("tid", "#{tid,jdbcType=BIGINT}");
        }
        
        if (record.getItemId() != null) {
            sql.VALUES("item_id", "#{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getThumbnail() != null) {
            sql.VALUES("thumbnail", "#{thumbnail,jdbcType=VARCHAR}");
        }
        
        if (record.getCombine() != null) {
            sql.VALUES("combine", "#{combine,jdbcType=VARCHAR}");
        }
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getCreated() != null) {
            sql.VALUES("created", "#{created,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }
        
        if (record.getMedium() != null) {
            sql.VALUES("medium", "#{medium,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(ItemImgs record) {
        SQL sql = new SQL();
        sql.UPDATE("item_imgs");
        
        if (record.getItemId() != null) {
            sql.SET("item_id = #{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getThumbnail() != null) {
            sql.SET("thumbnail = #{thumbnail,jdbcType=VARCHAR}");
        }
        
        if (record.getCombine() != null) {
            sql.SET("combine = #{combine,jdbcType=VARCHAR}");
        }
        
        if (record.getId() != null) {
            sql.SET("id = #{id,jdbcType=BIGINT}");
        }
        
        if (record.getCreated() != null) {
            sql.SET("created = #{created,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            sql.SET("url = #{url,jdbcType=VARCHAR}");
        }
        
        if (record.getMedium() != null) {
            sql.SET("medium = #{medium,jdbcType=VARCHAR}");
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
        
        sql.WHERE("tid = #{tid,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}