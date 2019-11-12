package com.youzan.dao.mapper;

import com.youzan.dao.model.ItemImgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ItemImgsMapper {
    @Delete({
        "delete from item_imgs",
        "where tid = #{tid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long tid);

    @Insert({
        "insert into item_imgs (item_id, ",
        "thumbnail, combine, ",
        "id, created, url, ",
        "medium, createTime, ",
        "modifyTime, companyCode, ",
        "authority_id)",
        "values (#{itemId,jdbcType=BIGINT}, ",
        "#{thumbnail,jdbcType=VARCHAR}, #{combine,jdbcType=VARCHAR}, ",
        "#{id,jdbcType=BIGINT}, #{created,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, ",
        "#{medium,jdbcType=VARCHAR}, #{createtime,jdbcType=VARCHAR}, ",
        "#{modifytime,jdbcType=VARCHAR}, #{companycode,jdbcType=VARCHAR}, ",
        "#{authorityId,jdbcType=VARCHAR})"
    })
    int insert(ItemImgs record);

    @InsertProvider(type=ItemImgsSqlProvider.class, method="insertSelective")
    int insertSelective(ItemImgs record);

    @Select({
        "select",
        "tid, item_id, thumbnail, combine, id, created, url, medium, createTime, modifyTime, ",
        "companyCode, authority_id",
        "from item_imgs",
        "where tid = #{tid,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="tid", property="tid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
        @Result(column="thumbnail", property="thumbnail", jdbcType=JdbcType.VARCHAR),
        @Result(column="combine", property="combine", jdbcType=JdbcType.VARCHAR),
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT),
        @Result(column="created", property="created", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="medium", property="medium", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifyTime", property="modifytime", jdbcType=JdbcType.VARCHAR),
        @Result(column="companyCode", property="companycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="authority_id", property="authorityId", jdbcType=JdbcType.VARCHAR)
    })
    ItemImgs selectByPrimaryKey(Long tid);

     @Select({
        "select",
        "id",
        "from item_imgs",
        "where id = #{id,jdbcType=BIGINT} and item_id = #{itemId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT),
    })
    Long selectByItemId(Long id,Long itemId);

    @UpdateProvider(type=ItemImgsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ItemImgs record);

    @Update({
        "update item_imgs",
        "set thumbnail = #{thumbnail,jdbcType=VARCHAR},",
          "combine = #{combine,jdbcType=VARCHAR},",
          "created = #{created,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "medium = #{medium,jdbcType=VARCHAR},",
          "modifyTime = #{modifytime,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT} and item_id = #{itemId,jdbcType=BIGINT}"
    })
    int updateByItemId(ItemImgs record);
}