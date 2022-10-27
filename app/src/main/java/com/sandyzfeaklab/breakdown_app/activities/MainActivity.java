package com.sandyzfeaklab.breakdown_app.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.sandyzfeaklab.breakdown_app.Energy_Entry;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.activities.materials.MaterialWithMachine;
import com.sandyzfeaklab.breakdown_app.activities.materials.Material_list_Activity;
import com.sandyzfeaklab.breakdown_app.dataModel.DataBaseDowload_Model;
import com.sandyzfeaklab.breakdown_app.database.DataBase_FireBAse_Link;
import com.sandyzfeaklab.breakdown_app.database.Material_Database;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView mtbf_butt, material_search, oil_cons_activity, pending_butt, logout;
    Dialog dialog;
    CollectionReference reference = FirebaseFirestore.getInstance().collection("database");

    DataBaseDowload_Model model;
    Double firestoreUpdate;
    String DATABASE_DOWNLOAD_URL, VERSION_NAME;
    int DATABASE_VERSION;
    DataBase_FireBAse_Link fireBAse_link;
    ProgressBar progressBar;
    TextView db_ver;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireBAse_link = new DataBase_FireBAse_Link(this);






        chekDBUpdate();



        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("oil_entry_added");
        firebaseMessaging.subscribeToTopic("Weekly_report");


        mAuth = FirebaseAuth.getInstance();


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , PackageManager.PERMISSION_GRANTED);

        logout = findViewById(R.id.logout);
        pending_butt = findViewById(R.id.pending_button);
        oil_cons_activity = findViewById(R.id.oil_cons_activity);
        material_search = findViewById(R.id.material_search_activity);
        TextView id = findViewById(R.id.idd);
        progressBar = findViewById(R.id.material_search_db_download);
        db_ver = findViewById(R.id.db_vers);

        id.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

        material_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                material_search_button_clicked_dialog();


            }
        });

        pending_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pending_activity.class);
                startActivity(intent);
            }
        });


        oil_cons_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Oil_consumption.class);

                startActivity(intent);


            }
        });

        ImageView fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Log.class);
                startActivity(intent);
            }
        });
    }

    //checking weather any updated db available

    public void chekDBUpdate() {

        reference.document(
                "database").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                model = documentSnapshot.toObject(DataBaseDowload_Model.class);

                assert model != null;
                DATABASE_VERSION = model.getVersion();
                DATABASE_DOWNLOAD_URL = model.getUrl();
                firestoreUpdate = Double.valueOf(model.getAppVer());

                db_ver.setText(String.valueOf(DATABASE_VERSION));

                String urlindb = fireBAse_link.get_DB_FIREBASE_link();


                if (urlindb.equals("")) {
                    fireBAse_link.save_DB_FIREBASE_LINK(DATABASE_DOWNLOAD_URL);


                } else if (fireBAse_link.get_DB_FIREBASE_link().equals(DATABASE_DOWNLOAD_URL)) {
                    progressBar.setProgress(100);
                    Toast.makeText(MainActivity.this, "Data Already Updated", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        getDbFromFireBase(DATABASE_DOWNLOAD_URL);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    fireBAse_link.updae_DB_FIREBASE_LINK(DATABASE_DOWNLOAD_URL);
                }


                if (checkAppUpdat(model.getAppVer())){
                    appupdatedialog();
                }

            }
        });

    }

    private void getDbFromFireBase(String FIREBASE_URL) throws IOException {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FIREBASE_URL);

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Db");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, "spares.db");


        mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                Material_Database.updateDatabae(getApplicationContext(), localFile);

                Toast.makeText(MainActivity.this, "Downloaded ", Toast.LENGTH_LONG).show();


            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);

            }
        });
    }

    public void energy_consp(View view) {

        Intent intent = new Intent(MainActivity.this, Energy_Entry.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();




    }

    private void material_search_button_clicked_dialog() {

        //TODO text validation need to implement
        //TODO implement search feature

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_options_dialog);
        TextInputEditText sap_code_enterd = dialog.findViewById(R.id.search_sap_code);
        TextInputEditText desc_enterd = dialog.findViewById(R.id.search_desc);
        Button search_butt = dialog.findViewById(R.id.search_SAP_Button);
        Button search_with_machine = dialog.findViewById(R.id.search_with_machine_but);


        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        search_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sap, desc;


                Bundle bundle = new Bundle();

                if (sap_code_enterd != null) {
                    bundle.putString("sapCode", sap_code_enterd.getText().toString());
                }
                if (desc_enterd != null) {
                    bundle.putString("desc", desc_enterd.getText().toString());
                }

                bundle.putString("activity", "MAINACTIVITY");


                Intent intent = new Intent(MainActivity.this, Material_list_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        search_with_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MaterialWithMachine.class);
                startActivity(intent);
            }
        });


        dialog.show();


    }

    private boolean checkAppUpdat(String appver) {



        try {
            VERSION_NAME = getApplicationContext().getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(VERSION_NAME) < Double.parseDouble(appver);

    }

    private void appupdatedialog() {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.updat_dialog);

        Button update = dialog.findViewById(R.id.download_update);
        Button update_cancel = dialog.findViewById(R.id.update_cancel);


        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( Intent.ACTION_VIEW,
                        Uri.parse("https://san4617.wixsite.com/maintenanceapp"));
                startActivity(intent  );

            }
        });

        update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}