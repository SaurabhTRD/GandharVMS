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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class In_Tanker_we_Adapter extends RecyclerView.Adapter<In_Tanker_we_Adapter.myviewholder>
{

    Context context;

    private static String BaseUrl ="https://firebasestorage.googleapis.com/v0/b/gandharvms.appspot.com/o";
    ArrayList<In_Tanker_Weighment_list> datalist ;

    public In_Tanker_we_Adapter(ArrayList<In_Tanker_Weighment_list> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_tr_we_item,parent,false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.intime.setText(datalist.get(position).getIn_Time());
        holder.serialnumber.setText(datalist.get(position).getSerial_number());
        holder.vehiclenumber.setText(datalist.get(position).getVehicle_number());
        holder.suppliername.setText(datalist.get(position).getSupplier_name());
        holder.materialname.setText(datalist.get(position).getMaterial_name());
        holder.driverno.setText(datalist.get(position).getDriver_Number());
        holder.oanumber.setText(datalist.get(position).getOA_number());
        holder.date.setText(datalist.get(position).getDate());
        holder.grossweight.setText(datalist.get(position).getGross_Weight());
        /*holder.tareweight.setText(datalist.get(position).getTare_Weight());
        holder.netweight.setText(datalist.get(position).getNet_Weight());*/
        holder.batchnumber.setText(datalist.get(position).getBatch_Number());
        holder.containerno.setText(datalist.get(position).getContainer_No());
        holder.sighby.setText(datalist.get(position).getSign_By());
        holder.shortagedip.setText(datalist.get(position).getShortage_Dip());
        holder.shortageweight.setText(datalist.get(position).getShortage_weight());
        holder.outTime.setText(datalist.get(position).getOuttime());

        Picasso.get()
                .load(BaseUrl + datalist.get(position).getInVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(BaseUrl + datalist.get(position).getInDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);

//                date,grossweight,tareweight,netweight,density,batchnumber,containerno,
//                sighby,datetime,shortagedip,shortageweight;


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

        class myviewholder extends RecyclerView.ViewHolder
    {
        TextView intime,serialnumber,vehiclenumber,suppliername,materialname,driverno,oanumber,date,grossweight,batchnumber,containerno,outTime,
        sighby,shortagedip,shortageweight;
        ImageView invehicleimage,indriverimage;
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
            invehicleimage=itemView.findViewById(R.id.listInVehicleImage);
            indriverimage=itemView.findViewById(R.id.listInDriverImage);
        }
    }
}
