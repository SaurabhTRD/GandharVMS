package com.android.gandharvms.IT_VehicleStatus_Grid;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.gridadaptercompleted;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class it_deptcomp_trackdata_adapter extends RecyclerView.Adapter<it_deptcomp_trackdata_adapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private Context context;
    String formattedDate;
    public it_deptcomp_trackdata_adapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
        this.Gridmodel = inwardcomresponsemodel;
        this.filteredGridList = inwardcomresponsemodel;
    }

    @NonNull
    @Override
    public it_deptcomp_trackdata_adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_deptcompexport_trackdata_table_cell, parent, false);
            return new it_deptcomp_trackdata_adapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_deptcompexport_trackdata_card_item,
                    parent, false);
            return new it_deptcomp_trackdata_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull it_deptcomp_trackdata_adapter.myviewHolder holder, int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.sernum.setText(club.getSerialNo());
        holder.date.setText(club.getDate().substring(0,12));
        String secintimelength = club.getSecIntime()!=null ? club.getSecIntime() : "";
        String secouttimelength = club.getSecOuttime()!=null ? club.getSecOuttime(): "";
        String secwaittimelength = club.getSecWTTime()!=null ? club.getSecWTTime(): "";
        holder.secIntime.setText(secintimelength);
        holder.secOuttime.setText(secouttimelength);
        holder.secwttime.setText(secwaittimelength);
        String weiintimelength = club.getWeiIntime()!=null ? club.getWeiIntime() : "";
        String weiouttimelength = club.getWeiOuttime()!=null ? club.getWeiOuttime(): "";
        String weiwaittimelength = club.getWeiWTTime()!=null ? club.getWeiWTTime(): "";
        holder.weiIntime.setText(weiintimelength);
        holder.weiOuttime.setText(weiouttimelength);
        holder.weiwttime.setText(weiwaittimelength);
        String samintimelength = club.getSamIntime()!=null ? club.getSamIntime() : "";
        String samouttimelength = club.getSamOuttime()!=null ? club.getSamOuttime(): "";
        String samwaittimelength = club.getSamWTTime()!=null ? club.getSamWTTime(): "";
        holder.samIntime.setText(samintimelength);
        holder.samOuttime.setText(samouttimelength);
        holder.samwttime.setText(samwaittimelength);
        String prointimelength = club.getProIntime()!=null ? club.getProIntime() : "";
        String proouttimelength = club.getProOuttime()!=null ? club.getProOuttime(): "";
        String prowaittimelength = club.getProWTTime()!=null ? club.getProWTTime(): "";
        holder.proIntime.setText(prointimelength);
        holder.proOuttime.setText(proouttimelength);
        holder.prowttime.setText(prowaittimelength);
        String labintimelength = club.getLabIntime()!=null ? club.getLabIntime() : "";
        String labouttimelength = club.getLabOuttime()!=null ? club.getLabOuttime(): "";
        String labwaittimelength = club.getLabWTTime()!=null ? club.getLabWTTime(): "";
        holder.labIntime.setText(labintimelength);
        holder.labOuttime.setText(labouttimelength);
        holder.labwttime.setText(labwaittimelength);
        String outweiintimelength = club.getOutWeiInTime()!=null ? club.getOutWeiInTime() : "";
        String outweiouttimelength = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime(): "";
        String outweiwaittimelength = club.getOutWeiWTTime()!=null ? club.getOutWeiWTTime(): "";
        holder.outweiInTime.setText(outweiintimelength);
        holder.outweiOutTime.setText(outweiouttimelength);
        holder.outweiwttime.setText(outweiwaittimelength);
        String outsecintimelength = club.getOutSecInTime()!=null ? club.getOutSecInTime() : "";
        String outsecouttimelength = club.getOutSecOutTime()!=null ? club.getOutSecOutTime(): "";
        String outsecwaittimelength = club.getOutSecWTTime()!=null ? club.getOutSecWTTime(): "";
        holder.outsecInTime.setText(outsecintimelength);
        holder.outsecOutTime.setText(outsecouttimelength);
        holder.outsecwttime.setText(outsecwaittimelength);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public
        TextView date,sernum, vehiclenum,secIntime,secOuttime,secwttime,
                weiIntime,weiOuttime,weiwttime,samIntime,samOuttime,samwttime,labIntime,labOuttime,labwttime,
                proIntime,proOuttime,prowttime,outweiInTime,outweiOutTime,outweiwttime,
                outsecInTime,outsecOutTime,outsecwttime;
        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.txtitdepttrackVehicleNumber);
            sernum = view.findViewById(R.id.txtitdepttrackSerialNumber);
            date=view.findViewById(R.id.txtitdepttrackDate);
            secIntime=view.findViewById(R.id.txtitdepttrackSecurityInTime);
            secOuttime=view.findViewById(R.id.txtitdepttrackSecurityOutTime);
            secwttime=view.findViewById(R.id.txtitdepttrackSecurityWaitTime);
            weiIntime=view.findViewById(R.id.txtitdepttrackWeighmentInTime);
            weiOuttime=view.findViewById(R.id.txtitdepttrackWeighmentOutTime);
            weiwttime=view.findViewById(R.id.txtitdepttrackWeighmentWaitTime);
            samIntime=view.findViewById(R.id.txtitdepttrackSamplingInTime);
            samOuttime=view.findViewById(R.id.txtitdepttrackSamplingOutTime);
            samwttime=view.findViewById(R.id.txtitdepttrackSamplingWaitTime);
            labIntime=view.findViewById(R.id.txtitdepttrackLoboratoryInTime);
            labOuttime=view.findViewById(R.id.txtitdepttrackLoboratoryOutTime);
            labwttime=view.findViewById(R.id.txtitdepttrackLoboratoryWaitTime);
            proIntime=view.findViewById(R.id.txtitdepttrackProductionInTime);
            proOuttime=view.findViewById(R.id.txtitdepttrackProductionOutTime);
            prowttime=view.findViewById(R.id.txtitdepttrackProductionWaitTime);
            outweiInTime=view.findViewById(R.id.txtitdepttrackOutWeiInTime);
            outweiOutTime=view.findViewById(R.id.txtitdepttrackOutWeiOutTime);
            outweiwttime=view.findViewById(R.id.txtitdepttrackOutWeiWaitTime);
            outsecInTime=view.findViewById(R.id.txtitdepttrackOutSecInTime);
            outsecOutTime=view.findViewById(R.id.txtitdepttrackOutSecOutTime);
            outsecwttime=view.findViewById(R.id.txtitdepttrackOutSecWaitTime);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<CommonResponseModelForAllDepartment> filteredList = new ArrayList<>();
                    for (CommonResponseModelForAllDepartment club : Gridmodel) {
                        if (club.getSerialNo().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNo().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredGridList = (ArrayList<CommonResponseModelForAllDepartment>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
