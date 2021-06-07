package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Model1;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Data_input extends AppCompatActivity {

    String Equipment_name = "Equipment Name",
            Work_Type = "Work Type",
            operation = "Operation",
            Part = "Part Name",
            Date="",
            problem_desc = "Problem", action_taken = "Action Taken", spares_used = "Spares Used", sap_no = "", Start_Time = " Start Time", end_time = "End Time", Action_taken_by = "Action taken by ";

    TextView starttime, endtime, time_taken;
    EditText part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by;
    Date start,end;

    ArrayList<Sap_code_Model> models = new ArrayList<>();

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = FirebaseFirestore.getInstance().collection("log");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null) {
                    models = (ArrayList<Sap_code_Model>) data.getExtras().getSerializable("Codes");
                    String sapcode = "";

                    for (int i = 0; i < models.size(); i++) {

                        sapcode += models.get(i).getSap_description() + " Qty - " + models.get(i).getSap_qty() + "\n";

                    }
                    Toast.makeText(this, sapcode, Toast.LENGTH_SHORT).show();
                    spares_used_et.setText(sapcode);
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_data_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spares_used_et = findViewById(R.id.spares_used_et);
        work_done_by = findViewById(R.id.work_done_by_et);
        part_name = findViewById(R.id.partname);
        problem_desc_et = findViewById(R.id.problem_desc_et);
        action_taken_et = findViewById(R.id.action_taken_et);


        //  sap_no_et=findViewById(R.id.sap_no_et);


        Button save_butt = findViewById(R.id.save_data);
        Button add_sap_code = findViewById(R.id.add_sap_code);

        TextView date = findViewById(R.id.date_main);


        starttime = findViewById(R.id.start_time);
        endtime = findViewById(R.id.end_time);
        time_taken = findViewById(R.id.time_taken);


        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        date.setText("Date : " + date1);

        Spinner equpi_list_spinne = (Spinner) findViewById(R.id.equip_list_spin);
        Spinner work_type_spinner = (Spinner) findViewById(R.id.work_type_spin);
        Spinner operation_spinner = (Spinner) findViewById(R.id.operation_spin);
        Spinner problem_category = findViewById(R.id.prob_cat);
        Spinner stoppage_category = findViewById(R.id.stoppage_cat);

        ArrayAdapter<CharSequence> equip_lis_adapter = ArrayAdapter.createFromResource(this, R.array.Equipment_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> work_type_adapter = ArrayAdapter.createFromResource(this, R.array.Work_Type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> operation_type_adapter = ArrayAdapter.createFromResource(this, R.array.Operation, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> problem_category_adapter = ArrayAdapter.createFromResource(this, R.array.Problem_Category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> stoppage_category_adapter = ArrayAdapter.createFromResource(this, R.array.Stoppage_Category, android.R.layout.simple_spinner_item);

        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        work_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problem_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stoppage_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        equpi_list_spinne.setAdapter(equip_lis_adapter);
        work_type_spinner.setAdapter(work_type_adapter);
        operation_spinner.setAdapter(operation_type_adapter);
        problem_category.setAdapter(problem_category_adapter);
        stoppage_category.setAdapter(stoppage_category_adapter);


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                new SingleDateAndTimePickerDialog.Builder(Data_input.this).displayAmPm(true)
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
                                    start=date;

                                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/M/yyyy");
                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat1.format(date);
                                Date=localDateFormat.format(date);
                                starttime.setText(starttim);
                            }
                        }).display();


            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);


                new SingleDateAndTimePickerDialog.Builder(Data_input.this).displayAmPm(true)
                        //.bottomSheet()
                        .curved()
                        .minutesStep(1)
                        //.stepSizeMinutes(15)
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
                        .title("End Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                end=date;

                                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat1.format(date);
                                endtime.setText(starttim);



                                printDifference(start,end);


                            }
                        }).display();

            }
        });


        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String work_Type = work_type_spinner.getSelectedItem().toString();
                String operation = operation_spinner.getSelectedItem().toString();
                String equipment_name = equpi_list_spinne.getSelectedItem().toString();


                if (endtime.getText().toString().equals(" End TIME : ")) {
                    FlatDialog flatDialog = new FlatDialog(Data_input.this);

                    flatDialog.setTitle("Want to mark as pending ??")

                            .setFirstButtonText("Yes").setSecondButtonText("NO").isCancelable(true).withFirstButtonListner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            reference.add(new Model(equipment_name, work_Type,
                                    operation, part_name.getText().toString(),
                                    problem_desc_et.getText().toString(),
                                    action_taken_et.getText().toString(),
                                    spares_used_et.getText().toString(),
                                    models, starttime.getText().toString(),
                                    endtime.getText().toString(),
                                    work_done_by.getText().toString(),"Pending",Date)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Data_input.this, "Added Sucussfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        }
                    }).withSecondButtonListner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flatDialog.cancel();
                        }
                    }).show();

                } else {
                    //adding data to firestore
                    reference.add(new Model(equipment_name,
                            work_Type,
                            operation,
                            part_name.getText().toString(),
                            problem_desc_et.getText().toString(),
                            action_taken_et.getText().toString(),
                            spares_used_et.getText().toString(),
                            models,
                            starttime.getText().toString(),
                            endtime.getText().toString(),
                            work_done_by.getText().toString(),
                            "Compleated",
                            Date)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Data_input.this, "Added Sucussfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }


            }
        });

        add_sap_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                uploaddate uploaddate= new uploaddate();
//                uploaddate.setPriority(10);
//
//                uploaddate.run();

                Intent intent = new Intent(Data_input.this, updata.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
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

        long elapsedMinutes = different / minutesInMilli + elapsedHours*60 + elapsedDays*24*60;
        different = different % minutesInMilli;

        time_taken.setText( elapsedMinutes +" Min");


    }

        class uploaddate extends Thread{



            CollectionReference reference = FirebaseFirestore.getInstance().collection("log");
            ArrayList<Sap_code_Model> models = new ArrayList<>();

            @Override
            public void run() {

                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                String json;
                try {
                    InputStream is = getAssets().open("data.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    json= new String(buffer, StandardCharsets.UTF_8);
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        reference.add(new Model(
                                jsonObject.getString("equipment_name"),
                                jsonObject.getString("work_Type"),
                                jsonObject.getString("operation"),
                                jsonObject.getString("part"),
                                jsonObject.getString("problem_desc"),
                                jsonObject.getString("action_taken"),
                                jsonObject.getString("spares_used"),
                                models,
                                jsonObject.getString("start_Time"),
                                jsonObject.getString("end_time"),
                                jsonObject.getString("action_taken_by"),
                                jsonObject.getString("status"),
                                jsonObject.getString("Date")))
                        ;
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                super.run();
            }
        }
}


