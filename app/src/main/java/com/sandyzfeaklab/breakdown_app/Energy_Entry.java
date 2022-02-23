package com.sandyzfeaklab.breakdown_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.adaptors.Energy_Rcv_Adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.Energy_cons_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class Energy_Entry extends AppCompatActivity {
    AnyChartView anyChartView;
    ArrayAdapter<CharSequence> dept_adap;
    boolean isAllFieldsChecked = false;
    CollectionReference reference = FirebaseFirestore.getInstance().collection("Energy_readings");
    Energy_cons_model model;
    Date date;
    RecyclerView rcView;
    Energy_Rcv_Adaptor adaptor;
    private Dialog dialog;
    private FirebaseAuth mAuth;
    //Previous day valurs to substract with today reading
    Long pre_HPDC1,  pre_HPDC2,  pre_HPDC3,  pre_GSPM2B,  pre_GSPM3B1,  pre_GSPM3B2,  pre_rotoCast,  pre_GSPM3B3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_entry);
        rcView=findViewById(R.id.energy_recycle);
        mAuth = FirebaseAuth.getInstance();
        date = new Date();

        anyChartView = findViewById(R.id.energy_daily_graph);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
     //   initChart();
        fabInit();

        Query query = reference.orderBy("ts", Query.Direction.DESCENDING);



        FirestoreRecyclerOptions<Energy_cons_model> options= new FirestoreRecyclerOptions.Builder<Energy_cons_model>().setQuery(query,Energy_cons_model.class).build();

        adaptor= new Energy_Rcv_Adaptor(options,this);

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        rcView.setAdapter(adaptor);





    }

    private void show_data_entry_dialog(@NonNull String area) {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.energy_dailog_custom);
        Spinner spinner = dialog.findViewById(R.id.energy_spinner);
        Button save = dialog.findViewById(R.id.energy_save);
        Button cancel = dialog.findViewById(R.id.energy_cancel);
        TextInputEditText reading = dialog.findViewById(R.id.energy_reading);

        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        if (area.equals("HPDC")) {
            dept_adap = ArrayAdapter.createFromResource(this, R.array.HPDC, R.layout.spinner_ext);
        } else if (area.equals("GSPM")) {
            dept_adap = ArrayAdapter.createFromResource(this, R.array.GSPM, R.layout.spinner_ext);
        }

        spinner.setAdapter(dept_adap);
        updateFeild(reading, spinner);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = checkFeilds(reading);

                if (isAllFieldsChecked) {

                    savelog(spinner.getSelectedItem().toString(), Long.valueOf(reading.getText().toString()));
                    dialog.dismiss();

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateFeild(TextInputEditText reading, Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (model != null) {

                    if (parent.getItemAtPosition(position).toString().equals("hpdc1")) {
                        if (model.getHpdc1() != null) {
                            reading.setText(String.valueOf(model.getHpdc1()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }

                    }
                    if (parent.getItemAtPosition(position).toString().equals("hpdc2")) {
                        if (model.getHpdc2() != null) {
                            reading.setText(String.valueOf(model.getHpdc2()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                    if (parent.getItemAtPosition(position).toString().equals("hpdc3")) {
                        if (model.getHpdc3() != null) {
                            reading.setText(String.valueOf(model.getHpdc3()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }

                    }
                    if (parent.getItemAtPosition(position).toString().equals("gspm2B")) {

                        if (model.getGspm2B() != null) {
                            reading.setText(String.valueOf(model.getGspm2B()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                    if (parent.getItemAtPosition(position).toString().equals("gspm3B1")) {
                        if (model.getGspm3B1() != null) {
                            reading.setText(String.valueOf(model.getGspm3B1()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                    if (parent.getItemAtPosition(position).toString().equals("gspm3B2")) {
                        if (model.getGspm3B2() != null) {
                            reading.setText(String.valueOf(model.getGspm3B2()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                    if (parent.getItemAtPosition(position).toString().equals("gspm3B3")) {
                        if (model.getGspm3B3() != null) {
                            reading.setText(String.valueOf(model.getGspm3B3()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                    if (parent.getItemAtPosition(position).toString().equals("rotoCast")) {
                        if (model.getRotoCast() != null) {
                            reading.setText(String.valueOf(model.getRotoCast()));
                        } else {
                            Objects.requireNonNull(reading.getText()).clear();
                        }


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void savelog(String equipmentName, Long reading) {



        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        SimpleDateFormat localDateFormat;
        localDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String date1 = localDateFormat.format(date);


        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDay = calendar.getTime();

        String previousDate= localDateFormat.format(previousDay);

        reference.document(previousDate).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Energy_cons_model previousDayModel = documentSnapshot.toObject(Energy_cons_model.class);

                assert previousDayModel != null;
                pre_HPDC1=previousDayModel.getHpdc1();
                pre_HPDC2=previousDayModel.getHpdc2();
                pre_HPDC3=previousDayModel.getHpdc3();
                pre_GSPM2B=previousDayModel.getGspm2B();
                pre_GSPM3B1=previousDayModel.getGspm3B1();
                pre_GSPM3B2=previousDayModel.getGspm3B2();
                pre_rotoCast=previousDayModel.getGspm3B3();
                pre_GSPM3B3=previousDayModel.getRotoCast();

            }
        });


        reference.document(date1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {

                    if (equipmentName.equals("hpdc1")){

                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),"hpdc1Units",reading-pre_HPDC1);
                    }
                    if (equipmentName.equals("hpdc2")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_HPDC2);
                    }
                    if (equipmentName.equals("hpdc3")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_HPDC3);
                    }
                    if (equipmentName.equals("gspm2B")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_GSPM2B);
                    }
                    if (equipmentName.equals("gspm3B1")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_GSPM3B1);
                    }
                    if (equipmentName.equals("gspm3B2")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_GSPM3B2);
                    }
                    if (equipmentName.equals("gspm3B3")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_GSPM3B3);
                    }
                    if (equipmentName.equals("rotoCast")){
                        reference.document(date1).update(equipmentName, reading,"ts",date,"name",user.getDisplayName(),equipmentName+"Units",reading-pre_rotoCast);
                    }

                } else {

                    //uid, name,  hpdc1,  hpdc2,  hpdc3,  gspm2B,  gspm3B1,  gspm3B2,  rotoCast,  gspm3B3,  hpdc1Units,
                    //                              hpdc2Units,  hpdc3Units,  gspm2BUnits,  gspm3B1Units,  gspm3B2Units,  rotoCastUnits,  gspm3B3Units, Date ts

                    reference.document(date1).set(new Energy_cons_model(user.getUid(), user.getDisplayName(),
                            0L, 0L, 0L, 0L, 0L, 0L,
                            0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,date));

                    reference.document(date1).update(equipmentName, reading);


                }

            }
        });


    }

    private void checkDocument() {

        SimpleDateFormat localDateFormat;
        localDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = localDateFormat.format(date);

        reference.document(date1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {

                    model = documentSnapshot.toObject(Energy_cons_model.class);


                }

            }
        });
    }

    private Boolean checkFeilds(TextInputEditText reading) {

        if (reading.length() < 5 && Integer.parseInt(Objects.requireNonNull(reading.getText()).toString())>0) {
            reading.setError("Enter Valid Reading");
            return false;
        }


        return true;
    }

    private void fabInit() {
        FabSpeedDial fabSpeedDial = findViewById(R.id.energy_fab);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                //TODO: add hpdc finishing option i.e trimming


                String area;
                switch (menuItem.getItemId()) {

                    case R.id.HPDC_ENERGY:
                        area = "HPDC";
                        show_data_entry_dialog(area);

                        return true;
                    case R.id.GSPM_ENERGY:
                        area = "GSPM";
                        show_data_entry_dialog(area);
                        return true;


                }
                return false;
            }
        });
    }

    public void updateChart(List<DataEntry> seriesData)    {
        Set set = Set.instantiate();
        set.data(seriesData);
        // Number ip3,Number gspm2b,Number gspm3b1,Number gspm3b2,Number gspm3b3,Number roto
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
        Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
        Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");
        Mapping series7Mapping = set.mapAs("{ x: 'x', value: 'value7' }");
        Mapping series8Mapping = set.mapAs("{ x: 'x', value: 'value8' }");

        Cartesian cartesian = AnyChart.line();

        Line series1 = cartesian.line(series1Mapping);
        series1.name("HPDC-1");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("HPDC-2");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("HPDC-3");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series4 = cartesian.line(series4Mapping);
        series4.name("2B");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

                Line series5 = cartesian.line(series5Mapping);
        series5.name("3B1");
        series5.hovered().markers().enabled(true);
        series5.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series5.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series6 = cartesian.line(series6Mapping);
        series6.name("3B2");
        series6.hovered().markers().enabled(true);
        series6.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series6.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series7 = cartesian.line(series7Mapping);
        series7.name("3B3");
        series7.hovered().markers().enabled(true);
        series7.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series7.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series8 = cartesian.line(series8Mapping);
        series8.name("RotoCast");
        series8.hovered().markers().enabled(true);
        series8.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series8.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }



    @Override
    protected void onStart() {
        super.onStart();
        checkDocument();
        adaptor.startListening();
        getDataFromFirestoreAndUpdate();



    }

    private void getDataFromFirestoreAndUpdate(){
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DataEntry> seriesData = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Energy_cons_model model=documentSnapshot.toObject(Energy_cons_model.class);

                    SimpleDateFormat localDateFormat2 ;
                    localDateFormat2 = new SimpleDateFormat("dd-MMM");
                    String da= localDateFormat2.format(model.getTs());


                    seriesData.add(new CustomDataEntry(da,model.getHpdc1Units(),model.getHpdc2Units()
                            ,model.getHpdc3Units(),model.getGspm2BUnits(),
                            model.getGspm3B1Units(),model.getGspm3B2Units(),
                            model.getGspm3B3Units(),model.getRotoCastUnits()));

                }
                updateChart(seriesData);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        reference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                checkDocument();
                getDataFromFirestoreAndUpdate();


            }
        });
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number ip1, Number ip2, Number ip3,Number gs2b,Number gs3b1,Number gs3b2,Number gs3b3,Number rc) {
            super(x, ip1);
            setValue("value2", ip2);
            setValue("value3", ip3);
            setValue("value4", gs2b);
            setValue("value5", gs3b1);
            setValue("value6", gs3b2);
            setValue("value7", gs3b3);
            setValue("value8", rc);
        }

    }
}