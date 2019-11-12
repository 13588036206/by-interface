package com.youzan.dao.reqBean;

import com.youzan.dao.model.SimpleGoodsItems;

import java.util.List;

public class GoodsSaveReq extends BaseReqBean{
    private List<SimpleGoodsItems> data;

    public List<SimpleGoodsItems> getData() {
        return data;
    }

    public void setData(List<SimpleGoodsItems> data) {
        this.data = data;
    }
}
