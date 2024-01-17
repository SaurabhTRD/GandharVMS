package com.android.gandharvms.Inward_Tanker_Production;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class In_Tanker_Pro_Adapter extends RecyclerView.Adapter<In_Tanker_Pro_Adapter.myviewholder> {

    ArrayList<In_Tanker_Production_list> datalist;

    public In_Tanker_Pro_Adapter(ArrayList<In_Tanker_Production_list> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_ta_pro_item,parent,false);
       return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
        holder.etint.setText(datalist.get(position).getIn_Time());
        holder.etreq.setText(datalist.get(position).getReq_to_unload());
        holder.ettankno.setText(datalist.get(position).getTank_Number_Request());
        holder.etconbyop.setText(datalist.get(position).getConfirm_unload());
        holder.tanknoun.setText(datalist.get(position).getTank_Number());
        holder.outTime.setText(datalist.get(position).getOutTime());
        holder.Material.setText(datalist.get(position).getMaterial());
        holder.Vehicle_Number.setText(datalist.get(position).getVehicle_Number());
        holder.etconunloadDateTime.setText(dateFormat.format(datalist.get(position).getCon_unload_DT().toDate()));
    }
    @Override
    public int getItemCount() {
        return datalist.size();
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
