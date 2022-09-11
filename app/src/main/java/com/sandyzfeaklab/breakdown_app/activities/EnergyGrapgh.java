package com.sandyzfeaklab.breakdown_app.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.dataModel.Energy_cons_model;
import com.sandyzfeaklab.breakdown_app.databinding.ActivityEnergyGrapghBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EnergyGrapgh extends AppCompatActivity {

    Date date;
    CollectionReference reference = FirebaseFirestore.getInstance().collection("Energy_readings");

    private ActivityEnergyGrapghBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnergyGrapghBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        date = new Date();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataFromFirestoreAndUpdate();
    }

    private void getDataFromFirestoreAndUpdate() {
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DataEntry> seriesData = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Energy_cons_model model = documentSnapshot.toObject(Energy_cons_model.class);

                    SimpleDateFormat localDateFormat2;
                    localDateFormat2 = new SimpleDateFormat("dd-MMM");
                    String da = localDateFormat2.format(model.getTs());


                    seriesData.add(new EnergyGrapgh.CustomDataEntry(da, model.getHpdc1Units(), model.getHpdc2Units()
                            , model.getHpdc3Units(), model.getGspm2BUnits(),
                            model.getGspm3B1Units(), model.getGspm3B2Units(),
                            model.getGspm3B3Units(), model.getRotoCastUnits()));

                }
                updateChart(seriesData);

            }
        });
    }


    public void updateChart(List<DataEntry> seriesData) {

        SimpleDateFormat localDateFormat;
        localDateFormat = new SimpleDateFormat("MMMM-yyyy");
        String date1 = localDateFormat.format(date);

        Set set = Set.instantiate();
        set.data(seriesData);
        // Number ip3,Number gspm2b,Number gspm3b1,Number gspm3b2,Number gspm3b3,Number roto
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
        Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
        Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");
        Mapping series7Mapping = set.mapAs("{ x: 'x', value: 'value7' }");
        Mapping series8Mapping = set.mapAs("{ x: 'x', value: 'value8' }");

        Cartesian cartesian = AnyChart.line();

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Furnace Kwh Reading for the month of " + date1);
        cartesian.yAxis(0).title("Units");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        Line series1 = cartesian.line(series1Mapping);
        series1.name("HPDC-1");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        Line series2 = cartesian.line(series2Mapping);
        series2.name("HPDC-2");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("HPDC-3");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series4 = cartesian.line(series4Mapping);
        series4.name("2B");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series5 = cartesian.line(series5Mapping);
        series5.name("3B1");
        series5.hovered().markers().enabled(true);
        series5.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series5.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series6 = cartesian.line(series6Mapping);
        series6.name("3B2");
        series6.hovered().markers().enabled(true);
        series6.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series6.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series7 = cartesian.line(series7Mapping);
        series7.name("3B3");
        series7.hovered().markers().enabled(true);
        series7.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series7.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series8 = cartesian.line(series8Mapping);
        series8.name("RotoCast");
        series8.hovered().markers().enabled(true);
        series8.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series8.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(8d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        cartesian.animation(true, 10);


        binding.energyGraph.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number ip1, Number ip2, Number ip3, Number gs2b, Number gs3b1, Number gs3b2, Number gs3b3, Number rc) {
            super(x, ip1);
            setValue("value2", ip2);
            setValue("value3", ip3);
            setValue("value4", gs2b);
            setValue("value5", gs3b1);
            setValue("value6", gs3b2);
            setValue("value7", gs3b3);
            setValue("value8", rc);
        }

    }
}