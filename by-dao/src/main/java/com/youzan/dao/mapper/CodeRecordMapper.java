package com.youzan.dao.mapper;

import com.youzan.dao.model.CodeRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface CodeRecordMapper {
    @Insert({
        "insert into code_record (code, ",
        "createtime,companyCode)",
        "values (#{code,jdbcType=VARCHAR},",
        "#{createtime,jdbcType=VARCHAR},#{companycode,jdbcType=VARCHAR})"
    })
    int insert(CodeRecord record);

    @Select({
            "select",
            "id, code, createtime, companyCode",
            "from code_record",
            "where companyCode = #{companycode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
            @Result(column="createtime", property="createtime", jdbcType=JdbcType.VARCHAR),
            @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR)
    })
    CodeRecord selectByCompanycode(String companycode);

    @Select({
            "select",
            "code",
            "from code_record",
            "where companyCode = #{companycode,jdbcType=VARCHAR} order by id desc limit 1"
    })
    @Results({
            @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
    })
    String getCode(String companycode);

}