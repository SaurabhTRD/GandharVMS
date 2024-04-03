package com.android.gandharvms.Outward_Tanker_Weighment;

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
import com.android.gandharvms.Outward_Truck_Weighment.Adapter_Weigh_Complete;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_Weighment extends RecyclerView.Adapter<Adapter_OT_completed_Weighment.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Adapter_OT_completed_Weighment(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_Weighment.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_weigh_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_Weighment.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_weigh_tableitem, viewGroup, false);
            return new Adapter_OT_completed_Weighment.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_Weighment.myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getIntime()!= null ? club.getIntime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getIntime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.oanum.setText(club.getOAnumber());
        holder.product.setText(club.getProductName());
        holder.customer.setText(club.getCustomerName());
        holder.transname.setText(club.getTransportName());
        holder.howqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.location.setText(club.getLocation());
        holder.tarewt.setText(club.getTareWeight());
        holder.remark.setText(club.getRemark());
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
        public TextView serial,vehicle,oanum,product,customer,transname,howqty,location,intime,outtime,tarewt,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.otweighserial);
            vehicle = itemView.findViewById(R.id.otweighvehicle);
            oanum = itemView.findViewById(R.id.otweighoanum);
            product = itemView.findViewById(R.id.otweighproduct);
            customer = itemView.findViewById(R.id.otweighcustomer);
            transname = itemView.findViewById(R.id.otweightransporter);
            howqty = itemView.findViewById(R.id.otweighhowqty);
            location = itemView.findViewById(R.id.otweighloaction);
            intime = itemView.findViewById(R.id.otweighintime);
            outtime = itemView.findViewById(R.id.otweighouttime);
            tarewt = itemView.findViewById(R.id.otweightarewt);
            remark = itemView.findViewById(R.id.otweighremark);
        }
    }
}
