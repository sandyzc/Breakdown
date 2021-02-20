package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data_input extends AppCompatActivity {

    String Part = "Part Name", problem_desc = "Problem", action_taken = "Action Taken", spares_used = "Spares Used", sap_no = "Sap No", operation = "Operation", end_time = "End Time", Action_taken_by = "Action taken by ", start_time = "Start Time";

    TextView starttime, endtime, time_taken;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_data_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText part_name, problem_desc_et, action_taken_et, spares_used_et, sap_no_et;


        part_name = findViewById(R.id.partname);
        problem_desc_et = findViewById(R.id.problem_desc_et);
        action_taken_et = findViewById(R.id.action_taken_et);
        spares_used_et = findViewById(R.id.spares_used_et);
        //  sap_no_et=findViewById(R.id.sap_no_et);


        Button save_butt = findViewById(R.id.save_data);
        Button add_sap_code=findViewById(R.id.add_sap_code);

        TextView date = findViewById(R.id.date_main);


        starttime = findViewById(R.id.start_time);
        endtime = findViewById(R.id.end_time);
        time_taken = findViewById(R.id.time_taken);


        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        date.setText("Date : " + date1);

        Spinner equpi_list_spinne = (Spinner) findViewById(R.id.equip_list_spin);
        Spinner work_type_spinner = (Spinner) findViewById(R.id.work_type_spin);
        Spinner operation_spinner = (Spinner) findViewById(R.id.operation_spin);
        Spinner problem_category = (Spinner) findViewById(R.id.prob_cat);
        Spinner stoppage_category = (Spinner) findViewById(R.id.stoppage_cat);

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


                                SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat.format(date);
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

                                SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm a");
                                String starttim = localDateFormat.format(date);
                                endtime.setText(starttim);

                                timeDiff();
                            }
                        }).display();

            }
        });


        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String work_type_selected = work_type_spinner.getSelectedItem().toString();
                String oper_type_selected = operation_spinner.getSelectedItem().toString();
                String equip_type_selected = equpi_list_spinne.getSelectedItem().toString();

                //String Part= "Part Name",problem_desc="Problem",action_taken="Action Taken",spares_used="Spares Used",sap_no="Sap No";

                Map<String, Object> data_log = new HashMap<>();
                data_log.put(Part, part_name.getText().toString());
                data_log.put(problem_desc, problem_desc_et.getText().toString());
                data_log.put(action_taken, action_taken_et.getText().toString());
                data_log.put(spares_used, spares_used_et.getText().toString());
                //data_log.put(sap_no,sap_no_et.getText().toString());
                data_log.put(operation, oper_type_selected);
                data_log.put(end_time, endtime.getText().toString());
                data_log.put(start_time, starttime.getText().toString());

                db.collection(equip_type_selected).document(date.getText().toString()).set(data_log).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Data_input.this, "Sucessfully added", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        add_sap_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Data_input.this,Add_Sap_codes.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void timeDiff() {
        String inputStart = starttime.getText().toString().toUpperCase();
        String inputStop = endtime.getText().toString().toUpperCase();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime start = LocalTime.parse(inputStart, f);
        LocalTime stop = LocalTime.parse(inputStop, f);
        Duration d = Duration.between(start, stop);

        if (d.toHours() > 24.0) {
            Toast.makeText(Data_input.this, " More than 24 Hrs", Toast.LENGTH_LONG).show();
        } else {
            String diff_hr = String.valueOf(d);
            String diff_mm = String.valueOf(d.toMinutes());

            time_taken.setText(diff_mm + " Min ");
        }


    }


}