package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Data_input extends AppCompatActivity {

    String Equipment_name = "Equipment Name",
            Work_Type = "Work Type",
            operation = "Operation",
            Part = "Part Name",
            Date = "",
            problem_desc = "Problem", action_taken = "Action Taken", spares_used = "Spares Used", sap_no = "", Start_Time = " Start Time", end_time = "End Time", Action_taken_by = "Action taken by ";
    int time;
    boolean isAllFieldsChecked = false;
    CheckBox checkBox;

    Timestamp timestampstart,timestampend;
    String shift="";

    TextView starttime, endtime;
    EditText part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken;
    Date start, end;
    ArrayAdapter<CharSequence> equip_lis_adapter;
    ArrayAdapter<CharSequence> operation_type_adapter;

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

        spares_used_et = findViewById(R.id.pend_spares_used_et);
        work_done_by = findViewById(R.id.pend_work_done_by_et);
        part_name = findViewById(R.id.pend_partname);
        problem_desc_et = findViewById(R.id.pend_problem_desc_et);
        action_taken_et = findViewById(R.id.pend_action_taken_et);
        checkBox = findViewById(R.id.checkBox);

        //  sap_no_et=findViewById(R.id.sap_no_et);


        Button save_butt = findViewById(R.id.pend_save_data);
        Button add_sap_code = findViewById(R.id.add_sap_code);

        TextView date = findViewById(R.id.pend_date_main);


        starttime = findViewById(R.id.pend_start_time);
        endtime = findViewById(R.id.pend_end_time);
        time_taken = findViewById(R.id.pend_time_taken);


        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        date.setText("Date : " + date1);

        Spinner equpi_list_spinne = (Spinner) findViewById(R.id.pend_equip_list_spin);
        Spinner work_type_spinner = (Spinner) findViewById(R.id.pend_work_type_spin);
        AutoCompleteTextView operation_spinner = (AutoCompleteTextView) findViewById(R.id.pend_operation_spin);
        Spinner problem_category = findViewById(R.id.pend_edit_prob_cat);
        Spinner stoppage_category = findViewById(R.id.pend_edit_stoppage_cat);
        Spinner area = findViewById(R.id.pend_spinner_area);


        ArrayAdapter<CharSequence> area_adapter = ArrayAdapter.createFromResource(this, R.array.Area, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> work_type_adapter = ArrayAdapter.createFromResource(this, R.array.Work_Type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> problem_category_adapter = ArrayAdapter.createFromResource(this, R.array.Problem_Category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> stoppage_category_adapter = ArrayAdapter.createFromResource(this, R.array.Stoppage_Category, android.R.layout.simple_spinner_item);


        area_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        work_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        problem_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stoppage_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        area.setAdapter(area_adapter);

        work_type_spinner.setAdapter(work_type_adapter);

        problem_category.setAdapter(problem_category_adapter);
        stoppage_category.setAdapter(stoppage_category_adapter);

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 1:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HPDC, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HPDC_ope, android.R.layout.simple_spinner_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 2:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.GSPM, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.GSPM_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 3:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.ColdBox, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.ColdBox_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 4:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HotBox, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HotBox_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 5:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.SandReclamation, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.SandReclamation, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 6:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingHPDC, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingHPDC_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 7:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingGSPM, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingGSPM_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 8:
                        equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Heat_Treatment, android.R.layout.simple_spinner_item);
                        operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Heat_Treatment_ope, android.R.layout.simple_spinner_item);
                        equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                }

                equpi_list_spinne.setAdapter(equip_lis_adapter);
                operation_spinner.setAdapter(operation_type_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting, android.R.layout.simple_spinner_item);
                operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting_ope, android.R.layout.simple_spinner_item);
            }
        });


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                start = date;

                                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                String starttim = localDateFormat1.format(date);
                                Date = localDateFormat.format(date);
                                starttime.setText(starttim);

                                timestampstart = new Timestamp(date.getTime());


                            }
                        }).display();


            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SingleDateAndTimePickerDialog.Builder(Data_input.this).displayAmPm(true)
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

                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                String starttim = localDateFormat1.format(date);
                                endtime.setText(starttim);

                                timestampend= new Timestamp(date.getTime());


                                printDifference(start, end);


                            }
                        }).display();

            }
        });


        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date astart = null,aend = null,bend = null,cshift = null,currentime = null;

                isAllFieldsChecked = CheckAllFields();

                try {
                    astart=new SimpleDateFormat("HH:mm a").parse("05:59 AM");
                    aend=new SimpleDateFormat("HH:mm a").parse("14:00 PM");
                    bend=new SimpleDateFormat("HH:mm a").parse("22:00 PM");
                    cshift=new SimpleDateFormat("HH:mm a").parse("00:00 AM");
                    currentime=new SimpleDateFormat("HH:mm a").parse(starttime.getText().toString());

                    assert currentime != null;
                    if (currentime.after(astart)&&currentime.before(aend)){
                        shift="A";
                    }
                    else if (currentime.after(aend)&&currentime
                            .before(bend)){
                        shift="B";

                    }else if (currentime.after(bend)&&currentime
                            .before(astart)|| currentime.after(cshift)&&currentime.before(astart)){
                        shift="C";

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                if (isAllFieldsChecked) {

                    String work_Type = work_type_spinner.getSelectedItem().toString();
                    String operation = operation_spinner.getText().toString();
                    String equipment_name = equpi_list_spinne.getSelectedItem().toString();


                    if (endtime.getText().toString().equals("END TIME") || checkBox.isChecked()) {
                        FlatDialog flatDialog = new FlatDialog(Data_input.this);

                        flatDialog.setTitle("Want to mark as pending ??")

                                .setFirstButtonText("Yes").setSecondButtonText("NO").setLargeTextField("").setLargeTextFieldHint("*Remarks on why pending").isCancelable(true).withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (Date.equals("")) {
                                    Date = date1;
                                }




                                reference.add(new Model(area.getSelectedItem().toString(),stoppage_category.getSelectedItem().toString(),problem_category.getSelectedItem().toString(),equipment_name, work_Type,
                                        operation, part_name.getText().toString(),
                                        problem_desc_et.getText().toString(),
                                        action_taken_et.getText().toString(),
                                        spares_used_et.getText().toString(),
                                        models, starttime.getText().toString(),
                                        " ",
                                        work_done_by.getText().toString(), "Pending", Date, time, "", flatDialog.getLargeTextField(),shift,timestampstart)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(Data_input.this, "Added Sucussfully", Toast.LENGTH_SHORT).show();
                                        documentReference.update("id", documentReference.getId());
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

                        reference.add(new Model(area.getSelectedItem().toString(),stoppage_category.getSelectedItem().toString(),problem_category.getSelectedItem().toString(),equipment_name,
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
                                Date, time, reference.document().getId(), "",shift,timestampend)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Data_input.this, "Added Sucussfully", Toast.LENGTH_SHORT).show();
                                documentReference.update("id", documentReference.getId());

                            }
                        });
                        finish();
                    }
                }

            }
        });


        add_sap_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Data_input.this, Add_Sap_codes.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    private Boolean CheckAllFields(){
        //part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken
        if (part_name.length()==0){
            part_name.setError("Field Cannot be empty");
            part_name.setHint("Field Cannot be empty");
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

}


