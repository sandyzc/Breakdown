package com.sandyzfeaklab.breakdown_app.database.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.adaptors.Energy_Rcv_Adaptor;
import com.sandyzfeaklab.breakdown_app.database.Material_List;

import java.util.ArrayList;
import java.util.List;

public class material_Adaptor extends RecyclerView.Adapter<material_Adaptor.MaterialHolder> {
    private List<Material_List> material_lists= new ArrayList<>();

    @NonNull
    @Override
    public MaterialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;
        material_Adaptor.MaterialHolder mViewholder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.material_stock_card, parent, false);
        mViewholder = new material_Adaptor.MaterialHolder(view);

        return mViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialHolder holder, int position) {

        Material_List current_list = material_lists.get(position);

        holder.equipment_used_in.setText(current_list.getMachine() + ","+current_list.getUsed_in_1()+","+current_list.getUsed_in_2()+","
        +current_list.getUsed_in_3());
        holder.material_description.setText(current_list.getDescription());
        holder.sapcode.setText(current_list.getSap_code());
        holder.stock.setText("STOCK : "+current_list.getStock());

    }

    @Override
    public int getItemCount() {
        return material_lists.size();
    }

    public class MaterialHolder extends RecyclerView.ViewHolder {

        TextView sapcode,stock,material_description,equipment_used_in;

        public MaterialHolder(@NonNull View itemView) {
            super(itemView);

            sapcode=itemView.findViewById(R.id.material_card_sapcode);
            stock=itemView.findViewById(R.id.material_card_stock);
            material_description=itemView.findViewById(R.id.material_card_description);
            equipment_used_in=itemView.findViewById(R.id.material_card_equipment);
        }
    }

    public void setMaterial_lists(List<Material_List> atttach_material_lists){
       this.material_lists =atttach_material_lists;
       notifyDataSetChanged();
    }

    void filter (List<Material_List> filterdlist){
        this.material_lists=filterdlist;

        notifyDataSetChanged();
    }
}
