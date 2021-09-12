package com.sandyzfeaklab.breakdown_app.adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramotion.foldingcell.FoldingCell;
import com.sandyzfeaklab.breakdown_app.Add_Sap_codes;
import com.sandyzfeaklab.breakdown_app.Data_input;
import com.sandyzfeaklab.breakdown_app.EditPending_Activity;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

public class Recycler_Adaptor extends FirestoreRecyclerAdapter<Model, Recycler_Adaptor.ViewHolder> {

    Context context;
    private ArrayList<Model> data;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Recycler_Adaptor(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }


    @NonNull
    @Override
    public Recycler_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        ViewHolder mvViewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        v = inflater.inflate(R.layout.cell_title_layout, parent, false);

        mvViewHolder = new ViewHolder(v);

        return mvViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {

        ArrayList<Sap_code_Model> sap_codesArrayList = model.getSap_no();


        String sap_no_saved = "";

        if (sap_codesArrayList != null) {
            for (int k = 0; k < sap_codesArrayList.size(); k++) {
                sap_no_saved += sap_codesArrayList.get(k).getSap_code() + ",";
            }
        }

        viewHolder.sap_no.setText(sap_no_saved);
        viewHolder.equipment_name.setText(model.getEquipment_name());
        viewHolder.work_Type.setText(model.getWork_Type());
        viewHolder.operation.setText(model.getOperation());
        viewHolder.part.setText(model.getPart());
        viewHolder.problem_desc.setText(model.getProblem_desc());
        viewHolder.action_taken.setText(model.getAction_taken());
        viewHolder.spares_used.setText(model.getSpares_used());
        viewHolder.time_taken.setText(String.valueOf(model.getTime_taken())+" Min");
        viewHolder.action_taken_by.setText(model.getaction_taken_by());



        if (model.getBeforeimageurl()!=null&& !model.getBeforeimageurl().isEmpty() ){
            viewHolder.picholder.setVisibility(View.VISIBLE);
            viewHolder.piclable.setVisibility(View.VISIBLE);
            Picasso.get().load(model.getBeforeimageurl()).placeholder(R.drawable.compleated).resize(100,100).centerInside().into(viewHolder.beforepic);

        }
        if (model.getAfterimageurl()!=null&& !model.getAfterimageurl().isEmpty() ){
            viewHolder.picholder.setVisibility(View.VISIBLE);
            viewHolder.piclable.setVisibility(View.VISIBLE);
            viewHolder.afterpic.setVisibility(View.VISIBLE);
            Picasso.get().load(model.getAfterimageurl()).placeholder(R.drawable.compleated).resize(100,100).centerInside().into(viewHolder.afterpic);

        }





        String date= model.getDate()+ " || "+model. getStart_Time();

        viewHolder.date.setText(date);


        if (model.getStatus().equals("Compleated")) {
            viewHolder.status.setImageResource(R.drawable.compleated);
            viewHolder.line1.setBackgroundColor(Color.rgb(0, 102, 52));
            viewHolder.line2.setBackgroundColor(Color.rgb(0, 102, 52));
            viewHolder.problem_desc.setTextColor(Color.rgb(0, 102, 52));
            viewHolder.editdetails.setVisibility(View.GONE);
        } else if (model.getStatus().equals("Pending")) {
            viewHolder.pending_remark.setVisibility(View.VISIBLE);
            viewHolder.pending_remark.setText("* "+model.getPending_remarks());
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
                documentReference = db.collection("log").document(model.getId());

                documentReference.delete();
            }
        });

        viewHolder.editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String equipment_name, String work_Type, String operation, String part, String problem_desc, String action_taken, String spares_used, ArrayList<Sap_code_Model> sap_no, String start_Time, String end_time,
                //                 String action_taken_by, String status1, String Date, int time,String id,String pending_remarks
                Toast.makeText(v.getContext(), model.getEquipment_name(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("id",model.getId());
                Intent intent = new Intent(v.getContext(), EditPending_Activity.class);
                intent.putExtras(bundle);

                v.getContext().startActivity(intent);

            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, equipment_name, work_Type, operation, part, problem_desc, action_taken, spares_used, sap_no, start_Time, pending_remark, action_taken_by, time_taken;
        ImageView status;
        ImageView showdetails, editdetails,beforepic,afterpic,delete;
        CardView cardView;
        View line1, line2;
        FoldingCell fc;
        LinearLayout expandedview,piclable,picholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pending_remark=itemView.findViewById(R.id.log_card_pending_remak);
            editdetails = itemView.findViewById(R.id.editdetails);
            cardView = itemView.findViewById(R.id.card1);
            showdetails = itemView.findViewById(R.id.showdetails);
            expandedview = itemView.findViewById(R.id.expanded);
            piclable=itemView.findViewById(R.id.before_after_lable);
            picholder=itemView.findViewById(R.id.before_after_image);
            equipment_name = itemView.findViewById(R.id.log_card_equip_name);
            work_Type = itemView.findViewById(R.id.log_card_work_type);
            operation = itemView.findViewById(R.id.log_card_operation);
            part = itemView.findViewById(R.id.log_card_partname);
            delete=itemView.findViewById(R.id.delete);
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
            beforepic=itemView.findViewById(R.id.before_pic_rcv);
            afterpic=itemView.findViewById(R.id.after_pic_RCV);




        }
    }


}
