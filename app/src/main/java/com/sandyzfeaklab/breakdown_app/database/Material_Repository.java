package com.sandyzfeaklab.breakdown_app.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Material_Repository {

    private final Material_list_DAO material_list_dao;
    private final LiveData<List<Material_List>> allMaterial;


    //TODO implement category list


    public Material_Repository(Application application) {
        Material_Database database = Material_Database.getInstance(application);
        material_list_dao = database.material_list_dao();

        allMaterial = material_list_dao.getAllMaterials();


    }


    public LiveData<List<Material_List>> allMaterials() {
        return allMaterial;
    }

    public LiveData<List<Material_List>> getSearched_Material_desc(String search_desc) {

        return material_list_dao.search_with_desc(search_desc);

    }
    public LiveData<List<Material_List>>serchviewlist(String sapcode,String desc){

        return material_list_dao.search_view(desc,sapcode);

    }


    public LiveData<List<Material_List>> getmachineList(String machine) {

        return material_list_dao.machine_list(machine);
    }


    public LiveData<List<Material_List>> getMaterial_cat_machine(String cat, String machine) {

        return material_list_dao.search_with_machine_catergory(cat, machine);
    }


    public LiveData<List<Material_List>> getSearched_Material_sap_code(String sap_code) {
        return material_list_dao.search_with_sap_code(sap_code);
    }
}
