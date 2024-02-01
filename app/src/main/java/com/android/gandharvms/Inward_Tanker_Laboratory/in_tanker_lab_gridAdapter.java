package com.android.gandharvms.Inward_Tanker_Laboratory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class in_tanker_lab_gridAdapter extends RecyclerView.Adapter<in_tanker_lab_gridAdapter.myviewHolder> {

    private List<in_Tanker_lab_model> insamplegrid;

    public in_tanker_lab_gridAdapter(List<in_Tanker_lab_model> insamplegrid) {
        this.insamplegrid = insamplegrid;
    }

    @NonNull
    @Override
    public in_tanker_lab_gridAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_row_header,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull in_tanker_lab_gridAdapter.myviewHolder holder, int position) {
        in_Tanker_lab_model obj = insamplegrid.get(position);

        holder.gdate.setText(obj.getDate_and_Time());
        holder.gvehicle.setText(obj.getVehicle_Number());
        holder.gmaterial.setText(obj.getMaterial());

    }


    @Override
    public int getItemCount() {
        return insamplegrid.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder{

        TextView gdate,gvehicle,gmaterial,gtatus;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            gdate = itemView.findViewById(R.id.textdate);
            gvehicle= itemView.findViewById(R.id.textVehicleNumber);
            gmaterial=itemView.findViewById(R.id.gridmaterial);
        }
    }
}
