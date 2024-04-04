package com.android.gandharvms.OutwardOut_Tanker_Security;

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
import com.android.gandharvms.Outward_Tanker_Security.Adapter_OT__Complete_sec;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_complete_OutSecurity extends RecyclerView.Adapter<Adapter_OT_complete_OutSecurity.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_OT_complete_OutSecurity(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_complete_OutSecurity.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_sec_complete_table_cell,viewGroup,false);
            return new Adapter_OT_complete_OutSecurity.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_sec_tableitem,
                    viewGroup, false);
            return new Adapter_OT_complete_OutSecurity.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_complete_OutSecurity.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getInTime()!= null ? club.getInTime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.initime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.partyname.setText(club.getNameofParty());
        holder.invoicenum.setText(club.getInvoiceNumber());
        holder.totalqty.setText(String.valueOf(club.getOutTotalQty()));
        holder.netwt.setText(String.valueOf(club.getNetWeight()));
        holder.goodsdisc.setText(club.getDescriptionofGoods());
        holder.sign.setText(club.getSignature());
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
        public TextView serial,vehicle,partyname,invoicenum,totalqty,netwt,initime,outtime,goodsdisc,sign,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.otoutsecserial);
            vehicle = itemView.findViewById(R.id.otoutsecvehicle);
            partyname = itemView.findViewById(R.id.otoutsecparty);
            invoicenum = itemView.findViewById(R.id.otoutsecinvoicenum);
            totalqty = itemView.findViewById(R.id.otoutsectotalqty);
            netwt = itemView.findViewById(R.id.otoutsecnetwt);
            initime = itemView.findViewById(R.id.otoutsecintime);
            outtime = itemView.findViewById(R.id.otoutsecouttime);
            goodsdisc = itemView.findViewById(R.id.otoutgoodsdisc);
            sign = itemView.findViewById(R.id.otoutsecsign);
            remark = itemView.findViewById(R.id.otoutsecremark);
        }
    }
}
