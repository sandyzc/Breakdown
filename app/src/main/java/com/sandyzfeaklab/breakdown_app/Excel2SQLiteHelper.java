package com.sandyzfeaklab.breakdown_app;
import android.content.ContentValues;
import android.util.Log;


import androidx.collection.ArraySet;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by santosh on 26-07-2017.
 */

public class Excel2SQLiteHelper {

    static CollectionReference reference = FirebaseFirestore.getInstance().collection("log");
    public static ArrayList<Sap_code_Model> models = new ArrayList<>();

    public static final String Tablename = "ItemCode";
    public static final String id = "_id";// 0 integer
    public static final String CODE = "code";
    public static final String Description = "description";


    public static void insertExcelToSqlite(Sheet sheet) {

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
            Row row = rit.next();

//            0 String area,
//            1 String stoppage_category,
//            2 String problem_category,
//            3 String equipment_name,
//            4 String work_Type,
//            5 String operation,
//            6 String part,
//            7 String problem_desc,
//            8 String action_taken,
//            9 String spares_used,
//            10 ArrayList<Sap_code_Model> sap_no,
//            11 String start_Time,
//            12 String end_time,
//            13 String action_taken_by,
//            14 String status1,
//            15 String Date,
//            16 int time,
//            17 String id,
//            18 String pending_remarks,
//            19 String shift,
//            20 java.util.Date ts,
//            21 String beforeimageurl,
//            22 String afterimageurl

        //    row.getCell(16).setCellType(CellType.NUMERIC);

            reference.add(new Model(
                    row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    models,
                    row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),

                    (int) row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue(),
                    row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(20, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getDateCellValue(),
                    row.getCell(21, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                    row.getCell(22, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue()
            )).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    documentReference.update("id", documentReference.getId());
                }
            });


        }
    }
}