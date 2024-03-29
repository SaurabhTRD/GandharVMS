package com.android.gandharvms.InwardCompletedGrid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class gridadaptercompleted extends RecyclerView.Adapter<gridadaptercompleted.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public gridadaptercompleted(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
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
    public gridadaptercompleted.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inward_dept_completed_table_cell, viewGroup, false);
            return new gridadaptercompleted.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inward_dept_completed_card_item,
                    viewGroup, false);
            return new gridadaptercompleted.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(gridadaptercompleted.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getInTime()!=null ? club.getInTime().length() : 0;
        int outtimelength = club.getOutTime()!=null ? club.getOutTime().length() : 0;
        if(nextProcess=='S')
        {
            bindSColumns(holder, club, intimelength, outtimelength);
        } else if (nextProcess=='W') {
            bindWColumns(holder, club, intimelength, outtimelength);
        }
        else if (nextProcess=='M') {
            bindMColumns(holder, club, intimelength, outtimelength);
        }
        else if (nextProcess=='L') {
            bindLColumns(holder, club, intimelength, outtimelength);
        }
        else if (nextProcess=='P') {
            bindPColumns(holder, club, intimelength, outtimelength);
        }
        else if (nextProcess=='R') {
            bindRColumns(holder, club, intimelength, outtimelength);
        }
    }

    private void bindSColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.partyname.setText(club.getPartyName());
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(String.valueOf(club.getQtyUOM()));
        holder.netweight.setText(String.valueOf(club.getNetWeight()));
        holder.netweightuom.setText(String.valueOf(club.getNetWeightUOM()));
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

    private void bindWColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        holder.partyname.setText(club.getPartyName());
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.oapo.setText(club.getOA_PO_number());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.grossweight.setText(String.valueOf(club.getGrossWeight()));
        holder.remark.setText(club.getRemark());
        holder.containerno.setText(String.valueOf(club.getContainerNo()));
        holder.sighby.setText(club.getSignBy());
        holder.shortagedip.setText(club.getShortageDip());
        holder.shortageweight.setText(club.getShortageWeight());
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
    }

    private void bindMColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.vehiclenum.setText(club.getVehicleNo());
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
    }

    private void bindLColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        holder.partyname.setText(club.getPartyName());
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.Apperance.setText(club.getApperance());
        holder.Odor.setText(club.getOdor());
        holder.Color.setText(club.getColor());
        holder.LQty.setText(String.valueOf(club.getLQty()));
        holder.Density.setText(String.valueOf(club.getDensity()));
        holder.RcsTest.setText(club.getRcsTest());
        holder.AnLinePoint.setText(String.valueOf(club.getAnLinePoint()));
        holder.FlashPoint.setText(String.valueOf(club.getFlashPoint()));
        holder._40KV.setText(String.valueOf(club.get_40KV()));
        holder._100KV.setText(String.valueOf(club.get_100KV()));
        holder.AdditionalTest.setText(club.getAdditionalTest());
        holder.SampleTest.setText(club.getSampleTest());
        holder.SignOf.setText(club.getSignBy());
        holder.DateAndTime.setText(club.getDateAndTime());
        holder.RemarkDescription.setText(club.getRemarkDescription());
        holder.ViscosityIndex.setText(String.valueOf(club.getViscosityIndex()));
    }
    private void bindPColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.UnloadAboveMaterialInTK.setText(String.valueOf(club.getUnloadAboveMaterialInTK()));
        holder.ProductName.setText(club.getProductName());
        holder.AboveMaterialIsUnloadInTK.setText(String.valueOf(club.getAboveMaterialIsUnloadInTK()));
        holder.OperatorName.setText(club.getOperatorName());
    }
    private void bindRColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.partyname.setText(club.getPartyName());
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(String.valueOf(club.getQtyUOM()));
        holder.ReceiveQTY.setText(String.valueOf(club.getReceiveQTY()));
        holder.ReceiveQTYUOM.setText(String.valueOf(club.getReceiveQTYUOM()));
        holder.StoreExtramaterials.setText(club.getStoreExtramaterials());
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
        TextView sernum, vehiclenum, material, intime, outtime,date,partyname,qty,netweight, reoprtingre,
                 qtyuom,netweightuom,extramaterials,remark,oapo,mob,Selectregister,IrCopy,DeliveryBill,TaxInvoice,
                 EwayBill,
                grossweight,batchnumber,containerno, sighby,shortagedip,shortageweight,

                Apperance,Odor,Color,LQty,Density,RcsTest,AnLinePoint,FlashPoint,_40KV,_100KV,
                AdditionalTest,SampleTest,SignOf,DateAndTime,RemarkDescription,ViscosityIndex,

                UnloadAboveMaterialInTK,ProductName,AboveMaterialIsUnloadInTK,OperatorName,
                ReceiveQTY,ReceiveQTYUOM,StoreExtramaterials;
        ImageView invehicleimage,indriverimage;

        public myviewHolder(View view) {
            super(view);
            sernum = view.findViewById(R.id.textcoSerialNumber);
            vehiclenum = view.findViewById(R.id.textcoVehicleNumber);
            material = view.findViewById(R.id.textcoMaterial);
            intime =view.findViewById(R.id.textcoInTime);
            outtime=view.findViewById(R.id.textcoOutTime);
            date=view.findViewById(R.id.textcodate);
            partyname=view.findViewById(R.id.textcopartyname);
            qty=view.findViewById(R.id.textcoqty);
            netweight=view.findViewById(R.id.textconetweight);
            reoprtingre=view.findViewById(R.id.textcoreoprtingre);
            qtyuom=view.findViewById(R.id.textcoqtyuom);
            netweightuom=view.findViewById(R.id.textconetweightuom);
            extramaterials=view.findViewById(R.id.textcoextramaterials);
            remark=view.findViewById(R.id.textcoremark);
            oapo=view.findViewById(R.id.textcooapo);
            mob=view.findViewById(R.id.textcomob);
            grossweight=view.findViewById(R.id.textcogrossweight);
            batchnumber=view.findViewById(R.id.textcobatchnumber);
            containerno=view.findViewById(R.id.textcocontainerno);
            invehicleimage=view.findViewById(R.id.textInVehicleImage);
            indriverimage=view.findViewById(R.id.textInDriverImage);
            Selectregister=view.findViewById(R.id.textcoSelectregister);
            IrCopy=view.findViewById(R.id.textcoIrCopy);
            DeliveryBill=view.findViewById(R.id.textcoDeliveryBill);
            TaxInvoice=view.findViewById(R.id.textcoTaxInvoice);
            EwayBill=view.findViewById(R.id.textcoEwayBill);
            sighby=view.findViewById(R.id.textcosighby);
            shortagedip=view.findViewById(R.id.textcoshortagedip);
            shortageweight=view.findViewById(R.id.textcoshortageweight);
            Apperance=view.findViewById(R.id.textcoApperance);
            Odor=view.findViewById(R.id.textcoOdor);
            Color=view.findViewById(R.id.textcoColor);
            LQty=view.findViewById(R.id.textcoLQty);
            Density=view.findViewById(R.id.textcoDensity);
            RcsTest=view.findViewById(R.id.textcoRcsTest);
            AnLinePoint=view.findViewById(R.id.textcoAnLinePoint);
            FlashPoint=view.findViewById(R.id.textcoFlashPoint);
            _40KV=view.findViewById(R.id.textco_40KV);
            _100KV=view.findViewById(R.id.textco_100KV);
            AdditionalTest=view.findViewById(R.id.textcoAdditionalTest);
            SampleTest=view.findViewById(R.id.textcoSampleTest);
            SignOf=view.findViewById(R.id.textcoSignOf);
            DateAndTime=view.findViewById(R.id.textcoDateAndTime);
            RemarkDescription=view.findViewById(R.id.textcoRemarkDescription);
            ViscosityIndex=view.findViewById(R.id.textcoViscosityIndex);
            UnloadAboveMaterialInTK=view.findViewById(R.id.textcoUnloadAboveMaterialInTK);
            ProductName=view.findViewById(R.id.textcoProductName);
            AboveMaterialIsUnloadInTK=view.findViewById(R.id.textcoAboveMaterialIsUnloadInTK);
            OperatorName=view.findViewById(R.id.textcoOperatorName);
            ReceiveQTY=view.findViewById(R.id.textcoReceiveQTY);
            ReceiveQTYUOM=view.findViewById(R.id.textcoReceiveQTYUOM);
            StoreExtramaterials=view.findViewById(R.id.textcoStoreExtramaterials);
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
            // Handle the ParseException or return the original inputDate if parsing fails
            return inputDate;
        }
    }
}
