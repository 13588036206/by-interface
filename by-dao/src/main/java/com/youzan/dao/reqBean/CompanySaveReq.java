package com.youzan.dao.reqBean;

import com.youzan.dao.model.Company;

import java.util.List;

public class CompanySaveReq extends BaseReqBean{
    private List<Company> data;

    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data = data;
    }
}
