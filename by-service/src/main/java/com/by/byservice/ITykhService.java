package com.by.byservice;

import com.by.dao.model.RespMsg;
import com.by.dao.model.Tykh;

import java.util.List;

public interface ITykhService {
    public List<Tykh> getBjd(String gsdm, String fsrq,String brand,String anyField,Double priceStart,Double priceEnd,String timeStart,String timeEnd);
    public String getMaxFsrqByGsdm(String gsdm);
    public List<Tykh> handleList(List<Tykh> list);
    public RespMsg responseMsg(List<Tykh> tykhList);
}
