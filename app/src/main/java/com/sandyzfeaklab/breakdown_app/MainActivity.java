package com.sandyzfeaklab.breakdown_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    ImageView mtbf_butt,mttr_butt, oil_cons_activity,pending_butt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
            ,PackageManager.PERMISSION_GRANTED);

        mttr_butt=findViewById(R.id.mttr_butt);
        mtbf_butt=findViewById(R.id.mtbf_butt);
        pending_butt=findViewById(R.id.pending_button);
        oil_cons_activity =findViewById(R.id.oil_cons_activity);


        mttr_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        mtbf_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });


        pending_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Pending_activity.class);
                startActivity(intent);
            }
        });


        oil_cons_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Oil_consumption.class);

                startActivity(intent);


            }
        });

        ImageView fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Add_Log.class);
                startActivity(intent);
            }
        });
    }


}