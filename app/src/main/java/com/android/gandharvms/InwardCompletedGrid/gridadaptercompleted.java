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
        String secintimelength = club.getSecIntime()!=null ? club.getSecIntime() : "";
        String secouttimelength = club.getSecOuttime()!=null ? club.getSecOuttime(): "";
        holder.secIntime.setText(secintimelength);
        holder.secOuttime.setText(secouttimelength);
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.invoiceno.setText(club.getInvoiceNo());
        holder.material.setText(club.getMaterial());
        holder.netweight.setText(String.valueOf(club.getNetWeight()));
        holder.netweightuom.setText(club.getUnitOfNetWeight());
        holder.extramaterials.setText(club.getExtramaterials());
        holder.secremark.setText(club.getSecRemark());

        String weiintime = club.getWeiIntime()!=null ? club.getWeiIntime() : "";
        String weiouttime = club.getWeiOuttime()!=null ? club.getWeiOuttime(): "";
        holder.weiIntime.setText(weiintime);
        holder.weiOuttime.setText(weiouttime);
        holder.partyname.setText(club.getPartyName());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.oapo.setText(club.getOA_PO_number());
        holder.qty.setText(String.valueOf(club.getWeighQty()));
        holder.qtyuom.setText(club.getWeiQTYUOM());
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

        String samintime = club.getSamIntime()!=null ? club.getSamIntime() : "";
        String samouttime = club.getSamOuttime()!=null ? club.getSamOuttime(): "";
        holder.samIntime.setText(samintime);
        holder.samOuttime.setText(samouttime);

        String labintime = club.getLabIntime()!=null ? club.getLabIntime() : "";
        String labouttime = club.getLabOuttime()!=null ? club.getLabOuttime(): "";
        holder.labIntime.setText(labintime);
        holder.labOuttime.setText(labouttime);
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

        String prointime = club.getProIntime()!=null ? club.getProIntime() : "";
        String proouttime = club.getProOuttime()!=null ? club.getProOuttime(): "";
        holder.proIntime.setText(prointime);
        holder.proOuttime.setText(proouttime);
        holder.UnloadAboveMaterialInTK.setText(String.valueOf(club.getUnloadAboveMaterialInTK()));
        holder.ProductName.setText(club.getProductName());
        holder.AboveMaterialIsUnloadInTK.setText(String.valueOf(club.getAboveMaterialIsUnloadInTK()));
        holder.OperatorName.setText(club.getOperatorName());

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
        holder.IrCopy.setText(club.getIrCopy());
        holder.DeliveryBill.setText(club.getDeliveryBill());
        holder.TaxInvoice.setText(club.getTaxInvoice());
        holder.EwayBill.setText(club.getEwayBill());

        //holder.remark.setText(club.getRemark());
        //holder.reoprtingre.setText(club.getReportingRemark());
        //holder.remark.setText(club.getRemark());
        //holder.Selectregister.setText(club.getSelectregister());
    }


    private void bindRColumns(gridadaptercompleted.myviewHolder holder, CommonResponseModelForAllDepartment club, int intimelength, int outtimelength) {
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.material.setText(club.getMaterial());
        if (intimelength > 0) {
            //holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            //holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.partyname.setText(club.getPartyName());
        holder.qty.setText(String.valueOf(club.getQty()));
        holder.qtyuom.setText(String.valueOf(club.getQtyUOM()));
        //holder.ReceiveQTY.setText(String.valueOf(club.getReceiveQTY()));
        //holder.ReceiveQTYUOM.setText(String.valueOf(club.getReceiveQTYUOM()));
        //holder.StoreExtramaterials.setText(club.getStoreExtramaterials());
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
        TextView secIntime,secOuttime,date,sernum, vehiclenum, invoiceno,material, netweight,netweightuom,
                 extramaterials,secremark,
                 weiIntime,weiOuttime,partyname,mob,oapo,qty,qtyuom,grossweight,containerno,weiremark, sighby;
        ImageView invehicleimage,indriverimage;
        TextView samIntime,samOuttime,labIntime,labOuttime,
                Apperance,Odor,Color,LQty,Density,RcsTest,AnLinePoint,FlashPoint,_40KV,_100KV,
                AdditionalTest,SampleTest,SignOf,DateAndTime,RemarkDescription,ViscosityIndex,LabRemark,

                proIntime,proOuttime,UnloadAboveMaterialInTK,ProductName,AboveMaterialIsUnloadInTK,OperatorName,
                outWeiInTime,outWeiOutTime,tareweight,weinetWeight,shortagedip,shortageweight;
        ImageView outvehicleimage,outdriverimage;

        TextView outSecInTime,outSecOutTime,IrCopy,DeliveryBill,TaxInvoice,EwayBill;
        public myviewHolder(View view) {
            super(view);
            secIntime=view.findViewById(R.id.textcoitreportSecIntime);
            secOuttime=view.findViewById(R.id.textcoitreportSecOuttime);
            date=view.findViewById(R.id.textcoitreportdate);
            sernum = view.findViewById(R.id.textcoitreportSerialNumber);
            vehiclenum = view.findViewById(R.id.textcoitreportVehicleNumber);
            invoiceno=view.findViewById(R.id.textcoitreportInvoiceNo);
            material = view.findViewById(R.id.textcoitreportMaterial);
            netweight=view.findViewById(R.id.textcoitreportnetweight);
            netweightuom=view.findViewById(R.id.textcoitreportnetweightuom);
            extramaterials=view.findViewById(R.id.textcoitreportextramaterials);
            secremark=view.findViewById(R.id.textcoitreportsecremark);

            weiIntime=view.findViewById(R.id.textcoitreportWeiIntime);
            weiOuttime=view.findViewById(R.id.textcoitreportWeiOuttime);
            partyname=view.findViewById(R.id.textcoitreportpartyname);
            mob=view.findViewById(R.id.textcoitreportmob);
            oapo=view.findViewById(R.id.textcoitreportoapo);
            qty=view.findViewById(R.id.textcoitreportqty);
            qtyuom=view.findViewById(R.id.textcoitreportqtyuom);
            grossweight=view.findViewById(R.id.textcoitreportgrossweight);
            containerno=view.findViewById(R.id.textcoitreportcontainerno);
            weiremark=view.findViewById(R.id.textcoitreportweiremark);
            sighby=view.findViewById(R.id.textcoitreportsighby);
            invehicleimage=view.findViewById(R.id.textitreportInVehicleImage);
            indriverimage=view.findViewById(R.id.textitreportInDriverImage);

            samIntime=view.findViewById(R.id.textcoitreportSamIntime);
            samOuttime=view.findViewById(R.id.textcoitreportSamOuttime);
            labIntime=view.findViewById(R.id.textcoitreportLabIntime);
            labOuttime=view.findViewById(R.id.textcoitreportLabOuttime);
            Apperance=view.findViewById(R.id.textcoitreportApperance);
            Odor=view.findViewById(R.id.textcoitreportOdor);
            Color=view.findViewById(R.id.textcoitreportColor);
            LQty=view.findViewById(R.id.textcoitreportLQty);
            Density=view.findViewById(R.id.textcoitreportDensity);
            RcsTest=view.findViewById(R.id.textcoitreportRcsTest);
            AnLinePoint=view.findViewById(R.id.textcoitreportAnLinePoint);
            FlashPoint=view.findViewById(R.id.textcoitreportFlashPoint);
            _40KV=view.findViewById(R.id.textcoitreport_40KV);
            _100KV=view.findViewById(R.id.textcoitreport_100KV);
            AdditionalTest=view.findViewById(R.id.textcoitreportAdditionalTest);
            SampleTest=view.findViewById(R.id.textcoitreportSampleTest);
            SignOf=view.findViewById(R.id.textcoitreportSignOf);
            DateAndTime=view.findViewById(R.id.textcoitreportDateAndTime);
            RemarkDescription=view.findViewById(R.id.textcoitreportRemarkDescription);
            ViscosityIndex=view.findViewById(R.id.textcoitreportViscosityIndex);
            LabRemark=view.findViewById(R.id.textcoitreportlabRemark);

            proIntime=view.findViewById(R.id.textcoitreportProIntime);
            proOuttime=view.findViewById(R.id.textcoitreportProOuttime);
            UnloadAboveMaterialInTK=view.findViewById(R.id.textcoitreportUnloadAboveMaterialInTK);
            ProductName=view.findViewById(R.id.textcoitreportProductName);
            AboveMaterialIsUnloadInTK=view.findViewById(R.id.textcoitreportAboveMaterialIsUnloadInTK);
            OperatorName=view.findViewById(R.id.textcoitreportOperatorName);

            outWeiInTime=view.findViewById(R.id.textcoitreportOutWeiInTime);
            outWeiOutTime=view.findViewById(R.id.textcoitreportOutWeiOutTime);
            tareweight=view.findViewById(R.id.textcoitreporttareweight);
            weinetWeight=view.findViewById(R.id.textcoitreportweinetWeight);
            shortagedip=view.findViewById(R.id.textcoitreportshortagedip);
            shortageweight=view.findViewById(R.id.textcoitreportshortageweight);
            outvehicleimage=view.findViewById(R.id.textitreportOutVehicleImage);
            outdriverimage=view.findViewById(R.id.textitreportOutDriverImage);

            outSecInTime=view.findViewById(R.id.textcoitreportOutSecInTime);
            outSecOutTime=view.findViewById(R.id.textcoitreportOutSecOutTime);
            IrCopy=view.findViewById(R.id.textcoitreportIrCopy);
            DeliveryBill=view.findViewById(R.id.textcoitreportDeliveryBill);
            TaxInvoice=view.findViewById(R.id.textcoitreportTaxInvoice);
            EwayBill=view.findViewById(R.id.textcoitreportEwayBill);


            //batchnumber=view.findViewById(R.id.textcoitreportbatchnumber);
            //ReceiveQTY=view.findViewById(R.id.textcoitreportReceiveQTY);
            //ReceiveQTYUOM=view.findViewById(R.id.textcoitreportReceiveQTYUOM);
            //StoreExtramaterials=view.findViewById(R.id.textcoitreportStoreExtramaterials);
            //reoprtingre=view.findViewById(R.id.textcoitreportreoprtingre);
            //Selectregister=view.findViewById(R.id.textcoitreportSelectregister);
            /*<TextView
            android:layout_width="100dp"
            android:id="@+id/textcoitreportReceiveQTY"
            android:textStyle="bold"
            android:layout_marginStart="5dp"
            android:gravity="center|start"
            android:textColor="@android:color/black"
            android:layout_height="match_parent"/>
    <TextView
            android:layout_width="100dp"
            android:id="@+id/textcoitreportReceiveQTYUOM"
            android:textStyle="bold"
            android:layout_marginStart="5dp"
            android:gravity="center|start"
            android:textColor="@android:color/black"
            android:layout_height="match_parent"/>
    <TextView
            android:layout_width="100dp"
            android:id="@+id/textcoitreportStoreExtramaterials"
            android:textStyle="bold"
            android:layout_marginStart="5dp"
            android:gravity="center|start"
            android:textColor="@android:color/black"
            android:layout_height="match_parent"/>*/

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
