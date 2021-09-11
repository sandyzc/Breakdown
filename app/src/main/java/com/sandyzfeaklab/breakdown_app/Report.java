package com.sandyzfeaklab.breakdown_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


    }

    public void textexcel(View view) {

        try (Workbook wb = new HSSFWorkbook()) { //or new HSSFWorkbook();
            CreationHelper creationHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");

            // Create a row and put some cells in it. Rows are 0 based.
            Row row = sheet.createRow((short) 0);
            // Create a cell and put a value in it.
            Cell cell = row.createCell((short) 0);
            cell.setCellValue(1);

            //numeric value
            row.createCell(1).setCellValue(1.2);

            //plain string value
            row.createCell(2).setCellValue("This is a string cell");

            //rich text string
            RichTextString str = creationHelper.createRichTextString("Apache");
            Font font = wb.createFont();
            font.setItalic(true);
            font.setUnderline(Font.U_SINGLE);
            str.applyFont(font);
            row.createCell(3).setCellValue(str);

            //boolean value
            row.createCell(4).setCellValue(true);

            //formula
            row.createCell(5).setCellFormula("SUM(A1:B1)");

//            //date
//            CellStyle style = wb.createCellStyle();
//            style.setDataFormat(creationHelper.createDataFormat().getFormat("m/d/yy h:mm"));
//            cell = row.createCell(6);
//            cell.setCellValue(new Date());
//            cell.setCellStyle(style);

            //hyperlink
            row.createCell(7).setCellFormula("SUM(A1:B1)");
            cell.setCellFormula("HYPERLINK(\"http://google.com\",\"Google\")");

            File file = new File(getExternalFilesDir(null), "plik.xlsx");
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(file);
                wb.write(outputStream);
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "NO OK", Toast.LENGTH_LONG).show();
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}