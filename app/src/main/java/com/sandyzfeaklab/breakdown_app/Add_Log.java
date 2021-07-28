package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.adaptors.Recycler_Adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.DateInputMask;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Add_Log extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Recycler_Adaptor adaptor;
    RecyclerView rcv;
    String logmsg = "";

    private final CollectionReference notebookRef = db.collection("log");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__log);


        rcv = findViewById(R.id.log_recyler_view);

        Query query = notebookRef.orderBy("timestamp", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();

        adaptor = new Recycler_Adaptor(options);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(Add_Log.this));
        rcv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv.setAdapter(adaptor);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Add_Log.this, Data_input.class);
            startActivity(intent);
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
                shareDialog();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Add_Log.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Add_Log.this).inflate(R.layout.share_details_dialog, viewGroup, false);
        alertDialog.setView(dialogView);
        AlertDialog alertDialog2 = alertDialog.create();
        alertDialog2.show();
        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        EditText dateEntered = dialogView.findViewById(R.id.share_et_date);
        CheckBox ashift = dialogView.findViewById(R.id.ashift);
        CheckBox bshift = dialogView.findViewById(R.id.bshift);
        CheckBox cshift = dialogView.findViewById(R.id.cshift);

      //  dateEntered.setText(date1);

        new DateInputMask(dateEntered);

        Button sharelog = dialogView.findViewById(R.id.share_log_btn);

        sharelog.setOnClickListener(v -> {

            String dateet = dateEntered.getText().toString();


            if (ashift.isChecked()) {

                notebookRef.whereGreaterThanOrEqualTo("shift", "A").whereEqualTo("date", dateet).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    String aaa = "";

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Model model = documentSnapshot.toObject(Model.class);

                            aaa = aaa + "#" + model.getEquipment_name()
                                    + " " + model.getStart_Time()
                                    + "\nProb: " + model.getProblem_desc()
                                    + "\nAction: " + model.getAction_taken()
                                    + "\n\n";

                        }
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, aaa);
                        startActivity(intent);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("OnFilure", e.getMessage());

                    }
                });

            }

            if (bshift.isChecked()) {

                Task task1 = notebookRef.whereGreaterThanOrEqualTo("shift", "B")
                        .whereEqualTo("date", dateet).get();

                Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1);

                allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                    @Override
                    public void onSuccess(List<QuerySnapshot> querySnapshots) {
                        String aaa = "";
                        for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Model model = documentSnapshot.toObject(Model.class);

                                aaa = aaa + "#" + model.getEquipment_name() + " " + model.getStart_Time() + " || "
                                        + model.getDate() + "\nProb: " + model.getProblem_desc() + "\naction: " + model.getAction_taken()+ "\n\n";

                            }
                        }
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, aaa);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("OnFilure", e.getMessage());

                    }
                });
            }

            if (cshift.isChecked()) {
                String yourDate = "";


                try {
                    Date myinputdate = new SimpleDateFormat("dd/MM/yyyy").parse(dateet);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(myinputdate);
                    cal.add(Calendar.DATE, 1);
                    yourDate = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                notebookRef.whereGreaterThanOrEqualTo("shift", "C").whereEqualTo("date", dateet).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    String aaa = "";

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Model model = documentSnapshot.toObject(Model.class);

                            aaa = aaa + "#" + model.getEquipment_name() + " " + model.getStart_Time() + " || "
                                    + model.getDate() + "\nProb: " + model.getProblem_desc() + "\naction: " + model.getAction_taken() + "\n\n";

                        }

                        logmsg = logmsg + aaa;

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("OnFilure", e.getMessage());

                    }
                });

                notebookRef.whereGreaterThanOrEqualTo("shift", "C").whereEqualTo("date", yourDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    String aaa = "";

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Model model = documentSnapshot.toObject(Model.class);

                            aaa = aaa + "#" + model.getEquipment_name() + " " + model.getStart_Time() + " || "
                                    + model.getDate() + "\nProb: " + model.getProblem_desc() + "\naction: " + model.getAction_taken() + "\n\n";

                        }

                        logmsg = logmsg + aaa;

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("OnFilure", e.getMessage());

                    }
                });



                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, logmsg);
                startActivity(intent);


            }

            alertDialog2.dismiss();

        });


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

class DateUtil {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
