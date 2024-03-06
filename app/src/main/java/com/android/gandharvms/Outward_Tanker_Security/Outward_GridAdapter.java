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
import com.android.gandharvms.OutwardOut_Tanker_Weighment;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Laboratory;

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
        holder.Status.setText(club.getCurrStatus());
        holder.sernum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.material.setText(club.getMaterialName());
        int secintimelength = club.getSecInTime()!=null ? club.getSecInTime().length() : 0;
        if (secintimelength > 0) {
            holder.secInTime.setText(club.getSecInTime().substring(12, secintimelength));
        }
        int secouttimelength = club.getSecOutTime()!=null ? club.getSecOutTime().length() : 0;
        if (secouttimelength > 0) {
            holder.secOutTime.setText(club.getSecOutTime().substring(12, secouttimelength));
        }

        int weiInTimelength = club.getWeiInTime()!=null ? club.getWeiInTime().length() : 0;
        if (weiInTimelength > 0) {
            holder.weiInTime.setText(club.getWeiInTime().substring(12, weiInTimelength));
        }
        int weiOutTimelength = club.getWeiOutTime()!=null ? club.getWeiOutTime().length() : 0;
        if (weiOutTimelength > 0) {
            holder.weiOutTime.setText(club.getWeiOutTime().substring(12, weiOutTimelength));
        }

        int bilInTimelength = club.getBilInTime()!=null ? club.getBilInTime().length() : 0;
        if (bilInTimelength > 0) {
            holder.bilInTime.setText(club.getBilInTime().substring(12, bilInTimelength));
        }
        int bilOutTimelength = club.getBilOutTime()!=null ? club.getBilOutTime().length() : 0;
        if (bilOutTimelength > 0) {
            holder.bilOutTime.setText(club.getBilOutTime().substring(12, bilOutTimelength));
        }

        int ipfLabInTimelength = club.getIPFLabInTime()!=null ? club.getIPFLabInTime().length() : 0;
        if (ipfLabInTimelength > 0) {
            holder.ipfLabInTime.setText(club.getIPFLabInTime().substring(12 ,ipfLabInTimelength));
        }
        int ipfLabOutTimelength = club.getIPFLabOutTime()!=null ? club.getIPFLabOutTime().length() : 0;
        if (ipfLabOutTimelength > 0) {
            holder.ipfLabOutTime.setText(club.getIPFLabOutTime().substring(12 ,ipfLabOutTimelength));
        }

        int ipfProInTimelength = club.getIPFProInTime()!=null ? club.getIPFProInTime().length() : 0;
        if (ipfProInTimelength > 0) {
            holder.ipfProInTime.setText(club.getIPFProInTime().substring(12 ,ipfProInTimelength));
        }
        int ipfProOutTimelength = club.getIPFProOutTime()!=null ? club.getIPFProOutTime().length() : 0;
        if (ipfProOutTimelength > 0) {
            holder.ipfProOutTime.setText(club.getIPFProOutTime().substring(12 ,ipfProOutTimelength));
        }

        int blfLabInTimelength = club.getBLFLabInTime()!=null ? club.getBLFLabInTime().length() : 0;
        if (blfLabInTimelength > 0) {
            holder.blfLabInTime.setText(club.getBLFLabInTime().substring(12 ,blfLabInTimelength));
        }
        int blfLabOutTimelength = club.getBLFLabOutTime()!=null ? club.getBLFLabOutTime().length() : 0;
        if (blfLabOutTimelength > 0) {
            holder.blfLabOutTime.setText(club.getBLFLabOutTime().substring(12 ,blfLabOutTimelength));
        }

        int blfProInTimelength = club.getBLFProInTime()!=null ? club.getBLFProInTime().length() : 0;
        if (blfProInTimelength > 0) {
            holder.blfProInTime.setText(club.getBLFProInTime().substring(12 ,blfProInTimelength));
        }
        int blfProOutTimelength = club.getBLFProOutTime()!=null ? club.getBLFProOutTime().length() : 0;
        if (blfProOutTimelength > 0) {
            holder.blfProOutTime.setText(club.getBLFProOutTime().substring(12 ,blfProOutTimelength));
        }

        int desInTimeInTimelength = club.getDesInTime()!=null ? club.getDesInTime().length() : 0;
        if (desInTimeInTimelength > 0) {
            holder.desInTime.setText(club.getDesInTime().substring(12 ,desInTimeInTimelength));
        }
        int desOutTimelength = club.getDesOutTime()!=null ? club.getDesOutTime().length() : 0;
        if (desOutTimelength > 0) {
            holder.desOutTime.setText(club.getDesOutTime().substring(12 ,desOutTimelength));
        }

        int brfBilInTimelength = club.getBRFBilInTime()!=null ? club.getBRFBilInTime().length() : 0;
        if (brfBilInTimelength > 0) {
            holder.brfBilInTime.setText(club.getBRFBilInTime().substring(12 ,brfBilInTimelength));
        }
        int brfBilOutTimelength = club.getBRFBilOutTime()!=null ? club.getBRFBilOutTime().length() : 0;
        if (brfBilOutTimelength > 0) {
            holder.brfBilOutTime.setText(club.getBRFBilOutTime().substring(12 ,brfBilOutTimelength));
        }

        int brfLabInTimelength = club.getBRFLabInTime()!=null ? club.getBRFLabInTime().length() : 0;
        if (brfLabInTimelength > 0) {
            holder.brfLabInTime.setText(club.getBRFLabInTime().substring(12 ,brfLabInTimelength));
        }
        int brfLabOutTimelength = club.getBRFLabOutTime()!=null ? club.getBRFLabOutTime().length() : 0;
        if (brfLabOutTimelength > 0) {
            holder.brfLabOutTime.setText(club.getBRFLabOutTime().substring(12 ,brfLabOutTimelength));
        }

        int brfProInTimelength = club.getBRFProInTime()!=null ? club.getBRFProInTime().length() : 0;
        if (brfProInTimelength > 0) {
            holder.brfProInTime.setText(club.getBRFProInTime().substring(12 ,brfProInTimelength));
        }
        int brfProOutTimelength = club.getBRFProOutTime()!=null ? club.getBRFProOutTime().length() : 0;
        if (brfProOutTimelength > 0) {
            holder.brfProOutTime.setText(club.getBRFProOutTime().substring(12 ,brfProOutTimelength));
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
                    if (currentst.equals("Billing")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Billing.class);
                    } else if (currentst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Security.class);
                    }else if (currentst.equals("Weighment")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_weighment.class);
                    }else if (currentst.equals("Laboratory")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Laboratory.class);
                    }else if (currentst.equals("Production")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Production.class);
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
        public TextView sernum, vehiclenum, material, Status, secInTime,secOutTime,weiInTime,weiOutTime,
                bilInTime,bilOutTime,ipfLabInTime, ipfLabOutTime,ipfProInTime,ipfProOutTime,blfLabInTime,blfLabOutTime,
                blfProInTime,blfProOutTime,desInTime,desOutTime,brfBilInTime,brfBilOutTime,
                brfProInTime,brfProOutTime,brfLabInTime,brfLabOutTime;

        public myviewHolder(View view) {
            super(view);
            sernum = view.findViewById(R.id.textoutwardgridSerialNumber);
            vehiclenum = view.findViewById(R.id.textoutwardgridVehicleNumber);
            material = view.findViewById(R.id.textoutwardgridMaterial);
            Status = view.findViewById(R.id.textoutwardgridStatus);
            secInTime = view.findViewById(R.id.textoutwardgridSecInTime);
            secOutTime = view.findViewById(R.id.textoutwardgridSecOutTime);
            weiInTime = view.findViewById(R.id.textoutwardgridWeiInTime);
            weiOutTime = view.findViewById(R.id.textoutwardgridWeiOutTime);
            bilInTime = view.findViewById(R.id.textoutwardgridBilInTime);
            bilOutTime = view.findViewById(R.id.textoutwardgridBilOutTime);
            ipfLabInTime = view.findViewById(R.id.textoutwardgridIPFLabInTime);
            ipfLabOutTime = view.findViewById(R.id.textoutwardgridIPFLabOutTime);
            ipfProInTime = view.findViewById(R.id.textoutwardgridIPFProInTime);
            ipfProOutTime = view.findViewById(R.id.textoutwardgridIPFProOutTime);
            blfLabInTime = view.findViewById(R.id.textoutwardgridBLFLabInTime);
            blfLabOutTime = view.findViewById(R.id.textoutwardgridBLFLabOutTime);
            blfProInTime = view.findViewById(R.id.textoutwardgridBLFProInTime);
            blfProOutTime = view.findViewById(R.id.textoutwardgridBLFProOutTime);
            desInTime = view.findViewById(R.id.textoutwardgridDesInTime);
            desOutTime = view.findViewById(R.id.textoutwardgridDesOutTime);
            brfBilInTime = view.findViewById(R.id.textoutwardgridBRFBilInTime);
            brfBilOutTime = view.findViewById(R.id.textoutwardgridBRFBilOutTime);
            brfProInTime = view.findViewById(R.id.textoutwardgridBRFProInTime);
            brfProOutTime = view.findViewById(R.id.textoutwardgridBRFProOutTime);
            brfLabInTime = view.findViewById(R.id.textoutwardgridBRFLabInTime);
            brfLabOutTime = view.findViewById(R.id.textoutwardgridBRFLabOutTime);
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
