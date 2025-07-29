package com.android.gandharvms.OT_VehicleStatus_Grid;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.OT_CompletedReport.Outward_Tanker_CompReportAdapter;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ot_departmentscompletedexport_trackdataadapter extends RecyclerView.Adapter<ot_departmentscompletedexport_trackdataadapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;

    public ot_departmentscompletedexport_trackdataadapter(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.otdeptcompexport_track_carditem,viewGroup,false);
            return new ot_departmentscompletedexport_trackdataadapter.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.otdeptcompexport_track__tablecell,
                    viewGroup, false);
            return new ot_departmentscompletedexport_trackdataadapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.serialnumber.setText(club.getSerialNumber());
        holder.vehiclenumber.setText(club.getVehicleNumber());
        String secintime = club.getSecInTime()!= null ? club.getSecInTime() :"";
        String secouttime = club.getSecOutTime()!=null ? club.getSecOutTime() : "";
        holder.secIntime.setText(secintime);
        holder.secOuttime.setText(secouttime);
        holder.secWTTime.setText(club.getSecWTTime());
        String bilIntime = club.getBilInTime()!= null ? club.getBilInTime() :"";
        String bilOutTime = club.getBilOutTime()!=null ? club.getBilOutTime() : "";
        holder.bilIntime.setText(bilIntime);
        holder.bilOutTime.setText(bilOutTime);
        holder.billWTTime.setText(club.getBillWTTime());
        String weiintime = club.getWeiInTime()!= null ? club.getWeiInTime() :"";
        String weiouttime = club.getWeiOutTime()!=null ? club.getWeiOutTime() : "";
        holder.weiInTime.setText(weiintime);
        holder.weiOutTime.setText(weiouttime);
        holder.weiWTTime.setText(club.getWeiWTTime());
        String prointime = club.getBLFProInTime()!= null ? club.getBLFProInTime() :"";
        String proouttime = club.getBLFProOutTime()!=null ? club.getBLFProOutTime() : "";
        holder.prointime.setText(prointime);
        holder.proouttime.setText(proouttime);
        holder.proWTTime.setText(club.getBLFProWTTime());
        String labintime = club.getIPFLabInTime()!= null ? club.getIPFLabInTime() :"";
        String labouttime = club.getIPFLabOutTime()!=null ? club.getIPFLabOutTime() : "";
        holder.labintime.setText(labintime);
        holder.labouttime.setText(labouttime);
        holder.labWTTime.setText(club.getIPFLabWTTime());
        String outweiintime = club.getOutWeiInTime()!= null ? club.getOutWeiInTime() :"";
        String outweiouttime = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime() : "";
        holder.outWeiIntime.setText(outweiintime);
        holder.outWeiOutTime.setText(outweiouttime);
        holder.outweiWTTime.setText(club.getOutWeiWTTime());
        String dataentryintime = club.getOutDataEntryInTime()!= null ? club.getOutDataEntryInTime() :"";
        String dataentryouttime = club.getOutDataEntryOutTime()!=null ? club.getOutDataEntryOutTime() : "";
        holder.dataentryIntime.setText(dataentryintime);
        holder.dataentryOutTime.setText(dataentryouttime);
        holder.dataentryWTTime.setText(club.getOutDataEntryWTTime());
        String outbilintime = club.getOutBilInTime()!= null ? club.getOutBilInTime() :"";
        String outbilouttime = club.getOutBilOutTime()!=null ? club.getOutBilOutTime() : "";
        holder.outbilInTime.setText(outbilintime);
        holder.outbilOutTime.setText(outbilouttime);
        holder.outbilWTTime.setText(club.getOutBilWTTime());
        String outsecintime = club.getOutSecInTime()!= null ? club.getOutSecInTime() :"";
        String outsecouttime = club.getOutSecOutTime()!=null ? club.getOutSecOutTime() : "";
        holder.outsecInTime.setText(outsecintime);
        holder.outsecOutTime.setText(outsecouttime);
        holder.outsecWTTime.setText(club.getOutSecWTTime());
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

    public class myviewHolder extends RecyclerView.ViewHolder{
        public TextView
                date,serialnumber,vehiclenumber,secIntime,secOuttime,secWTTime,
                bilIntime,bilOutTime,billWTTime,weiInTime,weiOutTime,weiWTTime,prointime,proouttime,proWTTime,
                labintime,labouttime,labWTTime,
                outWeiIntime,outWeiOutTime,outweiWTTime,dataentryIntime,dataentryOutTime,dataentryWTTime,
                outbilInTime,outbilOutTime,outbilWTTime,outsecInTime,outsecOutTime,outsecWTTime;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.OtdeptcomptrackexportdeptcomptrackexportDate);
            serialnumber= itemView.findViewById(R.id.Otdeptcomptrackexportserialnumber);
            vehiclenumber= itemView.findViewById(R.id.Otdeptcomptrackexportvehiclenumber);
            secIntime= itemView.findViewById(R.id.OtdeptcomptrackexportsecInTime);
            secOuttime= itemView.findViewById(R.id.OtdeptcomptrackexportsecOutTime);
            secWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportsecWTTime);
            bilIntime= itemView.findViewById(R.id.OtdeptcomptrackexportbilIntime);
            bilOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportbilOutTime);
            billWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportbillWTTime);
            weiInTime= itemView.findViewById(R.id.OtdeptcomptrackexportweiInTime);
            weiOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportweiOutTime);
            weiWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportweiWTTime);
            prointime= itemView.findViewById(R.id.Otdeptcomptrackexportipprointime);
            proouttime= itemView.findViewById(R.id.Otdeptcomptrackexportipproouttime);
            proWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportproWTTime);
            labintime= itemView.findViewById(R.id.Otdeptcomptrackexportiplabintime);
            labouttime= itemView.findViewById(R.id.Otdeptcomptrackexportiplabouttime);
            labWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportlabWTTime);
            outWeiIntime= itemView.findViewById(R.id.OtdeptcomptrackexportoutWeiIntime);
            outWeiOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutWeiOutTime);
            outweiWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutweiWTTime);
            dataentryIntime= itemView.findViewById(R.id.OtdeptcomptrackexportdataentryIntime);
            dataentryOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportdataentryOutTime);
            dataentryWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportdataentryWTTime);
            outbilInTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutbilInTime);
            outbilOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutBilOutTime);
            outbilWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutBillWTTime);
            outsecInTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutsecInTime);
            outsecOutTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutsecOutTime);
            outsecWTTime= itemView.findViewById(R.id.OtdeptcomptrackexportoutsecWTTime);
        }
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

    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the ParseException or return the original inputDate if parsing fails
            return inputDate;
        }
    }
}
