package com.sandyzfeaklab.breakdown_app.database.Viewmodel;


import androidx.room.Entity;
import androidx.room.Fts4;

import com.sandyzfeaklab.breakdown_app.database.Material_List;

@Fts4(contentEntity = Material_List.class)
@Entity(tableName = "spares_master_fts")
public class Material_list_fts {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
