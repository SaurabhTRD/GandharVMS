package com.android.gandharvms.Inward_Tanker_Security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class gridAdapter extends RecyclerView.Adapter<gridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<gridmodel> Gridmodel;
    private List<gridmodel> filteredGridList;
    private Context context;

    public gridAdapter(List<gridmodel> Gridmodel) {
        this.Gridmodel = Gridmodel;
        this.filteredGridList = Gridmodel;
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
        gridmodel club = filteredGridList.get(position);
        holder.sernum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicalnumber());
        holder.material.setText(club.getMaterial());
        holder.Status.setText(club.getStatus());
        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid grd = new grid();
                gridmodel club = filteredGridList.get(position);
                // grd.FetchVehicleDetailsFromGrid(club);
            }
        });
    }

    public int getItemCount() {
        return Gridmodel.size();
    }
    public class myviewHolder extends RecyclerView.ViewHolder
    {
        public TextView sernum, vehiclenum, material, Status;
        public myviewHolder(View view)
        {
            super(view);
            sernum = view.findViewById(R.id.textSerialNumber);
            vehiclenum = view.findViewById(R.id.textVehicleNumber);
            material = view.findViewById(R.id.textMaterial);
            Status = view.findViewById(R.id.textStatus);
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
                    List<gridmodel> filteredList = new ArrayList<>();
                    for (gridmodel club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.SerialNumber.toLowerCase().contains(charString.toLowerCase()) || club.vehicalnumber.toLowerCase().contains(charString.toLowerCase())) {
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
                filteredGridList = (ArrayList<gridmodel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
