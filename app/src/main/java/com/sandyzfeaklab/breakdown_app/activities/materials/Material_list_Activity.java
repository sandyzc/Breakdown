package com.sandyzfeaklab.breakdown_app.activities.materials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.database.Material_List;
import com.sandyzfeaklab.breakdown_app.database.Viewmodel.Material_ViewModel;
import com.sandyzfeaklab.breakdown_app.database.adaptor.material_Adaptor;

import java.util.List;

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

        rcView=findViewById(R.id.material_list_rcv);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(Material_list_Activity.this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        final material_Adaptor adaptor = new material_Adaptor();

        rcView.setAdapter(adaptor);

        viewModel= ViewModelProviders.of(this).get(Material_ViewModel.class);

        viewModel.materials_with_cat_machine(category,machine).observe(this, new Observer<List<Material_List>>() {
            @Override
            public void onChanged(List<Material_List> material_lists) {
                adaptor.setMaterial_lists(material_lists);
            }
        });

    }
}