package com.android.gandharvms.Outward_Tanker_Security;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Outward_GridAdapter extends RecyclerView.Adapter<Outward_GridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private List<Response_Outward_Security_Fetching> outwardGridmodel;
    private List<Response_Outward_Security_Fetching> outwardfilteredGridList;
    private Context context;

    public Outward_GridAdapter(List<Response_Outward_Security_Fetching> responseoutwardgrid) {
        this.outwardGridmodel = responseoutwardgrid;
        this.outwardfilteredGridList = responseoutwardgrid;
    }

    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_table_cell, viewGroup, false);
            return new myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_card_item,
                    viewGroup, false);
            return new myviewHolder(view);
        }
    }


//    public Outward_GridAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }


    @Override
    public void onBindViewHolder(myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
        holder.sernum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.material.setText(club.getMaterialName());
        holder.Status.setText(club.getCurrStatus());
        int intimelength = club.getInTime()!=null?club.getInTime().length():0;
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        int outtimelength = club.getOutTime().length();
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
                String vehicletype = club.getVehicleType();
                String currentst = club.getCurrStatus();
                char io = club.getI_O();
                Intent intent = new Intent();
                if (vehicletype.equals("OT") && io == 'I') {
                    if (currentst.equals("B")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Billing.class);
                    } else if (currentst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Security.class);
                    } else if (currentst.equals("Billing")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Billing.class);
                    }
                }

                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SerialNumber", club.getSerialNumber());
                intent.putExtra("VehicleNumber", club.getVehicleNumber());
                intent.putExtra("CurrStatus", club.getCurrentProcess());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return outwardGridmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView sernum, vehiclenum, material, Status, intime, outtime;

        public myviewHolder(View view) {
            super(view);
            sernum = view.findViewById(R.id.textSerialNumber);
            vehiclenum = view.findViewById(R.id.textVehicleNumber);
            material = view.findViewById(R.id.textMaterial);
            Status = view.findViewById(R.id.textStatus);
            intime = view.findViewById(R.id.textInTime);
            outtime = view.findViewById(R.id.textOutTime);
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
