package com.android.gandharvms.Inward_Truck_Security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
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
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.R;
import com.android.gandharvms.it_out_sec_CompletedgridAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ir_in_sec_CompletedgridAdapter extends RecyclerView.Adapter<ir_in_sec_CompletedgridAdapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public ir_in_sec_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel,Context context) {
        this.Gridmodel = inwardcomresponsemodel;
        this.filteredGridList = inwardcomresponsemodel;
        this.context = context;
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
    public myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_in_sec_completedgrid_table_cell, viewGroup, false);
            return new myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_in_sec_completedgrid_card_item,
                    viewGroup, false);
            return new myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getInTime()!=null ? club.getInTime().length() : 0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
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
//        holder.extramaterials.setText(club.getExtramaterials());

//        holder.extramaterials.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMaterialDialog(view, club.getExtramaterials()); // Passing the view and the material list (JSON string or list)
//            }
//        });
        // Setting the click listener for viewmaterial
        holder.viewmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaterialDialog(v, club.getExtramaterials());
            }
        });
        holder.reoprtingre.setText(club.getReportingRemark());
        holder.remark.setText(club.getRemark());
        holder.oapo.setText(club.getOA_PO_number());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.Selectregister.setText(club.getSelectregister());
        holder.IrCopy.setText(club.getIrCopy());
        holder.DeliveryBill.setText(club.getDeliveryBill());
        holder.TaxInvoice.setText(club.getTaxInvoice());
        holder.EwayBill.setText(club.getEwayBill());
        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonResponseModelForAllDepartment club = filteredGridList.get(position);
                Intent intent;
                intent = new Intent(view.getContext(), Inward_Truck_Security.class);
                intent.putExtra("VehicleNumber",club.getVehicleNo());
                intent.putExtra("Action","Up");
                view.getContext().startActivity(intent);
            }
        });
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
                qtyuom,netweight,netweightuom,reoprtingre,oapo,mob,Selectregister,IrCopy,DeliveryBill,TaxInvoice,
                EwayBill,extramaterials,remark;
        TextView viewmaterial;

        public myviewHolder(View view) {
            super(view);
            intime =view.findViewById(R.id.irinsectextcoInTime);
            outtime =view.findViewById(R.id.irinsectextcoOutTime);
            date =view.findViewById(R.id.irinsectextcodate);
            sernum =view.findViewById(R.id.irinsectextcoSerialNumber);
            vehiclenum = view.findViewById(R.id.irinsectextcoVehicleNumber);
            invoiceno =view.findViewById(R.id.irinsectextcoInvoiceNo);
            material =view.findViewById(R.id.irinsectextcoMaterial);
            oapo =view.findViewById(R.id.irinsectextcooapo);
            mob =view.findViewById(R.id.irinsectextcomob);
            partyname =view.findViewById(R.id.irinsectextcopartyname);
            qty =view.findViewById(R.id.irinsectextcoqty);
            qtyuom =view.findViewById(R.id.irinsectextcoqtyuom);
            netweight =view.findViewById(R.id.irinsectextconetweight);
            netweightuom =view.findViewById(R.id.irinsectextconetweightuom);
            reoprtingre =view.findViewById(R.id.irinsectextcoreoprtingre);
            Selectregister =view.findViewById(R.id.irinsectextcoSelectregister);
            IrCopy =view.findViewById(R.id.irinsectextcoIrCopy);
            DeliveryBill =view.findViewById(R.id.irinsectextcoDeliveryBill);
            TaxInvoice=view.findViewById(R.id.irinsectextcoTaxInvoice);
            EwayBill =view.findViewById(R.id.irinsectextcoEwayBill);
            //extramaterials =view.findViewById(R.id.irinsectextcoextramaterials);
            remark =view.findViewById(R.id.irinsectextcoremark);
            viewmaterial = view.findViewById(R.id.txtEditClickLink);
            viewmaterial.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//            viewmaterial.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showmaterialdialoge();
//                }
//            });

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
//    private void showmaterialdialoge() {
//        // Inflate the dialog layout
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View dialogView = inflater.inflate(R.layout.material_dialog, null);
//
//        // Get the RecyclerView from the dialog layout
//        RecyclerView recyclerViewDialog = dialogView.findViewById(R.id.recyclerViewDialog);
//        recyclerViewDialog.setLayoutManager(new LinearLayoutManager(context));
//
//        // Create the AlertDialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(dialogView)
//                .setTitle("History")
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        // Fetch and display the history list
////        callhistorylist(complaintid, SPCode, recyclerViewDialog);  // Use the passed complaint ID
//    }

    // Function to show material dialog
    private void showMaterialDialog(View view, String jsonMaterials) {
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
