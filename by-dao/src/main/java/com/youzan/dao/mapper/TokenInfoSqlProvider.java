package com.youzan.dao.mapper;

import com.youzan.dao.model.TokenInfo;
import org.apache.ibatis.jdbc.SQL;

public class TokenInfoSqlProvider {

    public String insertSelective(TokenInfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("token_info");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAccessToken() != null) {
            sql.VALUES("access_token", "#{accessToken,jdbcType=VARCHAR}");
        }
        
        if (record.getExpires() != null) {
            sql.VALUES("expires", "#{expires,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorityId() != null) {
            sql.VALUES("authority_id", "#{authorityId,jdbcType=VARCHAR}");
        }
        
        if (record.getScope() != null) {
            sql.VALUES("scope", "#{scope,jdbcType=VARCHAR}");
        }
        
        if (record.getRefreshToken() != null) {
            sql.VALUES("refresh_token", "#{refreshToken,jdbcType=VARCHAR}");
        }
        
        if (record.getCompanycode() != null) {
            sql.VALUES("companyCode", "#{companycode,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createTime", "#{createtime,jdbcType=VARCHAR}");
        }
        
        if (record.getModifytime() != null) {
            sql.VALUES("modifyTime", "#{modifytime,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(TokenInfo record) {
        SQL sql = new SQL();
        sql.UPDATE("token_info");
        
        if (record.getAccessToken() != null) {
            sql.SET("access_token = #{accessToken,jdbcType=VARCHAR}");
        }
        
        if (record.getExpires() != null) {
            sql.SET("expires = #{expires,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorityId() != null) {
            sql.SET("authority_id = #{authorityId,jdbcType=VARCHAR}");
        }
        
        if (record.getScope() != null) {
            sql.SET("scope = #{scope,jdbcType=VARCHAR}");
        }
        
        if (record.getRefreshToken() != null) {
            sql.SET("refresh_token = #{refreshToken,jdbcType=VARCHAR}");
        }
        
        if (record.getCompanycode() != null) {
            sql.SET("companyCode = #{companycode,jdbcType=VARCHAR}");
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