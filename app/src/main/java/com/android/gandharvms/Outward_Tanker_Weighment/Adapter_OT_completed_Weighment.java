package com.android.gandharvms.Outward_Tanker_Weighment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.Outward_Truck_Weighment.Adapter_Weigh_Complete;
import com.android.gandharvms.ProductOA_Adapter;
import com.android.gandharvms.R;
import com.android.gandharvms.productlistwithoanumber;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_Weighment extends RecyclerView.Adapter<Adapter_OT_completed_Weighment.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Adapter_OT_completed_Weighment(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_Weighment.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_weigh_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_Weighment.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_weigh_tableitem, viewGroup, false);
            return new Adapter_OT_completed_Weighment.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_Weighment.myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getIntime()!= null ? club.getIntime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getIntime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.serial.setText(club.getSerialNumber());
        holder.vehicle.setText(club.getVehicleNumber());
        holder.vehicle.setTextColor(Color.parseColor("#0000EE")); // Standard link blue
        holder.vehicle.setPaintFlags(holder.vehicle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.vehicle.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, Outward_Tanker_weighment.class);
            intent.putExtra("vehiclenumber", club.VehicleNumber);
            intent.putExtra("recordId", club.getId());
            intent.putExtra("mode", "update"); // Tell the activity to enter edit mode
            context.startActivity(intent);
        });



        holder.productoano.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.productoano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_OutwardTanker(view, club.getProductQTYUOMOA());
            }
        });
        //holder.oanum.setText(club.getOAnumber());
        //holder.product.setText(club.getProductName());
        holder.customer.setText(club.getCustomerName());
        holder.transname.setText(club.getTransportName());
        //holder.howqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.location.setText(club.getLocation());
        holder.tarewt.setText(club.getTareWeight());
        Picasso.get()
                .load(RetroApiClient.BASE_URL  + club.getInVehicleImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getInDriverImage() + "?alt=media")
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);
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
        public TextView serial,vehicle,customer,transname,location,intime,outtime,tarewt,remark,productoano;
        ImageView invehicleimage,indriverimage;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.otweighserial);
            vehicle = itemView.findViewById(R.id.otweighvehicle);
            productoano=itemView.findViewById(R.id.otweighProductoanumber);
            //oanum = itemView.findViewById(R.id.otweighoanum);
            //product = itemView.findViewById(R.id.otweighproduct);
            customer = itemView.findViewById(R.id.otweighcustomer);
            transname = itemView.findViewById(R.id.otweightransporter);
            //howqty = itemView.findViewById(R.id.otweighhowqty);
            location = itemView.findViewById(R.id.otweighloaction);
            intime = itemView.findViewById(R.id.otweighintime);
            outtime = itemView.findViewById(R.id.otweighouttime);
            tarewt = itemView.findViewById(R.id.otweightarewt);
            invehicleimage=itemView.findViewById(R.id.otweighInVehicleImage);
            indriverimage=itemView.findViewById(R.id.otweighInDriverImage);
            remark = itemView.findViewById(R.id.otweighremark);
        }
    }

    private void showMaterialDialog_OutwardTanker(View view, String jsonMaterials) {
        // Parse the JSON list of extra materials
        List<productlistwithoanumber> materialList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonMaterials);
            for (int i = 0; i < jsonArray.length(); i++) {
//                materialList.add(jsonArray.getString(i));
                JSONObject materialObject = jsonArray.getJSONObject(i);
                String OANumber = materialObject.getString("OANumber");
                String Product = materialObject.getString("ProductName");
                String Qty = materialObject.getString("ProductQty");
                String Qtyuom = materialObject.getString("ProductQtyuom");
                materialList.add(new productlistwithoanumber(OANumber,Product, Qty, Qtyuom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a dialog to show the list of materials
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Product With OANo");

        // Inflate the layout with a RecyclerView
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.material_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDialog);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ProductOA_Adapter adapter = new ProductOA_Adapter(materialList); // Pass the material list
        recyclerView.setAdapter(adapter);

        // Set the view and show the dialog
        builder.setView(dialogView);
        builder.setPositiveButton("Close", null);
        builder.show();
    }
}
