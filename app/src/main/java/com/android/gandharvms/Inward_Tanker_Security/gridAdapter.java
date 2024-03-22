package com.android.gandharvms.Inward_Tanker_Security;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardOut_Tanker_Security;
import com.android.gandharvms.InwardOut_Tanker_Weighment;
import com.android.gandharvms.InwardOut_Truck_Security;
import com.android.gandharvms.InwardOut_Truck_Weighment;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_Sampling;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class gridAdapter extends RecyclerView.Adapter<gridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Respo_Model_In_Tanker_security> Gridmodel;
    private List<Respo_Model_In_Tanker_security> filteredGridList;
    private Context context;

    public gridAdapter(List<Respo_Model_In_Tanker_security> respoModelInTankerSecurities) {
        this.Gridmodel = respoModelInTankerSecurities;
        this.filteredGridList = respoModelInTankerSecurities;
    }
    @Override
    public int getItemViewType(int position)
    {
        if (position % 2 == 0)
        {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == TYPE_ROW)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_cell, viewGroup, false);
            return new myviewHolder(view);
        } else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item,
                    viewGroup, false);
            return new myviewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(myviewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Respo_Model_In_Tanker_security club = filteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.Status.setText(club.getCurrStatus());
        int secintimelength = club.getSecIntime()!= null? club.getSecIntime().length():0;
        if(secintimelength>0)
        {
            holder.secintime.setText(club.getSecIntime());
        }

        int weiintimelength = club.getWeiIntime()!= null? club.getWeiIntime().length():0;
        if(weiintimelength>0)
        {
            holder.wegintime.setText(club.getWeiIntime());
        }

        int samintimelength = club.getSamIntime()!= null?club.getSamIntime().length():0;
        if(samintimelength>0)
        {
            holder.samintime.setText(club.getSamIntime());
        }

        int labintimelength = club.getLabIntime()!= null? club.getLabIntime().length():0;
        if(labintimelength>0)
        {
            holder.labintime.setText(club.getLabIntime());
        }

        int prointimelength = club.getProIntime()!= null?club.getProIntime().length():0;
        if(prointimelength>0)
        {
            holder.prointime.setText(club.getProIntime());
        }
        int storeintimelength = club.getStoreIntime()!= null?club.getStoreIntime().length():0;
        if(storeintimelength>0)
        {
            holder.storeintime.setText(club.getStoreIntime());
        }

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Respo_Model_In_Tanker_security club = filteredGridList.get(position);
                String vehitype=club.getVehicleType();
                String crst=club.getCurrStatus();
                char io = club.getI_O();
                Intent intent= new Intent();
                if(vehitype.equals("IT") && io=='I') {
                    if (crst.equals("Weighment") ) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Weighment.class);
                    } else if (crst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Security.class);
                    } else if (crst.equals("Sampling")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Sampling.class);
                    } else if (crst.equals("Laboratory")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Laboratory.class);
                    } else if (crst.equals("Production")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Production.class);
                    }
                } else if (vehitype.equals("IR") && io=='I') {
                    if (crst.equals("Weighment")) {
                        intent = new Intent(view.getContext(), Inward_Truck_weighment.class);
                    } else if (crst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Inward_Truck_Security.class);
                    } else if (crst.equals("Store")) {
                        intent = new Intent(view.getContext(), Inward_Truck_Store.class);
                    }
                }else if (vehitype.equals("IT") && io=='O') {
                    if (crst.equals("OutSecurity")) {
                        intent = new Intent(view.getContext(), InwardOut_Tanker_Weighment.class);
                    } else if (crst.equals("VehicleOut")) {
                        intent = new Intent(view.getContext(), InwardOut_Tanker_Security.class);
                    }
                }else if (vehitype.equals("IR") && io=='O') {
                    if (crst.equals("OutSecurity")) {
                        intent = new Intent(view.getContext(), InwardOut_Truck_Weighment.class);
                    } else if (crst.equals("VehicleOut")) {
                        intent = new Intent(view.getContext(), InwardOut_Truck_Security.class);
                    }
                }
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SerialNumber",club.getSerialNo());
                intent.putExtra("VehicleNumber",club.getVehicleNo());
                intent.putExtra("CurrStatus",club.getCurrStatus());
                view.getContext().startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return Gridmodel.size();
    }
    public class myviewHolder extends RecyclerView.ViewHolder
    {
        public TextView Status,sernum, vehiclenum, material,secintime,secouttime,
                wegintime,wegouttime,samintime,samouttime,labintime,labouttime,prointime,proouttime,storeintime;
        public myviewHolder(View view)
        {
            super(view);
            vehiclenum = view.findViewById(R.id.textVehicleNumber);
            Status = view.findViewById(R.id.textStatus);
            secintime =view.findViewById(R.id.textSecurityInTime);
            wegintime =view.findViewById(R.id.textWeighmentInTime);
            samintime =view.findViewById(R.id.textSamplingInTime);
            labintime =view.findViewById(R.id.textLoboratoryInTime);
            prointime =view.findViewById(R.id.textProductionInTime);
            storeintime=view.findViewById(R.id.textStoreInTime);
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
                    List<Respo_Model_In_Tanker_security> filteredList = new ArrayList<>();
                    for (Respo_Model_In_Tanker_security club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
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
                filteredGridList = (ArrayList<Respo_Model_In_Tanker_security>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
