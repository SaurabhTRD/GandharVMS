package com.android.gandharvms.Outward_Truck_Dispatch;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Adapter_OT_completed_Weighment;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OR_comp_industrial extends RecyclerView.Adapter<Adapter_OR_comp_industrial.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;

    public Adapter_OR_comp_industrial(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
    }

    @NonNull
    @Override
    public Adapter_OR_comp_industrial.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.despatch_industrial_completed_tablecell,viewGroup,false);
            return new Adapter_OR_comp_industrial.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.despatch_industrial_completed_carditem, viewGroup, false);
            return new Adapter_OR_comp_industrial.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getIlInTime()!= null ? club.getIlInTime().length() :0;
        int outtimelength = club.getIlOutTime()!=null ? club.getIlOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getIlInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getIlOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.oanum.setText(club.getOAnumber());
        holder.customer.setText(club.getCustomerName());
        holder.transname.setText(club.getTransportName());
        holder.packof10ltr.setText(String.valueOf(club.getIlpackof10ltrqty()));
        holder.packof18ltr.setText(String.valueOf(club.getIlpackof18ltrqty()));
        holder.packof20ltr.setText(String.valueOf(club.getIlpackof20ltrqty()));
        holder.packof26ltr.setText(String.valueOf(club.getIlpackof26ltrqty()));
        holder.packof50ltr.setText(String.valueOf(club.getIlpackof50ltrqty()));
        holder.packof210ltr.setText(String.valueOf(club.getIlpackof210ltrqty()));
        holder.packofboxbuxket.setText(String.valueOf(club.getIlpackofboxbuxketltrqty()));
        holder.totalqty.setText(club.getIltotqty());
        holder.weight.setText(club.getIlweight());
        holder.industrialsign.setText(club.getIlsign());
        holder.nextdepartment.setText(club.getPurposeProcess());
        holder.remark.setText(club.getIlRemark());

        holder.vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common_Outward_model club = filteredGridList.get(position);
                Intent intent = new Intent(v.getContext(), Outward_DesIndustriaLoading_Form.class);
                intent.putExtra("VehicleNumber", club.getVehicleNumber());
                intent.putExtra("Action", "Up");
                v.getContext().startActivity(intent);
            }
        });
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
        public TextView serial,vehicle,oanum,customer,transname,intime,outtime,packof10ltr,packof18ltr,packof20ltr,
                packof26ltr,packof50ltr, packof210ltr,packofboxbuxket,totalqty,weight,industrialsign,nextdepartment,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.orindusserialno);
            vehicle = itemView.findViewById(R.id.orindusvehicleno);
            oanum = itemView.findViewById(R.id.orindusoano);
            customer = itemView.findViewById(R.id.orinduscustomer);
            transname = itemView.findViewById(R.id.orindustransporter);
            intime = itemView.findViewById(R.id.orindusintime);
            outtime = itemView.findViewById(R.id.orindusouttime);
            packof10ltr = itemView.findViewById(R.id.orindus10ltr);
            packof18ltr=itemView.findViewById(R.id.orindus18ltr);
            packof20ltr=itemView.findViewById(R.id.orindus20ltr);
            packof26ltr=itemView.findViewById(R.id.orindus26ltr);
            packof50ltr=itemView.findViewById(R.id.orindus50ltr);
            packof210ltr=itemView.findViewById(R.id.orindus210ltr);
            packofboxbuxket=itemView.findViewById(R.id.orindusBoxbucket);
            totalqty=itemView.findViewById(R.id.orindustotqty);
            weight=itemView.findViewById(R.id.orindusweight);
            industrialsign=itemView.findViewById(R.id.orindusindustrialsign);
            nextdepartment=itemView.findViewById(R.id.orindusnextdepartmentr);
            remark = itemView.findViewById(R.id.orindusremark);
        }
    }
}
