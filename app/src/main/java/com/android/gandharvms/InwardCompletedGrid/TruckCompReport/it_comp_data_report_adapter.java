package com.android.gandharvms.InwardCompletedGrid.TruckCompReport;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.gridadaptercompleted;
import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial_Adapter;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class it_comp_data_report_adapter extends RecyclerView.Adapter<it_comp_data_report_adapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public it_comp_data_report_adapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
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
    public it_comp_data_report_adapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_comp_data_report_tablecell, viewGroup, false);
            return new it_comp_data_report_adapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ir_comp_data_report_carditem,
                    viewGroup, false);
            return new it_comp_data_report_adapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(it_comp_data_report_adapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        String secintimelength = club.getSecIntime()!=null ? club.getSecIntime() : "";
        String secouttimelength = club.getSecOuttime()!=null ? club.getSecOuttime(): "";
        holder.secIntime.setText(secintimelength);
        holder.secOuttime.setText(secouttimelength);
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.invoiceno.setText(club.getInvoiceNo());
        //holder.material.setText(club.getMaterial());
        holder.material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_InwardTanker(view, club.getExtramaterials());
            }
        });
        holder.partyname.setText(club.getPartyName());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.oapo.setText(club.getOA_PO_number());
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(club.getUnitOfQTY());
        holder.netweight.setText(String.valueOf(club.getNetWeight()));
        holder.netweightuom.setText(club.getUnitOfNetWeight());
        //holder.extramaterials.setText(club.getExtramaterials());
        holder.secremark.setText(club.getSecRemark());
        holder.IrCopy.setText(club.getIrCopy());
        holder.DeliveryBill.setText(club.getDeliveryBill());
        holder.TaxInvoice.setText(club.getTaxInvoice());
        holder.EwayBill.setText(club.getEwayBill());
        holder.selectregister.setText(club.getSelectregister());

        String weiintime = club.getWeiIntime()!=null ? club.getWeiIntime() : "";
        String weiouttime = club.getWeiOuttime()!=null ? club.getWeiOuttime(): "";
        holder.weiIntime.setText(weiintime);
        holder.weiOuttime.setText(weiouttime);
        holder.grossweight.setText(String.valueOf(club.getGrossWeight()));
        holder.containerno.setText(String.valueOf(club.getContainerNo()));
        holder.weiremark.setText(club.getWeiRemark());
        holder.sighby.setText(club.getSignBy());
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getInVehicleImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getInDriverImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.indriverimage);

        String storeIntime = club.getStoreIntime()!=null ? club.getStoreIntime() : "";
        String storeouttime = club.getStoreOuttime()!=null ? club.getStoreOuttime(): "";
        holder.storeintime.setText(storeIntime);
        holder.storeouttime.setText(storeouttime);
        holder.matreialdate.setText(formattedDate);
        holder.oapodate.setText(formattedDate);
        holder.invoicedate.setText(formattedDate);
        String recqty=String.format("%.4f", club.getReceiveQTY() / 100.0);
        //holder.ReceiveQTY.setText(recqty);
        //holder.ReQTYUom.setText(club.getReQTYUom());
        //holder.StoreExtramaterials.setText(club.getStoreExtramaterials());
        holder.StoreExtramaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog(view, club.getStoreExtramaterials());
            }
        });
        holder.storeremark.setText(club.getStorRemark());

        String outweiIntime = club.getOutWeiInTime()!=null ? club.getOutWeiInTime() : "";
        String outweiouttime = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime(): "";
        holder.outWeiInTime.setText(outweiIntime);
        holder.outWeiOutTime.setText(outweiouttime);
        holder.tareweight.setText(club.getTareWeight());
        holder.weinetWeight.setText(club.getWeiNetWeight());
        holder.shortagedip.setText(club.getShortageDip());
        holder.shortageweight.setText(club.getShortageWeight());
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getOutVehicleImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.outvehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getOutDriverImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120,120)
                .centerCrop().into(holder.outdriverimage);

        String outsecIntime = club.getOutSecInTime()!=null ? club.getOutSecInTime() : "";
        String outsecouttime = club.getOutSecOutTime()!=null ? club.getOutSecOutTime(): "";
        holder.outWeiInTime.setText(outsecIntime);
        holder.outWeiOutTime.setText(outsecouttime);
        holder.verIrCopy.setText(club.getIrCopy());
        holder.verDeliveryBill.setText(club.getDeliveryBill());
        holder.verTaxInvoice.setText(club.getTaxInvoice());
        holder.verEwayBill.setText(club.getEwayBill());
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
        TextView secIntime,secOuttime,date,sernum, vehiclenum, invoiceno,material,partyname,mob,oapo,qty,qtyuom,
                netweight,netweightuom,secremark,IrCopy,DeliveryBill,TaxInvoice,EwayBill,selectregister,
                weiIntime,weiOuttime,grossweight,containerno,weiremark, sighby;
        ImageView invehicleimage,indriverimage;

        TextView storeintime,storeouttime,matreialdate,oapodate,invoicedate,StoreExtramaterials,
                storeremark,
                outWeiInTime,outWeiOutTime,tareweight,weinetWeight,shortagedip,shortageweight;
        ImageView outvehicleimage,outdriverimage;

        TextView outSecInTime,outSecOutTime,verIrCopy,verDeliveryBill,verTaxInvoice,verEwayBill;
        public myviewHolder(View view) {
            super(view);
            secIntime=view.findViewById(R.id.textcoirreportSecInTime);
            secOuttime=view.findViewById(R.id.textcoirreportSecOuttime);
            date=view.findViewById(R.id.textcoirreportdate);
            sernum = view.findViewById(R.id.textcoirreportSerialNumber);
            vehiclenum = view.findViewById(R.id.textcoirreportVehicleNumber);
            invoiceno=view.findViewById(R.id.textcoirreportInvoiceNo);
            material = view.findViewById(R.id.textcoirreportMaterial);
            material.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            partyname=view.findViewById(R.id.textcoirreportpartyname);
            mob=view.findViewById(R.id.textcoirreportmob);
            oapo=view.findViewById(R.id.textcoirreportoapo);
            qty=view.findViewById(R.id.textcoirreportqty);
            qtyuom=view.findViewById(R.id.textcoirreportqtyuom);
            netweight=view.findViewById(R.id.textcoirreportnetweight);
            netweightuom=view.findViewById(R.id.textcoirreportnetweightuom);
            //extramaterials=view.findViewById(R.id.textcoirreportextramaterials);
            secremark=view.findViewById(R.id.textcoirreportsecremark);
            IrCopy=view.findViewById(R.id.textcoirreportIrCopy);
            DeliveryBill=view.findViewById(R.id.textcoirreportDeliveryBill);
            TaxInvoice=view.findViewById(R.id.textcoirreportTaxInvoice);
            EwayBill=view.findViewById(R.id.textcoirreportEwayBill);
            selectregister=view.findViewById(R.id.textcoirreportselectRegister);

            weiIntime=view.findViewById(R.id.textcoirreportWeiIntime);
            weiOuttime=view.findViewById(R.id.textcoirreportWeiOuttime);
            grossweight=view.findViewById(R.id.textcoirreportgrossweight);
            containerno=view.findViewById(R.id.textcoirreportcontainerno);
            weiremark=view.findViewById(R.id.textcoirreportweiremark);
            sighby=view.findViewById(R.id.textcoirreportsighby);
            invehicleimage=view.findViewById(R.id.textcoirreportInVehicleImage);
            indriverimage=view.findViewById(R.id.textcoirreportInDriverImage);

            storeintime=view.findViewById(R.id.textcoirreportstoreIntime);
            storeouttime=view.findViewById(R.id.textcoirreportstoreOuttime);
            matreialdate=view.findViewById(R.id.textcoirreportMaRedate);
            oapodate=view.findViewById(R.id.textcoirreportOAPOdate);
            invoicedate=view.findViewById(R.id.textcoirreportinvdate);
            //ReceiveQTY=view.findViewById(R.id.textcoirreportReceiveQTY);
            //ReQTYUom=view.findViewById(R.id.textcoirreportReceiveQTYUOM);
            StoreExtramaterials=view.findViewById(R.id.textcoirreportStoreExtramaterials);
            StoreExtramaterials.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            storeremark=view.findViewById(R.id.textcoirreportstoreremark);

            outWeiInTime=view.findViewById(R.id.textcoirreportOutWeiInTime);
            outWeiOutTime=view.findViewById(R.id.textcoirreportOutWeiOutTime);
            tareweight=view.findViewById(R.id.textcoirreporttareweight);
            weinetWeight=view.findViewById(R.id.textcoirreportweinetWeight);
            shortagedip=view.findViewById(R.id.textcoirreportshortagedip);
            shortageweight=view.findViewById(R.id.textcoirreportshortageweight);
            outvehicleimage=view.findViewById(R.id.textcoirreportOutVehicleImage);
            outdriverimage=view.findViewById(R.id.textcoirreportOutDriverImage);

            outSecInTime=view.findViewById(R.id.textcoirreportOutSecInTime);
            outSecOutTime=view.findViewById(R.id.textcoirreportOutSecOutTime);
            verIrCopy=view.findViewById(R.id.textcoirreportverIrCopy);
            verDeliveryBill=view.findViewById(R.id.textcoirreportverDeliveryBill);
            verTaxInvoice=view.findViewById(R.id.textcoirreportverTaxInvoice);
            verEwayBill=view.findViewById(R.id.textcoirreportverEwayBill);
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

    private void showMaterialDialog(View view, String jsonMaterials) {
        // Parse the JSON list of extra materials
        List<ExtraMaterial> extramaterialList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonMaterials);
            for (int i = 0; i < jsonArray.length(); i++) {
//                materialList.add(jsonArray.getString(i));
                JSONObject materialObject = jsonArray.getJSONObject(i);
                String Material = materialObject.getString("Material");
                String Qty = materialObject.getString("Qty");
                String Qtyuom = materialObject.getString("Qtyuom");
                String Receivingqty= materialObject.getString("recivingqty");
                String recqtyUOM = materialObject.getString("recQtyUOM");
                extramaterialList.add(new ExtraMaterial(Material, Qty, Qtyuom,Receivingqty,recqtyUOM));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a dialog to show the list of materials
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
        builder.setTitle("Extra Materials");

        // Inflate the layout with a RecyclerView
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.material_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDialog);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ExtraMaterial_Adapter adapter = new ExtraMaterial_Adapter(extramaterialList); // Pass the material list
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
