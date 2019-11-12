package com.youzan.dao.mapper;

import com.youzan.dao.model.GoodsQuantity;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class GoodsQuantitySqlProvider {

    public String insertAll(Map map) {
        List<GoodsQuantity> list = (List<GoodsQuantity>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO goods_quantity ");
        sb.append("(goodsid,bycode,quantity) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].goodsid},#'{'list[{0}].bycode},#'{'list[{0}].quantity})");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{String.valueOf(i)}));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}