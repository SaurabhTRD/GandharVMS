package com.android.gandharvms.Inward_Truck_store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;
import com.android.gandharvms.R;

import java.util.List;

public class ExtraMaterial_Adapter extends RecyclerView.Adapter<ExtraMaterial_Adapter.MaterialViewHolder> {

    private List<ExtraMaterial> materials;

    public ExtraMaterial_Adapter(List<ExtraMaterial> materials) {
        this.materials = materials;
    }

    @NonNull
    @Override
    public ExtraMaterial_Adapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storeextramaterial, parent, false);
        return new ExtraMaterial_Adapter.MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        ExtraMaterial material = materials.get(position);
        holder.stroematerial.setText(material.getMaterial());
        holder.stroeqty.setText(String.valueOf(material.getQty()));
        holder.storeqtyuom.setText(material.getQtyuom());
        holder.stroerecqty.setText(material.getRecivingqty());
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView stroematerial,stroeqty,storeqtyuom,stroerecqty;
        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            stroematerial = itemView.findViewById(R.id.etstorematerial);
            stroeqty = itemView.findViewById(R.id.etstroeqty);
            storeqtyuom = itemView.findViewById(R.id.etstoreunitofmeasurement);
            stroerecqty=itemView.findViewById(R.id.etstorereceiveqty);
        }
    }
}
