package com.sandyzfeaklab.breakdown_app.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.AddOildetails;
import com.sandyzfeaklab.breakdown_app.EditPending_Activity;
import com.sandyzfeaklab.breakdown_app.Oil_consumption;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.OIl_Consump_model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Oil_cons_list_adaptor extends FirestoreRecyclerAdapter<OIl_Consump_model, Oil_cons_list_adaptor.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     *
     * @param options
     */
    Context context;
    private DocumentReference documentReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public Oil_cons_list_adaptor(@NonNull FirestoreRecyclerOptions<OIl_Consump_model> options, Context context) {
        super(options);
        this.context=context;



    }

    @Override
    protected void onBindViewHolder(@NonNull Oil_cons_list_adaptor.ViewHolder viewHolder, int i, @NonNull OIl_Consump_model oIl_consump_model) {

        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("dd-MM-yyyy").format(oIl_consump_model.getTimestamp());

        if (oIl_consump_model.getUid() != null &&!oIl_consump_model.getUid().equals(user.getUid())){
            viewHolder.modify.setVisibility(View.GONE);
        }

        viewHolder.name.setText(oIl_consump_model.getUsername());
        viewHolder.date.setText(date1);
        viewHolder.equip.setText(oIl_consump_model.getEquip());
        viewHolder.remarks.setText(oIl_consump_model.getReason());
        viewHolder.qty.setText(String.valueOf(oIl_consump_model.getQty()));
        viewHolder.oilType.setText(oIl_consump_model.getOilType());

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String savedID = getSnapshots().getSnapshot(i).getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", savedID);

                Intent intent = new Intent(v.getContext(), AddOildetails.class);
                intent.putExtra("type","update");
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }

        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String savedID = getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).getId();
                documentReference = db.collection("oil Consump").document(savedID);
                documentReference.delete();



            }
        });
    }

    @NonNull
    @Override
    public Oil_cons_list_adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        Oil_cons_list_adaptor.ViewHolder mvViewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        v = inflater.inflate(R.layout.oil_cons_card, parent, false);

        mvViewHolder = new ViewHolder(v);

        return mvViewHolder;

    }


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     */

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView qty,equip,remarks,date,oilType,name;
        ImageView edit,delete;
        LinearLayout modify;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.oil_cons_rcv_name);
            modify=itemView.findViewById(R.id.modifiy);
            edit=itemView.findViewById(R.id.oil_cons_rcv_edit);
            delete=itemView.findViewById(R.id.oil_cons_rcv_del);
            qty=itemView.findViewById(R.id.oil_cons_rcv_qty);
            equip=itemView.findViewById(R.id.oil_cons_rcv_equip);
            remarks=itemView.findViewById(R.id.oil_cons_rcv_reason);
            date=itemView.findViewById(R.id.oil_cons_rcv_date);
            oilType=itemView.findViewById(R.id.oil_cons_rcv_oil_type);
        }
    }

}




