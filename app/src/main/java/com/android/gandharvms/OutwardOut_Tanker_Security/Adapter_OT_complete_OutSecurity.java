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
        int intimelength = club.getOutInTime()!= null ? club.getOutInTime().length() :0;
        int outtimelength = club.getUpdatedDate()!=null ? club.getUpdatedDate().length() : 0;
        if (intimelength > 0) {
            holder.initime.setText(club.getOutInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getUpdatedDate().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.partyname.setText(club.getCustomerName());
        holder.invoicenum.setText(club.getOutInvoiceNumber());
        holder.totalqty.setText(String.valueOf(club.getOutTotalQty()));
        holder.totalqtyuom.setText(club.getUnitofmeasurementname());
        holder.netwt.setText(String.valueOf(club.getNetWeight()));
        holder.sign.setText(club.getSignature());
        holder.remark.setText(club.getOutSRemark());
        holder.transportlrcopy.setText(club.getTransportLRcopy());
        holder.tremcard.setText(club.getTremcard());
        holder.ewaybill.setText(club.getEwaybill());
        holder.testreport.setText(club.getTest_Report());
        holder.invoice.setText(club.getInvoice());
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
        public TextView serial,vehicle,partyname,invoicenum,totalqty,totalqtyuom,netwt,transportlrcopy,tremcard,ewaybill,
                testreport,invoice,initime,outtime,goodsdisc,sign,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.otoutsecserial);
            vehicle = itemView.findViewById(R.id.otoutsecvehicle);
            partyname = itemView.findViewById(R.id.otoutsecparty);
            invoicenum = itemView.findViewById(R.id.otoutsecinvoicenum);
            totalqty = itemView.findViewById(R.id.otoutsectotalqty);
            totalqtyuom=itemView.findViewById(R.id.otoutsectotalqtyuom);
            netwt = itemView.findViewById(R.id.otoutsecnetwt);
            initime = itemView.findViewById(R.id.otoutsecintime);
            outtime = itemView.findViewById(R.id.otoutsecouttime);
            transportlrcopy = itemView.findViewById(R.id.otoutsectransportlrcopy);
            tremcard = itemView.findViewById(R.id.otoutsectremcard);
            ewaybill = itemView.findViewById(R.id.otoutsecewaybill);
            testreport = itemView.findViewById(R.id.otoutsectestreport);
            invoice = itemView.findViewById(R.id.otoutsecinvoice);
            sign = itemView.findViewById(R.id.otoutsecsign);
            remark = itemView.findViewById(R.id.otoutsecremark);
        }
    }
}
