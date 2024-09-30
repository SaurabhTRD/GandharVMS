package com.android.gandharvms.Outward_Truck_Dispatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OR_Comp_SmallPack extends RecyclerView.Adapter<Adapter_OR_Comp_SmallPack.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;

    public Adapter_OR_Comp_SmallPack(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.despatch_smallpack_completed_tablecell,viewGroup,false);
            return new Adapter_OR_Comp_SmallPack.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.despatch_smallpack_completed_carditem, viewGroup, false);
            return new Adapter_OR_Comp_SmallPack.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getSplInTime()!= null ? club.getSplInTime().length() :0;
        int outtimelength = club.getSplOutTime()!=null ? club.getSplOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getSplInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getSplOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.oanum.setText(club.getOAnumber());
        holder.customer.setText(club.getCustomerName());
        holder.transname.setText(club.getTransportName());
        holder.packof7ltr.setText(String.valueOf(club.getSplpackof7ltrqty()));
        holder.packof7_5ltr.setText(String.valueOf(club.getSplpackof7_5ltrqty()));
        holder.packof8_5ltr.setText(String.valueOf(club.getSplpackof8_5ltrqty()));
        holder.packof10ltr.setText(String.valueOf(club.getSplpackof10ltrqty()));
        holder.packof11ltr.setText(String.valueOf(club.getSplpackof11ltrqty()));
        holder.packof12ltr.setText(String.valueOf(club.getSplpackof12ltrqty()));
        holder.packof13ltr.setText(String.valueOf(club.getSplpackof13ltrqty()));
        holder.packof15ltr.setText(String.valueOf(club.getSplpackof15ltrqty()));
        holder.packof18ltr.setText(String.valueOf(club.getSplpackof18ltrqty()));
        holder.packof20ltr.setText(String.valueOf(club.getSplpackof20ltrqty()));
        holder.packof26ltr.setText(String.valueOf(club.getSplpackof26ltrqty()));
        holder.packof50ltr.setText(String.valueOf(club.getSplpackof50ltrqty()));
        holder.packof210ltr.setText(String.valueOf(club.getSplpackof210ltrqty()));
        holder.packofboxbuxket.setText(String.valueOf(club.getSplpackofboxbuxketltrqty()));
        holder.totalqty.setText(club.getSpltotqty());
        holder.weight.setText(club.getSplweight());
        holder.industrialsign.setText(club.getSplsign());
        holder.nextdepartment.setText(club.getPurposeProcess());
        holder.remark.setText(club.getSplRemark());
    }

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

    public class myviewHolder extends RecyclerView.ViewHolder{
        public TextView serial,vehicle,oanum,customer,transname,intime,outtime,packof7ltr,packof7_5ltr,
                packof8_5ltr,packof10ltr, packof11ltr, packof12ltr, packof13ltr, packof15ltr, packof18ltr, packof20ltr
                , packof26ltr, packof50ltr, packof210ltr,packofboxbuxket,totalqty,weight,industrialsign,nextdepartment,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.orsmallpackserialno);
            vehicle = itemView.findViewById(R.id.orsmallpackvehicleno);
            oanum = itemView.findViewById(R.id.orsmallpackoano);
            customer = itemView.findViewById(R.id.orsmallpackcustomer);
            transname = itemView.findViewById(R.id.orsmallpacktransporter);
            intime = itemView.findViewById(R.id.orsmallpackintime);
            outtime = itemView.findViewById(R.id.orsmallpackouttime);
            packof7ltr = itemView.findViewById(R.id.orsmallpack7ltr);
            packof7_5ltr = itemView.findViewById(R.id.orsmallpack7_5ltr);
            packof8_5ltr = itemView.findViewById(R.id.orsmallpack8_5ltr);
            packof10ltr = itemView.findViewById(R.id.orsmallpack10ltr);
            packof11ltr = itemView.findViewById(R.id.orsmallpack11ltr);
            packof12ltr = itemView.findViewById(R.id.orsmallpack12ltr);
            packof13ltr = itemView.findViewById(R.id.orsmallpack13ltr);
            packof15ltr = itemView.findViewById(R.id.orsmallpack15ltr);
            packof18ltr=itemView.findViewById(R.id.orsmallpack18ltr);
            packof20ltr=itemView.findViewById(R.id.orsmallpack20ltr);
            packof26ltr=itemView.findViewById(R.id.orsmallpack26ltr);
            packof50ltr=itemView.findViewById(R.id.orsmallpack50ltr);
            packof210ltr=itemView.findViewById(R.id.orsmallpack210ltr);
            packofboxbuxket=itemView.findViewById(R.id.orsmallpackBoxbucket);
            totalqty=itemView.findViewById(R.id.orsmallpacktotqty);
            weight=itemView.findViewById(R.id.orsmallpackweight);
            industrialsign=itemView.findViewById(R.id.orsmallpacktrialsign);
            nextdepartment=itemView.findViewById(R.id.orsmallpacknextdepartmentr);
            remark = itemView.findViewById(R.id.orsmallpackremark);
        }
    }
}
