package com.android.gandharvms.Inward_Truck_store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import com.android.gandharvms.Inward_Truck_Security.ir_in_sec_CompletedgridAdapter;
import com.android.gandharvms.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ir_stor_CompletedgridAdapter extends RecyclerView.Adapter<ir_stor_CompletedgridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public ir_stor_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
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
    public ir_stor_CompletedgridAdapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_stor_completedgrid_table_cell, viewGroup, false);
            return new ir_stor_CompletedgridAdapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_stor_completedgrid_card_item,
                    viewGroup, false);
            return new ir_stor_CompletedgridAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ir_stor_CompletedgridAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getInTime()!=null ? club.getInTime().length() : 0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        formattedDate = formatDate(club.getDate());
        holder.matreialdate.setText(formattedDate);
        holder.invoiceno.setText(club.getInvoiceNo());
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.oapo.setText(club.getOA_PO_number());
        formattedDate = formatDate(club.getDate());
        holder.oapodate.setText(formattedDate);
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(club.getUnitOfQTY());
        holder.invoiceno.setText(club.getInvoiceNo());
        formattedDate = formatDate(club.getDate());
        holder.invoicedate.setText(formattedDate);
        holder.ReceiveQTY.setText(String.valueOf(club.getReceiveQTY()));
        holder.ReQTYUom.setText(club.getReQTYUom());
        holder.StoreExtramaterials.setText(club.getStoreExtramaterials());
        holder.remark.setText(club.getRemark());
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
        TextView sernum,vehiclenum,material,matreialdate, intime, outtime,oapo,oapodate,qty,
                qtyuom,invoiceno,invoicedate,ReceiveQTY,ReQTYUom,StoreExtramaterials
                ,remark;

        public myviewHolder(View view) {
            super(view);
            sernum =view.findViewById(R.id.irstoretextcoSerialNumber);
            vehiclenum = view.findViewById(R.id.irstoretextcoVehicleNumber);
            material =view.findViewById(R.id.irstoretextcoMaterial);
            matreialdate =view.findViewById(R.id.irstoretextcoMaRedate);
            intime =view.findViewById(R.id.irstoretextcoInTime);
            outtime =view.findViewById(R.id.irstoretextcoOutTime);
            oapo =view.findViewById(R.id.irstoretextcooapo);
            oapodate =view.findViewById(R.id.irstoretextcoOAPOdate);
            qty =view.findViewById(R.id.irstoretextcoqty);
            qtyuom =view.findViewById(R.id.irstoretextcoqtyuom);
            invoiceno =view.findViewById(R.id.irstoretextcoinvNo);
            invoicedate =view.findViewById(R.id.irstoretextcoinvdate);
            ReceiveQTY =view.findViewById(R.id.irstoretextcoReceiveQTY);
            ReQTYUom =view.findViewById(R.id.irstoretextcoReceiveQTYUOM);
            StoreExtramaterials =view.findViewById(R.id.irstoretextcoStoreExtramaterials);
            remark =view.findViewById(R.id.irstoretextcoremark);
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
}
