package com.youzan.dao.respBean;

import com.youzan.dao.model.Company;

import java.util.List;

public class CompanyRespBean extends BaseRespBean{
    private List<Company> data;
    private Integer count;

    public CompanyRespBean() {
    }

    public CompanyRespBean(String code, String state, String message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
