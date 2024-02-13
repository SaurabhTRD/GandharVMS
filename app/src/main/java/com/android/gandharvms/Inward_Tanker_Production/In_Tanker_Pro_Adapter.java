package com.android.gandharvms.Inward_Tanker_Production;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Tanker_Security.ListingResponse_InTankerSequrity;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;

public class In_Tanker_Pro_Adapter extends RecyclerView.Adapter<In_Tanker_Pro_Adapter.myviewholder> {

    Context context;
    ArrayList<In_Tanker_Production_list> datalist;
    private final List<ListingResponse_InTankerproduction> listingResponseInTankerproductions;


    public In_Tanker_Pro_Adapter(List<ListingResponse_InTankerproduction> listingResponseInTankerproductions) {
        this.context = context;
//        this.datalist = datalist;
        this.listingResponseInTankerproductions = listingResponseInTankerproductions;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_ta_pro_item,parent,false);
       return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        ListingResponse_InTankerproduction data = listingResponseInTankerproductions.get(position);
        if (data.getInTime()!= null){
            holder.etint.setText(data.getInTime());
        }
        holder.etreq.setText(data.getSerialNo());
        holder.ettankno.setText(String.valueOf(data.getAboveMaterialIsUnloadInTK()));
        holder.etconbyop.setText(String.valueOf(data.getUnloadAboveMaterialInTK()));
        if (data.getOutTime() != null){
            holder.outTime.setText(data.getOutTime());
        }
        holder.Material.setText(data.getOutTime());
        holder.Vehicle_Number.setText(data.getVehicleNo());
        if (data.getDate() != null){
            holder.etconunloadDateTime.setText(data.getDate());
        }


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
//        holder.etint.setText(datalist.get(position).getIn_Time());
//        holder.etreq.setText(datalist.get(position).getReq_to_unload());
//        holder.ettankno.setText(datalist.get(position).getTank_Number_Request());
//        holder.etconbyop.setText(datalist.get(position).getConfirm_unload());
//        holder.tanknoun.setText(datalist.get(position).getTank_Number());
//        holder.outTime.setText(datalist.get(position).getOutTime());
//        holder.Material.setText(datalist.get(position).getMaterial());
//        holder.Vehicle_Number.setText(datalist.get(position).getVehicle_Number());
//        holder.etconunloadDateTime.setText(dateFormat.format(datalist.get(position).getCon_unload_DT().toDate()));
    }
    @Override
    public int getItemCount() {
        return listingResponseInTankerproductions.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView etint, etreq,ettankno,etconbyop,tanknoun,outTime,etconunloadDateTime,Material,Vehicle_Number;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            etint= itemView.findViewById(R.id.listintime);
            etreq=itemView.findViewById(R.id.opunload);
            ettankno=itemView.findViewById(R.id.tanklist);
            etconbyop=itemView.findViewById(R.id.unloadop);
            tanknoun = itemView.findViewById(R.id.tanknun);
            outTime=itemView.findViewById(R.id.listouttime);
            Material=itemView.findViewById(R.id.listmaterial);
            Vehicle_Number=itemView.findViewById(R.id.listvehNumber);
            etconunloadDateTime = itemView.findViewById(R.id.condt);
        }
    }
}
