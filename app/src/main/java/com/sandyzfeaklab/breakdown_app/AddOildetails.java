package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddOildetails extends AppCompatActivity {

    TextInputEditText qty, reason;
    private FirebaseAuth mAuth;
    Spinner dept, oiltype;
    ArrayAdapter<CharSequence> dept_adap;
    String area_selected, type,id;
    Button save;
    OIl_Consump_model model;
    boolean isAllFieldsChecked = false;
    ArrayAdapter<String> ad;
    CollectionReference reference = FirebaseFirestore.getInstance().collection("oil Consump");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_oil_log);

        intViews();
        mAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        area_selected = bundle.getString("Area");


        if (type.equals("update")) {
            Bundle bundle1 = getIntent().getExtras();
            id = bundle1.getString("id");

            if (!id.equals("")) {
                updateData(id);
            }
        } else if (type.equals("save")) {
            setAdapt(area_selected);
        }


        String[] oiltype_adap = {"ULTRASAFE 620", "HLP68"};

        ad = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                oiltype_adap);


        oiltype.setAdapter(ad);


    }

    private void setAdapt(String area_selected) {

        if (area_selected.equals("HPDC")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.HPDC, android.R.layout.simple_spinner_item);
        }
        if (area_selected.equals("GSPM")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.GSPM, android.R.layout.simple_spinner_item);

        }

        if (area_selected.equals("Finishing")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.Finishing, android.R.layout.simple_spinner_item);

        }
        if (area_selected.equals("CoreShop")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.Coreshop, android.R.layout.simple_spinner_item);
        }

        dept_adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dept.setAdapter(dept_adap);

    }


    private void intViews() {
        qty = findViewById(R.id.add_oil_cons_dialog_select_qnty);
        reason = findViewById(R.id.add_oil_cons_dialog_select_reason);
        dept = findViewById(R.id.add_oil_cons_dialog_select_dept);
        oiltype = findViewById(R.id.add_oil_cons_dialog_select_oil);
        save = findViewById(R.id.add_oil_cons_dialog_save);


    }

    public void SAve_oil(View view) {
        //String oilType, String reason, String equip, String id, String area, int qty

        isAllFieldsChecked = checkFeilds();

        if (isAllFieldsChecked) {

            if (type.equals("update")){
                updateSavedLog(id);
            }else {
                newLog();
            }


            finish();
        }

    }

    private void updateSavedLog(String id){

        reference.document(id).update("equip",dept.getSelectedItem().toString());
        reference.document(id).update("oilType",oiltype.getSelectedItem().toString());
        reference.document(id).update("qty",Integer.parseInt(Objects.requireNonNull(qty.getText()).toString()));
        reference.document(id).update("reason", Objects.requireNonNull(reason.getText()).toString());
    }

    private void newLog(){

        Date date = new Date();

        SimpleDateFormat localDateFormat;
        localDateFormat = new SimpleDateFormat("MMMM");

        String mnth = localDateFormat.format(date);
        FirebaseUser user = mAuth.getCurrentUser();

        reference.add(new OIl_Consump_model(oiltype.getSelectedItem().toString(),
                Objects.requireNonNull(reason.getText()).toString(),
                dept.getSelectedItem().toString(),
                "",
                area_selected,
                Integer.parseInt(Objects.requireNonNull(qty.getText()).toString()), date, mnth,user.getDisplayName(),user.getUid())).
                addOnSuccessListener(documentReference -> documentReference.update("id", documentReference.getId()));

    }

    private Boolean checkFeilds() {

        if (qty.length() < 1) {
            qty.setError("Quantity cannot be empty");
            return false;
        }

        if (Integer.parseInt(Objects.requireNonNull(qty.getText()).toString()) <= 0) {
            qty.setError("Quantity should be more than 0");
            return false;
        }

        if (Objects.requireNonNull(reason.getText()).toString().length() < 4) {

            reason.setError("Enter Proper reason for OIl top Up");
            return false;

        }

        return true;
    }

    @SuppressLint("SetTextI18n")
    private void updateData(String id) {

        reference.document(id).get().addOnSuccessListener(documentSnapshot -> {
            model = documentSnapshot.toObject(OIl_Consump_model.class);

            assert model != null;


            qty.setText(String.valueOf(model.getQty()));
            reason.setText(model.getReason());

            setAdapt(model.getArea());
            save.setText("Update");


        });

    }

}
