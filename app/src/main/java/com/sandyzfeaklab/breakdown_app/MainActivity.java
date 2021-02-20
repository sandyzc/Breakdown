package com.sandyzfeaklab.breakdown_app;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton mttr_butt,mtbf_butt,uptime_time,pending_butt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mttr_butt=findViewById(R.id.mttr_butt);
        mtbf_butt=findViewById(R.id.mtbf_butt);
        pending_butt=findViewById(R.id.pending_button);
        uptime_time=findViewById(R.id.up_time_butt);


        mttr_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this,Mttr.class);

                startActivity(intent);

            }
        });


        mtbf_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        pending_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        uptime_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Data_input.class);
                startActivity(intent);
            }
        });
    }

}