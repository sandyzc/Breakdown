package com.sandyzfeaklab.breakdown_app.database.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Material_Repository;

import java.util.List;

public class Material_ViewModel extends AndroidViewModel {
    private final Material_Repository repository;
    private final LiveData<List<Material_List>> allMaterials;


    public Material_ViewModel(@NonNull Application application) {
        super(application);

        repository = new Material_Repository(application);
        allMaterials = repository.allMaterials();


    }

    public LiveData<List<Material_List>> getMachine_Materials(String machine) {
        return repository.getmachineList(machine);

    }

    public LiveData<List<Material_List>> getAllMaterials() {
        return allMaterials;
    }

    public LiveData<List<Material_List>> searchWithDesription(String description) {

        return repository.getSearched_Material_desc(description);
    }


    public LiveData<List<Material_List>>searchview(String desc, String sapcode){

        return repository.serchviewlist(sapcode,desc);
    }

    public LiveData<List<Material_List>> materials_with_cat_machine(String category, String machine) {

        return repository.getMaterial_cat_machine(category, machine);

    }


    public LiveData<List<Material_List>> searchWithSapcode(String sapCode) {

        return repository.getSearched_Material_sap_code(sapCode);
    }
}
