package com.android.gandharvms.OutwardOut_Truck_Billing;

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
import com.android.gandharvms.Outward_Truck_Weighment.Adater_Weigh_Out_Complete;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Billing_Out_Complete extends RecyclerView.Adapter<Adapter_Billing_Out_Complete.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Billing_Out_Complete(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Billing_Out_Complete.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_bill_complete_table,viewGroup,false);
            return new Adapter_Billing_Out_Complete.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_bill_tableitem, viewGroup, false);
            return new Adapter_Billing_Out_Complete.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Billing_Out_Complete.myviewHolder holder, int position) {
        Common_Outward_model obj = filteredGridList.get(position);
        int intimelength = obj.getOutInTime() != null ? obj.getOutInTime().length() :0;
        int outtimelength = obj.getOutOutTime()!= null ? obj.getOutOutTime().length() :0;
        if (intimelength > 0){
            holder.intime.setText(obj.getOutInTime().substring(12,intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(obj.getOutOutTime().substring(12,outtimelength));
        }
        holder.serialnum.setText(obj.getSerialNumber());
        holder.vehicle.setText(obj.getVehicleNumber());
        holder.oanum.setText(obj.getOAnumber());
        holder.transport.setText(obj.getTransportName());
        holder.driverno.setText(obj.getMobileNumber());
        holder.grswt.setText(obj.getGrossWeight());
        holder.tarewt.setText(obj.getTareWeight());
        holder.netwt.setText(obj.getNetWeight());
        holder.sealnum.setText(obj.getSealNumber());
        holder.invoiceno.setText(obj.getOutInvoiceNumber());
        holder.iltotqty.setText(obj.getIltotqty());
        holder.spltotqty.setText(obj.getSpltotqty());
        holder.batch.setText(obj.getBatchNo());
        holder.remark.setText(obj.getOutRemark());
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

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView intime,outtime,serialnum,vehicle,oanum,transport,driverno,grswt,netwt,tarewt,sealnum,
                invoiceno,iltotqty,spltotqty,batch,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.orbillintime);
            outtime = itemView.findViewById(R.id.orbillouttime);
            serialnum = itemView.findViewById(R.id.orbillserial);
            vehicle  = itemView.findViewById(R.id.orbillvehicle);
            oanum = itemView.findViewById(R.id.orbilloanum);
            transport = itemView.findViewById(R.id.orbilltrans);
            driverno = itemView.findViewById(R.id.orbilldriver);
            grswt = itemView.findViewById(R.id.orbillgrswt);
            tarewt = itemView.findViewById(R.id.orbilltarewt);
            netwt = itemView.findViewById(R.id.orbillnetwt);
            sealnum = itemView.findViewById(R.id.orbillseal);
            invoiceno = itemView.findViewById(R.id.orinvoiceno);
            iltotqty = itemView.findViewById(R.id.orindustotqty);
            spltotqty = itemView.findViewById(R.id.orsmalltotqty);
            batch = itemView.findViewById(R.id.orbillbatch);
            remark = itemView.findViewById(R.id.orbillremark);
        }
    }
}
