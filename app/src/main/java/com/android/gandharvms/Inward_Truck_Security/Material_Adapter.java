package com.android.gandharvms.Inward_Truck_Security;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class Material_Adapter extends RecyclerView.Adapter<Material_Adapter.MaterialViewHolder> {

    private List<matriallist> materials;
    public Material_Adapter(List<matriallist> materials) {
        this.materials = materials;
    }

    @NonNull
    @Override
    public Material_Adapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmayteriallist, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        matriallist material = materials.get(position);
        holder.materialTextView.setText(material.getMaterial());
        holder.qty.setText(String.valueOf(material.getQty()));
        holder.qtyuom.setText(material.getQtyuom());
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView materialTextView,qty,qtyuom;
        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            materialTextView = itemView.findViewById(R.id.etallmaterial);
            qty = itemView.findViewById(R.id.ethisdate);
            qtyuom = itemView.findViewById(R.id.ethisComplainant);
        }
    }
}
