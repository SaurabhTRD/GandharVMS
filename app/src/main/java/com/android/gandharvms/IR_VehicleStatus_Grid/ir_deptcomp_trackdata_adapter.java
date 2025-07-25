package com.android.gandharvms.IR_VehicleStatus_Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.IT_VehicleStatus_Grid.it_deptcomp_trackdata_adapter;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class ir_deptcomp_trackdata_adapter extends RecyclerView.Adapter<ir_deptcomp_trackdata_adapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private Context context;
    String formattedDate;
    public ir_deptcomp_trackdata_adapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
        this.Gridmodel = inwardcomresponsemodel;
        this.filteredGridList = inwardcomresponsemodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ir_deptcompexport_trackdata_table_cell, parent, false);
            return new ir_deptcomp_trackdata_adapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ir_deptcompexport_trackdata_card_item,
                    parent, false);
            return new ir_deptcomp_trackdata_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
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
        String stroeintimelength = club.getStoreIntime()!=null ? club.getStoreIntime() : "";
        String stroeouttimelength = club.getStoreOuttime()!=null ? club.getStoreOuttime(): "";
        String stroewaittimelength = club.getStoreWTTime()!=null ? club.getStoreWTTime(): "";
        holder.storeIntime.setText(stroeintimelength);
        holder.storeOuttime.setText(stroeouttimelength);
        holder.storewttime.setText(stroewaittimelength);
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
                weiIntime,weiOuttime,weiwttime,storeIntime,storeOuttime,storewttime,
                outweiInTime,outweiOutTime,outweiwttime,
                outsecInTime,outsecOutTime,outsecwttime;
        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.txtirdepttrackVehicleNumber);
            sernum = view.findViewById(R.id.txtirdepttrackSerialNumber);
            date=view.findViewById(R.id.txtirdepttrackDate);
            secIntime=view.findViewById(R.id.txtirdepttrackSecurityInTime);
            secOuttime=view.findViewById(R.id.txtirdepttrackSecurityOutTime);
            secwttime=view.findViewById(R.id.txtirdepttrackSecurityWaitTime);
            weiIntime=view.findViewById(R.id.txtirdepttrackWeighmentInTime);
            weiOuttime=view.findViewById(R.id.txtirdepttrackWeighmentOutTime);
            weiwttime=view.findViewById(R.id.txtirdepttrackWeighmentWaitTime);
            storeIntime=view.findViewById(R.id.txtirdepttrackStoreInTime);
            storeOuttime=view.findViewById(R.id.txtirdepttrackStoreOutTime);
            storewttime=view.findViewById(R.id.txtirdepttrackStoreWaitTime);
            outweiInTime=view.findViewById(R.id.txtirdepttrackOutWeiInTime);
            outweiOutTime=view.findViewById(R.id.txtirdepttrackOutWeiOutTime);
            outweiwttime=view.findViewById(R.id.txtirdepttrackOutWeiWaitTime);
            outsecInTime=view.findViewById(R.id.txtirdepttrackOutSecInTime);
            outsecOutTime=view.findViewById(R.id.txtirdepttrackOutSecOutTime);
            outsecwttime=view.findViewById(R.id.txtirdepttrackOutSecWaitTime);
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
