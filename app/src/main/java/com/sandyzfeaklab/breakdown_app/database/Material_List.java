package com.sandyzfeaklab.breakdown_app.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spares_master")
public class Material_List {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String sap_code;
    @NonNull
    private String description;
    private int stock;
    private String sub_assembly;
    private String image;
    private String machine;
    private String used_in_1;
    private String used_in_2;
    private String used_in_3;
    private String used_in_4;
    private String used_in_5;
    private String used_in_6;
    private String used_in_7;
    private String used_in_8;
    private String used_in_9;
    private String used_in_10;

    public void setSap_code(String sap_code) {
        this.sap_code = sap_code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSub_assembly(String sub_assembly) {
        this.sub_assembly = sub_assembly;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setUsed_in_1(String used_in_1) {
        this.used_in_1 = used_in_1;
    }

    public void setUsed_in_2(String used_in_2) {
        this.used_in_2 = used_in_2;
    }

    public void setUsed_in_3(String used_in_3) {
        this.used_in_3 = used_in_3;
    }

    public void setUsed_in_4(String used_in_4) {
        this.used_in_4 = used_in_4;
    }

    public void setUsed_in_5(String used_in_5) {
        this.used_in_5 = used_in_5;
    }

    public void setUsed_in_6(String used_in_6) {
        this.used_in_6 = used_in_6;
    }

    public void setUsed_in_7(String used_in_7) {
        this.used_in_7 = used_in_7;
    }

    public void setUsed_in_8(String used_in_8) {
        this.used_in_8 = used_in_8;
    }

    public void setUsed_in_9(String used_in_9) {
        this.used_in_9 = used_in_9;
    }

    public void setUsed_in_10(String used_in_10) {
        this.used_in_10 = used_in_10;
    }

    public String getSap_code() {
        return sap_code;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public String getSub_assembly() {
        return sub_assembly;
    }

    public String getImage() {
        return image;
    }

    public String getMachine() {
        return machine;
    }

    public String getUsed_in_1() {
        return used_in_1;
    }

    public String getUsed_in_2() {
        return used_in_2;
    }

    public String getUsed_in_3() {
        return used_in_3;
    }

    public String getUsed_in_4() {
        return used_in_4;
    }

    public String getUsed_in_5() {
        return used_in_5;
    }

    public String getUsed_in_6() {
        return used_in_6;
    }

    public String getUsed_in_7() {
        return used_in_7;
    }

    public String getUsed_in_8() {
        return used_in_8;
    }

    public String getUsed_in_9() {
        return used_in_9;
    }

    public String getUsed_in_10() {
        return used_in_10;
    }
}
