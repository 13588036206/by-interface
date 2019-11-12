package com.youzan.dao.mapper;

import com.youzan.dao.model.Company;
import com.youzan.dao.model.RefreshToken;
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
    TokenInfo selectByauthorityId(String authorityId);


    @Select({
            "select",
            "access_token, authority_id",
            "from token_info",
            "where companyCode = #{companycode,jdbcType=VARCHAR} and bycode = #{bycode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
            @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR),
    })
    TokenInfo selectByBycode(String companycode,String bycode);

    @Select({
            "select",
            "authority_id",
            "from token_info",
            "where companyCode = #{companycode,jdbcType=VARCHAR} and byCode = #{bycode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR)
    })
    String selectAuthorityId(String companycode,String bycode);

    @UpdateProvider(type=TokenInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TokenInfo record);

    @Update({
        "update token_info",
        "set access_token = #{accessToken,jdbcType=VARCHAR},",
          "expires = #{expires,jdbcType=VARCHAR},",
          "scope = #{scope,jdbcType=VARCHAR},",
          "refresh_token = #{refreshToken,jdbcType=VARCHAR},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where authority_id = #{authorityId,jdbcType=INTEGER}"
    })
    int updateByAuthorityId(TokenInfo record);

    @Select({
            "select",
            "authority_id, authority_name, case when corresponding ='Y' then 'true' else 'false' end corresponding,bycode",
            "from token_info",
            "where companycode = #{companycode,jdbcType=VARCHAR}",
            "and (#{bycode,jdbcType=VARCHAR} is null or bycode = #{bycode,jdbcType=VARCHAR})",
            "and (#{anyField,jdbcType=VARCHAR} is null or authority_name like #{anyField,jdbcType=VARCHAR})",
            "and (#{corresponding,jdbcType=VARCHAR} is null or ifnull(corresponding,'N') = #{corresponding,jdbcType=VARCHAR})",
            "limit ${start},${end}"
    })
    @Results({
            @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.BIGINT),
            @Result(column="authority_name", property="authorityName", jdbcType=JdbcType.VARCHAR),
            @Result(column="corresponding", property="corresponding", jdbcType=JdbcType.VARCHAR),
            @Result(column="bycode", property="bycode", jdbcType=JdbcType.VARCHAR)
    })
    List<Company> selectByCondition(String companycode,String bycode, String anyField, String corresponding, Integer start, Integer end);

    @Select({
            "select",
            "count(*) count",
            "from token_info",
            "where companycode = #{companycode,jdbcType=VARCHAR}",
            "and (#{bycode,jdbcType=VARCHAR} is null or bycode = #{bycode,jdbcType=VARCHAR})",
            "and (#{anyField,jdbcType=VARCHAR} is null or authority_name like #{anyField,jdbcType=VARCHAR})",
            "and (#{corresponding,jdbcType=VARCHAR} is null or ifnull(corresponding,'N') = #{corresponding,jdbcType=VARCHAR})"
    })
    @Results({
            @Result(column="count", property="count", jdbcType=JdbcType.INTEGER)
    })
    Integer count(String companycode,String bycode, String anyField, String corresponding);

    @Update({
            "update token_info",
            "set corresponding = #{corresponding,jdbcType=VARCHAR},",
            "bycode = #{bycode,jdbcType=VARCHAR},",
            "modifyTime = #{modifytime,jdbcType=VARCHAR}",
            "where authority_id = #{authorityId,jdbcType=BIGINT}"
    })
    int updateCompany(Company company);

    @Select({
            "select",
            "a.client_id, a.client_secret, b.refresh_token",
            "from user_info a inner join token_info b on a.companyCode = b.companyCode"
    })
    @Results({
            @Result(column="client_id", property="clientId", jdbcType=JdbcType.VARCHAR),
            @Result(column="client_secret", property="clientSecret", jdbcType=JdbcType.VARCHAR),
            @Result(column="refresh_token", property="refreshToken", jdbcType=JdbcType.VARCHAR),
    })
    List<RefreshToken> selcectAllToken();

    @Select({
            "select",
            "DISTINCT companyCode",
            "from token_info where corresponding = 'Y'"
    })
    @Results({
            @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
    })
    List<String> selcectAllCompanycode();

    @Select({
            "select",
            "byCode",
            "from token_info",
            "where authority_id not in (${authorityIds})",
            "and companyCode = #{companycode,jdbcType=VARCHAR} and byCode is not null"
    })
    @Results({
            @Result(column="byCode", property="byCode", jdbcType=JdbcType.VARCHAR),
    })
    List<String> selcectBycodes(String authorityIds,String companycode);

    @Update({
            "update token_info",
            "set authority_name = #{authorityName,jdbcType=VARCHAR}",
            "where authority_id = #{authorityId,jdbcType=BIGINT}"
    })
    int updateAuthorityName(String authorityName,String authorityId);
}