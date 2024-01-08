package com.android.gandharvms.Inward_Truck_Weighment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;

public class In_Truck_weighment_Adapter extends RecyclerView.Adapter<In_Truck_weighment_Adapter.myviewholder> {

    ArrayList<In_Truck_weigment_list> datalist;

    public In_Truck_weighment_Adapter(ArrayList<In_Truck_weigment_list> datalist) {
        this.datalist = datalist;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_truck_weighment_item,parent,false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.etintime.setText(datalist.get(position).getIn_Time());
        holder.etserialnumber.setText(datalist.get(position).getSerial_Number());
        holder.etvehicalnumber.setText(datalist.get(position).getVehicle_Number());
        holder.etsupplier.setText(datalist.get(position).getSupplier());
        holder.etmaterial.setText(datalist.get(position).getMaterial());
        holder.etcustomer.setText(datalist.get(position).getCustomer());
        holder.etdriver.setText(datalist.get(position).getDriver_No());
        holder.etoanumber.setText(datalist.get(position).getOA_Number());
        holder.etdate.setText(datalist.get(position).getDate());
        holder.etgrossweight.setText(datalist.get(position).getGross_Weight());
        holder.ettareweight.setText(datalist.get(position).getTare_Weight());
        holder.etnetweight.setText(datalist.get(position).getNet_Weight());
        holder.etdensity.setText(datalist.get(position).getDensity());
        holder.etbatchno.setText(datalist.get(position).getBatch_No());
        holder.etsignby.setText(datalist.get(position).getSign_By());
        holder.etdatetime.setText(datalist.get(position).getDate_Time());
        holder.outTime.setText(datalist.get(position).getOutTime());

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView etintime, etserialnumber,etvehicalnumber,etsupplier,etmaterial,etcustomer,etdriver,etoanumber,etdate,
                etgrossweight,ettareweight,etnetweight,etdensity,etbatchno,etsignby,etdatetime,outTime;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            etintime = itemView.findViewById(R.id.listintime);
            etserialnumber = itemView.findViewById(R.id.listserailnumber);
            etvehicalnumber= itemView.findViewById(R.id.vehiclenumber);
            etsupplier = itemView.findViewById(R.id.suppliername);
            etmaterial= itemView.findViewById(R.id.materialname);
            etcustomer =itemView.findViewById(R.id.custname);
            etdriver= itemView.findViewById(R.id.listdriver);
            etoanumber=itemView.findViewById(R.id.oanumber);
            etdate= itemView.findViewById(R.id.date);
            etgrossweight= itemView.findViewById(R.id.grossweight);
            ettareweight = itemView.findViewById(R.id.tareweight);
            etnetweight = itemView.findViewById(R.id.etnetweight);
            etdensity = itemView.findViewById(R.id.density);
            etbatchno = itemView.findViewById(R.id.batchnumber);
            etsignby = itemView.findViewById(R.id.signby);
            etdatetime = itemView.findViewById(R.id.datetime);
            outTime=itemView.findViewById(R.id.listouttime);
        }
    }
}
