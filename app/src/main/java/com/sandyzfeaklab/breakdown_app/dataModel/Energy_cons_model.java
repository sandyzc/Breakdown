package com.sandyzfeaklab.breakdown_app.dataModel;

import java.util.Date;

public class Energy_cons_model {

    String uid,name;
    Long hpdc1, hpdc2, hpdc3, gspm2B, gspm3B1, gspm3B2, rotoCast, gspm3B3,hpdc1Units, hpdc2Units, hpdc3Units, gspm2BUnits, gspm3B1Units, gspm3B2Units, rotoCastUnits, gspm3B3Units;
    Date ts;

    public Energy_cons_model(String uid, String name, Long hpdc1, Long hpdc2, Long hpdc3, Long gspm2B, Long gspm3B1, Long gspm3B2, Long rotoCast, Long gspm3B3, Long hpdc1Units,
                             Long hpdc2Units, Long hpdc3Units, Long gspm2BUnits, Long gspm3B1Units, Long gspm3B2Units, Long rotoCastUnits, Long gspm3B3Units, Date ts) {
        this.uid = uid;
        this.name = name;
        this.hpdc1 = hpdc1;
        this.hpdc2 = hpdc2;
        this.hpdc3 = hpdc3;
        this.gspm2B = gspm2B;
        this.gspm3B1 = gspm3B1;
        this.gspm3B2 = gspm3B2;
        this.rotoCast = rotoCast;
        this.gspm3B3 = gspm3B3;
        this.hpdc1Units = hpdc1Units;
        this.hpdc2Units = hpdc2Units;
        this.hpdc3Units = hpdc3Units;
        this.gspm2BUnits = gspm2BUnits;
        this.gspm3B1Units = gspm3B1Units;
        this.gspm3B2Units = gspm3B2Units;
        this.rotoCastUnits = rotoCastUnits;
        this.gspm3B3Units = gspm3B3Units;
        this.ts = ts;
    }

    public Long getHpdc1Units() {
        return hpdc1Units;
    }

    public Long getHpdc2Units() {
        return hpdc2Units;
    }

    public Long getHpdc3Units() {
        return hpdc3Units;
    }

    public Long getGspm2BUnits() {
        return gspm2BUnits;
    }

    public Long getGspm3B1Units() {
        return gspm3B1Units;
    }

    public Long getGspm3B2Units() {
        return gspm3B2Units;
    }

    public Long getRotoCastUnits() {
        return rotoCastUnits;
    }

    public Long getGspm3B3Units() {
        return gspm3B3Units;
    }

    public Energy_cons_model(Long hpdc1Units, Long hpdc2Units, Long hpdc3Units, Long gspm2BUnits, Long gspm3B1Units, Long gspm3B2Units, Long rotoCastUnits, Long gspm3B3Units) {
        this.hpdc1Units = hpdc1Units;
        this.hpdc2Units = hpdc2Units;
        this.hpdc3Units = hpdc3Units;
        this.gspm2BUnits = gspm2BUnits;
        this.gspm3B1Units = gspm3B1Units;
        this.gspm3B2Units = gspm3B2Units;
        this.rotoCastUnits = rotoCastUnits;
        this.gspm3B3Units = gspm3B3Units;
    }

    public Energy_cons_model(String uid, String Name
            , Long hpdc1, Long HPDC2, Long HPDC3, Long GSPM2B, Long GSPM3B1, Long GSPM3B2, Long rotoCast, Long GSPM3B3, Date ts) {
        this.uid = uid;
        this.name = Name;

        this.hpdc1 = hpdc1;
        this.hpdc2 = HPDC2;
        this.hpdc3 = HPDC3;
        this.gspm2B = GSPM2B;
        this.gspm3B1 = GSPM3B1;
        this.gspm3B2 = GSPM3B2;
        this.rotoCast = rotoCast;
        this.gspm3B3 = GSPM3B3;
        this.ts = ts;
    }

    public Energy_cons_model() {
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Long getHpdc1() {
        return hpdc1;
    }

    public Long getHpdc2() {
        return hpdc2;
    }

    public Long getHpdc3() {
        return hpdc3;
    }

    public Long getGspm2B() {
        return gspm2B;
    }

    public Long getGspm3B1() {
        return gspm3B1;
    }

    public Long getGspm3B2() {
        return gspm3B2;
    }

    public Long getRotoCast() {
        return rotoCast;
    }

    public Long getGspm3B3() {
        return gspm3B3;
    }

    public Date getTs() {
        return ts;
    }
}
