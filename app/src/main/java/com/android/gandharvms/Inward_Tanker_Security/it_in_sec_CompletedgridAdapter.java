package com.android.gandharvms.Inward_Tanker_Security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;
import com.android.gandharvms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class it_in_sec_CompletedgridAdapter extends RecyclerView.Adapter<it_in_sec_CompletedgridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public it_in_sec_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
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
    public it_in_sec_CompletedgridAdapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_in_sec_completedgrid_table_cell, viewGroup, false);
            return new it_in_sec_CompletedgridAdapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_in_sec_completedgrid_card_item,
                    viewGroup, false);
            return new it_in_sec_CompletedgridAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(it_in_sec_CompletedgridAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getInTime()!=null ? club.getInTime().length() : 0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.extramaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_InwardTanker(view, club.getExtramaterials());
            }
        });
        holder.invoiceno.setText(club.getInvoiceNo());
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.partyname.setText(club.getPartyName());
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(club.getUnitOfQTY());
        holder.netweight.setText(String.valueOf(club.getNetWeight()));
        holder.netweightuom.setText(club.getUnitOfNetWeight());
        holder.reoprtingre.setText(club.getReportingRemark());
        holder.remark.setText(club.getRemark());
        holder.oapo.setText(club.getOA_PO_number());
        holder.mob.setText(club.getDriver_MobileNo());

        holder.vehiclenum.setText(club.getVehicleNo());
        /*new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonResponseModelForAllDepartment club = filteredGridList.get(position);
                Intent intent;
                intent = new Intent(view.getContext(), Inward_Tanker_Security.class);
                intent.putExtra("VehicleNumber",club.getVehicleNo());
                intent.putExtra("Action","Up");
                view.getContext().startActivity(intent);
            }
        });*/
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
        TextView date,sernum,vehiclenum,invoiceno,material, intime, outtime,partyname,qty,
                qtyuom,netweight,netweightuom,reoprtingre,oapo,mob,remark,extramaterial;

        public myviewHolder(View view) {
            super(view);
            intime =view.findViewById(R.id.itinsectextcoInTime);
            outtime =view.findViewById(R.id.itinsectextcoOutTime);
            date =view.findViewById(R.id.itinsectextcodate);
            sernum =view.findViewById(R.id.itinsectextcoSerialNumber);
            vehiclenum = view.findViewById(R.id.itinsectextcoVehicleNumber);
            invoiceno =view.findViewById(R.id.itinsectextcoInvoiceNo);
//            material =view.findViewById(R.id.itinsectextcoMaterial);
            oapo =view.findViewById(R.id.itinsectextcooapo);
            mob =view.findViewById(R.id.itinsectextcomob);
            partyname =view.findViewById(R.id.itinsectextcopartyname);
            qty =view.findViewById(R.id.itinsectextcoqty);
            qtyuom =view.findViewById(R.id.itinsectextcoqtyuom);
            netweight =view.findViewById(R.id.itinsectextconetweight);
            netweightuom =view.findViewById(R.id.itinsectextconetweightuom);
            reoprtingre =view.findViewById(R.id.itinsectextcoreoprtingre);
            remark =view.findViewById(R.id.itinsectextcoremark);
            extramaterial = view.findViewById(R.id.itinsectextcoMaterial);
            extramaterial.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
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
    // Function to show material dialog
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
}
