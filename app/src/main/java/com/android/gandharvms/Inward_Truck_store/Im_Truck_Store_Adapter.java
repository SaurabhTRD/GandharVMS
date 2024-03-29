package com.android.gandharvms.Inward_Truck_store;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;

public class Im_Truck_Store_Adapter extends RecyclerView.Adapter<Im_Truck_Store_Adapter.myviewholder> {

    ArrayList<In_Truck_Store_list> datalist;

    public Im_Truck_Store_Adapter(ArrayList<In_Truck_Store_list> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_truck_store_item,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        //,,,,,,,,,;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");

        holder.etintime.setText(datalist.get(position).getIn_Time());
        holder.etserialnumber.setText(datalist.get(position).getSerial_Number());
        holder.etvehicalnum.setText(datalist.get(position).getVehicle_Number());
        holder.etpo.setText(datalist.get(position).getPO_No());
        holder.etdate.setText(dateFormat.format(datalist.get(position).getPo_Date().toDate()));
        holder.etmaterialrdate.setText(datalist.get(position).getMaterial_Rec_Date());
        holder.etmaterial.setText(datalist.get(position).getMaterial());
        holder.etqty.setText(datalist.get(position).getQty());
        holder.etoum.setText(datalist.get(position).getReceiveQTY_Uom());
        holder.etremark.setText(datalist.get(position).getRemarks());
        holder.outTime.setText(datalist.get(position).getOutTime());
        holder.etinvQty.setText(datalist.get(position).getInvoice_Quantity());
        holder.etinvqtyuom.setText(datalist.get(position).getInv_QuantityUom());
        holder.etinvDate.setText(datalist.get(position).getInvoice_Date());
        holder.etinvNum.setText(datalist.get(position).getInvoice_Number());
        holder.extramaterials.setText(datalist.get(position).getExtramaterials());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView etintime,etinvqtyuom,etserialnumber,etvehicalnum,etpo,etdate,etmaterialrdate,
                etmaterial,etqty,etoum,etremark,outTime,etinvQty,etinvDate,etinvNum,extramaterials;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            etintime =itemView.findViewById(R.id.listintime);
            etserialnumber = itemView.findViewById(R.id.listserialnumber);
            etvehicalnum= itemView.findViewById(R.id.listvehiclenumber);
            etpo = itemView.findViewById(R.id.listpo);
            etdate = itemView.findViewById(R.id.listmaterialdate);
            etmaterialrdate= itemView.findViewById(R.id.listmaterialrecdate);
            etmaterial = itemView.findViewById(R.id.listmaterial);
            etqty = itemView.findViewById(R.id.listqty);
            etoum = itemView.findViewById(R.id.listuom);
            etremark = itemView.findViewById(R.id.listremark);
            outTime=itemView.findViewById(R.id.listouttime);
            etinvQty=itemView.findViewById(R.id.listinvQty);
            etinvqtyuom=itemView.findViewById(R.id.listinvqtyuom);
            etinvDate=itemView.findViewById(R.id.listinvDate);
            etinvNum=itemView.findViewById(R.id.listinvNum);
            extramaterials=itemView.findViewById(R.id.listextramaterials);
        }
    }
}
