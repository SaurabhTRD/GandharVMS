package com.android.gandharvms.Outward_Truck_Production;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Truck_Logistic.Adapter_Logi_complete;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OR_OUT_Dataentrycompleted extends RecyclerView.Adapter<Adapter_OR_OUT_Dataentrycompleted.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    public Adapter_OR_OUT_Dataentrycompleted(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
//        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_OR_OUT_Dataentrycompleted.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_dataentry_completed_tabe_cell,viewGroup,false);
            return new Adapter_OR_OUT_Dataentrycompleted.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_out_dataentry_completed_card_item,
                    viewGroup, false);
            return new Adapter_OR_OUT_Dataentrycompleted.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getProductionInTime()!= null ? club.getProductionInTime().length() :0;
        int outtimelength = club.getProductionOutTime()!=null ? club.getProductionOutTime().length() : 0;
        if (intimelength > 0){
            holder.intime.setText(club.getProductionInTime().substring(12, intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(club.getProductionOutTime().substring(12,outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.singofindu.setText(club.getIlsign());
        holder.signofsmallpack.setText(club.getSplsign());
        holder.signofsec.setText(club.getSecurityCreatedBy());
        holder.secdatetime.setText(club.getSecurityCreatedDate());
        holder.signofweighment.setText(club.getWeighmentCreatedBy());
        holder.weighmnetdatetime.setText(club.getWeighmentCreatedDate());
        holder.remark.setText(club.getProductionRemark());

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common_Outward_model club = filteredGridList.get(position);
                Intent intent = new Intent(view.getContext(), Outward_Tanker_Security.class);
                intent.putExtra("VehicleNumber", club.getVehicleNumber());
                intent.putExtra("Action", "Up");
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
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
        public TextView intime,outtime,serialnum,vehiclenum,singofindu,signofsmallpack,signofsec,secdatetime,
                signofweighment,weighmnetdatetime,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.oroutdeprolintime);
            outtime = itemView.findViewById(R.id.oroutdeproouttime);
            serialnum = itemView.findViewById(R.id.oroutdeproserialnum);
            vehiclenum =itemView.findViewById(R.id.oroutdeprovehiclenum);
            singofindu = itemView.findViewById(R.id.oroutdeprosignofil);
            signofsmallpack = itemView.findViewById(R.id.oroutdeprosignofsmallpack);
            signofsec = itemView.findViewById(R.id.oroutdeprosignbysecurity);
            secdatetime = itemView.findViewById(R.id.oroutdeprosecdatetime);
            signofweighment = itemView.findViewById(R.id.oroutdeprosignofweighment);
            weighmnetdatetime=itemView.findViewById(R.id.oroutdeproweighmentdatetime);
            remark = itemView.findViewById(R.id.oroutdeproremark);
        }
    }
}
