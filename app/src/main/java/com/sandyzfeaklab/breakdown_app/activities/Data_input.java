package com.sandyzfeaklab.breakdown_app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.DataInput_Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;

public class Data_input extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    private static final int REQUEST_GET_BEFORE_FILE = 2;
    String Date = "",BEFORE_URI,AFTER_URI;
    StorageReference riversRef, riversRef1;
    int time;
    boolean isAllFieldsChecked = false;
    CheckBox checkBox, gshift, cb_Breakdown,cb_ser_req,cb_planned;
    ImageView beforepic, afterpic;
    Timestamp timestampstart;
    String shift = "";
    TextView starttime, endtime;
    EditText part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken;
    Date start, end;
    ArrayAdapter<CharSequence> equip_lis_adapter;
    ArrayAdapter<CharSequence> operation_type_adapter;
    ArrayList<Sap_code_Model> models = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = FirebaseFirestore.getInstance().collection("log");

    // Create a Cloud Storage reference from the app
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_data_input);


        //TODO : Workdone by using multi auto compleate view
        //TODO: createlist of people


        spares_used_et = findViewById(R.id.pend_spares_used_et);
        work_done_by = findViewById(R.id.pend_work_done_by_et);
        part_name = findViewById(R.id.partname);
        problem_desc_et = findViewById(R.id.pend_problem_desc_et);
        action_taken_et = findViewById(R.id.pend_action_taken_et);
        checkBox = findViewById(R.id.checkBox);
        gshift = findViewById(R.id.G_SHiftcheckBox);
        beforepic = findViewById(R.id.before_pic);
        afterpic = findViewById(R.id.after_pic);
        cb_Breakdown=findViewById(R.id.cb_DI_Breakdown);
        cb_ser_req=findViewById(R.id.cb_DI_Service_REQ);
        cb_planned=findViewById(R.id.cb_DI_Planned);

        Button save_butt = findViewById(R.id.pend_save_data);
        Button add_sap_code = findViewById(R.id.add_sap_code);
        TextView date = findViewById(R.id.pend_date_main);
        starttime = findViewById(R.id.pend_start_time);
        endtime = findViewById(R.id.pend_end_time);

        time_taken = findViewById(R.id.pend_time_taken);

        Bundle bundle = getIntent().getExtras();

        String area_selected = bundle.getString("Area");




        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        date.setText("Date : " + date1);

        Spinner equpi_list_spinne = findViewById(R.id.pend_equip_list_spin);

        Spinner operation_spinner = findViewById(R.id.pend_operation_spin);
        Spinner problem_category = findViewById(R.id.pend_edit_prob_cat);
        TextView area = findViewById(R.id.pend_spinner_area);
        area.setText(area_selected);



        ArrayAdapter<CharSequence> work_type_adapter = ArrayAdapter.createFromResource(this, R.array.Work_Type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> problem_category_adapter = ArrayAdapter.createFromResource(this, R.array.Problem_Category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> stoppage_category_adapter = ArrayAdapter.createFromResource(this, R.array.Stoppage_Category, android.R.layout.simple_spinner_item);




        work_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problem_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stoppage_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        problem_category.setAdapter(problem_category_adapter);



        ActivityResultLauncher<Intent> beforePicIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Intent intent = result.getData();
                if (intent != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                        beforepic.setImageBitmap(bitmap);

                        BEFORE_URI = getPathFromURI(intent.getData());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        ActivityResultLauncher<Intent> afterPicIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Intent intent = result.getData();
                if (intent != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                        afterpic.setImageURI(intent.getData());

                        AFTER_URI = getPathFromURI(intent.getData());

                        //   AFTER_URI=intent.getData().normalizeScheme().toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });




        if (area_selected.equals("HPDC")) {
            equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HPDC, android.R.layout.simple_spinner_item);
            operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HPDC_ope, android.R.layout.simple_spinner_item);
            operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        if (area_selected.equals("GSPM")) {
            equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.GSPM, android.R.layout.simple_spinner_item);
            operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.GSPM_ope, android.R.layout.simple_spinner_item);
            equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        if (area_selected.equals("Finishing")) {
                equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingGSPM, android.R.layout.simple_spinner_item);
                operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.FinishingGSPM_ope, android.R.layout.simple_spinner_item);
                equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        if (area_selected.equals("HotBox"))
           {
                equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HotBox, android.R.layout.simple_spinner_item);
                operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.HotBox_ope, android.R.layout.simple_spinner_item);
                equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
            if(area_selected.equals("ColdBox"))
            {
                equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.ColdBox, android.R.layout.simple_spinner_item);
                operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.ColdBox_ope, android.R.layout.simple_spinner_item);
                equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }


        if (area_selected.equals("Heat_Treatment")) {
            equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Heat_Treatment, android.R.layout.simple_spinner_item);
            operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Heat_Treatment_ope, android.R.layout.simple_spinner_item);
            equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        if (area_selected.equals("Sand_Reclamation")) {
            equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.SandReclamation, android.R.layout.simple_spinner_item);
            operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.SandReclamation, android.R.layout.simple_spinner_item);
            equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        if (area_selected.equals("Melting")) {
            equip_lis_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting, android.R.layout.simple_spinner_item);
            operation_type_adapter = ArrayAdapter.createFromResource(Data_input.this, R.array.Melting_ope, android.R.layout.simple_spinner_item);
            equip_lis_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            operation_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        equpi_list_spinne.setAdapter(equip_lis_adapter);
        operation_spinner.setAdapter(operation_type_adapter);




        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SingleDateAndTimePickerDialog.Builder singleDateAndTimePickerDialog = new SingleDateAndTimePickerDialog.Builder(Data_input.this).displayAmPm(false)
                        .curved()
                        .minutesStep(1)
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                // Retrieve the SingleDateAndTimePicker
                                picker.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

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
                                endtime.setVisibility(View.VISIBLE);
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                String starttim = localDateFormat1.format(date);
                                Date = localDateFormat.format(date);
                                starttime.setText(starttim);

                                timestampstart = new Timestamp(date.getTime());


                            }
                        }).defaultDate(start);
                singleDateAndTimePickerDialog.display();


            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!starttime.getText().toString().equals("")) {

                    SingleDateAndTimePickerDialog.Builder singleDateAndTimePickerDialog = new SingleDateAndTimePickerDialog.Builder(Data_input.this).displayAmPm(false)
                            .curved()
                            .minutesStep(1)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {
                                    // Retrieve the SingleDateAndTimePicker
                                    picker.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
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

                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                    String starttim = localDateFormat1.format(date);
                                    endtime.setText(starttim);

                                    printDifference(start, end);


                                }
                            }).defaultDate(end);

                    singleDateAndTimePickerDialog.display();

                } else {
                    Toast.makeText(Data_input.this, "Select Start time", Toast.LENGTH_SHORT).show();
                }


            }
        });


        save_butt.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {


                Date astart, aend = null, bend, cshift, currentime;

                isAllFieldsChecked = CheckAllFields();

                try {
                    astart = new SimpleDateFormat("HH:mm a").parse("05:59 AM");
                    aend = new SimpleDateFormat("HH:mm a").parse("13:59 PM");
                    bend = new SimpleDateFormat("HH:mm a").parse("21:59 PM");
                    cshift = new SimpleDateFormat("HH:mm a").parse("00:00 AM");
                    currentime = new SimpleDateFormat("HH:mm a").parse(starttime.getText().toString());

                    assert currentime != null;
                    if (currentime.after(astart) && currentime.before(aend)) {
                        shift = "A";
                    } else if (currentime.after(aend) && currentime
                            .before(bend)) {
                        shift = "B";
                    } else if (currentime.after(bend) || currentime.after(cshift) && currentime.before(astart)) {

                        //Person in night shit need not select previous date in picker

                        if (currentime.after(cshift)) {
                            Calendar calendar = GregorianCalendar.getInstance();
                            calendar.add(Calendar.DAY_OF_YEAR, -1);
                            Date previousDay = calendar.getTime();

                            SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date = localDateFormat.format(previousDay);


                        }
                        shift = "C";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (isAllFieldsChecked) {



                    String operation = operation_spinner.getSelectedItem().toString();
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

                                if (gshift.isChecked()) {
                                    shift = "G";
                                }



                                reference.add(new DataInput_Model(area_selected
                                        , problem_category.getSelectedItem().toString(), equipment_name, worktype(),
                                        operation, part_name.getText().toString(),
                                        problem_desc_et.getText().toString(),
                                        action_taken_et.getText().toString(),
                                        spares_used_et.getText().toString(),
                                        models, starttime.getText().toString(),
                                        " ",
                                        work_done_by.getText().toString(), "Pending", Date, time, "",
                                        flatDialog.getLargeTextField(), shift, timestampstart, "", "",user.getDisplayName(),user.getUid()))

                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

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
                        if (gshift.isChecked()) {
                            shift = "G";
                        }


                        reference.add(new DataInput_Model(area_selected, problem_category.getSelectedItem().toString(), equipment_name,
                                worktype(),
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
                                Date, Integer.parseInt(time_taken.getText().toString()), reference.document().getId(), "", shift, timestampstart, "", "",user.getDisplayName(),user.getUid())).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                if (BEFORE_URI != null && AFTER_URI != null) {
                                    uploadtocloud(BEFORE_URI, AFTER_URI, documentReference.getId());
                                }

                                documentReference.update("id", documentReference.getId());


                                Toast.makeText(Data_input.this, "Added Sucussfully", Toast.LENGTH_SHORT).show();

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

//                Intent intent = new Intent(Data_input.this, Add_Sap_codes.class);
//                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        afterpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                //set type
                intent.setType("image/*");
                //launch intent

                afterPicIntentActivityResultLauncher.launch(intent);
            }
        });

        beforepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intialize intent
                Intent intent = new Intent(Intent.ACTION_PICK);
                //set type
                intent.setType("image/*");
                //launch intent

                beforePicIntentActivityResultLauncher.launch(intent);
            }
        });


    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (Objects.requireNonNull(data).getExtras() != null) {
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

    private Boolean CheckAllFields() {

        if (worktype().equals("")){
            Toast.makeText(Data_input.this, "Select Work type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (problem_desc_et.length() < 3) {
            problem_desc_et.setError("Please enter proper details");

            return false;
        }
        if (action_taken_et.length() < 3) {
            action_taken_et.setError("Please enter proper details");
            return false;
        }
        if (work_done_by.length() < 2) {
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

        time_taken.setText(String.valueOf(elapsedMinutes));


    }


    private void uploadtocloud(String beforuri, String afteruri, String databaseID) {


        Uri beforefile = Uri.fromFile(new File(beforuri));
        Uri afterfile = Uri.fromFile(new File(afteruri));
        riversRef = storageRef.child(databaseID + " " + "BEFORE");
        riversRef1 = storageRef.child(databaseID + " " + "AFTER");

        riversRef.putFile(beforefile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DocumentReference documentReference = db.collection("log").document(databaseID);
                            documentReference.update("beforeimageurl", uri.toString());
                        }
                    });


                }
            }
        });

        riversRef1.putFile(afterfile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DocumentReference documentReference = db.collection("log").document(databaseID);
                            documentReference.update("afterimageurl", uri.toString());
                        }
                    });

                }


            }
        });


    }
    private String worktype(){
        String worktype="";

        if (cb_Breakdown.isChecked()){
            worktype="Breakdown";

        }if (cb_ser_req.isChecked()){
            worktype="Service Request";
        }
        if (cb_planned.isChecked()){
            worktype="Planned";
        }

        return worktype;
    }
}

