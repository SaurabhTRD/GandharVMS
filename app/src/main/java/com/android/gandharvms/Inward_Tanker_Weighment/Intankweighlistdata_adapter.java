package com.android.gandharvms.Inward_Tanker_Weighment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;
import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Intankweighlistdata_adapter extends RecyclerView.Adapter<Intankweighlistdata_adapter.myviewholder> {
    Context context;
    /*ArrayList<InTanWeighRequestModel> data ;*/
    private final List<InTanWeighResponseModel> responsedatalist;

    public Intankweighlistdata_adapter(List<InTanWeighResponseModel> responsedatalist) {
        this.context = context;
        this.responsedatalist = responsedatalist;
    }

    public Intankweighlistdata_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_tr_we_item, parent, false);
        return new Intankweighlistdata_adapter.myviewholder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull Intankweighlistdata_adapter.myviewholder holder, int position) {
        InTanWeighResponseModel data = responsedatalist.get(position);
        if(data.getInTime()!=null)
        {
            holder.intime.setText(data.getInTime());
        }
        if(data.getOutTime()!=null)
        {
            holder.outTime.setText(data.getOutTime());
        }
        holder.serialnumber.setText(data.getSerialNo());
        holder.vehiclenumber.setText(data.getVehicleNo());
        holder.suppliername.setText(data.getPartyName());
        holder.materialname.setText(data.getMaterial());
        holder.driverno.setText(data.getDriver_MobileNo());
        holder.oanumber.setText(data.getOA_PO_number());
        if(data.getDate()!=null)
        {
            holder.date.setText(data.getDate());
        }
        /*holder.date.setText(dateFormat.format(data.getDate()));*/
        holder.grossweight.setText(data.getGrossWeight());
        /*holder.tareweight.setText(data.getTare_Weight());
        holder.netweight.setText(data.getNet_Weight());*/
        holder.batchnumber.setText(data.getRemark());
        holder.containerno.setText(String.valueOf(data.getContainerNo()));
        holder.sighby.setText(data.getSignBy());
        holder.shortagedip.setText(data.getShortageDip());
        holder.shortageweight.setText(data.getShortageWeight());


        /*Picasso.get()
                .load(BaseUrl + data.getInVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(BaseUrl + data.getInDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);

//                date,grossweight,tareweight,netweight,density,batchnumber,containerno,
//                sighby,datetime,shortagedip,shortageweight;*/
    }

    @Override
    public int getItemCount() {
        return responsedatalist.size();
    }

    /*public void setData(List<InTanWeighResponseModel> newData) {
        responsedatalist.clear();
        responsedatalist.addAll(newData);
        notifyDataSetChanged();
    }*/
    private String formatTimestamp(DateTime timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            return sqlDateFormat.format(timestamp);
        }
        return "";
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView intime, serialnumber, vehiclenumber, suppliername, materialname, driverno, oanumber, date, grossweight, batchnumber, containerno, outTime,
                sighby, shortagedip, shortageweight;
        ImageView invehicleimage, indriverimage;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.intime);
            serialnumber = itemView.findViewById(R.id.serialnumber);
            vehiclenumber = itemView.findViewById(R.id.vehiclenumber);
            suppliername = itemView.findViewById(R.id.suppliername);
            materialname = itemView.findViewById(R.id.materialname);
            driverno = itemView.findViewById(R.id.Driverno);
            oanumber = itemView.findViewById(R.id.oanumber);
            date = itemView.findViewById(R.id.date);
            grossweight = itemView.findViewById(R.id.grossweight);
            /*tareweight = itemView.findViewById(R.id.tareweight);
            netweight = itemView.findViewById(R.id.etnetweight);*/
            batchnumber = itemView.findViewById(R.id.batchnumber);
            containerno = itemView.findViewById(R.id.containerno);
            sighby = itemView.findViewById(R.id.signby);
            shortagedip = itemView.findViewById(R.id.shortagedip);
            shortageweight = itemView.findViewById(R.id.shortageweight);
            outTime = itemView.findViewById(R.id.listouttime);
            invehicleimage = itemView.findViewById(R.id.listInVehicleImage);
            indriverimage = itemView.findViewById(R.id.listInDriverImage);
        }
    }
}
