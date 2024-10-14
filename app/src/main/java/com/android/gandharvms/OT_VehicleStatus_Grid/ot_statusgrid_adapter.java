package com.android.gandharvms.OT_VehicleStatus_Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class ot_statusgrid_adapter extends RecyclerView.Adapter<ot_statusgrid_adapter.myviewHolder> implements Filterable  {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private List<Response_Outward_Security_Fetching> outwardGridmodel;
    private List<Response_Outward_Security_Fetching> outwardfilteredGridList;
    private Context context;

    public ot_statusgrid_adapter(List<Response_Outward_Security_Fetching> responseoutwardgrid) {
        this.outwardGridmodel = responseoutwardgrid;
        this.outwardfilteredGridList = responseoutwardgrid;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_statusgrid_tableitem, viewGroup, false);
            return new ot_statusgrid_adapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_statusgrid_carditem,
                    viewGroup, false);
            return new ot_statusgrid_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.transporter.setText(club.getTransportName());
        holder.Status.setText(club.getCurrStatus());
        holder.date.setText(club.getDate().substring(0,10));
        int secintimelength = club.getSecInTime()!=null ? club.getSecInTime().length() : 0;
        if (secintimelength > 0) {
            holder.secInTime.setText(club.getSecInTime());
        }

        int bilInTimelength = club.getBilInTime()!=null ? club.getBilInTime().length() : 0;
        if (bilInTimelength > 0) {
            holder.bilInTime.setText(club.getBilInTime());
        }

        int weiInTimelength = club.getWeiInTime()!=null ? club.getWeiInTime().length() : 0;
        if (weiInTimelength > 0) {
            holder.weiInTime.setText(club.getWeiInTime());
        }

        int proTimelength = club.getBLFProInTime()!=null ? club.getBLFProInTime().length() : 0;
        if (proTimelength > 0) {
            holder.protime.setText(club.getBLFProInTime());
        }

        int labTimelength = club.getIPFLabInTime()!=null ? club.getIPFLabInTime().length() : 0;
        if (labTimelength > 0) {
            holder.labtime.setText(club.getIPFLabInTime());
        }

        int outweitimelength = club.getOutWeiTime()!=null ? club.getOutWeiTime().length() : 0;
        if (outweitimelength > 0) {
            holder.outweitime.setText(club.getOutWeiTime());
        }

        int outdataentrytimelength = club.getOutDataEntryTime()!=null ? club.getOutDataEntryTime().length() : 0;
        if (outdataentrytimelength > 0) {
            holder.outdataentrytime.setText(club.getOutDataEntryTime());
        }
        int outbilltimelength = club.getOutBillTime()!=null ? club.getOutBillTime().length() : 0;
        if (outbilltimelength > 0) {
            holder.outbilltime.setText(club.getOutBillTime());
        }
        int outsectimelength=club.getOutSecTime()!=null?club.getOutSecTime().length():0;
        if(outsectimelength>0)
        {
            holder.outsectime.setText(club.getOutSecTime());
        }
    }

    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @Override
    public int getItemCount() {
        return outwardGridmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView vehiclenum,transporter,Status,date,secInTime,bilInTime,weiInTime,
                protime,labtime,outweitime,outdataentrytime,outbilltime,outsectime;

        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.otstatusgridVehicleNumber);
            transporter = view.findViewById(R.id.otstatusgridTransporter);
            Status = view.findViewById(R.id.otstatusgridStatus);
            date = view.findViewById(R.id.otstatusgriddate);
            secInTime = view.findViewById(R.id.otstatusgridSecInTime);
            bilInTime = view.findViewById(R.id.otstatusgridBilInTime);
            weiInTime = view.findViewById(R.id.otstatusgridWeiInTime);
            protime = view.findViewById(R.id.otstatusgridproduction);
            labtime = view.findViewById(R.id.otstatusgridLaboratory);
            outweitime = view.findViewById(R.id.otstatusgridoutweitime);
            outdataentrytime = view.findViewById(R.id.otstatusgridoutdatanentrytime);
            outbilltime = view.findViewById(R.id.otstatusgridoutbilltime);
            outsectime=view.findViewById(R.id.otstatusgridoutsectime);

        }
    }

    public Filter getFilter() {
        return new Filter() {
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    outwardfilteredGridList = outwardGridmodel;
                } else {
                    List<Response_Outward_Security_Fetching> filteredList = new ArrayList<>();
                    for (Response_Outward_Security_Fetching club : outwardfilteredGridList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNumber().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNumber().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(club);
                        }
                    }
                    outwardfilteredGridList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = outwardfilteredGridList;
                return filterResults;
            }

            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                outwardfilteredGridList = (ArrayList<Response_Outward_Security_Fetching>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
