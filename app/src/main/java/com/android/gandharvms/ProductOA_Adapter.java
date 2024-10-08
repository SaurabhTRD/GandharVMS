package com.android.gandharvms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;

import java.util.List;

public class ProductOA_Adapter extends RecyclerView.Adapter<ProductOA_Adapter.MaterialViewHolder> {
    private List<productlistwithoanumber> materials;
    public ProductOA_Adapter(List<productlistwithoanumber> materials) {
        this.materials = materials;
    }

    @NonNull
    @Override
    public ProductOA_Adapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductwithoalist, parent, false);
        return new ProductOA_Adapter.MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOA_Adapter.MaterialViewHolder holder, int position) {
        productlistwithoanumber material = materials.get(position);
        holder.oanumber.setText(material.getOANumber());
        holder.product.setText(material.getProductName());
        holder.qty.setText(String.valueOf(material.getProductQty()));
        holder.qtyuom.setText(material.getProductQtyuom());
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView oanumber,product,qty,qtyuom;
        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            oanumber = itemView.findViewById(R.id.etoutwardforalloanumber);
            product = itemView.findViewById(R.id.etoutwardforallproduct);
            qty = itemView.findViewById(R.id.etoutwardforallTotalqty);
            qtyuom = itemView.findViewById(R.id.etoutwardforalltotqtyuom);
        }
    }
}
