package com.sandyzfeaklab.breakdown_app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sandyzfeaklab.breakdown_app.Energy_Entry;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.activities.materials.MaterialWithMachine;
import com.tombayley.activitycircularreveal.CircularReveal;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView mtbf_butt, material_search, oil_cons_activity, pending_butt, logout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        firebaseMessaging.subscribeToTopic("oil_entry_added");
        firebaseMessaging.subscribeToTopic("Weekly_report");


        mAuth = FirebaseAuth.getInstance();


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , PackageManager.PERMISSION_GRANTED);

        logout = findViewById(R.id.logout);
        pending_butt = findViewById(R.id.pending_button);
        oil_cons_activity = findViewById(R.id.oil_cons_activity);
        material_search=findViewById(R.id.material_search_activity);
        TextView id = findViewById(R.id.idd);

        id.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
//
//        id.setText(mAuth.getCurrentUser().getUid());


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

        material_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CircularReveal.presentActivity(new CircularReveal.Builder(
                        MainActivity.this,
                        v,
                        new Intent(MainActivity.this, MaterialWithMachine.class),1000
                ));

            }
        });

        pending_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pending_activity.class);
                startActivity(intent);
            }
        });


        oil_cons_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Oil_consumption.class);

                startActivity(intent);


            }
        });

        ImageView fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Log.class);
                startActivity(intent);
            }
        });
    }


    public void energy_consp(View view) {

        Intent intent = new Intent(MainActivity.this, Energy_Entry.class);
        startActivity(intent);
    }
}