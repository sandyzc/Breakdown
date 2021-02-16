package com.sandyzfeaklab.Breakdown_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Add_Sap_codes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__sap_codes);

        FloatingActionButton fab_sap= findViewById(R.id.fab_add_Sap);


        fab_sap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FlatDialog flatDialog = new FlatDialog(Add_Sap_codes.this);
                flatDialog.setTitle("Login")
                        .setBackgroundColor(R.color.nemak)
                        .setSubtitle("write your profile info here")
                        .setFirstTextFieldHint("email")
                        .setSecondTextFieldHint("password")
                        .setFirstButtonText("CONNECT")
                        .setSecondButtonText("CANCEL")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(Add_Sap_codes.this, flatDialog.getFirstTextField(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }
}