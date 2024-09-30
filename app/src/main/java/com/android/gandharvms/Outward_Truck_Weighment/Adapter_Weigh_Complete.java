package com.android.gandharvms.Outward_Truck_Weighment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Truck_Logistic.Adapter_Logi_complete;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Weigh_Complete extends RecyclerView.Adapter<Adapter_Weigh_Complete.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Weigh_Complete(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Weigh_Complete.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_weigh_complete_table_cell,viewGroup,false);
            return new Adapter_Weigh_Complete.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.or_in_weigh_tableitem, viewGroup, false);
            return new Adapter_Weigh_Complete.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Weigh_Complete.myviewHolder holder, @SuppressLint("RecyclerView") int position) {

        Common_Outward_model obj = filteredGridList.get(position);
      int intimelength = obj.getIntime() != null ? obj.getIntime().length() :0;
      int outtimelength = obj.getOutTime()!= null ? obj.getOutTime().length() :0;
      if (intimelength > 0){
          holder.intime.setText(obj.getIntime().substring(12,intimelength));
      }
      if (outtimelength > 0){
          holder.outtime.setText(obj.getOutTime().substring(12,outtimelength));
      }
        holder.serialnum.setText(obj.getSerialNumber());
        holder.vehicle.setText(obj.getVehicleNumber());
        holder.customer.setText(obj.getCustomerName());
        holder.oanum.setText(obj.getOAnumber());
        holder.loadedqty.setText(String.valueOf(obj.getHowMuchQuantityFilled()) );
        holder.loadedqtyuom.setText(obj.getHowMuchQTYUOM());
        holder.tarewe.setText(obj.getTareWeight());
        Picasso.get()
                .load(RetroApiClient.BASE_URL  + obj.getInVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + obj.getInDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);
        holder.remark.setText(obj.getRemark());

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
        public TextView intime,serialnum,vehicle,customer,oanum,loadedqty,loadedqtyuom,tarewe,remark,outtime;

        ImageView invehicleimage,indriverimage;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.orweintime);
            serialnum = itemView.findViewById(R.id.orweinserial);
            vehicle = itemView.findViewById(R.id.orweinvehicle);
            customer = itemView.findViewById(R.id.orweincust);
            oanum = itemView.findViewById(R.id.orweinoa);
            loadedqty = itemView.findViewById(R.id.orweinloaded);
            loadedqtyuom=itemView.findViewById(R.id.orweiloadedqtyuom);
            tarewe = itemView.findViewById(R.id.orweintare);
            invehicleimage=itemView.findViewById(R.id.orweinInVehicleImage);
            indriverimage=itemView.findViewById(R.id.orweinInDriverImage);
            remark = itemView.findViewById(R.id.orweinremark);
            outtime = itemView.findViewById(R.id.orweouttime);
        }
    }
}
