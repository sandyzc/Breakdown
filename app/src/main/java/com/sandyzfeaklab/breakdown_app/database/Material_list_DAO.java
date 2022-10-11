package com.sandyzfeaklab.breakdown_app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Material_list_DAO {

    @Query("SELECT * FROM spares_master WHERE description LIKE  :search")
    LiveData<List<Material_List>> search_with_desc(String search);


    @Query("SELECT * FROM spares_master WHERE sap_code LIKE :search1")
    LiveData<List<Material_List>> search_with_sap_code(String search1);

    @Query("SELECT * FROM spares_master WHERE catergory LIKE :category AND machine LIKE :machine ")
    LiveData<List<Material_List>> search_with_machine_catergory(String category, String machine);

    @Query("SELECT * FROM spares_master ORDER BY sap_code")
    LiveData<List<Material_List>> getAllMaterials();

    @Query("SELECT * FROM spares_master WHERE machine LIKE :machine")
    LiveData<List<Material_List>> machine_list(String machine);

    @Query("SELECT * FROM spares_master WHERE description LIKE :desc or sap_code LIKE :sapcode ")
    LiveData<List<Material_List>> search_view(String desc, String sapcode);




}
