package com.sandyzfeaklab.breakdown_app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Excel2Firestore extends AppCompatActivity {

    ProgressBar progressBar;
    ArrayList<Sap_code_Model> models = new ArrayList<>();
    TextView textView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel2_firestore);

        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.progresspercent);


    }

    public void Upload(View view) {

        mGetContent.launch("application/vnd.ms-excel");

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

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {

                  
                    Workbook wb = null;

                    try {
                        FileInputStream inputStream = new FileInputStream(getPathFromURI(uri));
                         wb = new HSSFWorkbook(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Sheet sheet1 = null;
                    if (wb != null) {
                        sheet1 = wb.getSheetAt(0);
                    }

                    Sheet sheet2 = null;
                    if (wb != null) {
                        sheet2 = wb.getSheetAt(0);
                    }
                    if (sheet1 == null) {
                        return;
                    }
                    if (sheet2 == null) {
                        return;
                    }
                    Excel2SQLiteHelper.insertExcelToSqlite( sheet1);
                    Excel2SQLiteHelper.insertExcelToSqlite( sheet2);


                }
            });
}