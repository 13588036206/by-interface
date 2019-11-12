package com.youzan.dao.reqBean;

public class GoodsItemsReq extends BaseReqBean{
    private String bycode;
    private String anyField;
    private String goodsId;
    private String corresponding;
    private Integer start;
    private Integer end;

    public String getBycode() {
        return bycode;
    }

    public void setBycode(String bycode) {
        this.bycode = bycode;
    }

    public String getAnyField() {
        return anyField;
    }

    public void setAnyField(String anyField) {
        this.anyField = anyField;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getCorresponding() {
        return corresponding;
    }

    public void setCorresponding(String corresponding) {
        this.corresponding = corresponding;
    }
}
