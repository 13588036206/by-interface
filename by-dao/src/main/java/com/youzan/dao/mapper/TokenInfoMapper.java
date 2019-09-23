package com.youzan.dao.mapper;

import com.youzan.dao.model.TokenInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface TokenInfoMapper {
    @Delete({
        "delete from token_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into token_info (access_token, ",
        "expires, authority_id, ",
        "scope, refresh_token, ",
        "companyCode, createTime, ",
        "modifyTime)",
        "values (#{accessToken,jdbcType=VARCHAR}, ",
        "#{expires,jdbcType=VARCHAR}, #{authorityId,jdbcType=VARCHAR}, ",
        "#{scope,jdbcType=VARCHAR}, #{refreshToken,jdbcType=VARCHAR}, ",
        "#{companycode,jdbcType=VARCHAR}, #{createtime,jdbcType=VARCHAR}, ",
        "#{modifytime,jdbcType=VARCHAR})"
    })
    int insert(TokenInfo record);

    @InsertProvider(type=TokenInfoSqlProvider.class, method="insertSelective")
    int insertSelective(TokenInfo record);

    @Select({
        "select",
        "id, access_token, expires, authority_id, scope, refresh_token, companyCode, ",
        "createTime, modifyTime",
        "from token_info",
        "where authority_id = #{authorityId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="expires", property="expires", jdbcType=JdbcType.VARCHAR),
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR),
        @Result(column="scope", property="scope", jdbcType=JdbcType.VARCHAR),
        @Result(column="refresh_token", property="refreshToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR)
    })
    TokenInfo selectByAuthorityId(String authorityId);


    @Select({
            "select",
            "id, access_token, expires, authority_id, scope, refresh_token, companyCode, ",
            "createTime, modifyTime",
            "from token_info",
            "where companyCode = #{companycode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="expires", property="expires", jdbcType=JdbcType.VARCHAR),
            @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR),
            @Result(column="scope", property="scope", jdbcType=JdbcType.VARCHAR),
            @Result(column="refresh_token", property="refreshToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
            @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
            @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR)
    })
    List<TokenInfo> selectByCompanyCode(String companycode);

    @UpdateProvider(type=TokenInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TokenInfo record);

    @Update({
        "update token_info",
        "set access_token = #{accessToken,jdbcType=VARCHAR},",
          "expires = #{expires,jdbcType=VARCHAR},",
          "scope = #{scope,jdbcType=VARCHAR},",
          "refresh_token = #{refreshToken,jdbcType=VARCHAR},",
          "companyCode = #{companycode,jdbcType=VARCHAR},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    int updateByAuthorityId(TokenInfo record);
}