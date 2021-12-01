package com.sandyzfeaklab.breakdown_app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandyzfeaklab.breakdown_app.adaptors.Oil_cons_list_adaptor;
import com.sandyzfeaklab.breakdown_app.dataModel.DataInput_Model;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Oil_consumption extends AppCompatActivity {

    ProgressBar anyChartView;
    Oil_cons_list_adaptor adaptor;
    TextView oilcons_gspm_mnth, oilcons_hpdc_mnth, oilcons_coreshop_mnth, oilcons_gspm_ytd, oilcons_hpdc_ytd, oilcons_coreshop_ytd, oilcons_mnth_total,
            oilcons_cost, textView16, textView14, textView17;
    Button gspm, hpdc, coreshop;
    RecyclerView rcView;
   public String area;

    CollectionReference reference = FirebaseFirestore.getInstance().collection("oil Consump");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_consumption);


        initViews();
        fabInit();
        handleClick();

        Query query = reference.orderBy("timestamp", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<OIl_Consump_model> options= new FirestoreRecyclerOptions.Builder<OIl_Consump_model>().setQuery(query,OIl_Consump_model.class).build();

        adaptor=new Oil_cons_list_adaptor(options,this);

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(Oil_consumption.this));
        rcView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        rcView.setAdapter(adaptor);
        anyChartView.setMax(10000);


    }


    private void initViews() {
        anyChartView = findViewById(R.id.oil_chart);
        oilcons_gspm_mnth = findViewById(R.id.oil_cons_gspm_month);
        oilcons_hpdc_mnth = findViewById(R.id.oil_cons_hpdc_month);
        oilcons_coreshop_mnth = findViewById(R.id.oil_cons_coreshop_month);
        oilcons_gspm_ytd = findViewById(R.id.oil_cons_gspm_total);
        oilcons_hpdc_ytd = findViewById(R.id.oil_cons_hpdc_total);
        oilcons_coreshop_ytd = findViewById(R.id.oil_cons_coreshop_total);
        oilcons_mnth_total = findViewById(R.id.oil_cons_monthly_total);
        gspm = findViewById(R.id.oil_cons_gspm_btn);
        hpdc = findViewById(R.id.oil_cons_hpdc_btn);
        coreshop = findViewById(R.id.oil_cons_coreshop_btn);
        oilcons_cost = findViewById(R.id.oil_cons_cost);
        textView16 = findViewById(R.id.textView16);
        textView14 = findViewById(R.id.textView14);
        textView17 = findViewById(R.id.textView17);
        rcView=findViewById(R.id.oil_cons_dash_rcv);


    }

    private void handleClick(){
        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "GSPM");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        oilcons_gspm_mnth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "GSPM");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "HPDC");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        oilcons_hpdc_mnth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "HPDC");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "CoreShop");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        oilcons_coreshop_mnth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oil_consumption.this, Oil_cons_list.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "CoreShop");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        oilcons_gspm_ytd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Oil_consumption.this, OIl_Cons_Graph.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "GSPM");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        oilcons_hpdc_ytd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oil_consumption.this, OIl_Cons_Graph.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "HPDC");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        oilcons_coreshop_ytd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oil_consumption.this, OIl_Cons_Graph.class);
                Bundle bundle = new Bundle();
                bundle.putString("Area", "CoreShop");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void fabInit() {
        FabSpeedDial fabSpeedDial = findViewById(R.id.addOil_cons_fab);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                //TODO: add hpdc finishing option i.e trimming

                Intent intent = new Intent(Oil_consumption.this, AddOildetails.class);
                Bundle bundle = new Bundle();

                switch (menuItem.getItemId()) {


                    case R.id.HPDC_oil:
                        bundle.putString("Area", "HPDC");
                        bundle.putString("type","save");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;
                    case R.id.GSPM_Oil_cons:
                        bundle.putString("Area", "GSPM");
                        bundle.putString("type","save");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;

                    case R.id.CoreShop:
                        bundle.putString("Area", "CoreShop");
                        bundle.putString("type","save");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);

                        return true;
                    case R.id.Finishing_Oil_cons_:
                        bundle.putString("Area", "Finishing");
                        bundle.putString("type","save");
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        return true;

                }

                return false;
            }
        });
    }

    public void check_oil() {

        Date date = new Date();

        SimpleDateFormat localDateFormat = new SimpleDateFormat("MMMM");

        String mnth = localDateFormat.format(date);

        reference.whereEqualTo("mnth", mnth).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            int gspm_cons_mnth = 0;
            int hpdc_cons_mnth = 0;
            int coreshop_cons_mnth = 0;

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    OIl_Consump_model oIl_consump_model = documentSnapshot.toObject(OIl_Consump_model.class);

                    area= oIl_consump_model.getArea();

                    if(area!=null){

                        if (area.equals("GSPM")) {
                            gspm_cons_mnth += oIl_consump_model.getQty();
                        }
                        if (area.equals("HPDC")) {
                            hpdc_cons_mnth += oIl_consump_model.getQty();

                        }
                        if (area.equals("CoreShop")) {
                            coreshop_cons_mnth += oIl_consump_model.getQty();
                        }
                    }



                }

                setValuesmnth(gspm_cons_mnth,coreshop_cons_mnth,hpdc_cons_mnth);
