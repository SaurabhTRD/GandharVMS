package com.android.gandharvms.Outward_Truck_Logistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.android.gandharvms.Outward_Truck_Security.Adapter_OR_Completesec;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Logi_complete extends RecyclerView.Adapter<Adapter_Logi_complete.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Logi_complete(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Logi_complete.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_logi_complete_table_cell,viewGroup,false);
            return new Adapter_Logi_complete.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_logi_tableitem,
                    viewGroup, false);
            return new Adapter_Logi_complete.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Logi_complete.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getInTime()!= null ? club.getInTime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0){
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(club.getOutTime().substring(12,outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.transporter.setText(club.getTransportName());
        holder.place.setText(club.getPlace());
        holder.oanumber.setText(club.getOAnumber());
        holder.custname.setText(club.getCustomerName());
        holder.loadedqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.loadedqtyuom.setText(club.getHowMuchQTYUOM());
        holder.remark.setText(club.getRemark());

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common_Outward_model club = filteredGridList.get(position);
                Intent intent;
                 intent = new Intent(view.getContext(), Outward_Truck_Logistics.class);
                 intent.putExtra("vehiclenum",club.getVehicleNumber());
                 intent.putExtra("Action","Up");
                 view.getContext().startActivity(intent);
            }
        });

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
        public TextView intime,serialnum,vehiclenum,transporter,place,oanumber,custname,loadedqty,loadedqtyuom,remark,outtime;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.orlintime);
            serialnum = itemView.findViewById(R.id.orlserialnum);
            vehiclenum =itemView.findViewById(R.id.orlvehiclenum);
            transporter = itemView.findViewById(R.id.orltransporter);
            place = itemView.findViewById(R.id.orlplace);
            oanumber = itemView.findViewById(R.id.orloanum);
            custname = itemView.findViewById(R.id.orlcustname);
            loadedqty = itemView.findViewById(R.id.orlloadedqty);
            loadedqtyuom=itemView.findViewById(R.id.orlladedqtyuom);
            remark = itemView.findViewById(R.id.orlremark);
            outtime = itemView.findViewById(R.id.orlouttime);
        }
    }
}
