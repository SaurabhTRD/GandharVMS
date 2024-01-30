package com.android.gandharvms.Inward_Tanker_Sampling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class gridAdapter_in_tanker_sampling extends RecyclerView.Adapter<gridAdapter_in_tanker_sampling.myviewHolder> {

    private List<gridmodel_in_tanker_sampling> samplegrid;
    public gridAdapter_in_tanker_sampling(List<gridmodel_in_tanker_sampling> gridmodel){
        this.samplegrid = gridmodel;
    }

    @NonNull
    @Override
    public gridAdapter_in_tanker_sampling.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_column_header,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull gridAdapter_in_tanker_sampling.myviewHolder holder, int position) {

        gridmodel_in_tanker_sampling grid = samplegrid.get(position);
        holder.etdtate.setText(grid.getDate());
        holder.etvehiclenum.setText(grid.getVehicle_Number());
        holder.etrecevingtime.setText(grid.getSample_Reciving_Time());
    }


    @Override
    public int getItemCount() {
        return samplegrid.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        TextView etdtate,etvehiclenum,etrecevingtime,etstatus;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            etdtate = itemView.findViewById(R.id.textdate);
            etvehiclenum = itemView.findViewById(R.id.textVehicleNumber);
            etrecevingtime = itemView.findViewById(R.id.samplerectime);
        }
    }
}
