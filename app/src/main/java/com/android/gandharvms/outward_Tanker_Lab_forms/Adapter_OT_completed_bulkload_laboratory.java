package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Production_forms.Adapter_OT_completed_bulkload_production;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_bulkload_laboratory extends RecyclerView.Adapter<Adapter_OT_completed_bulkload_laboratory.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;
    public Adapter_OT_completed_bulkload_laboratory(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_bulkload_laboratory.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulkloadlab_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_bulkload_laboratory.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulklab_tableitem, viewGroup, false);
            return new Adapter_OT_completed_bulkload_laboratory.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_bulkload_laboratory.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getLabInTime()!= null ? club.getLabInTime().length() :0;
        int outtimelength = club.getLabOutTime()!=null ? club.getLabOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getLabInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getLabOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.density29.setText(String.valueOf(club.getDensity_29_5C()));
        holder.batchno.setText(club.getBatchNo());
        holder.remark.setText(club.getLabRemark());
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
        public TextView serialnum,vehiclenum,density29,intime,outtime,batchno,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serialnum = itemView.findViewById(R.id.otbulklabserial);
            vehiclenum= itemView.findViewById(R.id.otbilklabvehivle);
            density29 = itemView.findViewById(R.id.otbilklabdensity);
            intime = itemView.findViewById(R.id.otinbulklabintime);
            outtime = itemView.findViewById(R.id.otinbulklabouttime);
            batchno = itemView.findViewById(R.id.otinbulklabbatchno);
            remark = itemView.findViewById(R.id.otinbulklabremark);
        }
    }
}
