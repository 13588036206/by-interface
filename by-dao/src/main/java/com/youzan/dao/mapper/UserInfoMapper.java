package com.youzan.dao.mapper;

import com.youzan.dao.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UserInfoMapper {
    @Delete({
        "delete from user_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user_info (client_id, ",
        "client_secret, companyCode, ",
        "createTime, modifyTime)",
        "values (#{clientId,jdbcType=VARCHAR}, ",
        "#{clientSecret,jdbcType=VARCHAR}, #{companycode,jdbcType=VARCHAR}, ",
        "#{createtime,jdbcType=VARCHAR}, #{modifytime,jdbcType=VARCHAR})"
    })
    int insert(UserInfo record);

    @Select({
        "select",
        "id, client_id, client_secret, companyCode, createTime, modifyTime",
        "from user_info",
        "where companyCode = #{companycode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="client_id", property="clientId", jdbcType=JdbcType.VARCHAR),
        @Result(column="client_secret", property="clientSecret", jdbcType=JdbcType.VARCHAR),
        @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR)
    })
    UserInfo selectByCompanyCode(String companycode);

    @Update({
        "update user_info",
        "set client_id = #{clientId,jdbcType=VARCHAR},",
          "client_secret = #{clientSecret,jdbcType=VARCHAR},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where companyCode = #{companycode,jdbcType=INTEGER}"
    })
    int updateByCompanyCode(UserInfo record);

    @Select({
            "select",
            "client_secret",
            "from user_info",
            "where companyCode = #{companycode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="client_secret", property="clientSecret",jdbcType=JdbcType.VARCHAR)
    })
    String selectKeyByCompanyCode(String companycode);
}