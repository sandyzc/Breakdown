package com.sandyzfeaklab.breakdown_app.activities.materials;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sandyzfeaklab.breakdown_app.R;

public class MaterialWithMachine extends AppCompatActivity {


     private static final String fill="Fill";

     CardView fillcell,hpdc,gspm,coreshop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_with_machine);


        fillcell=findViewById(R.id.mat_with_machine_fill);
        hpdc=findViewById(R.id.mat_with_machine_hpdc);
        gspm=findViewById(R.id.mat_with_machine_gspm);
        coreshop=findViewById(R.id.mat_with_machine_coreshop);




        fillcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("activity","MACHINE");
                bundle.putString("machine",fill);

                Intent intent = new Intent(MaterialWithMachine.this, Material_list_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        hpdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gspm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        coreshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


}