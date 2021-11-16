package com.sandyzfeaklab.breakdown_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sandyzfeaklab.breakdown_app.adaptors.Recycler_Adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.DataInput_Model;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Add_Log extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("log");
    Recycler_Adaptor adaptor;
    RecyclerView rcv;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__log);


        rcv = findViewById(R.id.log_recyler_view);

        Query query = notebookRef.orderBy("timestamp", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<DataInput_Model> options = new FirestoreRecyclerOptions.Builder<DataInput_Model>().setQuery(query, DataInput_Model.class).build();

        adaptor = new Recycler_Adaptor(options, this);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(Add_Log.this));
        rcv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv.setAdapter(adaptor);

        FabSpeedDial fabSpeedDial = findViewById(R.id.fab_speed_dial);

         fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                //TODO: add hpdc finishing option i.e trimming

                Intent intent = new Intent(Add_Log.this, Data_input.class);
                Bundle bundle= new Bundle();

                switch (menuItem.getItemId()){


                    case R.id.HPDC:
                        bundle.putString("Area","HPDC");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;
                    case R.id.GSPM:
                        bundle.putString("Area","GSPM");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;
                    case R.id.Melting:
                        bundle.putString("Area","Melting");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;
                    case R.id.Heat_Treatment:
                        bundle.putString("Area","Heat_Treatment");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;

                    case R.id.HotBox:
                        bundle.putString("Area","HotBox");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);

                        return true;
                    case R.id.ColdBox:
                        bundle.putString("Area","ColdBox");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);

                        return true;
                    case R.id.Sand_Reclamation:

                        bundle.putString("Area","Sand_Reclamation");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;
                    case R.id.Finishing:

                        bundle.putString("Area","Finishing");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;

                }

                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
                //shareDialog();
                Intent intent = new Intent(this, reportGen.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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


