package com.android.gandharvms.Inward_Tanker_Sampling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.R;

import java.util.List;

public class In_Tanker_sa_Adapter extends RecyclerView.Adapter<In_Tanker_sa_Adapter.myviewHolder>
{

    List<CommonResponseModelForAllDepartment> datalist;

    public In_Tanker_sa_Adapter(List<CommonResponseModelForAllDepartment> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_tr_sa_item,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        holder.t1.setText(datalist.get(position).getDate());
        holder.t2.setText(datalist.get(position).getSampleReceivingTime());
        holder.t3.setText(datalist.get(position).getSampleSubmittedTime());
        holder.t4.setText(datalist.get(position).getVehicleNo());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.listdate);
            t2 = itemView.findViewById(R.id.listrecivingtime);
            t3 = itemView.findViewById(R.id.listsubtime);
            t4 = itemView.findViewById(R.id.listvehiclenumber);

        }
    }
}
