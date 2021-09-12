package com.sandyzfeaklab.breakdown_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sandyzfeaklab.breakdown_app.adaptors.Recycler_Adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;

import java.util.ArrayList;

public class Pending_activity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Recycler_Adaptor adaptor;
    private CollectionReference notebookRef = db.collection("log");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RecyclerView rcv= findViewById(R.id.pending_rcv1);

        Query query = notebookRef.whereEqualTo("status","Pending");


        FirestoreRecyclerOptions<Model>options=new FirestoreRecyclerOptions.Builder<Model>().setQuery(query,Model.class).build();

        adaptor= new Recycler_Adaptor(options);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv.setAdapter(adaptor);



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