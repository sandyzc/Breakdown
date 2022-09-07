package com.sandyzfeaklab.breakdown_app.database.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sandyzfeaklab.breakdown_app.MainActivity;
import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Material_Repository;

import java.util.List;

public class Material_ViewModel extends AndroidViewModel {
    private Material_Repository repository;
    private LiveData<List<Material_List>> allMaterials;
    private LiveData<List<Material_List>> desc_search_Materials;
    private LiveData<List<Material_List>> sap_code_Materials;


    public Material_ViewModel(@NonNull Application application) {
        super(application);

        repository = new Material_Repository(application);
        allMaterials= repository.allMaterials();

    }

    public LiveData<List<Material_List>> getAllMaterials() {
        return allMaterials;
    }

    public LiveData<List<Material_List> > searchWithDesription(String description){
        desc_search_Materials=repository.getSearched_Material_desc(description);

        return desc_search_Materials;
    }

    public LiveData<List<Material_List> > searchWithSapcode(String sapCode){
        sap_code_Materials=repository.getSearched_Material_sap_code(sapCode);

        return sap_code_Materials;
    }
}