//                oilcons_gspm_mnth.setText((gspm_cons_mnth + " Ltrs"));
//                oilcons_coreshop_mnth.setText((coreshop_cons_mnth + " Ltrs"));
//                oilcons_hpdc_mnth.setText((hpdc_cons_mnth + " Ltrs"));
//
//                oilcons_mnth_total.setText((gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth) + " Ltrs");
//
//                oilcons_cost.setText((gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth) * 57 + " Rs");
//                anyChartView.setProgress(gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth);

            }
        });

        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            int gspm_cons= 0;
            int hpdc_cons = 0;
            int coreshop_cons = 0;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    OIl_Consump_model oIl_consump_model = documentSnapshot.toObject(OIl_Consump_model.class);

                    area= oIl_consump_model.getArea();

                    if(area!=null){

                        if (area.equals("GSPM")) {
                            gspm_cons += oIl_consump_model.getQty();
                        }
                        if (area.equals("HPDC")) {
                            hpdc_cons += oIl_consump_model.getQty();

                        }
                        if (area.equals("CoreShop")) {
                            coreshop_cons += oIl_consump_model.getQty();
                        }
                    }
                    setValuesYTD(gspm_cons,hpdc_cons,coreshop_cons);

//                    oilcons_gspm_ytd.setText(String.valueOf(gspm_cons));
//                    oilcons_hpdc_ytd.setText(String.valueOf(hpdc_cons));
//                    oilcons_coreshop_ytd.setText(String.valueOf(coreshop_cons));




                }

            }
        });

    }

    private void setValuesYTD(int gspm_cons,int hpdc_cons,int coreshop_cons){

        oilcons_gspm_ytd.setText(String.valueOf(gspm_cons));
        oilcons_hpdc_ytd.setText(String.valueOf(hpdc_cons));
        oilcons_coreshop_ytd.setText(String.valueOf(coreshop_cons));

    }

    private void setValuesmnth(int gspm_cons_mnth,int coreshop_cons_mnth,int hpdc_cons_mnth){
        this.oilcons_gspm_mnth.setText((gspm_cons_mnth + " Ltrs"));
        oilcons_coreshop_mnth.setText((coreshop_cons_mnth + " Ltrs"));
        oilcons_hpdc_mnth.setText((hpdc_cons_mnth + " Ltrs"));

        oilcons_mnth_total.setText((gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth) + " Ltrs");

        oilcons_cost.setText((gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth) * 57 + " Rs");
        anyChartView.setProgress(gspm_cons_mnth + coreshop_cons_mnth + hpdc_cons_mnth);
    }



    @Override
    protected void onStart() {
        super.onStart();



        adaptor.startListening();


        reference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                int gspm_cons_mnth = 0;
                int hpdc_cons_mnth = 0;
                int coreshop_cons_mnth = 0;


                check_oil();
//                if (error!=null){
//                    return;
//                }
//                assert value != null;
//                for (DocumentChange documentChange :value.getDocumentChanges()){
//                    DocumentSnapshot documentSnapshot = documentChange.getDocument();
//                    OIl_Consump_model oIl_consump_model = documentSnapshot.toObject(OIl_Consump_model.class);
//
//                    area= oIl_consump_model.getArea();
//
//                    if(area!=null){
//
//                        if (area.equals("GSPM")) {
//                            gspm_cons_mnth += oIl_consump_model.getQty();
//                        }
//                        if (area.equals("HPDC")) {
//                            hpdc_cons_mnth += oIl_consump_model.getQty();
//
//                        }
//                        if (area.equals("CoreShop")) {
//                            coreshop_cons_mnth += oIl_consump_model.getQty();
//                        }
//                    }
//
//
//                }
//                setValuesmnth(gspm_cons_mnth,coreshop_cons_mnth,hpdc_cons_mnth);


            }
        });

    }
}