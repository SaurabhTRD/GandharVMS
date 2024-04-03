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
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Weighment.Adapter_OT_completed_Weighment;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_inproc_prodcuction extends RecyclerView.Adapter<Adapter_OT_completed_inproc_prodcuction.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Adapter_OT_completed_inproc_prodcuction(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_inproc_prodcuction.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_inproc_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_inproc_prodcuction.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_inproc_tableitem, viewGroup, false);
            return new Adapter_OT_completed_inproc_prodcuction.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_inproc_prodcuction.myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.flushingno.setText(String.valueOf(club.getFlushing_No()));
        holder.oanum.setText(club.getOAnumber());
        holder.transporter.setText(club.getTransportName());
        holder.product.setText(club.getProductName());
        holder.howqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.customer.setText(club.getCustomerName());
        holder.location.setText(club.getLocation());
        holder.packingstatus.setText(club.getPackingStatus());
        holder.blendingsts.setText(club.getBlendingMaterialStatus());
        holder.prosign.setText(club.getSignatureofOfficer());
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

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView serialnum,vehiclenum,flushingno,oanum,transporter,product,howqty,customer,location,packingstatus,blendingsts,prosign,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serialnum = itemView.findViewById(R.id.otinprocserial);
            vehiclenum = itemView.findViewById(R.id.otinprocvehicle);
            flushingno = itemView.findViewById(R.id.otinprocflushing);
            oanum = itemView.findViewById(R.id.otinprocoanum);
            transporter = itemView.findViewById(R.id.otinproctransporter);
            product = itemView.findViewById(R.id.otinprocprodcut);
            howqty = itemView.findViewById(R.id.otinprochowqty);
            customer = itemView.findViewById(R.id.otinproccustomer);
            location = itemView.findViewById(R.id.otinproclocation);
            packingstatus = itemView.findViewById(R.id.otinprocpackingstatus);
            blendingsts = itemView.findViewById(R.id.otinprocblendingstatus);
            prosign = itemView.findViewById(R.id.otinprocsigh);
            remark = itemView.findViewById(R.id.otinprocremark);

        }
    }
}
