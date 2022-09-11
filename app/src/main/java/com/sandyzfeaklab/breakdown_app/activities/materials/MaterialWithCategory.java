package com.sandyzfeaklab.breakdown_app.activities.materials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sandyzfeaklab.breakdown_app.R;
import com.tombayley.activitycircularreveal.CircularReveal;

public class MaterialWithCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_with_catergory);

    }

    public void material_cat_pne(View view) {

        Bundle bundle= new Bundle();
        bundle.putString("CAT","Pneumatic");
        bundle.putString("m/c","Fill");
        CircularReveal.presentActivity(new CircularReveal.Builder(
                MaterialWithCategory.this,
                view,
                new Intent(MaterialWithCategory.this, Material_list_Activity.class).putExtras(bundle),1000
        ));

    }

    public void material_cat_ele(View view) {

        Bundle bundle= new Bundle();
        bundle.putString("CAT","Elec & Electronics");
        bundle.putString("m/c","Fill");

        CircularReveal.presentActivity(new CircularReveal.Builder(
                MaterialWithCategory.this,
                view,
                new Intent(MaterialWithCategory.this, Material_list_Activity.class).putExtras(bundle),1000
        ));
    }

    public void material_cat_mech(View view) {

        Bundle bundle= new Bundle();
        bundle.putString("CAT","Mechanical");
        bundle.putString("m/c","Fill");
        CircularReveal.presentActivity(new CircularReveal.Builder(
                MaterialWithCategory.this,
                view,
                new Intent(MaterialWithCategory.this, Material_list_Activity.class).putExtras(bundle),1000
        ));
    }

    public void material_cat_hyd(View view) {

        Bundle bundle= new Bundle();
        bundle.putString("CAT","Hydraulic");
        bundle.putString("m/c","Fill");

        CircularReveal.presentActivity(new CircularReveal.Builder(
                MaterialWithCategory.this,
                view,
                new Intent(MaterialWithCategory.this, Material_list_Activity.class).putExtras(bundle),1000
        ));
    }
}