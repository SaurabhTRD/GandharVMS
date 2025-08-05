package com.android.gandharvms.OR_VehicleStatus_Grid;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.OT_VehicleStatus_Grid.ot_departmentscompletedexport_trackdataadapter;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class or_departmentscompletedexport_trackdataadapter extends RecyclerView.Adapter<or_departmentscompletedexport_trackdataadapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;

    public or_departmentscompletedexport_trackdataadapter(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordeptcompexport_track_carditem,viewGroup,false);
            return new or_departmentscompletedexport_trackdataadapter.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordeptcompexport_track__tablecell,
                    viewGroup, false);
            return new or_departmentscompletedexport_trackdataadapter.myviewHolder(view);
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
        String logIntime = club.getLogInTime()!= null ? club.getLogInTime() :"";
        String logOutTime = club.getLogOutTime()!=null ? club.getLogOutTime() : "";
        holder.logIntime.setText(logIntime);
        holder.logOutTime.setText(logOutTime);
        holder.logWTTime.setText(club.getLogWTTime());
        String weiintime = club.getWeiInTime()!= null ? club.getWeiInTime() :"";
        String weiouttime = club.getWeiOutTime()!=null ? club.getWeiOutTime() : "";
        holder.weiInTime.setText(weiintime);
        holder.weiOutTime.setText(weiouttime);
        holder.weiWTTime.setText(club.getWeiWTTime());
        String ilintime = club.getIlInTime()!= null ? club.getIlInTime() :"";
        String ilouttime = club.getIlOutTime()!=null ? club.getIlOutTime() : "";
        holder.ilintime.setText(ilintime);
        holder.ilouttime.setText(ilouttime);
        holder.ilWTTime.setText(club.getIndustrialWTTime());
        String splintime = club.getSplInTime()!= null ? club.getSplInTime() :"";
        String splouttime = club.getSplOutTime()!=null ? club.getSplOutTime() : "";
        holder.splintime.setText(splintime);
        holder.splouttime.setText(splouttime);
        holder.splWTTime.setText(club.getSmallpackWTTime());
        String outweiintime = club.getOutWeiInTime()!= null ? club.getOutWeiInTime() :"";
        String outweiouttime = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime() : "";
        holder.outWeiIntime.setText(outweiintime);
        holder.outWeiOutTime.setText(outweiouttime);
        holder.outweiWTTime.setText(club.getOutWeiWTTime());
        String dataentryintime = club.getOutDEProductionIntime()!= null ? club.getOutDEProductionIntime() :"";
        String dataentryouttime = club.getOutDEProductinOutTime()!=null ? club.getOutDEProductinOutTime() : "";
        holder.dataentryIntime.setText(dataentryintime);
        holder.dataentryOutTime.setText(dataentryouttime);
        holder.dataentryWTTime.setText(club.getOutDEProWTTime());
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
                logIntime,logOutTime,logWTTime,weiInTime,weiOutTime,weiWTTime,ilintime,ilouttime,ilWTTime,
                splintime,splouttime,splWTTime,
                outWeiIntime,outWeiOutTime,outweiWTTime,dataentryIntime,dataentryOutTime,dataentryWTTime,
                outbilInTime,outbilOutTime,outbilWTTime,outsecInTime,outsecOutTime,outsecWTTime;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.OrdeptcomptrackexportDate);
            serialnumber= itemView.findViewById(R.id.Ordeptcomptrackexportserialnumber);
            vehiclenumber= itemView.findViewById(R.id.Ordeptcomptrackexportvehiclenumber);
            secIntime= itemView.findViewById(R.id.OrdeptcomptrackexportsecInTime);
            secOuttime= itemView.findViewById(R.id.OrdeptcomptrackexportsecOutTime);
            secWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportsecWTTime);
            logIntime= itemView.findViewById(R.id.OrdeptcomptrackexportlogIntime);
            logOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportlogOutTime);
            logWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportlogWTTime);
            weiInTime= itemView.findViewById(R.id.OrdeptcomptrackexportweiInTime);
            weiOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportweiOutTime);
            weiWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportweiWTTime);
            ilintime= itemView.findViewById(R.id.Ordeptcomptrackexportilintime);
            ilouttime= itemView.findViewById(R.id.Ordeptcomptrackexportilouttime);
            ilWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportilWTTime);
            splintime= itemView.findViewById(R.id.Ordeptcomptrackexportsplintime);
            splouttime= itemView.findViewById(R.id.Ordeptcomptrackexportsplouttime);
            splWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportsplWTTime);
            outWeiIntime= itemView.findViewById(R.id.OrdeptcomptrackexportoutWeiIntime);
            outWeiOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutWeiOutTime);
            outweiWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutweiWTTime);
            dataentryIntime= itemView.findViewById(R.id.OrdeptcomptrackexportoutdataentryIntime);
            dataentryOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutdataentryOutTime);
            dataentryWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutdataentryWTTime);
            outbilInTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutbilInTime);
            outbilOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutbilOutTime);
            outbilWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutbilWTTime);
            outsecInTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutsecInTime);
            outsecOutTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutsecOutTime);
            outsecWTTime= itemView.findViewById(R.id.OrdeptcomptrackexportoutsecWTTime);
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
