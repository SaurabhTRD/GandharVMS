package com.android.gandharvms.Outwardout_Tanker_Weighment;

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
import com.android.gandharvms.Outward_Tanker_Weighment.Adapter_OT_completed_Weighment;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_outweighment extends RecyclerView.Adapter<Adapter_OT_completed_outweighment.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Adapter_OT_completed_outweighment(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_outweighment.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_weigh_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_outweighment.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_weigh_tableitem, viewGroup, false);
            return new Adapter_OT_completed_outweighment.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_outweighment.myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getIntime()!= null ? club.getIntime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getIntime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.density.setText(String.valueOf(club.getDensity_29_5C()));
        holder.tarewt.setText(club.getTareWeight());
        holder.netwt.setText(club.getNetWeight());
        holder.grswt.setText(club.getGrossWeight());
        holder.sealnum.setText(club.getSealNumber());
        holder.numpack.setText(club.getNumberofPack());
        holder.shdip.setText(String.valueOf(club.getShortageDip()));
        holder.shshort.setText(String.valueOf(club.getShortageWeight()));
        Picasso.get()
                .load(RetroApiClient.BASE_URL  + club.getOutVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getOutDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);
        holder.remark.setText(club.getRemark());
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
        public TextView serial,vehiclenum,density,tarewt,intime,outtime,netwt,grswt,sealnum,numpack,shdip,shshort,remark;

        ImageView invehicleimage,indriverimage;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.otweighoutserial);
            vehiclenum = itemView.findViewById(R.id.otweighoutvehicle);
            density = itemView.findViewById(R.id.otweighoutdensity);
            tarewt = itemView.findViewById(R.id.otweightarewt);
            intime = itemView.findViewById(R.id.otweighoutintime);
            outtime = itemView.findViewById(R.id.otweighoutouttime);
            netwt = itemView.findViewById(R.id.otweighnetwt);
            grswt = itemView.findViewById(R.id.otweighoutgrswt);
            sealnum = itemView.findViewById(R.id.otweighoutseal);
            numpack = itemView.findViewById(R.id.otweighoutnumberpack);
            shdip = itemView.findViewById(R.id.otweighoutshdip);
            shshort = itemView.findViewById(R.id.otweighoutshwt);
            invehicleimage=itemView.findViewById(R.id.otweighoutInVehicleImage);
            indriverimage=itemView.findViewById(R.id.otweighoutInDriverImage);
            remark = itemView.findViewById(R.id.otweighouremark);
        }
    }
}
