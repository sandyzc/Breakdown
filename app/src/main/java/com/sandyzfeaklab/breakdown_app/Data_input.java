package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Data_input extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    private static final int REQUEST_GET_BEFORE_FILE = 2;
    private static final int REQUEST_GET_AFTER_FILE = 3;
    String download;
    String Date = "";
    String BEFORE_URI;
    String AFTER_URI;
    StorageReference riversRef, riversRef1;
    int time;
    boolean isAllFieldsChecked = false;
    CheckBox checkBox,gshift;
    ImageView beforepic, afterpic;
    Timestamp timestampstart, timestampend;
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
        beforepic = findViewById(R.id.before_pic);
        afterpic = findViewById(R.id.after_pic);

        //  sap_no_et=findViewById(R.id.sap_no_et);


        Button save_butt = findViewById(R.id.pend_save_data);
        Button add_sap_code = findViewById(R.id.add_sap_code);

        TextView date = findViewById(R.id.pend_date_main);


        starttime = findViewById(R.id.pend_start_time);
        endtime = findViewById(R.id.pend_end_time);
        TextView to =findViewById(R.id.textView10);
        time_taken = findViewById(R.id.pend_time_taken);


        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        date.setText("Date : " + date1);

        Spinner equpi_list_spinne = findViewById(R.id.pend_equip_list_spin);
        Spinner work_type_spinner = findViewById(R.id.pend_work_type_spin);
        Spinner operation_spinner = findViewById(R.id.pend_operation_spin);
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
                                endtime.setVisibility(View.VISIBLE);
                                to.setVisibility(View.VISIBLE);

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

                if (!starttime.getText().toString().equals(null)) {

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

                                    printDifference(start, end);




                                }
                            }).display();

                }else
                {
                    Toast.makeText(Data_input.this, "Select Start time", Toast.LENGTH_SHORT).show();
                }


            }
        });


        save_butt.setOnClickListener(new View.OnClickListener() {



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

                    } else if (currentime.after(bend) && currentime
                            .before(astart) || currentime.after(cshift) && currentime.before(astart)) {
                        shift = "C";


                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (isAllFieldsChecked) {

                    String work_Type = work_type_spinner.getSelectedItem().toString();
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

                                if (gshift.isChecked()){
                                    shift="G";
                                }


                                reference.add(new Model(area.getSelectedItem().toString(), stoppage_category.getSelectedItem().toString()
                                        , problem_category.getSelectedItem().toString(), equipment_name, work_Type,
                                        operation, part_name.getText().toString(),
                                        problem_desc_et.getText().toString(),
                                        action_taken_et.getText().toString(),
                                        spares_used_et.getText().toString(),
                                        models, starttime.getText().toString(),
                                        " ",
                                        work_done_by.getText().toString(), "Pending", Date, time, "",
                                        flatDialog.getLargeTextField(), shift, timestampstart, "", "")).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                        if (gshift.isChecked()){
                            shift="G";
                        }


                        reference.add(new Model(area.getSelectedItem().toString(), stoppage_category.getSelectedItem().toString(), problem_category.getSelectedItem().toString(), equipment_name,
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
                                Date, Integer.parseInt(time_taken.getText().toString()), reference.document().getId(), "", shift, timestampstart, "", "")).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                if (BEFORE_URI!=null&&AFTER_URI!=null){
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

                Intent intent = new Intent(Data_input.this, Add_Sap_codes.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GET_BEFORE_FILE) {
                assert data != null;
                Uri selectedImageUri = data.getData();
                // Get the path from the Uri
                final String path = getPathFromURI(selectedImageUri);
                BEFORE_URI = getRealPathFromURI(getApplicationContext(), selectedImageUri);
                if (path != null) {
                    File f = new File(path);

                    selectedImageUri = Uri.fromFile(f);
                }
                // Set the image in ImageView
                beforepic.setImageURI(selectedImageUri);
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GET_AFTER_FILE) {
                assert data != null;
                Uri selectedImageUri = data.getData();
                AFTER_URI = getRealPathFromURI(getApplicationContext(), selectedImageUri);
                // Get the path from the Uri
                final String path = getPathFromURI(selectedImageUri);
                if (path != null) {
                    File f = new File(path);

                    selectedImageUri = Uri.fromFile(f);
                }
                // Set the image in ImageView
                afterpic.setImageURI(selectedImageUri);
            }
        }


    }

    private Boolean CheckAllFields() {
        //part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken
        if (part_name.length() == 0) {
            part_name.setError("Field Cannot be empty");
            part_name.setHint("Field Cannot be empty");
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

        time_taken.setText((int) elapsedMinutes);


    }

    public void befor_photo_click(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_BEFORE_FILE);


    }

    public void after_photo_click(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_AFTER_FILE);


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
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null
                , MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}

