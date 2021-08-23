package com.sandyzfeaklab.breakdown_app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPending_Activity extends AppCompatActivity {

    TextView starttime, endtime, pend_partname, pend_equip_list_spin,  pend_operation_spin, date_txt, pend_area;
    EditText problem_desc_et, action_taken_et, spares_used_et, work_done_by, time_taken;
    Spinner pend_work_type_spin;
    Date start, end;
    Model model;
    Button save;
    String shift="";
    int time;
    boolean isAllFieldsChecked = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef, riversRef1;
    ImageView beforepic,afterpic;

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
        beforepic=findViewById(R.id.before_pic_editdata);
        afterpic=findViewById(R.id.after_pic_editdata);

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

                model = documentSnapshot.toObject(Model.class);

                pend_partname.setText(model.getPart());
                pend_equip_list_spin.setText(model.getEquipment_name());
                pend_operation_spin.setText(model.getOperation());
               // starttime.setText(model.getStart_Time());
                endtime.setText(model.getEnd_time());
                pend_area.setText(model.getArea());

                problem_desc_et.setText(model.getProblem_desc());
                action_taken_et.setText(model.getAction_taken());
                spares_used_et.setText(model.getSpares_used());
                work_done_by.setText(model.getaction_taken_by());
                Picasso.get().load(model.getBeforeimageurl()).placeholder(R.drawable.time).into(beforepic);
                Picasso.get().load(model.getAfterimageurl()).placeholder(R.drawable.time).into(afterpic);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();

                Date astart,aend = null,bend,cshift ,currentime;


                try {
                    astart=new SimpleDateFormat("HH:mm a").parse("05:59 AM");
                    aend=new SimpleDateFormat("HH:mm a").parse("13:59 PM");
                    bend=new SimpleDateFormat("HH:mm a").parse("21:59 PM");
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


                CheckAllFields();

                documentReference.update(
                        "work_Type",pend_work_type_spin.getSelectedItem().toString(),"problem_desc",problem_desc_et.getText().toString()
                        ,"action_taken",action_taken_et.getText().toString(),"spares_used",spares_used_et.getText().toString(),"stoppage_category",stoppage_category.getSelectedItem().toString()
                        ,"problem_category",problem_category.getSelectedItem().toString()
                        ,"start_Time",starttime.getText().toString()
                        ,"end_time",endtime.getText().toString()
                        ,"time_taken",time
                        ,"action_taken_by",work_done_by.getText().toString()
                        ,"status","Compleated"
                        ,"shift",shift
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(EditPending_Activity.this, "Work Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();



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



                                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/M/yyyy");
                                SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                String starttim = localDateFormat1.format(date);


                                starttime.setText(starttim);

                            }
                        }).display();


            }
        });


        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!starttime.getText().toString().equals("")) {

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

                                    SimpleDateFormat localDateFormat1 = new SimpleDateFormat("HH:mm a");
                                    String starttim = localDateFormat1.format(date);
                                    endtime.setText(starttim);

                                    printDifference(start, end);




                                }
                            }).display();

                }else
                {
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
}