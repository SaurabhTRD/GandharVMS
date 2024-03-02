package com.android.gandharvms.Inward_Truck_Security;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class In_Truck_Security_Adapter extends RecyclerView.Adapter<In_Truck_Security_Adapter.myviewholder> {

    Context context;

    ArrayList<In_Truck_security_list> datalist;
    private final List<CommonResponseModelForAllDepartment> listingResponseInTankerSec;


    public In_Truck_Security_Adapter(List<CommonResponseModelForAllDepartment> listingResponseInTankerSec) {
        this.datalist = datalist;
        this.listingResponseInTankerSec = listingResponseInTankerSec;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_truck_secuirty_item,parent,false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        CommonResponseModelForAllDepartment data = listingResponseInTankerSec.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");

//        holder.etintime.setText(datalist.get(position).getIntime());
//        holder.etserialnumber.setText(datalist.get(position).getSerialnumber());
//        holder.etvehicalnumber.setText(datalist.get(position).getVehicalNumber());
//        holder.etsinvocieno.setText(datalist.get(position).getInvoicenumber());
//        holder.etsdate.setText(dateFormat.format(datalist.get(position).getDate().toDate()));
//        holder.etssupplier.setText(datalist.get(position).getSupplier());
//        holder.etsmaterial.setText(datalist.get(position).getMaterial());
//        holder.etsqty.setText(datalist.get(position).getQty());
//        holder.etsuom.setText(datalist.get(position).getUOM());
//        holder.etsnetwt.setText(datalist.get(position).getEtsnetweight());
//        holder.etsuom2.setText(datalist.get(position).getUOM2());
//        holder.outTime.setText(datalist.get(position).getOutTime());
//        holder.selectregister.setText(datalist.get(position).getSelectRegister());
//
//        holder.lrcopy.setText(datalist.get(position).getLrcopy());
//        holder.deliverybill.setText(datalist.get(position).getDeliverybill());
//        holder.taxinvoice.setText(datalist.get(position).getTaxinvoice());
//        holder.ewaybill.setText(datalist.get(position).getEwaybill());
//        holder.reoprtingre.setText(datalist.get(position).getReporting_Remark());
//
//        holder.mob.setText(datalist.get(position).getDriver_Mobile_Number());
//        holder.oapo.setText(datalist.get(position).getOA_PO_Number());

        holder.etintime.setText(data.getInTime());
        holder.etserialnumber.setText(data.getSerialNo());
        holder.etvehicalnumber.setText(data.getVehicleNo());
        holder.etsinvocieno.setText(data.getInvoiceNo());
        holder.etsdate.setText(data.getDate());
        holder.etssupplier.setText(data.getPartyName());
        holder.etsmaterial.setText(data.getMaterial());
        holder.etsqty.setText(String.valueOf(data.getQty()));
        holder.etsuom.setText(String.valueOf(data.getQtyUOM()));
        holder.etsnetwt.setText(String.valueOf(data.getNetWeight()));
        holder.etsuom2.setText(String.valueOf(data.getNetWeightUOM()));
        holder.outTime.setText(data.getOutTime());
        holder.selectregister.setText(data.getSelectregister());
        holder.lrcopy.setText(data.getIrCopy());
        holder.deliverybill.setText(data.getDeliveryBill());
        holder.taxinvoice.setText(data.getTaxInvoice());
        holder.reoprtingre.setText(data.getReportingRemark());
        holder.mob.setText(String.valueOf(data.getDriver_MobileNo()));
        holder.oapo.setText(data.getOA_PO_number());


    }


    @Override
    public int getItemCount() {
        return listingResponseInTankerSec.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView etintime,etserialnumber,etvehicalnumber,etsinvocieno,etsdate,etssupplier,etsmaterial,etsqty,etsuom,etsnetwt,etsuom2,outTime,selectregister,lrcopy, deliverybill, taxinvoice, ewaybill,reoprtingre,
        mob,oapo;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            etintime=itemView.findViewById(R.id.listintime);
            etserialnumber= itemView.findViewById(R.id.listserialnumber);
            etvehicalnumber= itemView.findViewById(R.id.listvehiclenumber);
            etsinvocieno= itemView.findViewById(R.id.listinvoiceno);
            etsdate=itemView.findViewById(R.id.listdate);
            etssupplier= itemView.findViewById(R.id.listpartyname);
            etsmaterial= itemView.findViewById(R.id.listmaterial);
            etsqty= itemView.findViewById(R.id.listqty);
            etsuom= itemView.findViewById(R.id.listuom);
            etsnetwt= itemView.findViewById(R.id.listnetweight);
            etsuom2= itemView.findViewById(R.id.listuom2);
            outTime=itemView.findViewById(R.id.listouttime);
            selectregister= itemView.findViewById(R.id.listregister);
            lrcopy=itemView.findViewById(R.id.listlrcopy);
            deliverybill=itemView.findViewById(R.id.listdelivery);
            taxinvoice=itemView.findViewById(R.id.listtaxinvoice);
            ewaybill=itemView.findViewById(R.id.listewaybill);
            reoprtingre = itemView.findViewById(R.id.listreportingremark);
            mob = itemView.findViewById(R.id.listdrivermobilenumber);
            oapo = itemView.findViewById(R.id.listoaponumber);






        }
    }
}
