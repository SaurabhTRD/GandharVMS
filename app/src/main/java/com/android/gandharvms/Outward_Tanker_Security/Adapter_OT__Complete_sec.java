package com.android.gandharvms.Outward_Tanker_Security;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Truck_Security.Adapter_OR_Completesec;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.Outward_Truck_Weighment.Adater_Weigh_Out_Complete;
import com.android.gandharvms.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter_OT__Complete_sec extends RecyclerView.Adapter<Adapter_OT__Complete_sec.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;


    public Adapter_OT__Complete_sec(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT__Complete_sec.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_sec_complete_table_cell,viewGroup,false);
            return new Adapter_OT__Complete_sec.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_sec_tableitem,
                    viewGroup, false);
            return new Adapter_OT__Complete_sec.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT__Complete_sec.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getInTime()!= null ? club.getInTime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        formattedDate = formattedDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.billholdremark.setText(
                (club.getHoldRemark() != null && !club.getHoldRemark().isEmpty())
                        ? club.getHoldRemark()
                        : "NA"
        );
        holder.kl.setText(String.valueOf(club.getKl()));
        holder.transporter.setText(club.getTransportName());
        holder.place.setText(club.getPlace());
        holder.mobile.setText(club.getMobileNumber());
        holder.remark.setText(club.getRemark());

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common_Outward_model club = filteredGridList.get(position);
                Intent intent = new Intent(view.getContext(), Outward_Tanker_Security.class);
                intent.putExtra("VehicleNumber", club.getVehicleNumber());
                intent.putExtra("Action", "Up");
                view.getContext().startActivity(intent);
            }
        });
    }

    private String formattedDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
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
        public TextView date,serialnum,vehiclenum,billholdremark,intime,outtime,kl,transporter,place,mobile,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.otsecuritydate);
            serialnum = itemView.findViewById(R.id.otsinserialnum);
            vehiclenum = itemView.findViewById(R.id.otsvehiclenum);
            billholdremark=itemView.findViewById(R.id.otsbillingholdremark);
            intime = itemView.findViewById(R.id.otsintime);
            outtime = itemView.findViewById(R.id.otsouttime);
            kl = itemView.findViewById(R.id.otsecuritykl);
            transporter = itemView.findViewById(R.id.otsintransporter);
            place = itemView.findViewById(R.id.otsinplace);
            mobile = itemView.findViewById(R.id.otsinmob);
            remark = itemView.findViewById(R.id.otsinremark);
        }
    }
}
