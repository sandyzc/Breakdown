package com.sandyzfeaklab.breakdown_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.dataModel.DateInputMask;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;

public class reportGen extends AppCompatActivity {

    EditText enteredDate;
    Spinner shift;
    TextView generatedReport;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = FirebaseFirestore.getInstance().collection("log");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_gen);
        enteredDate=findViewById(R.id.editTextDate2);
        shift=findViewById(R.id.shiftspinner);
        generatedReport=findViewById(R.id.generatedreport);

        //shift.setBackgroundResource(R.color.themeColor);

        ArrayAdapter<CharSequence> shiftadaptor = ArrayAdapter.createFromResource(this, R.array.Shift, android.R.layout.simple_spinner_item);
        shiftadaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        shift.setAdapter(shiftadaptor);


        new DateInputMask(enteredDate);


    }

    public void Genrate_report(View view) {

        String selectedShift=shift.getSelectedItem().toString();
        String dateEntered = enteredDate.getText().toString();

        generatedReport.setText("");
        reference.whereEqualTo("date", dateEntered).whereEqualTo("shift", selectedShift).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String aaa = "";
            int num=1;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Model model = documentSnapshot.toObject(Model.class);

                    aaa = aaa +num+ "#" + model.getEquipment_name()
                            + " " + model.getStart_Time()
                            + "\nProb: " + model.getProblem_desc()
                            + "\nAction: " + model.getAction_taken()
                            + "\nShift: " + model.getShift()
                            + "\n\n";
                    num++;

                }
                if (aaa.equals("")){
                    generatedReport.setText("no report found");
                }else {
                    generatedReport.setText(aaa);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("OnFilure", e.getMessage());

            }
        });



    }

    public void send_report(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, generatedReport.getText().toString());
        startActivity(intent);
    }
}