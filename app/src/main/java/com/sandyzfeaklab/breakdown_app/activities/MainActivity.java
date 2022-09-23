package com.sandyzfeaklab.breakdown_app.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sandyzfeaklab.breakdown_app.Energy_Entry;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.activities.materials.MaterialWithMachine;
import com.sandyzfeaklab.breakdown_app.activities.materials.Material_list_Activity;
import com.sandyzfeaklab.breakdown_app.activities.materials.Material_with_machin_kt;
import com.tombayley.activitycircularreveal.CircularReveal;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView mtbf_butt, material_search, oil_cons_activity, pending_butt, logout;

    private FirebaseAuth mAuth;
    Dialog dialog;

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

                material_search_button_clicked_dialog();

//                CircularReveal.presentActivity(new CircularReveal.Builder(
//                        MainActivity.this,
//                        v,
//                        new Intent(MainActivity.this, Material_with_machin_kt.class),1000
//                ));

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

    //TODO Download DB option and progress bar need to implement


    private void material_search_button_clicked_dialog(){

        //TODO text validation need to implement
        //TODO implement search feature

        dialog= new Dialog(this);
        dialog.setContentView(R.layout.search_options_dialog);
        TextInputEditText sap_code_enterd= dialog.findViewById(R.id.search_sap_code);
        TextInputEditText desc_enterd= dialog.findViewById(R.id.search_desc);
        Button search_butt= dialog.findViewById(R.id.search_SAP_Button);
        Button search_with_machine= dialog.findViewById(R.id.search_with_machine_but);


        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        search_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, Material_list_Activity.class);
                startActivity(intent);

            }
        });
        dialog.show();




    }
}