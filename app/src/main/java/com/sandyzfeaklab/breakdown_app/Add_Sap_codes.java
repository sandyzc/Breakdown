package com.sandyzfeaklab.breakdown_app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manojbhadane.genericadapter.GenericAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;
import com.sandyzfeaklab.breakdown_app.databinding.ActivityAddSapCodesBinding;
import com.sandyzfeaklab.breakdown_app.databinding.SapCodeCardViewBinding;

import java.util.ArrayList;

public class Add_Sap_codes extends AppCompatActivity {

    private ActivityAddSapCodesBinding mDataBinding;
    ArrayList<Sap_code_Model> data;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String desc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add__sap_codes)



        mDataBinding=DataBindingUtil.setContentView(this,R.layout.activity_add__sap_codes);

        FloatingActionButton fab_sap= findViewById(R.id.fab_add_Sap);

        data= new ArrayList<>();

        DatabaseReference myRef = database.getReference();
        //myRef.setValue("Hello, World!");


        fab_sap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FlatDialog flatDialog = new FlatDialog(Add_Sap_codes.this);
                flatDialog.setTitle("Add SAP ")
                        .setBackgroundColor(R.color.nemak)
                        .setFirstTextFieldInputType(2)
                        .setSubtitle("Enter the SAP Code")
                        .setFirstButtonText("ADD")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {



                                flatDialog.dismiss();

                                data.add(new Sap_code_Model().setSap_code(flatDialog.getFirstTextField()));


                                myRef.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        Sap_code_Model data=snapshot.getValue(Sap_code_Model.class);
                                        desc.se

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }
                        })
                        .show();

            }
        });

        mDataBinding.sapCodeRcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        mDataBinding.sapCodeRcv.setAdapter(new GenericAdapter<Sap_code_Model,SapCodeCardViewBinding>(this,data) {
            @Override
            public int getLayoutResId() {

                return R.layout.sap_code_card_view;
            }

            @Override
            public void onBindData(Sap_code_Model model, int position, SapCodeCardViewBinding dataBinding) {

                dataBinding.editTextNumber.setText(String.valueOf(model.getSap_code()));


            }

            @Override
            public void onItemClick(Sap_code_Model model, int position) {

            }


        });


    }
}