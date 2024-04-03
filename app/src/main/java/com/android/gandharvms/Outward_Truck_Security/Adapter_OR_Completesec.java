package com.android.gandharvms.Outward_Truck_Security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Weighment.it_in_weigh_CompletedgridAdapter;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OR_Completesec extends RecyclerView.Adapter<Adapter_OR_Completesec.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_OR_Completesec(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OR_Completesec.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_sec_complete_table_cell,viewGroup,false);
            return new Adapter_OR_Completesec.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_sec_tableitem,
                    viewGroup, false);
            return new Adapter_OR_Completesec.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OR_Completesec.myviewHolder holder,@SuppressLint("RecyclerView") int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getInTime()!= null ? club.getInTime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.date.setText(club.getDate());
        holder.serialnumber.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        //holder.intime.setText(club.getIntime());
        holder.transporter.setText(club.getTransportName());
        holder.place.setText(club.getPlace());
        holder.mobilenum.setText(club.getMobileNumber());
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
        public TextView date,serialnumber,vehiclenum,intime,outtime,transporter,place,mobilenum,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.orsinsecuritydate);
            serialnumber = itemView.findViewById(R.id.orsinserialnum);
            vehiclenum = itemView.findViewById(R.id.orsvehiclenum);
            intime = itemView.findViewById(R.id.orsintime);
            outtime = itemView.findViewById(R.id.orsouttime);
            transporter = itemView.findViewById(R.id.orsintransporter);
            place = itemView.findViewById(R.id.orsinplace);
            mobilenum = itemView.findViewById(R.id.orsinmob);
            remark = itemView.findViewById(R.id.orsinremark);
        }
    }
}
