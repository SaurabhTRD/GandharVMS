package com.android.gandharvms.OR_VehicleStatus_Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Tanker_Security.Outward_GridAdapter;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class or_statusgrid_adapter extends RecyclerView.Adapter<or_statusgrid_adapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private List<Response_Outward_Security_Fetching> outwardGridmodel;
    private List<Response_Outward_Security_Fetching> outwardfilteredGridList;
    private Context context;

    public or_statusgrid_adapter(List<Response_Outward_Security_Fetching> responseoutwardgrid) {
        this.outwardGridmodel = responseoutwardgrid;
        this.outwardfilteredGridList = responseoutwardgrid;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_statusgrid_tableitem, viewGroup, false);
            return new or_statusgrid_adapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_statusgrid_carditem,
                    viewGroup, false);
            return new or_statusgrid_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.transporter.setText(club.getTransportName());
        holder.Status.setText(club.getCurrStatus());
        holder.date.setText(club.getDate().substring(0,10));
        int sectimelength = club.getSecInTime()!=null ? club.getSecInTime().length() : 0;
        if (sectimelength > 0) {
            holder.secTime.setText(club.getSecInTime());
        }

        int logtimelength = club.getLogInTime()!=null ? club.getLogInTime().length() : 0;
        if (logtimelength > 0) {
            holder.logtime.setText(club.getLogInTime());
        }

        int weiTimelength = club.getWeiInTime()!=null ? club.getWeiInTime().length() : 0;
        if (weiTimelength > 0) {
            holder.weiTime.setText(club.getWeiInTime());
        }

        int indusTimelength = club.getIndusTime()!=null ? club.getIndusTime().length() : 0;
        if (indusTimelength > 0) {
            holder.induspacktime.setText(club.getIndusTime());
        }

        int smallpacktimelength = club.getSamllPackTime()!=null ? club.getSamllPackTime().length() : 0;
        if (smallpacktimelength > 0) {
            holder.smallpacktime.setText(club.getSamllPackTime());
        }

        int outweitime = club.getOutWeiTime()!=null ? club.getOutWeiTime().length() : 0;
        if (outweitime > 0) {
            holder.outweitime.setText(club.getOutWeiTime());
        }

        int outdataentry = club.getIRDataEntryTime()!=null ? club.getIRDataEntryTime().length() : 0;
        if (outdataentry > 0) {
            holder.outdataentrytime.setText(club.getIRDataEntryTime());
        }

        int outbilltime = club.getOutBillTime()!=null ? club.getOutBillTime().length() : 0;
        if (outbilltime > 0) {
            holder.outbilltime.setText(club.getOutBillTime());
        }

        int outsectime=club.getOutSecTime()!=null?club.getOutSecTime().length():0;
        if(outsectime>0)
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
        public TextView vehiclenum,transporter,Status,date,secTime,logtime,weiTime,induspacktime,smallpacktime,
                outweitime,outdataentrytime,outbilltime,outsectime;

        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.orstatusgridVehicleNumber);
            transporter = view.findViewById(R.id.orstatusgridTransporter);
            Status = view.findViewById(R.id.orstatusgridStatus);
            date = view.findViewById(R.id.orstatusgriddate);
            secTime = view.findViewById(R.id.orstatusgridSecTime);
            logtime = view.findViewById(R.id.orstatusgridLogTime);
            weiTime = view.findViewById(R.id.orstatusgridWeiTime);
            induspacktime = view.findViewById(R.id.orstatusgridindustrialpack);
            smallpacktime = view.findViewById(R.id.orstatusgridsmallpack);
            outweitime = view.findViewById(R.id.orstatusgridoutweitime);
            outdataentrytime = view.findViewById(R.id.orstatusgridoutdataentrytime);
            outbilltime = view.findViewById(R.id.orstatusgridoutbilltime);
            outsectime=view.findViewById(R.id.orstatusgridoutsectime);
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
