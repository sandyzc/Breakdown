package com.sandyzfeaklab.breakdown_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manojbhadane.genericadapter.GenericAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;
import com.sandyzfeaklab.breakdown_app.databinding.SapCodeCardViewBinding;

import java.util.ArrayList;

public class Add_Sap_codes extends AppCompatActivity {

    ArrayList<Sap_code_Model> data;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add__sap_codes)


        com.sandyzfeaklab.breakdown_app.databinding.ActivityAddSapCodesBinding mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add__sap_codes);

        FloatingActionButton fab_sap = findViewById(R.id.fab_add_Sap);

        data = new ArrayList<>();

        DatabaseReference myRef = database.getReference();
        //myRef.setValue("Hello, World!");


        fab_sap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FlatDialog flatDialog = new FlatDialog(Add_Sap_codes.this);
                flatDialog.setTitle("Add SAP ")
                        .setBackgroundColor(R.color.nemak)
                        .setFirstTextFieldInputType(2)
                        .setSubtitle("Enter the SAP Code").setFirstTextFieldHint("Enter SAP CODE")
                        .setFirstButtonText("ADD").setSecondTextFieldHint("Qty").setSecondTextFieldInputType(2).isCancelable(true)
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                database.goOnline();

                                flatDialog.dismiss();



                                myRef.child(String.valueOf(flatDialog.getFirstTextField())).child("0").child("sap_description").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                                        data.add(new Sap_code_Model().setSap_description(String.valueOf(task.getResult().getValue())).setSap_code(Integer.parseInt(flatDialog.getFirstTextField())).setSap_qty(Integer.parseInt(flatDialog.getSecondTextField())));
                                        mDataBinding.sapCodeRcv.getAdapter().notifyDataSetChanged();
                                    }
                                });

                            }
                        })
                        .show();

            }
        });

        mDataBinding.sapCodeRcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mDataBinding.sapCodeRcv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mDataBinding.sapCodeRcv.setAdapter(new GenericAdapter<Sap_code_Model, SapCodeCardViewBinding>(this, data) {
            @Override
            public int getLayoutResId() {

                return R.layout.sap_code_card_view;
            }

            @Override
            public void onBindData(Sap_code_Model model, int position, SapCodeCardViewBinding dataBinding) {

                dataBinding.editTextNumber.setText(String.valueOf(model.getSap_code()));
                dataBinding.editTextdescription.setText(model.getSap_description());
                dataBinding.sapNoQtCard.setText(String.valueOf(model.getSap_qty()));

            }

            @Override
            public void onItemClick(Sap_code_Model model, int position) {

            }


        });

            mDataBinding.fabSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle= new Bundle();
                    bundle.putSerializable("Codes",data);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        Bundle bundle= new Bundle();
        bundle.putSerializable("Codes",data);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();

    }
}