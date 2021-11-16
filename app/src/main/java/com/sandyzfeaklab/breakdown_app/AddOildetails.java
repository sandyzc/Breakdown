package com.sandyzfeaklab.breakdown_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddOildetails extends AppCompatActivity {

    TextInputEditText qty,reason;
    Spinner dept,oiltype;
    ArrayAdapter<CharSequence> dept_adap;
    String area_selected;
    Button save;
    OIl_Consump_model model;
    boolean isAllFieldsChecked = false;
    ArrayAdapter ad;
    CollectionReference reference = FirebaseFirestore.getInstance().collection("oil Consump");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_oil_log);

        intViews();

        Bundle bundle = getIntent().getExtras();
        area_selected  = bundle.getString("Area");

        String id = bundle.getString("id");

        if (!id.equals("")){
            updateData(id);
        }
        else {
            setAdapt(area_selected);
        }

        String[] oiltype_adap= {"ULTRASAFE 620","HLP68"};

        ad= new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                oiltype_adap);



        oiltype.setAdapter(ad);


    }

    private void setAdapt(String area_selected){

        if (area_selected.equals("HPDC")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.HPDC, android.R.layout.simple_spinner_item);
        }
        if (area_selected.equals("GSPM")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.GSPM, android.R.layout.simple_spinner_item);

        }

        if (area_selected.equals("Finishing")) {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.Finishing, android.R.layout.simple_spinner_item);

        }
        if (area_selected.equals("CoreShop"))
        {
            dept_adap = ArrayAdapter.createFromResource(AddOildetails.this, R.array.Coreshop, android.R.layout.simple_spinner_item);
        }

        dept_adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dept.setAdapter(dept_adap);

    }


    private void intViews(){
        qty=findViewById(R.id.add_oil_cons_dialog_select_qnty);
        reason=findViewById(R.id.add_oil_cons_dialog_select_reason);
        dept=findViewById(R.id.add_oil_cons_dialog_select_dept);
        oiltype=findViewById(R.id.add_oil_cons_dialog_select_oil);
        save=findViewById(R.id.add_oil_cons_dialog_save);


    }

    public void SAve_oil(View view) {
        //String oilType, String reason, String equip, String id, String area, int qty

       isAllFieldsChecked= checkFeilds();

       if (isAllFieldsChecked){

           Date date=new Date();

           SimpleDateFormat localDateFormat = new SimpleDateFormat("MMMM");

           String mnth = localDateFormat.format(date);


           reference.add(new OIl_Consump_model(oiltype.getSelectedItem().toString(),
                   reason.getText().toString(),
                   dept.getSelectedItem().toString(),
                   "",
                   area_selected,
                   Integer.parseInt(Objects.requireNonNull(qty.getText()).toString()),date,mnth)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
               @Override
               public void onSuccess(DocumentReference documentReference) {
                   documentReference.update("id",documentReference.getId());
               }
           });


           finish();
       }

    }

    private Boolean checkFeilds(){

        if(qty.length()<1)
        {
            qty.setError("Quantity cannot be empty");
            return false;
        }

        if(Integer.parseInt(qty.getText().toString()) <= 0 ) {
            qty.setError("Quantity should be more than 0");
            return false;
        }

        if(Objects.requireNonNull(reason.getText()).toString().length()<4)
        {

            reason.setError("Enter Proper reason for OIl top Up");
                return false;

        }

        return true;
    }

    private void updateData(String id){

        reference.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                model=documentSnapshot.toObject(OIl_Consump_model.class);

                assert model!= null ;


                qty.setText(String.valueOf(model.getQty()));
                reason.setText(model.getReason());

                setAdapt(model.getArea());
                save.setText("Update");




            }
        });

    }

}
