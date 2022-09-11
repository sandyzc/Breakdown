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
    private String location;
    private String station;
    private String gen_desc;
    private String catergory;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getGen_desc() {
        return gen_desc;
    }

    public void setGen_desc(String gen_desc) {
        this.gen_desc = gen_desc;
    }

    public String getCatergory() {
        return catergory;
    }

    public void setCatergory(String catergory) {
        this.catergory = catergory;
    }

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


}
