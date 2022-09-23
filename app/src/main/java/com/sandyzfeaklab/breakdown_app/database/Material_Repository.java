package com.sandyzfeaklab.breakdown_app.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Material_Repository {

    private Material_list_DAO material_list_dao;
    private LiveData<List<Material_List>> allMaterial;
    private LiveData<List<Material_List>> searched_Material_desc;
    private LiveData<List<Material_List>> searched_Material_sap_code;
    private LiveData<List<Material_List>> material_cat_machine;

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
        searched_Material_desc = material_list_dao.search_with_desc(search_desc);

        return searched_Material_desc;

    }

    public LiveData<List<Material_List>> getMaterial_cat_machine( String cat, String machine){

        material_cat_machine=material_list_dao.search_with_machine_catergory(cat,machine);

        return material_cat_machine;
    }


    public LiveData<List<Material_List>> getSearched_Material_sap_code(String sap_code) {
        searched_Material_sap_code = material_list_dao.search_with_sap_code(sap_code);
        return searched_Material_sap_code;
    }
}
