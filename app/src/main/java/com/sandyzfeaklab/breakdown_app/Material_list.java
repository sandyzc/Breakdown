package com.sandyzfeaklab.breakdown_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Viewmodel.Material_ViewModel;
import com.sandyzfeaklab.breakdown_app.database.adaptor.material_Adaptor;

import java.util.List;

public class Material_list extends AppCompatActivity {
private Material_ViewModel viewModel;
private RecyclerView rcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        rcView=findViewById(R.id.material_list_rcv);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(Material_list.this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        final material_Adaptor adaptor = new material_Adaptor();

        rcView.setAdapter(adaptor);

        viewModel= ViewModelProviders.of(this).get(Material_ViewModel.class);
        viewModel.getAllMaterials().observe(this, new Observer<List<Material_List>>() {
            @Override
            public void onChanged(List<Material_List> material_lists) {
                adaptor.setMaterial_lists(material_lists);
            }
        });

    }
}