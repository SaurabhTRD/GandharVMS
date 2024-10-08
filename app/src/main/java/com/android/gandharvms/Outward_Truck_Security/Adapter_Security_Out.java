package com.android.gandharvms.Outward_Truck_Security;

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
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Security_Out extends RecyclerView.Adapter<Adapter_Security_Out.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Security_Out(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Security_Out.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_sec_complete_table_cell,viewGroup,false);
            return new Adapter_Security_Out.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_sec_tableitem,
                    viewGroup, false);
            return new Adapter_Security_Out.myviewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_Security_Out.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getOutInTime()!= null ? club.getOutInTime().length() :0;
        int outtimelength = club.getUpdatedDate()!=null ? club.getUpdatedDate().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getOutInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getUpdatedDate().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.partyname.setText(club.getCustomerName());
        holder.sign.setText(club.getSignature());
        holder.invoiceno.setText(club.getOutInvoiceNumber());
        holder.netweight.setText(String.valueOf(club.getNetWeight()));
        holder.iltotqty.setText(club.getIltotqty());
        holder.spltotqty.setText(club.getSpltotqty());
        holder.discription.setText(club.getDescriptionofGoods());
        holder.transportlrcopy.setText(club.getTransportLRcopy());
        holder.tremcard.setText(club.getTremcard());
        holder.ewaybill.setText(club.getEwaybill());
        holder.testreport.setText(club.getTest_Report());
        holder.invoice.setText(club.getInvoice());
        holder.remark.setText(club.getOutSRemark());

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

    public class myviewHolder  extends RecyclerView.ViewHolder{
        public TextView intime,outtime,serialnum,vehiclenum,partyname,sign,invoice,netweight,iltotqty,
                spltotqty,transportlrcopy,tremcard,ewaybill, testreport,invoiceno,discription,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.oroutsecintime);
            outtime = itemView.findViewById(R.id.oroutsecouttime);
            serialnum = itemView.findViewById(R.id.oroutsecserial);
            vehiclenum = itemView.findViewById(R.id.oroutsecvehicle);
            partyname = itemView.findViewById(R.id.oroutsecparty);
            invoiceno = itemView.findViewById(R.id.oroutsecinvoice);
            netweight = itemView.findViewById(R.id.oroutsecnetweight);
            iltotqty = itemView.findViewById(R.id.oroutseciltotqty);
            spltotqty=itemView.findViewById(R.id.oroutsecspltotqty);
            discription = itemView.findViewById(R.id.oroutsecdiscription);
            transportlrcopy = itemView.findViewById(R.id.oroutsectransportlrcopy);
            tremcard = itemView.findViewById(R.id.oroutsectremcard);
            ewaybill = itemView.findViewById(R.id.oroutsecewaybill);
            testreport = itemView.findViewById(R.id.oroutsectestreport);
            invoice = itemView.findViewById(R.id.oroutsecinvoice1);
            sign = itemView.findViewById(R.id.oroutsecsign);
            remark = itemView.findViewById(R.id.oroutsecremark);
        }
    }
}
