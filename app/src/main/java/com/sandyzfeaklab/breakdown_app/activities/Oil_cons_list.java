package com.sandyzfeaklab.breakdown_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.adaptors.Oil_cons_list_adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Oil_cons_list extends AppCompatActivity {

    String area_selected;
    RecyclerView recyclerView;
    Oil_cons_list_adaptor adaptor;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("oil Consump");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oil_cons_list);


        recyclerView=findViewById(R.id.oil_cons_list_rcv);

        Bundle bundle = getIntent().getExtras();
        area_selected  = bundle.getString("Area");
        getSupportActionBar().setTitle(area_selected);

        Date date = new Date();

        SimpleDateFormat localDateFormat = new SimpleDateFormat("MMMM");

        String mnth = localDateFormat.format(date);

        Query query = notebookRef.whereEqualTo("area",area_selected).whereEqualTo("mnth", mnth).orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<OIl_Consump_model> options= new FirestoreRecyclerOptions.Builder<OIl_Consump_model>().setQuery(query,OIl_Consump_model.class).build();

        adaptor=new Oil_cons_list_adaptor(options,this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Oil_cons_list.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adaptor);




    }

    @Override
    protected void onStart() {
        super.onStart();
adaptor.startListening();


    }


}