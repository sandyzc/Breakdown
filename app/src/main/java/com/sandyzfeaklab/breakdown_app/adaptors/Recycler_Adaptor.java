package com.sandyzfeaklab.breakdown_app.adaptors;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ramotion.foldingcell.FoldingCell;
import com.sandyzfeaklab.breakdown_app.Add_Sap_codes;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.dataModel.Model;
import com.sandyzfeaklab.breakdown_app.dataModel.Sap_code_Model;

import java.util.ArrayList;
import java.util.HashSet;

public class Recycler_Adaptor extends FirestoreRecyclerAdapter<Model,Recycler_Adaptor.ViewHolder> {

    Context context;
    private ArrayList<Model> data;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

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

        View v=null;
        ViewHolder mvViewHolder= null;

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());

        v= inflater.inflate(R.layout.log_card_view,parent,false);

        mvViewHolder= new ViewHolder(v);

        return mvViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {
        if (unfoldedIndexes.contains(i)) {
            viewHolder.fc.unfold(true);
        } else {
            viewHolder.fc.fold(false);
        }

       ArrayList<Sap_code_Model> sap_codesArrayList =model.getSap_no();



        String sap_no_saved ="";

        if (sap_codesArrayList!=null){
            for (int k=0; k<sap_codesArrayList.size();k++){
                sap_no_saved+=sap_codesArrayList.get(k).getSap_code()+",";
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

        viewHolder.start_Time.setText(model.getStart_Time());
        viewHolder.end_time.setText(model.getEnd_time());
        viewHolder.action_taken_by.setText(model.getAction_taken_by());
        viewHolder.status.setText(model.getStatus());

        if (model.getStatus().equals("Compleated")){
            viewHolder.status.setTextColor(Color.rgb(0,102,52));
        }
        else if (model.getStatus().equals("Pending")){
            viewHolder.status.setTextColor(Color.RED);
        }
    }

//    @Override
//    public int getItemCount() {
//
//        return data.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView equipment_name, work_Type,  operation,  part,  problem_desc,  action_taken,  spares_used,  sap_no,  start_Time,  end_time,  action_taken_by,status;

        FoldingCell fc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipment_name=itemView.findViewById(R.id.log_card_equip_name);
            work_Type=itemView.findViewById(R.id.log_card_work_type);
            operation=itemView.findViewById(R.id.log_card_operation);
            part=itemView.findViewById(R.id.log_card_partname);
            problem_desc=itemView.findViewById(R.id.log_card_prob_desc);
            action_taken=itemView.findViewById(R.id.log_card_action_taken);
            spares_used=itemView.findViewById(R.id.log_card_spares_used);
            sap_no=itemView.findViewById(R.id.log_card_sap_no);
            start_Time=itemView.findViewById(R.id.log_card_start_time);
            end_time=itemView.findViewById(R.id.log_card_end_time);
            action_taken_by=itemView.findViewById(R.id.log_card_attended_by);
            status=itemView.findViewById(R.id.status);



            fc=itemView.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });

        }
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }
}
