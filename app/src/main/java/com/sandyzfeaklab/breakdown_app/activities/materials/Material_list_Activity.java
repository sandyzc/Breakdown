package com.sandyzfeaklab.breakdown_app.activities.materials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.activities.AddOildetails;
import com.sandyzfeaklab.breakdown_app.activities.Oil_consumption;
import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Viewmodel.Material_ViewModel;
import com.sandyzfeaklab.breakdown_app.database.adaptor.material_Adaptor;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Material_list_Activity extends AppCompatActivity {
    private Material_ViewModel viewModel;
    private RecyclerView rcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);


        Bundle bundle = getIntent().getExtras();

        String category = bundle.getString("CAT");
        String machine = bundle.getString("m/c");

        rcView = findViewById(R.id.material_list_rcv);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(Material_list_Activity.this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        final material_Adaptor adaptor = new material_Adaptor();


        viewModel = ViewModelProviders.of(this).get(Material_ViewModel.class);


        rcView.setAdapter(adaptor);

        viewModel.getAllMaterials().observe(this, new Observer<List<Material_List>>() {
            @Override
            public void onChanged(List<Material_List> material_lists) {
                adaptor.setMaterial_lists(material_lists);
            }
        });

        fabInit(machine, adaptor);


    }


    private void fabInit(String machine, material_Adaptor adaptor) {
        FabSpeedDial fabSpeedDial = findViewById(R.id.mat_list_filter_fab);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.mat_menu_ele:
                        viewModel.materials_with_cat_machine("Elec & Electronics", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                getSupportActionBar().setTitle("Elec & Electronics");
                            }
                        });
                        return true;
                    case R.id.mat_menu_hyd:
                        viewModel.materials_with_cat_machine("Hydraulic", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                getSupportActionBar().setTitle("Hydraulic");
                            }
                        });
                        return true;
                    case R.id.mat_menu_mech:
                        viewModel.materials_with_cat_machine("Mechanical", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                getSupportActionBar().setTitle("Mechanical");
                            }
                        });
                        return true;
                    case R.id.mat_menu_pne:
                        viewModel.materials_with_cat_machine("Pneumatic", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                getSupportActionBar().setTitle("Pneumatic");
                            }
                        });
                        return true;


                }

                return false;
            }
        });
    }
}