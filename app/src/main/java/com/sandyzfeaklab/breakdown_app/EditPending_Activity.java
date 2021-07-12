package com.sandyzfeaklab.breakdown_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPending_Activity extends AppCompatActivity {

    TextView starttime, endtime, pend_partname, pend_equip_list_spin,  pend_operation_spin, date_txt, pend_area;
    EditText problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken;
    Spinner pend_work_type_spin;
    Date start, end;
    Button save;
    int time;
    boolean isAllFieldsChecked = false;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pending);

        pend_partname = findViewById(R.id.pend_partname);
        pend_equip_list_spin = findViewById(R.id.pend_equip_list_spin);
        pend_work_type_spin = findViewById(R.id.pend_work_type_spin);
        pend_operation_spin = findViewById(R.id.pend_operation_spin);
        starttime = findViewById(R.id.pend_start_time);
        endtime = findViewById(R.id.pend_end_time);
        date_txt = findViewById(R.id.edit_pend_date_main);

        problem_desc_et = findViewById(R.id.pend_problem_desc_et);
        action_taken_et = findViewById(R.id.pend_action_taken_et);
        spares_used_et = findViewById(R.id.pend_spares_used_et);
        work_done_by = findViewById(R.id.pend_work_done_by_et);
        time_taken = findViewById(R.id.pend_time_taken);
        pend_area = findViewById(R.id.pend_spinner_area);
        save=findViewById(R.id.edit_pend_save_data);


        Spinner problem_category = findViewById(R.id.edit_prob_cat);
        Spinner stoppage_category = findViewById(R.id.edit_stoppage_cat);


        ArrayAdapter<CharSequence> work_type_adapter = ArrayAdapter.createFromResource(this, R.array.Work_Type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> problem_category_adapter = ArrayAdapter.createFromResource(this, R.array.Problem_Category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> stoppage_category_adapter = ArrayAdapter.createFromResource(this, R.array.Stoppage_Category, android.R.layout.simple_spinner_item);


        work_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        problem_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stoppage_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        problem_category.setAdapter(problem_category_adapter);
        stoppage_category.setAdapter(stoppage_category_adapter);
        pend_work_type_spin.setAdapter(work_type_adapter);
        Bundle bundle = getIntent().getExtras();

        String id = bundle.getString("id");

        pend_partname.setText(id);


        documentReference = db.collection("log").document(id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Model model = documentSnapshot.toObject(Model.class);

                pend_partname.setText(model.getPart());
                pend_equip_list_spin.setText(model.getEquipment_name());
                pend_operation_spin.setText(model.getOperation());
                starttime.setText(model.getStart_Time());
                endtime.setText(model.getEnd_time());
                pend_area.setText(model.getArea());

                problem_desc_et.setText(model.getProblem_desc());
                action_taken_et.setText(model.getAction_taken());
                spares_used_et.setText(model.getSpares_used());
                work_done_by.setText(model.getaction_taken_by());
                ;

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();

                CheckAllFields();
// operation,
//                        part,
//                        problem_desc
//                        ,action_taken
//                        ,spares_used
//                        , start_Time
//                        ,end_time
//                        , action_taken_by
//                        ,status
//                        ,stoppage_category
//                        ,problem_category
//                        ,Date
//                        ,area
//                        ,id
//                        ,"pending_remarks"

                documentReference.update(
                        "work_Type",pend_work_type_spin.getSelectedItem().toString(),"problem_desc",problem_desc_et.getText().toString()
                        ,"action_taken",action_taken_et.getText().toString(),"spares_used",spares_used_et.getText().toString(),"stoppage_category",stoppage_category.getSelectedItem().toString()
                        ,"problem_category",problem_category.getSelectedItem().toString()
                        ,"start_Time",starttime.getText().toString()
                        ,"end_time",endtime.getText().toString()
                        ,"time_taken",time
                        ,"action_taken_by",work_done_by.getText().toString()
                        ,"status","Compleated"
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(EditPending_Activity.this, "Work Updated", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                new SingleDateAndTimePickerDialog.Builder(EditPending_Activity.this).displayAmPm(true)
                        //.bottomSheet()
                        .curved()
                        .minutesStep(1)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                // Retrieve the SingleDateAndTimePicker
                            }

                            @Override
                            public void onClosed(SingleDateAndTimePicker picker) {
                                // On dialog closed
                            }
                        })
                        .title("Start Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                start = date;

                                String Date;

                                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/M/yyyy");
                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat1.format(date);
                                Date = localDateFormat.format(date);
                                starttime.setText(starttim);

                            }
                        }).display();


            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SingleDateAndTimePickerDialog.Builder(EditPending_Activity.this).displayAmPm(true)
                        .curved()
                        .minutesStep(1)
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                // Retrieve the SingleDateAndTimePicker
                            }

                            @Override
                            public void onClosed(SingleDateAndTimePicker picker) {
                                // On dialog closed
                            }
                        })
                        .title("End Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                end = date;

                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat1.format(date);
                                endtime.setText(starttim);


                                printDifference(start, end);


                            }
                        }).display();

            }
        });



    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli + elapsedHours * 60 + elapsedDays * 24 * 60;
        different = different % minutesInMilli;
        time = (int) elapsedMinutes;

        time_taken.setText(elapsedMinutes + " Min");


    }

    private Boolean CheckAllFields(){
        //part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken
        if (pend_partname.length()==0){
            pend_partname.setError("Field Cannot be empty");
            pend_partname.setHint("Field Cannot be empty");
            return false;
        }
        if (problem_desc_et.length()<3){
            problem_desc_et.setError("Please enter proper details");

            return false;
        }
        if (action_taken_et.length()<3){
            action_taken_et.setError("Please enter proper details");
            return false;
        }
        if (work_done_by.length()<2){
            work_done_by.setError("Please enter proper details");
            return false;
        }
        return true;

    }
}