package com.android.gandharvms.Inward_Truck_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.R;

import java.util.List;

public class InTruckStoreList_DataAdapter extends RecyclerView.Adapter<InTruckStoreList_DataAdapter.myviewholder>{
    Context context;
    private final List<CommonResponseModelForAllDepartment> storeDataList;

    public InTruckStoreList_DataAdapter(List<CommonResponseModelForAllDepartment> storeDataList) {
        this.storeDataList = storeDataList;
    }

    @NonNull
    @Override
    public InTruckStoreList_DataAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_truck_store_item,parent,false);
        return new InTruckStoreList_DataAdapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InTruckStoreList_DataAdapter.myviewholder holder, int position) {
        CommonResponseModelForAllDepartment storedatalist=storeDataList.get(position);
        int intimelength = storedatalist.getInTime().length();
        int outimelength = storedatalist.getOutTime().length();
        holder.etintime.setText(storedatalist.getInTime().substring(12,intimelength));
        holder.etserialnumber.setText(storedatalist.getSerialNo());
        holder.etvehicalnum.setText(storedatalist.getVehicleNo());
        holder.etpo.setText(storedatalist.getOA_PO_number());
        holder.etdate.setText(storedatalist.getDate());
        holder.etmaterialrdate.setText(storedatalist.getDate());
        holder.etmaterial.setText(storedatalist.getMaterial());
        holder.etqty.setText(String.valueOf(storedatalist.getQty()));
        holder.etoum.setText(String.valueOf(storedatalist.getReceiveQTYUOM()));
        holder.etremark.setText(storedatalist.getRemark());
        holder.outTime.setText(storedatalist.getOutTime().substring(12,outimelength));
        holder.etinvQty.setText(String.valueOf(storedatalist.getQty()));
        holder.etinvqtyuom.setText(String.valueOf(storedatalist.getQtyUOM()));
        holder.etinvDate.setText(storedatalist.getDate());
        holder.etinvNum.setText(storedatalist.getInvoiceNo());
        holder.extramaterials.setText(storedatalist.getStoreExtramaterials());
    }

    public int getItemCount() {
        return storeDataList.size();
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
