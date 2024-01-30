package com.android.gandharvms.Inward_Tanker_Weighment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Tanker_Security.gridmodel;
import com.android.gandharvms.R;

import java.util.List;

public class in_tanker_weigment_gridAdapter extends RecyclerView.Adapter<in_tanker_weigment_gridAdapter.myviewHolder> {

    private List<gridmodel> Gridmodel;

    public in_tanker_weigment_gridAdapter(List<gridmodel> Gridmodel) {
        this.Gridmodel = Gridmodel;
    }
    @NonNull
    @Override
    public in_tanker_weigment_gridAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_cell,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull in_tanker_weigment_gridAdapter.myviewHolder holder, int position) {

        gridmodel grid = Gridmodel.get(position);

        holder.sernum.setText(grid.getSerialNumber());
        holder.vehiclenum.setText(grid.getVehicalnumber());
        holder.material.setText(grid.getMaterial());
    }


    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        TextView sernum,vehiclenum,material;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            sernum = itemView.findViewById(R.id.textSerialNumber);
            vehiclenum = itemView.findViewById(R.id.textVehicleNumber);
            material = itemView.findViewById(R.id.textMaterial);
        }
    }
}
