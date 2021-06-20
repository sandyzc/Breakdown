package com.sandyzfeaklab.breakdown_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.sandyzfeaklab.breakdown_app.adaptors.Recycler_Adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class Add_Log extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Recycler_Adaptor adaptor;
    private final CollectionReference notebookRef = db.collection("log");

    DatePickerTimeline datePickerTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__log);





         RecyclerView rcv= findViewById(R.id.log_recyler_view);

        Query query = notebookRef.orderBy("date",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Model>options=new FirestoreRecyclerOptions.Builder<Model>().setQuery(query,Model.class).build();

        adaptor= new Recycler_Adaptor(options);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv.setAdapter(adaptor);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Add_Log.this,Data_input.class);
            startActivity(intent);
        });


      //  datePickerTimeline = findViewById(R.id.datePickerTimeline);

// Set a Start date (Default, 1 Jan 1970)

        final Calendar c= Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int date1 = c.get(Calendar.DATE);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 5);
//        datePickerTimeline.setActiveDate(date);


     //   datePickerTimeline.setInitialDate(2021, 1,1);

// Set a date Selected Listener

//        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
//            @Override
//
//            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
//
//            }
//
//            @Override
//            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
//                // Do Something
//            }
//        });

//// Disable date
//        Date[] dates = {Calendar.getInstance().getTime()};
//        datePickerTimeline.deactivateDates(dates);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_filter,menu);

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
    }


}
