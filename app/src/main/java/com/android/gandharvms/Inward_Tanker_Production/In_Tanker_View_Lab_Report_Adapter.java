package com.android.gandharvms.Inward_Tanker_Production;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;

public class In_Tanker_View_Lab_Report_Adapter extends RecyclerView.Adapter<In_Tanker_View_Lab_Report_Adapter.myviewholder> {

    ArrayList<In_Tanker_View_Lab_Report_List> ViewLabDataList;

    public In_Tanker_View_Lab_Report_Adapter(ArrayList<In_Tanker_View_Lab_Report_List> viewLabDataList){
        this.ViewLabDataList=viewLabDataList;
    }

    public myviewholder onCreateViewHolder(ViewGroup parent,int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tank_production_lab_report_item,parent,false);
        return new myviewholder(view);
    }

    public void onBindViewHolder(myviewholder holder,int position){
        holder.etMaterial.setText(ViewLabDataList.get(position).getMaterial());
        holder.etpsample.setText(ViewLabDataList.get(position).getSample_reciving());
        holder.etvehiclenumber.setText(ViewLabDataList.get(position).getVehicle_Number());
        holder.etpapperance.setText(ViewLabDataList.get(position).getApperance());
        holder.etpodor.setText(ViewLabDataList.get(position).getOdor());
        holder.etpcolour.setText(ViewLabDataList.get(position).getColor());
        holder.etpdensity.setText(ViewLabDataList.get(position).getDensity());
        holder.etqty.setText(ViewLabDataList.get(position).getQty());
        holder.etpremark.setText(ViewLabDataList.get(position).getRemark());



    }
    public int getItemCount() {
        return ViewLabDataList.size();
    }
    class myviewholder extends RecyclerView.ViewHolder{

        TextView etMaterial,etpsample,etvehiclenumber,etpapperance,etpodor,etpcolour,etpdensity,etqty,etpremark,outTime;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            etMaterial=itemView.findViewById(R.id.txtMaterial);
            etpsample = itemView.findViewById(R.id.recdate);
            etvehiclenumber = itemView.findViewById(R.id.listvehnumber);
            etpapperance = itemView.findViewById(R.id.listappreance);
            etpodor = itemView.findViewById(R.id.lsitodor);
            etpcolour = itemView.findViewById(R.id.listcolour);
            etpdensity = itemView.findViewById(R.id.listdensity);
            etqty = itemView.findViewById(R.id.listqty);
            etpremark = itemView.findViewById(R.id.listremark);
            outTime=itemView.findViewById(R.id.listouttime);
        }
    }
}