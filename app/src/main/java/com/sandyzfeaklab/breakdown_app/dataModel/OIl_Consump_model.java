package com.sandyzfeaklab.breakdown_app.dataModel;

import java.util.Date;

public class OIl_Consump_model {

    String oilType,reason,equip,id,area,mnth,username,uid,shift;
    int qty,wweek_no;
    java.util.Date timestamp;

    public String getUid() {
        return uid;
    }

    public OIl_Consump_model(String oilType, String reason, String equip, String id, String area, int qty, java.util.Date ts, String month, String usr, String uid,String shift,int wweek_no) {
        this.oilType = oilType;
        this.reason = reason;
        this.equip = equip;
        this.id = id;
        this.area = area;
        this.mnth=month;
        this.qty = qty;
        this.timestamp = ts;
        this.username=usr;
        this.uid=uid;
        this.shift=shift;
        this.wweek_no=wweek_no;
    }

    public OIl_Consump_model() {
    }

    public String getUsername() {
        return username;
    }

    public String getMnth() {
        return mnth;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getOilType() {
        return oilType;
    }

    public String getReason() {
        return reason;
    }

    public String getEquip() {
        return equip;
    }

    public int getQty() {
        return qty;
    }

    public String getShift() {
        return shift;
    }

    public int getWweek_no() {
        return wweek_no;
    }
}
