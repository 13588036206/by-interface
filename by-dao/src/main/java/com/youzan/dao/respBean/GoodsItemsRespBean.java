package com.youzan.dao.respBean;

import com.youzan.dao.model.SimpleGoodsItems;

import java.util.List;

public class GoodsItemsRespBean extends BaseRespBean{
    private List<SimpleGoodsItems> data;
    private Integer count;

    public GoodsItemsRespBean() {
    }

    public GoodsItemsRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public List<SimpleGoodsItems> getData() {
        return data;
    }

    public void setData(List<SimpleGoodsItems> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
