package com.android.gandharvms.Outward_Truck_Weighment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adater_Weigh_Out_Complete extends RecyclerView.Adapter<Adater_Weigh_Out_Complete.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adater_Weigh_Out_Complete(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
//        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }
    @NonNull
    @Override
    public Adater_Weigh_Out_Complete.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_weigh_complete_tablecell,viewGroup,false);
            return new Adater_Weigh_Out_Complete.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_weigh_tableitem, viewGroup, false);
            return new Adater_Weigh_Out_Complete.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adater_Weigh_Out_Complete.myviewHolder holder, int position) {

        Common_Outward_model obj = filteredGridList.get(position);
        int intimelength = obj.getOutInTime() != null ? obj.getOutInTime().length() :0;
        int outtimelength = obj.getUpdatedDate()!= null ? obj.getUpdatedDate().length() :0;
        if (intimelength > 0){
            holder.intime.setText(obj.getOutInTime().substring(12,intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(obj.getUpdatedDate().substring(12,outtimelength));
        }
        holder.serialnum.setText(obj.getSerialNumber());
        holder.vehiclenum.setText(obj.getVehicleNumber());
        holder.tarewe.setText(obj.getTareWeight());
        holder.netwe.setText(obj.getNetWeight());
        holder.grswt.setText(obj.getGrossWeight());
        holder.nopack.setText(obj.getNumberofPack());
        holder.seal.setText(obj.getSealNumber());
        holder.shdip.setText(String.valueOf(obj.getShortageDip()));
        holder.shwt.setText(String.valueOf(obj.getShortageWeight()));
        Picasso.get()
                .load(RetroApiClient.BASE_URL  + obj.getOutVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + obj.getOutDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);
        holder.remark.setText(obj.getRemark());
    }

    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }

    @Override
    public Filter getFilter() {
        return  new Filter()    {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<Common_Outward_model> filteredList = new ArrayList<>();
                    for (Common_Outward_model club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNumber().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNumber().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(club);
                        }
                    }
                    filteredGridList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredGridList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredGridList = (ArrayList<Common_Outward_model>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView intime,outtime,serialnum,vehiclenum,tarewe,netwe,grswt,nopack,seal,shdip,shwt,remark;
        ImageView invehicleimage,indriverimage;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.orweoutintime);
            outtime = itemView.findViewById(R.id.orweoutouttime);
            serialnum = itemView.findViewById(R.id.orweoutserial);
            vehiclenum = itemView.findViewById(R.id.orweoutvehicle);
            tarewe = itemView.findViewById(R.id.orweouttare);
            netwe = itemView.findViewById(R.id.orweoutnetwe);
            grswt = itemView.findViewById(R.id.orweoutgrswt);
            nopack = itemView.findViewById(R.id.orweoutnopck);
            seal = itemView.findViewById(R.id.orweoutsealnum);
            shdip = itemView.findViewById(R.id.orweoutshdip);
            shwt = itemView.findViewById(R.id.orweoutshwe);
            invehicleimage=itemView.findViewById(R.id.orweoutInVehicleImage);
            indriverimage=itemView.findViewById(R.id.orweoutInDriverImage);
            remark = itemView.findViewById(R.id.orweoutremark);
        }
    }
}
