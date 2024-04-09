package com.android.gandharvms.Outward_Tanker_Production_forms;

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

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_bulkload_production extends RecyclerView.Adapter<Adapter_OT_completed_bulkload_production.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;
    public Adapter_OT_completed_bulkload_production(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_bulkload_production.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulkloadprod_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_bulkload_production.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulkload_prod_tablecell, viewGroup, false);
            return new Adapter_OT_completed_bulkload_production.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_bulkload_production.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getProInTime()!= null ? club.getProInTime().length() :0;
        int outtimelength = club.getProOutTime()!=null ? club.getProOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getProInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getProOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.oanum.setText(club.getOAnumber());
        holder.product.setText(club.getProductName());
        holder.customer.setText(club.getCustomerName());
        holder.destination.setText(club.getLocation());
        holder.qtykl.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.transporter.setText(club.getTransportName());
        holder.officer.setText(club.getPsign());
        holder.remark.setText(club.getProRemark());
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

    public class myviewHolder extends RecyclerView.ViewHolder{
        public TextView serialnum,vehiclenum,oanum,product,customer,destination,qtykl,transporter,intime,outtime,officer,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serialnum = itemView.findViewById(R.id.otbulkpserial);
            vehiclenum = itemView.findViewById(R.id.otbulkpvehicle);
            oanum = itemView.findViewById(R.id.otbulkpoanum);
            product = itemView.findViewById(R.id.otbulkpproduct);
            customer = itemView.findViewById(R.id.otbulkpcustomer);
            destination = itemView.findViewById(R.id.otbulkpdestination);
            qtykl = itemView.findViewById(R.id.otbulkpqtykl);
            transporter = itemView.findViewById(R.id.otbulkptransporter);
            intime = itemView.findViewById(R.id.otbulkpintime);
            outtime = itemView.findViewById(R.id.otbulkpouttime);
            officer = itemView.findViewById(R.id.otbulkpofficer);
            remark = itemView.findViewById(R.id.otbulkpremark);
        }
    }
}