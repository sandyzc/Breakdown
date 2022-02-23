package com.sandyzfeaklab.breakdown_app.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.Energy_cons_model;

import java.text.SimpleDateFormat;

public class Energy_Rcv_Adaptor extends FirestoreRecyclerAdapter<Energy_cons_model, Energy_Rcv_Adaptor.Viewholder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;


    public Energy_Rcv_Adaptor(@NonNull FirestoreRecyclerOptions<Energy_cons_model> options, Context context) {

        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder viewholder, int i, @NonNull Energy_cons_model model) {

        SimpleDateFormat localDateFormat;
        localDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = localDateFormat.format(model.getTs());

        viewholder.name.setText(String.valueOf(model.getName()));
        viewholder.date.setText(formatedDate);
        viewholder.hpdc1.setText(String.valueOf(model.getHpdc1()));
        viewholder.hpdc2.setText(String.valueOf(model.getHpdc2()));
        viewholder.hpdc3.setText(String.valueOf(model.getHpdc3()));
        viewholder.gspm2b.setText(String.valueOf(model.getGspm2B()));
        viewholder.gspm3b1.setText(String.valueOf(model.getGspm3B1()));
        viewholder.gspm3b2.setText(String.valueOf(model.getGspm3B2()));
        viewholder.gspm3b3.setText(String.valueOf(model.getGspm3B3()));
        viewholder.rotocast.setText(String.valueOf(model.getRotoCast()));
        viewholder.hpdc1units.setText(String.valueOf(model.getHpdc1Units())+" Units");
        viewholder.hpdc2units.setText(String.valueOf(model.getHpdc2Units())+" Units");
        viewholder.hpdc3units.setText(String.valueOf(model.getHpdc3Units())+" Units");
        viewholder.gspm2bunits.setText(String.valueOf(model.getGspm2BUnits())+" Units");
        viewholder.gspm3b1units.setText(String.valueOf(model.getGspm3B1Units())+" Units");
        viewholder.gspm3b2units.setText(String.valueOf(model.getGspm3B2Units())+" Units");
        viewholder.gspm3b3units.setText(String.valueOf(model.getGspm3B3Units())+" Units");
        viewholder.rotocastunits.setText(String.valueOf(model.getRotoCastUnits())+" Units");

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        Energy_Rcv_Adaptor.Viewholder mViewholder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.energy_rcv_item, parent, false);
        mViewholder = new Viewholder(view);


        return mViewholder;
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        TextView date, name, hpdc1, hpdc2, hpdc3, gspm2b, gspm3b1, gspm3b2, gspm3b3, rotocast, hpdc1units, hpdc2units, hpdc3units, gspm2bunits, gspm3b1units, gspm3b2units, gspm3b3units, rotocastunits;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.energy_rcv_date);
            name = itemView.findViewById(R.id.energy_rcv_namw);
            hpdc1 = itemView.findViewById(R.id.energy_rcv_ip1);
            hpdc2 = itemView.findViewById(R.id.energy_rcv_ip2);
            hpdc3 = itemView.findViewById(R.id.energy_rcv_ip3);
            gspm2b = itemView.findViewById(R.id.energy_rcv_2b);
            gspm3b1 = itemView.findViewById(R.id.energy_rcv_3b1);
            gspm3b2 = itemView.findViewById(R.id.energy_rcv_3b2);
            gspm3b3 = itemView.findViewById(R.id.energy_rcv_3b3);
            rotocast = itemView.findViewById(R.id.energy_rcv_rc);
            hpdc1units = itemView.findViewById(R.id.energy_rcv_ip1_units);
            hpdc2units = itemView.findViewById(R.id.energy_rcv_ip2_units);
            hpdc3units = itemView.findViewById(R.id.energy_rcv_ip3_units);
            gspm2bunits = itemView.findViewById(R.id.energy_rcv_2b_units);
            gspm3b1units = itemView.findViewById(R.id.energy_rcv_3b1_units);
            gspm3b2units = itemView.findViewById(R.id.energy_rcv_3b2_units);
            gspm3b3units = itemView.findViewById(R.id.energy_rcv_3b3_units);
            rotocastunits = itemView.findViewById(R.id.energy_rcv_rc_units);
        }
    }
}
