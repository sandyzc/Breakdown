package com.sandyzfeaklab.breakdown_app.adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sandyzfeaklab.breakdown_app.EditPending_Activity;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.DataInput_Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

public class Recycler_Adaptor extends FirestoreRecyclerAdapter<DataInput_Model, Recycler_Adaptor.ViewHolder> {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
    StorageReference riversRef, riversRef1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private ArrayList<DataInput_Model> data;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private DocumentReference documentReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Recycler_Adaptor(@NonNull FirestoreRecyclerOptions<DataInput_Model> options, Context context) {

        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public Recycler_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        ViewHolder mvViewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        v = inflater.inflate(R.layout.add_log_card, parent, false);

        mvViewHolder = new ViewHolder(v);

        return mvViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull DataInput_Model dataInputModel) {

        ArrayList<Sap_code_Model> sap_codesArrayList = dataInputModel.getSap_no();


        String sap_no_saved = "";

        if (sap_codesArrayList != null) {
            for (int k = 0; k < sap_codesArrayList.size(); k++) {
                sap_no_saved += sap_codesArrayList.get(k).getSap_code() + ",";
            }
        }

        if (dataInputModel.getUid() != null &&!dataInputModel.getUid().equals(user.getUid())){
            viewHolder.editdetails.setVisibility(View.GONE);
            viewHolder.delete.setVisibility(View.GONE);

        }

        viewHolder.sap_no.setText(sap_no_saved);
        viewHolder.equipment_name.setText(dataInputModel.getEquipment_name());
        viewHolder.work_Type.setText(dataInputModel.getWork_Type());
        viewHolder.operation.setText(dataInputModel.getOperation());
        viewHolder.part.setText(dataInputModel.getPart());
        viewHolder.problem_desc.setText(dataInputModel.getProblem_desc());
        viewHolder.action_taken.setText(dataInputModel.getAction_taken());
        viewHolder.spares_used.setText(dataInputModel.getSpares_used());
        viewHolder.time_taken.setText(dataInputModel.getTime_taken() + " Min");
        viewHolder.action_taken_by.setText(dataInputModel.getaction_taken_by());


        if (dataInputModel.getBeforeimageurl() != null && !dataInputModel.getBeforeimageurl().isEmpty()) {
            viewHolder.picholder.setVisibility(View.VISIBLE);
            viewHolder.piclable.setVisibility(View.VISIBLE);
            Picasso.get().load(dataInputModel.getBeforeimageurl()).placeholder(R.drawable.compleated).resize(100, 100).centerInside().into(viewHolder.beforepic);

        }
        if (dataInputModel.getAfterimageurl() != null && !dataInputModel.getAfterimageurl().isEmpty()) {
            viewHolder.picholder.setVisibility(View.VISIBLE);
            viewHolder.piclable.setVisibility(View.VISIBLE);
            viewHolder.afterpic.setVisibility(View.VISIBLE);
            Picasso.get().load(dataInputModel.getAfterimageurl()).placeholder(R.drawable.compleated).resize(100, 100).centerInside().into(viewHolder.afterpic);

        }


        String date = dataInputModel.getDate() + " || " + dataInputModel.getStart_Time();

        viewHolder.date.setText(date);


        if (dataInputModel.getStatus().equals("Compleated")) {
            viewHolder.status.setImageResource(R.drawable.compleated);
            viewHolder.line1.setBackgroundColor(Color.rgb(0, 102, 52));
            viewHolder.line2.setBackgroundColor(Color.rgb(0, 102, 52));
            viewHolder.problem_desc.setTextColor(Color.rgb(0, 102, 52));

        } else if (dataInputModel.getStatus().equals("Pending")) {
            viewHolder.pending_remark.setVisibility(View.VISIBLE);
            viewHolder.pending_remark.setText("* " + dataInputModel.getPending_remarks());
            viewHolder.status.setImageResource(R.drawable.pending);
            viewHolder.line1.setBackgroundColor(Color.RED);
            viewHolder.line2.setBackgroundColor(Color.RED);
            viewHolder.problem_desc.setTextColor(Color.RED);
        }

        viewHolder.showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.expandedview.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                    viewHolder.expandedview.setVisibility(View.VISIBLE);
                    viewHolder.showdetails.setImageResource(R.drawable.ic_baseline_expand_less_24);

                } else {
                    TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                    viewHolder.expandedview.setVisibility(View.GONE);
                    viewHolder.showdetails.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }


            }

        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = dataInputModel.getId();

                if (id != null) {

                    deleteEntry(dataInputModel, id);

                } else {
                    String savedID = getSnapshots().getSnapshot(i).getId();
                    deleteEntry(dataInputModel, savedID);


                }


            }
        });

        viewHolder.editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savedID = getSnapshots().getSnapshot(i).getId();
                documentReference = db.collection("log").document(savedID);
                Toast.makeText(v.getContext(), dataInputModel.getEquipment_name(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("id", savedID);
                Intent intent = new Intent(v.getContext(), EditPending_Activity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);


            }
        });
    }

    private void deleteEntry(DataInput_Model dataInputModel, String id) {

        riversRef = storageRef.child(id + " " + "BEFORE");
        riversRef1 = storageRef.child(id + " " + "AFTER");

        if (!dataInputModel.getAfterimageurl().equals("")) {
            riversRef1.delete().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context.getApplicationContext(), "Failed to delete after image", Toast.LENGTH_SHORT).show();
                }
            });

        }

        if (!dataInputModel.getBeforeimageurl().equals("")) {
            riversRef.delete().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context.getApplicationContext(), "Failed to delete before image", Toast.LENGTH_SHORT).show();
                }
            });
        }

        documentReference = db.collection("log").document(id);

        documentReference.delete();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, equipment_name, work_Type, operation, part, problem_desc, action_taken, spares_used, sap_no, pending_remark, action_taken_by, time_taken;
        ImageView status;
        ImageView showdetails, editdetails, beforepic, afterpic, delete;
        CardView cardView;
        View line1, line2;
        LinearLayout expandedview, piclable, picholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pending_remark = itemView.findViewById(R.id.log_card_pending_remak);
            editdetails = itemView.findViewById(R.id.editdetails);
            cardView = itemView.findViewById(R.id.card1);
            showdetails = itemView.findViewById(R.id.showdetails);
            expandedview = itemView.findViewById(R.id.expanded);
            piclable = itemView.findViewById(R.id.before_after_lable);
            picholder = itemView.findViewById(R.id.before_after_image);
            equipment_name = itemView.findViewById(R.id.log_card_equip_name);
            work_Type = itemView.findViewById(R.id.log_card_work_type);
            operation = itemView.findViewById(R.id.log_card_operation);
            part = itemView.findViewById(R.id.log_card_partname);
            delete = itemView.findViewById(R.id.delete);
            problem_desc = itemView.findViewById(R.id.log_card_prob_desc2);
            action_taken = itemView.findViewById(R.id.log_card_action_taken1);
            spares_used = itemView.findViewById(R.id.log_card_spares_used);
            sap_no = itemView.findViewById(R.id.log_card_sap_no);
            time_taken = itemView.findViewById(R.id.log_card_duruation);
//            start_Time=itemView.findViewById(R.id.log_card_start_time);
//           end_time=itemView.findViewById(R.id.log_card_end_time);
            action_taken_by = itemView.findViewById(R.id.log_card_attended_by);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date_log_card);
            line1 = itemView.findViewById(R.id.line1);
            line2 = itemView.findViewById(R.id.line2);
            beforepic = itemView.findViewById(R.id.before_pic_rcv);
            afterpic = itemView.findViewById(R.id.after_pic_RCV);




        }
    }


}
