package com.android.gandharvms.Outward_Tanker_Billing;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Security.Adapter_OT__Complete_sec;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.ProductOA_Adapter;
import com.android.gandharvms.R;
import com.android.gandharvms.productlistwithoanumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_Completed_Billing extends RecyclerView.Adapter<Adapter_OT_Completed_Billing.myviewHolder>  implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_OT_Completed_Billing(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_Completed_Billing.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bill_complete_tablecell,viewGroup,false);
            return new Adapter_OT_Completed_Billing.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bill_tableitem,
                    viewGroup, false);
            return new Adapter_OT_Completed_Billing.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_Completed_Billing.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getintime()!= null ? club.getintime().length() :0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getintime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehicelnum.setText(club.getVehicleNumber());
        holder.date.setText(club.getDate());
        holder.transporter.setText(club.getTransportName());
        holder.productoano.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.productoano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_OutwardTanker(view, club.getProductQTYUOMOA());
            }
        });
        //holder.oanum.setText(club.getOAnumber());
        holder.custname.setText(club.getCustomerName());
        //holder.productname.setText(club.getProductName());
        //holder.howqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.location.setText(club.getLocation());
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

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView serialnum,vehicelnum,date,transporter,intime,outtime,custname,location,remark,productoano;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serialnum = itemView.findViewById(R.id.otbillinserial);
            vehicelnum = itemView.findViewById(R.id.otbillinvehicle);
            date = itemView.findViewById(R.id.otbillindate);
            transporter = itemView.findViewById(R.id.otbillintransporter);
            intime = itemView.findViewById(R.id.otbillinintime);
            outtime = itemView.findViewById(R.id.otbilliniuttime);
            //oanum = itemView.findViewById(R.id.otbillinoanum);
            custname = itemView.findViewById(R.id.otbillincustname);
            //productname = itemView.findViewById(R.id.otbillinproduct);
            //howqty = itemView.findViewById(R.id.otbillinhowmuchqty);
            location = itemView.findViewById(R.id.otbillinlocation);
            remark = itemView.findViewById(R.id.otbillinremark);
            productoano=itemView.findViewById(R.id.otbillinMaterial);
        }
    }
}
