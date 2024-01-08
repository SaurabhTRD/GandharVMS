package com.android.gandharvms.Inward_Tanker_Security;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;

public class In_Tanker_Se_Adapter extends RecyclerView.Adapter<In_Tanker_Se_Adapter.myviewHolder> {



    Context context;
    ArrayList<In_Tanker_Security_list>inTankerSecurityListArrayList;

    public In_Tanker_Se_Adapter(Context context, ArrayList<In_Tanker_Security_list> inTankerSecurityListArrayList) {
        this.context = context;
        this.inTankerSecurityListArrayList = inTankerSecurityListArrayList;
    }

    @NonNull
    @Override
    public In_Tanker_Se_Adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.in_tr_se_item,parent,false);
        return new myviewHolder(v);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {

//        In_Tanker_Security_list inTankerSecurityList = inTankerSecurityListArrayList.get(position);


        holder.serialnumber.setText(inTankerSecurityListArrayList.get(position).getSerialNumber());
        holder.vehiclenumber.setText(inTankerSecurityListArrayList.get(position).getVehicalnumber());
        holder.invoiceno.setText(inTankerSecurityListArrayList.get(position).getInvoiceno());
        holder.date.setText(inTankerSecurityListArrayList.get(position).getDate());
        holder.partyname.setText(inTankerSecurityListArrayList.get(position).getPartyname());
        holder.material.setText(inTankerSecurityListArrayList.get(position).getMaterial());
        holder.qty.setText(inTankerSecurityListArrayList.get(position).getQty());
        holder.uom.setText(inTankerSecurityListArrayList.get(position).getUom());
        holder.netweight.setText(inTankerSecurityListArrayList.get(position).getNetweight());
        holder.intime.setText(inTankerSecurityListArrayList.get(position).getIntime());
        holder.outTime.setText(inTankerSecurityListArrayList.get(position).getOuttime());
        holder.qtyuom.setText(inTankerSecurityListArrayList.get(position).getQtyuom());
        holder.netweightuom.setText(inTankerSecurityListArrayList.get(position).getNetweightuom());
        holder.extramaterials.setText(inTankerSecurityListArrayList.get(position).getExtramaterials());







//        holder.selectregister.setText(inTankerSecurityList.SelectRegister);
//        holder.serialnumber.setText(inTankerSecurityList.SerialNumber);
//        holder.vehiclenumber.setText(inTankerSecurityList.vehicalnumber);
//        holder.invoiceno.setText(inTankerSecurityList.invoiceno);
//        holder.date.setText(inTankerSecurityList.date);
//        holder.partyname.setText(inTankerSecurityList.partyname);
//        holder.material.setText(inTankerSecurityList.material);
//        holder.qty.setText(inTankerSecurityList.qty);
//        holder.uom.setText(inTankerSecurityList.uom);
//        holder.netweight.setText(inTankerSecurityList.netweight);
//        holder.intime.setText(inTankerSecurityList.intime);

    }

    @NonNull
    @Override
    public int getItemCount() {
      return inTankerSecurityListArrayList.size();
    }

    public static class myviewHolder extends RecyclerView.ViewHolder{

        TextView serialnumber,vehiclenumber,invoiceno,date,partyname,material,qty,uom,netweight,intime,outTime,qtyuom,netweightuom,extramaterials;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);


            serialnumber = itemView.findViewById(R.id.listserialnumber);
            vehiclenumber = itemView.findViewById(R.id.listvehiclenumber);
            invoiceno= itemView.findViewById(R.id.listinvoiceno);
            date = itemView.findViewById(R.id.listdate);
            partyname = itemView.findViewById(R.id.listsupplier);
            material = itemView.findViewById(R.id.listmaterial);
            qty = itemView.findViewById(R.id.listqty);
            uom= itemView.findViewById(R.id.listuom);
            netweight= itemView.findViewById(R.id.listnetweight);
            intime = itemView.findViewById(R.id.listintime);
            outTime = itemView.findViewById(R.id.listouttime);
            qtyuom= itemView.findViewById(R.id.listuom);
            netweightuom=itemView.findViewById(R.id.listnetweightuom);
            extramaterials=itemView.findViewById(R.id.listextramaterials);

        }
    }
}
