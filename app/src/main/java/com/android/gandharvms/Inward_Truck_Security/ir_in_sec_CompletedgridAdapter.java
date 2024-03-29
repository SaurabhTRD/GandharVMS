package com.android.gandharvms.Inward_Truck_Security;

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
import com.android.gandharvms.R;
import com.android.gandharvms.it_out_sec_CompletedgridAdapter;

import java.text.ParseException;
import java.util.ArrayList;
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

    public ir_in_sec_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
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
    public ir_in_sec_CompletedgridAdapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_in_sec_completedgrid_table_cell, viewGroup, false);
            return new ir_in_sec_CompletedgridAdapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_in_sec_completedgrid_card_item,
                    viewGroup, false);
            return new ir_in_sec_CompletedgridAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ir_in_sec_CompletedgridAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        holder.extramaterials.setText(club.getExtramaterials());
        holder.reoprtingre.setText(club.getReportingRemark());
        holder.remark.setText(club.getRemark());
        holder.oapo.setText(club.getOA_PO_number());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.Selectregister.setText(club.getSelectregister());
        holder.IrCopy.setText(club.getIrCopy());
        holder.DeliveryBill.setText(club.getDeliveryBill());
        holder.TaxInvoice.setText(club.getTaxInvoice());
        holder.EwayBill.setText(club.getEwayBill());
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
            extramaterials =view.findViewById(R.id.irinsectextcoextramaterials);
            remark =view.findViewById(R.id.irinsectextcoremark);
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
