package com.android.gandharvms.Inward_Tanker_Security;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class In_Tanker_Se_Adapter extends RecyclerView.Adapter<In_Tanker_Se_Adapter.myviewHolder> {



    Context context;
    ArrayList<In_Tanker_Security_list>inTankerSecurityListArrayList;
    private final List<CommonResponseModelForAllDepartment> listingResponseInTankerSec;

    public In_Tanker_Se_Adapter(List<CommonResponseModelForAllDepartment>listingResponseInTankerSec ) {
        this.context = context;
        this.listingResponseInTankerSec = listingResponseInTankerSec;
    }


    @NonNull
    @Override
    public In_Tanker_Se_Adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View v = LayoutInflater.from(context).inflate(R.layout.in_tr_se_item,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_tr_se_item,parent,false);
        return new myviewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull  In_Tanker_Se_Adapter.myviewHolder holder, int position) {
        CommonResponseModelForAllDepartment data = listingResponseInTankerSec.get(position);
        holder.serialnumber.setText(data.getSerialNo());
        holder.vehiclenumber.setText(data.getVehicleNo());
        holder.invoiceno.setText(data.getInvoiceNo());
        if (data.getDate() != null){
            holder.date.setText(data.getInTime());
        }
        holder.partyname.setText(data.getPartyName());
        holder.material.setText(data.getMaterial());
        if (data.getInTime() != null){
            holder.intime.setText(data.getInTime());
        }
        if (data.getOutTime()!= null){
            holder.outTime.setText(data.getOutTime());
        }
        holder.extramaterials.setText(data.getExtramaterials());
        holder.remark.setText(data.getRemark());
        holder.oapo.setText(data.getOA_PO_number());
        holder.mob.setText(String.valueOf(data.getDriver_MobileNo()));
        holder.reoprtingre.setText(data.getReportingRemark());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                @SuppressLint("RestrictedApi") Intent intent = new Intent(context, MenuView.ItemView.class);
                Intent intent1 = new Intent(context,android.R.layout.class);

            }
        });
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
      return listingResponseInTankerSec.size();
    }

    public static class myviewHolder extends RecyclerView.ViewHolder{

        TextView serialnumber,vehiclenumber,invoiceno,date,partyname,material,qty,uom,netweight,intime,outTime,qtyuom,netweightuom,extramaterials,remark,oapo,mob
                ,reoprtingre;
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

            remark = itemView.findViewById(R.id.listremark);
            oapo=itemView.findViewById(R.id.listoaponum);
            mob =itemView.findViewById(R.id.listdrivernumber);
            reoprtingre = itemView.findViewById(R.id.listreportingremark);



        }
    }
}
