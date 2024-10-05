package com.android.gandharvms.IT_VehicleStatus_Grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class it_statusgrid_adapter extends RecyclerView.Adapter<it_statusgrid_adapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Respo_Model_In_Tanker_security> Gridmodel;
    private List<Respo_Model_In_Tanker_security> filteredGridList;

    public it_statusgrid_adapter(List<Respo_Model_In_Tanker_security> respoModelInTankerSecurities) {
        this.Gridmodel = respoModelInTankerSecurities;
        this.filteredGridList = respoModelInTankerSecurities;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_statusgrid_tableitem, viewGroup, false);
            return new it_statusgrid_adapter.myviewHolder(view);
        } else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_statusgrid_carditem,
                    viewGroup, false);
            return new it_statusgrid_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Respo_Model_In_Tanker_security club = filteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.Status.setText(club.getCurrStatus());
        holder.sernum.setText(club.getSerialNo());
        holder.date.setText(club.getDate().substring(0,10));
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
        int outweitime = club.getOutWeiTime()!= null?club.getOutWeiTime().length():0;
        if(outweitime>0)
        {
            holder.outweitime.setText(club.getOutWeiTime());
        }
        int outsectime = club.getOutSecTime()!= null?club.getOutSecTime().length():0;
        if(outsectime>0)
        {
            holder.outsectime.setText(club.getOutSecTime());
        }
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

    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }


    public class myviewHolder extends RecyclerView.ViewHolder
    {
        public TextView Status,sernum,date, vehiclenum, material,secintime,
                wegintime,samintime,labintime,prointime,outweitime,outsectime;
        public myviewHolder(View view)
        {
            super(view);
            Status = view.findViewById(R.id.txtitstatusgridStatus);
            vehiclenum = view.findViewById(R.id.txtitstatusgridVehicleNumber);
            sernum = view.findViewById(R.id.txtitstatusgridSerialNumber);
            date = view.findViewById(R.id.txtitstatusgridDate);
            secintime =view.findViewById(R.id.txtitstatusgridSecurityInTime);
            wegintime =view.findViewById(R.id.txtitstatusgridWeighmentInTime);
            samintime =view.findViewById(R.id.txtitstatusgridSamplingInTime);
            labintime =view.findViewById(R.id.txtitstatusgridLoboratoryInTime);
            prointime =view.findViewById(R.id.txtitstatusgridProductionInTime);
            outweitime=view.findViewById(R.id.txtitstatusgridOutWeiTime);
            outsectime=view.findViewById(R.id.txtitstatusgridOutSecTime);
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
