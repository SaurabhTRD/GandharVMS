package com.android.gandharvms.Inward_Tanker_Laboratory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;

public class In_Tanker_lab_Adapter extends RecyclerView.Adapter<In_Tanker_lab_Adapter.myviewholder> {

    ArrayList<In_Tanker_lab_list> datalist;

    public In_Tanker_lab_Adapter(ArrayList<In_Tanker_lab_list> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_ta_lab_item,parent,false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.ettime.setText(datalist.get(position).getIn_Time());
        holder.etpsample.setText(datalist.get(position).getSample_reciving());
        holder.etvehiclenumber.setText(datalist.get(position).getVehicle_Number());
        holder.etpapperance.setText(datalist.get(position).getApperance());
        holder.etpodor.setText(datalist.get(position).getOdor());
        holder.etpcolour.setText(datalist.get(position).getColor());
        holder.etpdensity.setText(datalist.get(position).getDensity());
        holder.etqty.setText(datalist.get(position).getQty());
        holder.etPrcstest.setText(datalist.get(position).getRcs_Test());
        holder.etpkv.setText(datalist.get(position).getKv40Value());
        holder.ethundred.setText(datalist.get(position).getKv100value());
        holder.etanline.setText(datalist.get(position).getAnline_Point());
        holder.etflash.setText(datalist.get(position).getFlash_Point());
        holder.etpaddtest.setText(datalist.get(position).getAdditional_test());
        holder.etpsamplere.setText(datalist.get(position).getSample_test());
        holder.etpremark.setText(datalist.get(position).getRemark());
        holder.etpsignQc.setText(datalist.get(position).getSign_of());
        holder.etpdatesignofsign.setText(datalist.get(position).getDate_and_Time());
        holder.outTime.setText(datalist.get(position).getOuttime());

        holder.etsupplier.setText(datalist.get(position).getSupplier());
        holder.discp.setText(datalist.get(position).getRemark_Discription());
        holder.visco.setText(datalist.get(position).getViscosity_Index());




    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView ettime, etpsample,etvehiclenumber,etpapperance,etpodor,etpcolour,etpdensity,etqty,etPrcstest,etpkv,ethundred,
                etanline,etflash,etpaddtest,etpsamplere,etpremark,etpsignQc,etpdatesignofsign,outTime,etsupplier,discp,visco;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ettime = itemView.findViewById(R.id.listintime);
            etpsample = itemView.findViewById(R.id.recdate);
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
