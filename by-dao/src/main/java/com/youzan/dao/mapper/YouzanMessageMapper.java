package com.youzan.dao.mapper;

import com.youzan.dao.model.YouzanMessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface YouzanMessageMapper {

    @Insert({
        "insert into youzan_message (type, ",
        "orderNo, skuVersionText, ",
        "skuIntervalText, buyerPhone, ",
        "payTime, kdtId, ",
        "shopDisPlayNo, price, ",
        "appId, buyerId, ",
        "status, env, expireTime, ",
        "effectTime, companyCode)",
        "values (#{type,jdbcType=VARCHAR}, ",
        "#{orderno,jdbcType=VARCHAR}, #{skuversiontext,jdbcType=VARCHAR}, ",
        "#{skuintervaltext,jdbcType=INTEGER}, #{buyerphone,jdbcType=VARCHAR}, ",
        "#{paytime,jdbcType=VARCHAR}, #{kdtid,jdbcType=VARCHAR}, ",
        "#{shopdisplayno,jdbcType=VARCHAR}, #{price,jdbcType=INTEGER}, ",
        "#{appid,jdbcType=VARCHAR}, #{buyerid,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{env,jdbcType=VARCHAR}, #{expiretime,jdbcType=VARCHAR}, ",
        "#{effecttime,jdbcType=VARCHAR},#{companycode,jdbcType=VARCHAR})"
    })
    int insert(YouzanMessage record);

    @Select({
            "select",
            "id, type, orderNo, skuVersionText, skuIntervalText, buyerPhone, payTime, kdtId, ",
            "shopDisPlayNo, price, appId, buyerId, status, env, expireTime, effectTime, companyCode",
            "from youzan_message",
            "where orderNo = #{orderno,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="orderNo", property="orderno", jdbcType=JdbcType.VARCHAR),
            @Result(column="skuVersionText", property="skuversiontext", jdbcType=JdbcType.VARCHAR),
            @Result(column="skuIntervalText", property="skuintervaltext", jdbcType=JdbcType.INTEGER),
            @Result(column="buyerPhone", property="buyerphone", jdbcType=JdbcType.VARCHAR),
            @Result(column="payTime", property="paytime", jdbcType=JdbcType.VARCHAR),
            @Result(column="kdtId", property="kdtid", jdbcType=JdbcType.VARCHAR),
            @Result(column="shopDisPlayNo", property="shopdisplayno", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.INTEGER),
            @Result(column="appId", property="appid", jdbcType=JdbcType.VARCHAR),
            @Result(column="buyerId", property="buyerid", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="env", property="env", jdbcType=JdbcType.VARCHAR),
            @Result(column="expireTime", property="expiretime", jdbcType=JdbcType.VARCHAR),
            @Result(column="effectTime", property="effecttime", jdbcType=JdbcType.VARCHAR),
            @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR)
    })
    YouzanMessage selectByOrderNo(String orderno);


    @Update({
        "update youzan_message",
        "set type = #{type,jdbcType=VARCHAR},",
          "skuVersionText = #{skuversiontext,jdbcType=VARCHAR},",
          "skuIntervalText = #{skuintervaltext,jdbcType=INTEGER},",
          "buyerPhone = #{buyerphone,jdbcType=VARCHAR},",
          "payTime = #{paytime,jdbcType=VARCHAR},",
          "kdtId = #{kdtid,jdbcType=VARCHAR},",
          "shopDisPlayNo = #{shopdisplayno,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "appId = #{appid,jdbcType=VARCHAR},",
          "buyerId = #{buyerid,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=VARCHAR},",
          "env = #{env,jdbcType=VARCHAR},",
          "expireTime = #{expiretime,jdbcType=VARCHAR},",
          "effectTime = #{effecttime,jdbcType=VARCHAR}",
          "companyCode = #{companycode,jdbcType=VARCHAR}",
        "where orderNo = #{orderno,jdbcType=VARCHAR}"
    })
    int updateByOrderNo(YouzanMessage record);
}