package com.sandyzfeaklab.breakdown_app.activities.materials;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Viewmodel.Material_ViewModel;
import com.sandyzfeaklab.breakdown_app.database.adaptor.material_Adaptor;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Material_list_Activity extends AppCompatActivity {
    private Material_ViewModel viewModel;
    private RecyclerView rcView;
    final material_Adaptor adaptor = new material_Adaptor();
    androidx.appcompat.widget.SearchView searchView;
    String machine, sapCodeSearched, descSearched, activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            machine = bundle.getString("machine");
            sapCodeSearched = bundle.getString("sapCode");
            descSearched = bundle.getString("desc");
            activity = bundle.getString("activity");

        }


        rcView = findViewById(R.id.material_list_rcv);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(Material_list_Activity.this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        viewModel = ViewModelProviders.of(this).get(Material_ViewModel.class);


        rcView.setAdapter(adaptor);

        searchView = findViewById(R.id.searchview1);

        setlist(activity,machine,sapCodeSearched,descSearched);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {

                    String query = "%"+ newText +"%";

                    search_db(query);
                }

                return false;
            }
        });

    }

    private void search_db(String search_query) {

        viewModel.searchview(search_query, search_query).observe(this, new Observer<List<Material_List>>() {
            @Override
            public void onChanged(List<Material_List> material_lists) {
                adaptor.setMaterial_lists(material_lists);
            }
        });

//        viewModel.filtered(search_query).observe(this, new Observer<List<Material_List>>() {
//                    @Override
//                    public void onChanged(List<Material_List> material_lists) {
//                        adaptor.setMaterial_lists(material_lists);
//                    }
//                }
//        );
    }


    private void setlist(String activity, String machine, String sapCodeSearched, String descSearched) {



        if (activity.equals("MACHINE")) {

            Toast.makeText(this, "with machine", Toast.LENGTH_LONG).show();
            viewModel.getMachine_Materials(machine).observe(this, new Observer<List<Material_List>>() {
                @Override
                public void onChanged(List<Material_List> material_lists) {
                    adaptor.setMaterial_lists(material_lists);
                }
            });

            fabInit(machine,adaptor);

        } else {

            if (sapCodeSearched.length()>1) {

                searchView.setVisibility(View.INVISIBLE);

                Toast.makeText(this, "sap code search", Toast.LENGTH_LONG).show();
                viewModel.searchWithSapcode(sapCodeSearched).observe(this, new Observer<List<Material_List>>() {
                    @Override
                    public void onChanged(List<Material_List> material_lists) {
                        adaptor.setMaterial_lists(material_lists);
                    }
                });
            } else if (descSearched.length()>1) {

                Toast.makeText(this, "desc", Toast.LENGTH_LONG).show();
                String query = "%" + descSearched + "%";
                viewModel.searchWithDesription(query).observe(this, new Observer<List<Material_List>>() {
                    @Override
                    public void onChanged(List<Material_List> material_lists) {
                        adaptor.setMaterial_lists(material_lists);
                    }
                });
            } else {

                viewModel.getAllMaterials().observe(this, new Observer<List<Material_List>>() {
                    @Override
                    public void onChanged(List<Material_List> material_lists) {
                        adaptor.setMaterial_lists(material_lists);
                    }
                });
            }

        }

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
                                adaptor.notifyDataSetChanged();

                            }
                        });
                        return true;
                    case R.id.mat_menu_hyd:
                        viewModel.materials_with_cat_machine("Hydraulic", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                adaptor.notifyDataSetChanged();

                            }
                        });
                        return true;
                    case R.id.mat_menu_mech:
                        viewModel.materials_with_cat_machine("Mechanical", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                adaptor.notifyDataSetChanged();

                            }
                        });
                        return true;
                    case R.id.mat_menu_pne:
                        viewModel.materials_with_cat_machine("Pneumatic", machine).observe(Material_list_Activity.this, new Observer<List<Material_List>>() {
                            @Override
                            public void onChanged(List<Material_List> material_lists) {
                                adaptor.setMaterial_lists(material_lists);
                                adaptor.notifyDataSetChanged();

                            }
                        });
                        return true;


                }

                return false;
            }
        });
    }
}