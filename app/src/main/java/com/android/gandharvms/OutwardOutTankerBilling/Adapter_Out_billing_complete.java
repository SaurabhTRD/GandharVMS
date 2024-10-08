package com.android.gandharvms.OutwardOutTankerBilling;

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
import com.android.gandharvms.OutwardOut_Truck_Billing.Adapter_Billing_Out_Complete;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.ProductOA_Adapter;
import com.android.gandharvms.R;
import com.android.gandharvms.productlistwithoanumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Out_billing_complete extends RecyclerView.Adapter<Adapter_Out_billing_complete.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Adapter_Out_billing_complete(List<Common_Outward_model> gridmodel) {
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
    public Adapter_Out_billing_complete.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_bill_complet_table,viewGroup,false);
            return new Adapter_Out_billing_complete.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_out_bill_tableitem, viewGroup, false);
            return new Adapter_Out_billing_complete.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Out_billing_complete.myviewHolder holder, int position) {

        Common_Outward_model obj = filteredGridList.get(position);
        int intimelength = obj.getOutInTime() != null ? obj.getOutInTime().length() :0;
        int outtimelength = obj.getOutOutTime()!= null ? obj.getOutOutTime().length() :0;
        if (intimelength > 0){
            holder.intime.setText(obj.getOutInTime().substring(12,intimelength));
        }
        if (outtimelength > 0){
            holder.outtime.setText(obj.getOutOutTime().substring(12,outtimelength));
        }
        holder.serialnum.setText(obj.getSerialNumber());
        holder.vehiclenum.setText(obj.getVehicleNumber());
        holder.tramnsporter.setText(obj.getTransportName());
        holder.productoano.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.productoano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_OutwardTanker(view, obj.getProductQTYUOMOA());
            }
        });
        //holder.oapo.setText(obj.getOAnumber());
        holder.drivernum.setText(obj.getMobileNumber());
        holder.batchnum.setText(obj.getBatch_No());
        holder.density.setText(String.valueOf(obj.getDensity_29_5C()));
        holder.tarewt.setText(String.valueOf(obj.getTareWeight()));
        holder.netwt.setText(String.valueOf(obj.getNetWeight()));
        holder.grswt.setText(String.valueOf(obj.getGrossWeight()));
        holder.sealnum.setText(obj.getSealNumber());
        holder.totalqty.setText(String.valueOf(obj.getOutTotalQty()));
        holder.invoicenum.setText(obj.getOutInvoiceNumber());
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

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView serialnum,vehiclenum,tramnsporter,productoano,drivernum,batchnum,density,tarewt,netwt,grswt,
                sealnum,totalqty,invoicenum,intime,outtime,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            serialnum = itemView.findViewById(R.id.otoutbillserial);
            vehiclenum = itemView.findViewById(R.id.otoutbillvehicle);
            tramnsporter = itemView.findViewById(R.id.otoutbilltrans);
            //oapo = itemView.findViewById(R.id.orbilloutoapo);
            productoano = itemView.findViewById(R.id.otoutbillprodOaNo);
            drivernum = itemView.findViewById(R.id.orbilloutdrivernum);
            batchnum = itemView.findViewById(R.id.otoutbillbatch);
            density = itemView.findViewById(R.id.otoutbilldensity);
            tarewt = itemView.findViewById(R.id.otoutbilltarewt);
            netwt = itemView.findViewById(R.id.otoutbillnetwt);
            grswt = itemView.findViewById(R.id.otoutbillgrswt);
            sealnum = itemView.findViewById(R.id.otoutbillseal);
            totalqty = itemView.findViewById(R.id.otoutbilltotalqty);
            invoicenum = itemView.findViewById(R.id.otoutbillinvoice);
            intime = itemView.findViewById(R.id.otoutbillintime);
            outtime = itemView.findViewById(R.id.otoutbillouttime);
            remark = itemView.findViewById(R.id.otoutbillremark);

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
