package com.by.byservice;

import com.by.dao.model.RespMsg;
import com.by.dao.model.Tykh;

import java.util.List;

public interface ITykhService {
    List<Tykh> getBjd(String gsdm, String fsrq, String brand, String anyField, Double priceStart, Double priceEnd, String timeStart, String timeEnd);
    String getMaxFsrqByGsdm(String gsdm);
    List<Tykh> handleList(List<Tykh> list);
    RespMsg responseMsg(List<Tykh> tykhList);
}
