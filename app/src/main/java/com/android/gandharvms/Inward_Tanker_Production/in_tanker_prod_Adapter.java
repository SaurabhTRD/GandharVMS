package com.android.gandharvms.Inward_Tanker_Production;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class in_tanker_prod_Adapter extends RecyclerView.Adapter<in_tanker_prod_Adapter.myviewHolder> {

    private List<in_tanker_production_model> productiongrid;

    public in_tanker_prod_Adapter(List<in_tanker_production_model> productiongrid) {
        this.productiongrid = productiongrid;
    }

    @NonNull
    @Override
    public in_tanker_prod_Adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridlistview_row,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull in_tanker_prod_Adapter.myviewHolder holder, int position) {
        in_tanker_production_model obj = productiongrid.get(position);

        holder.sernum.setText(obj.getSerial_Number());
        holder.vehiclenum.setText(obj.getVehicle_Number());
        holder.material.setText(obj.getMaterial());

    }


    @Override
    public int getItemCount() {
        return productiongrid.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder{

        TextView sernum,vehiclenum,material;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            sernum=itemView.findViewById(R.id.textSerialNumber);
            vehiclenum= itemView.findViewById(R.id.textVehicleNumber);
            material=itemView.findViewById(R.id.textMaterial);

        }
    }
}
