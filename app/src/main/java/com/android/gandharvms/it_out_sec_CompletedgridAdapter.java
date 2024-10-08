package com.android.gandharvms;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class it_out_sec_CompletedgridAdapter extends RecyclerView.Adapter<it_out_sec_CompletedgridAdapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public it_out_sec_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
        this.Gridmodel = inwardcomresponsemodel;
        this.filteredGridList = inwardcomresponsemodel;
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
    public it_out_sec_CompletedgridAdapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_out_sec_completedgrid_table_cell, viewGroup, false);
            return new it_out_sec_CompletedgridAdapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_out_sec_completedgrid_card_item,
                    viewGroup, false);
            return new it_out_sec_CompletedgridAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(it_out_sec_CompletedgridAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getOutInTime()!=null ? club.getOutInTime().length() : 0;
        int outtimelength = club.getUpdatedDate()!=null ? club.getUpdatedDate().length() : 0;
        holder.vehiclenum.setText(club.getVehicleNo());
        if (intimelength > 0) {
            holder.outintime.setText(club.getOutInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outouttime.setText(club.getUpdatedDate().substring(12, outtimelength));
        }
        holder.invoiceno.setText(club.getInvoiceNo());
        //holder.material.setText(club.getMaterial());
        holder.material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_InwardTanker(view, club.getExtramaterials());
            }
        });
        holder.partyname.setText(club.getPartyName());
        holder.translatelrcopy.setText(club.getIrCopy());
        holder.deliverybill.setText(club.getDeliveryBill());
        holder.taxinvoice.setText(club.getTaxInvoice());
        holder.ewaybill.setText(club.getEwayBill());
    }
    public int getItemCount() {
        return Gridmodel.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<CommonResponseModelForAllDepartment> filteredList = new ArrayList<>();
                    for (CommonResponseModelForAllDepartment club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNo().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNo().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredGridList = (ArrayList<CommonResponseModelForAllDepartment>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public
        TextView vehiclenum,invoiceno,outintime,outouttime,material,partyname,translatelrcopy,deliverybill,taxinvoice,ewaybill;

        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.itoutsectextcoVehicleNumber);
            invoiceno =view.findViewById(R.id.itoutsectextcoInvoiceNo);
            outintime =view.findViewById(R.id.itoutsectextcoOutInTime);
            outouttime=view.findViewById(R.id.itoutsectextcoOutOutTime);
            material =view.findViewById(R.id.itoutsectextcoMaterial);
            material.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            partyname =view.findViewById(R.id.itoutsectextcopartyname);
            translatelrcopy =view.findViewById(R.id.itoutsectextcoIrCopy);
            deliverybill =view.findViewById(R.id.itoutsectextcoDeliveryBill);
            taxinvoice =view.findViewById(R.id.itoutsectextcoTaxInvoice);
            ewaybill =view.findViewById(R.id.itoutsectextcoEwayBill);
        }
    }

    private void showMaterialDialog_InwardTanker(View view, String jsonMaterials) {
        // Parse the JSON list of extra materials
        List<matriallist> materialList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonMaterials);
            for (int i = 0; i < jsonArray.length(); i++) {
//                materialList.add(jsonArray.getString(i));
                JSONObject materialObject = jsonArray.getJSONObject(i);
                String Material = materialObject.getString("Material");
                int Qty = materialObject.getInt("Qty");
                String Qtyuom = materialObject.getString("Qtyuom");
                materialList.add(new matriallist(Material, Qty, Qtyuom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a dialog to show the list of materials
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Extra Materials");

        // Inflate the layout with a RecyclerView
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.material_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDialog);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Material_Adapter adapter = new Material_Adapter(materialList); // Pass the material list
        recyclerView.setAdapter(adapter);

        // Set the view and show the dialog
        builder.setView(dialogView);
        builder.setPositiveButton("Close", null);
        builder.show();
    }

    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }
}
