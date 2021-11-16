package com.sandyzfeaklab.breakdown_app.dataModel;

import java.util.Date;

public class OIl_Consump_model {

    String oilType,reason,equip,id,area,mnth;
    int qty;
    java.util.Date timestamp;

    public OIl_Consump_model(String oilType, String reason, String equip, String id, String area, int qty, java.util.Date ts,String month) {
        this.oilType = oilType;
        this.reason = reason;
        this.equip = equip;
        this.id = id;
        this.area = area;
        this.mnth=month;
        this.qty = qty;
        this.timestamp = ts;
    }

    public OIl_Consump_model() {
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
}
