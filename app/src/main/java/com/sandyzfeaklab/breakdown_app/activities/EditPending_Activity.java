package com.sandyzfeaklab.breakdown_app.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.DataInput_Model;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EditPending_Activity extends AppCompatActivity {

    private static final int REQUEST_GET_AFTER_FILE = 3;
    private static final int REQUEST_GET_BEFORE_FILE = 2;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView starttime, endtime, pend_partname, pend_equip_list_spin, pend_operation_spin, date_txt, pend_area;
    EditText problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken;
    Spinner pend_work_type_spin;
    Date start, end;
    DataInput_Model dataInputModel;
    Button save;
    String shift = "";
    int time;
    boolean isAllFieldsChecked = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef, riversRef1;
    ImageView beforepic, afterpic;
    String BEFORE_URI, Date;
    String AFTER_URI,starttimerce,status;
    CheckBox compleated;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pending);

        //TODO: Remove unwanted viewholders
        //TODO:Create correct holder for only required data

        compleated=findViewById(R.id.checkBoxCompleated);
        pend_partname = findViewById(R.id.pend_partname);
        pend_equip_list_spin = findViewById(R.id.pend_equip_list_spin);
        pend_work_type_spin = findViewById(R.id.pend_work_type_spin);
        pend_operation_spin = findViewById(R.id.pend_operation_spin);
        starttime = findViewById(R.id.pend_start_time);
        endtime = findViewById(R.id.pend_end_time);
        date_txt = findViewById(R.id.edit_pend_date_main);
        beforepic = findViewById(R.id.before_pic_editdata);
        afterpic = findViewById(R.id.after_pic_editdata);

        problem_desc_et = findViewById(R.id.pend_problem_desc_et);
        action_taken_et = findViewById(R.id.pend_action_taken_et);
        spares_used_et = findViewById(R.id.pend_spares_used_et);
        work_done_by = findViewById(R.id.pend_work_done_by_et);
        time_taken = findViewById(R.id.edit_pend_time_taken);
        pend_area = findViewById(R.id.pend_spinner_area);
        save = findViewById(R.id.edit_pend_save_data);

        if (compleated.isChecked()){
            status="Compleated";
        }else{
            status="Pending";
        }

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

                dataInputModel = documentSnapshot.toObject(DataInput_Model.class);
                assert dataInputModel != null;
                starttimerce= dataInputModel.getStart_Time();
                start= dataInputModel.getTimestamp();
                pend_partname.setText(dataInputModel.getPart());
                pend_equip_list_spin.setText(dataInputModel.getEquipment_name());
                pend_operation_spin.setText(dataInputModel.getOperation());
                if (!dataInputModel.getStart_Time().equals("")) {
                    starttime.setText(dataInputModel.getStart_Time());
                }

                endtime.setText(dataInputModel.getEnd_time());
                pend_area.setText(dataInputModel.getArea());
                time_taken.setText(String.valueOf(dataInputModel.getTime_taken()));
                problem_desc_et.setText(dataInputModel.getProblem_desc());
                action_taken_et.setText(dataInputModel.getAction_taken());
                spares_used_et.setText(dataInputModel.getSpares_used());
                work_done_by.setText(dataInputModel.getaction_taken_by());
                if (!dataInputModel.getBeforeimageurl().equals("")) {
                    Picasso.get().load(dataInputModel.getBeforeimageurl()).placeholder(R.drawable.time).into(beforepic);
                }
                if (!dataInputModel.getAfterimageurl().equals("")) {
                    Picasso.get().load(dataInputModel.getAfterimageurl()).placeholder(R.drawable.time).into(afterpic);
                }


            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();

                Date astart, aend = null, bend, cshift, currentime;


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
                        shift = "C";

                        if (currentime.after(cshift)) {
                            Calendar calendar = GregorianCalendar.getInstance();
                            calendar.add(Calendar.DAY_OF_YEAR, -1);
                            Date previousDay = calendar.getTime();

                            SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date = localDateFormat.format(previousDay);


                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                CheckAllFields();

                documentReference.update(
                        "work_Type", pend_work_type_spin.getSelectedItem().toString(), "problem_desc", problem_desc_et.getText().toString()
                        , "action_taken", action_taken_et.getText().toString(), "spares_used", spares_used_et.getText().toString(), "stoppage_category", stoppage_category.getSelectedItem().toString()
                        , "problem_category", problem_category.getSelectedItem().toString()
                        , "start_Time", starttime.getText().toString()
                        , "end_time", endtime.getText().toString()
                        , "time_taken", Integer.parseInt(time_taken.getText().toString())
                        , "action_taken_by", work_done_by.getText().toString()
                        , "status", "Compleated"
                        , "shift", shift
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        if (BEFORE_URI != null && AFTER_URI != null) {
                            uploadtocloud(BEFORE_URI, AFTER_URI, dataInputModel.getId());
                        }
                        Toast.makeText(EditPending_Activity.this, "Work Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();


            }
        });



            starttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (starttimerce.equals("")){


                    new SingleDateAndTimePickerDialog.Builder(EditPending_Activity.this).displayAmPm(false)

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

                                    SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                    String starttim = localDateFormat1.format(date);


                                    starttime.setText(starttim);

                                }
                            }).defaultDate(start).display();


                }
                }
            });




        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!starttime.getText().toString().equals("")) {

                    new SingleDateAndTimePickerDialog.Builder(EditPending_Activity.this).displayAmPm(false)
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

                                    SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                    String starttim = localDateFormat1.format(date);
                                    endtime.setText(starttim);

                                    printDifference(start, end);


                                }
                            }).defaultDate(end).display();

                } else {
                    Toast.makeText(EditPending_Activity.this, "Select Start time", Toast.LENGTH_SHORT).show();
                }


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

        time_taken.setText(String.valueOf(elapsedMinutes));


    }

    private Boolean CheckAllFields() {
        //part_name, problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken
        if (pend_partname.length() == 0) {
            pend_partname.setError("Field Cannot be empty");
            pend_partname.setHint("Field Cannot be empty");
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

    public void after_pic_editdata(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_AFTER_FILE);
    }

    public void before_pic_editdata(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_BEFORE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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


}