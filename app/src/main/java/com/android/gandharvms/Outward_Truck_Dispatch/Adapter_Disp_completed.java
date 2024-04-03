package com.android.gandharvms.Outward_Truck_Dispatch;

import android.annotation.SuppressLint;
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

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Disp_completed extends RecyclerView.Adapter<Adapter_Disp_completed.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Disp_completed(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Disp_completed.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_des_comple_table_cell,viewGroup,false);
            return new Adapter_Disp_completed.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_des_tableitem, viewGroup, false);
            return new Adapter_Disp_completed.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Disp_completed.myviewHolder holder, @SuppressLint("RecyclerView") int position) {

        Common_Outward_model obj = filteredGridList.get(position);
        int intimelength = obj.getDespatchInTime() != null ? obj.getDespatchInTime().length() :0;
        int outtimelength = obj.getDespatchOutTime()!= null ? obj.getDespatchOutTime().length() :0;
        if (intimelength > 0 ){
            holder.intime.setText(obj.getDespatchOutTime().substring(12,intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(obj.getDespatchOutTime().substring(12,outtimelength));
        }
        holder.serialnum.setText(obj.getSerialNumber());
        holder.vehiclenum.setText(obj.getVehicleNumber());
        holder.transporter.setText(obj.getTransportName());
        holder.oanum.setText(obj.getOAnumber());
        holder.qty.setText(String.valueOf(obj.getHowMuchQuantityFilled()));
        holder.other.setText(obj.getDespatchOther());
        holder.sighdis.setText(obj.getDespatch_Sign());
        holder.remark.setText(obj.getDespatchRemark());
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
        public TextView intime,outtime,serialnum,vehiclenum,transporter,oanum,qty,other,sighdis,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.ordesintime);
            outtime = itemView.findViewById(R.id.ordesouttime);
            serialnum = itemView.findViewById(R.id.ordesinserial);
            vehiclenum = itemView.findViewById(R.id.ordesinvehicle);
            transporter = itemView.findViewById(R.id.ordestransporter);
            oanum = itemView.findViewById(R.id.ordesinoa);
            qty = itemView.findViewById(R.id.ordesinqty);
            other = itemView.findViewById(R.id.ordesother);
            sighdis = itemView.findViewById(R.id.ordesinofficer);
            remark = itemView.findViewById(R.id.ordesinremark);
        }
    }
}
