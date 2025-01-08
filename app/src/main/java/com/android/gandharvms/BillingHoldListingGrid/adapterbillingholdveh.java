package com.android.gandharvms.BillingHoldListingGrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Tanker_Security.Adapter_OT__Complete_sec;
import com.android.gandharvms.Outward_Tanker_Security.BillingHoldStatusModel;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class adapterbillingholdveh extends RecyclerView.Adapter<adapterbillingholdveh.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<BillingHoldStatusModel> Gridmodel;
    private List<BillingHoldStatusModel> filteredGridList;
    private Context context;

    public adapterbillingholdveh(List<BillingHoldStatusModel> gridmodel,Context contextcon) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
        context=contextcon;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gridbillingholdveh_tablecell,viewGroup,false);
            return new adapterbillingholdveh.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gridbillingholdveh_carditem,
                    viewGroup, false);
            return new adapterbillingholdveh.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        BillingHoldStatusModel club = filteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.holdremark.setText(club.getHoldRemark());
        holder.date.setText(club.getCreatedDate().substring(0,12));
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

    @Override
    public Filter getFilter() {
        return  new Filter()    {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<BillingHoldStatusModel> filteredList = new ArrayList<>();
                    for (BillingHoldStatusModel club : Gridmodel) {
                        if (club.getVehicleNo().toLowerCase().contains(charString.toLowerCase()) || club.getHoldRemark().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredGridList = (ArrayList<BillingHoldStatusModel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView vehiclenum,holdremark,date;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            vehiclenum = itemView.findViewById(R.id.txtgridbilholdvehicleno);
            holdremark = itemView.findViewById(R.id.txtgridbilholdremark);
            date = itemView.findViewById(R.id.txtgridbilholdDate);
        }
    }
}
