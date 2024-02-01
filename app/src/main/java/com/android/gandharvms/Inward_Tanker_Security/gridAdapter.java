package com.android.gandharvms.Inward_Tanker_Security;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class gridAdapter extends RecyclerView.Adapter<gridAdapter.myviewHolder> {

    private List<gridmodel> Gridmodel;

    public gridAdapter(List<gridmodel> Gridmodel) {
        this.Gridmodel = Gridmodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_cell,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull gridAdapter.myviewHolder holder, int position) {
        gridmodel grid = Gridmodel.get(position);

        holder.sernum.setText(grid.getSerialNumber());
        holder.vehiclenum.setText(grid.getVehicalnumber());
        holder.material.setText(grid.getMaterial());

    }


    public int getItemCount() {
        return Gridmodel.size();
    }

    public  static  class myviewHolder extends RecyclerView.ViewHolder {
        TextView sernum,vehiclenum,material;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            sernum = itemView.findViewById(R.id.textSerialNumber);
            vehiclenum = itemView.findViewById(R.id.textVehicleNumber);
            material = itemView.findViewById(R.id.textMaterial);
        }
    }
}
