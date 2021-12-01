package com.sandyzfeaklab.breakdown_app;

import android.annotation.SuppressLint;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;
import com.sandyzfeaklab.breakdown_app.databinding.ActivityOilConsGraphBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OIl_Cons_Graph extends AppCompatActivity {

    CollectionReference reference = FirebaseFirestore.getInstance().collection("oil Consump");
    String area;
    AnyChartView anyChartView;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };
   /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oil_cons_graph);
        anyChartView =findViewById(R.id.oil_cons_graph);

        Bundle bundle = getIntent().getExtras();
        area=bundle.getString("Area");
        check_oil();


    }
    private void check_oil() {

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();


        Date date = new Date();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("MMMM");
        String mnth = localDateFormat.format(date);

        reference.whereEqualTo("mnth", mnth).whereEqualTo("area", area).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            int gspm_cons_mnth = 0;


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    OIl_Consump_model oIl_consump_model = documentSnapshot.toObject(OIl_Consump_model.class);

                    @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd").format(oIl_consump_model.getTimestamp());

                    gspm_cons_mnth+=oIl_consump_model.getQty();


                    data.add(new ValueDataEntry(oIl_consump_model.getEquip(), gspm_cons_mnth));

                }
                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.AUTO)
                        .anchor(Anchor.CENTER_TOP)
                        .offsetX(0d)
                        .offsetY(5d)

                        ;

                cartesian.animation(true);
                cartesian.title("Oil Consumption in "+area+" for the month of "+mnth);

                cartesian.yScale().minimum(0d);

               // cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                cartesian.xAxis(0).title("Equipment");
                cartesian.yAxis(0).title("OIl Consumption in Ltr");


               //artesian.data(data);
            }
        });





        anyChartView.setChart(cartesian);


    }
}


