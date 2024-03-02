package com.android.gandharvms.Inward_Tanker_Laboratory;

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

public class InTanLabListData_Adapter extends RecyclerView.Adapter<InTanLabListData_Adapter.myviewholder> {
    Context context;
    private final List<CommonResponseModelForAllDepartment> responselabdatalist;

    public InTanLabListData_Adapter(List<CommonResponseModelForAllDepartment> responselabdatalist) {
        this.responselabdatalist = responselabdatalist;
    }

    public InTanLabListData_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_ta_lab_item,parent,false);
        return new InTanLabListData_Adapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        CommonResponseModelForAllDepartment datalist=responselabdatalist.get(position);
        int intimelength = datalist.getInTime().length();
        int outimelength = datalist.getOutTime().length();
        holder.ettime.setText(datalist.getInTime().substring(12,intimelength));
        holder.etvehiclenumber.setText(datalist.getVehicleNo());
        holder.etpapperance.setText(datalist.getApperance());
        holder.etpodor.setText(datalist.getOdor());
        holder.etpcolour.setText(datalist.getColor());
        holder.etpdensity.setText(String.valueOf(datalist.getDensity()));
        holder.etqty.setText(String.valueOf(datalist.getLQty()));
        holder.etPrcstest.setText(datalist.getRcsTest());
        holder.etpkv.setText(String.valueOf(datalist.get_40KV()));
        holder.ethundred.setText(String.valueOf(datalist.get_100KV()));
        holder.etanline.setText(String.valueOf(datalist.getAnLinePoint()));
        holder.etflash.setText(String.valueOf(datalist.getFlashPoint()));
        holder.etpaddtest.setText(datalist.getAdditionalTest());
        holder.etpsamplere.setText(datalist.getSampleTest());
        holder.etpremark.setText(datalist.getRemark());
        holder.etpsignQc.setText(datalist.getSignOf());
        holder.etpdatesignofsign.setText(datalist.getDateAndTime());
        holder.outTime.setText(datalist.getOutTime().substring(12,outimelength));
        holder.etsupplier.setText(datalist.getPartyName());
        holder.discp.setText(datalist.getRemarkDescription());
        holder.visco.setText(String.valueOf(datalist.getViscosityIndex()));
    }
    @Override
    public int getItemCount() {
        return responselabdatalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView ettime, etpsample,etvehiclenumber,etpapperance,etpodor,etpcolour,etpdensity,etqty,etPrcstest,etpkv,ethundred,
                etanline,etflash,etpaddtest,etpsamplere,etpremark,etpsignQc,etpdatesignofsign,outTime,etsupplier,discp,visco;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ettime = itemView.findViewById(R.id.listintime);
            etvehiclenumber = itemView.findViewById(R.id.listvehnumber);
            etpapperance = itemView.findViewById(R.id.listappreance);
            etpodor = itemView.findViewById(R.id.lsitodor);
            etpcolour = itemView.findViewById(R.id.listcolour);
            etpdensity = itemView.findViewById(R.id.listdensity);
            etqty = itemView.findViewById(R.id.listqty);
            etPrcstest = itemView.findViewById(R.id.listrcstest);
            etpkv = itemView.findViewById(R.id.list40kv);
            ethundred = itemView.findViewById(R.id.list100kv);
            etanline = itemView.findViewById(R.id.listaniline);
            etflash = itemView.findViewById(R.id.listflashpoint);
            etpaddtest = itemView.findViewById(R.id.listadditionaltest);
            etpsamplere = itemView.findViewById(R.id.listresultrelasedate);
            etpremark = itemView.findViewById(R.id.listremark);
            etpsignQc = itemView.findViewById(R.id.listsignofqc);
            etpdatesignofsign = itemView.findViewById(R.id.listdtsign);
            outTime=itemView.findViewById(R.id.listouttime);
            etsupplier=itemView.findViewById(R.id.listsupplier);
            discp = itemView.findViewById(R.id.listdiscription);
            visco = itemView.findViewById(R.id.listviscosity);
        }
    }
}
