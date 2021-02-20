package com.sandyzfeaklab.breakdown_app.dataModel;

public class Sap_code_Model {

    private String sap_code;
    private String sap_description;
    private int sap_qty;

    public String getSap_code() {
        return sap_code;
    }

    public Sap_code_Model setSap_code(String sap_code) {
        this.sap_code = sap_code;
        return this;
    }

    public String getSap_description() {
        return sap_description;
    }

    public Sap_code_Model setSap_description(String sap_description) {
        this.sap_description = sap_description;
        return this;
    }

    public int getSap_qty() {
        return sap_qty;
    }

    public Sap_code_Model setSap_qty(int sap_qty) {
        this.sap_qty = sap_qty;
        return this;
    }
}
