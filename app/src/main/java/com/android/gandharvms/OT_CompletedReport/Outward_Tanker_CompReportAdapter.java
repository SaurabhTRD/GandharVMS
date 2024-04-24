package com.android.gandharvms.OT_CompletedReport;

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
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.OutwardOut_Tanker_Security.Adapter_OT_complete_OutSecurity;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Outward_Tanker_CompReportAdapter extends RecyclerView.Adapter<Outward_Tanker_CompReportAdapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;

    public Outward_Tanker_CompReportAdapter(List<Common_Outward_model> gridmodel) {
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
    public Outward_Tanker_CompReportAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_tanker_compreport_carditem,viewGroup,false);
            return new Outward_Tanker_CompReportAdapter.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_tanker_compreport_tablecell,
                    viewGroup, false);
            return new Outward_Tanker_CompReportAdapter.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Outward_Tanker_CompReportAdapter.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        String secintime = club.getSecInTime()!= null ? club.getSecInTime() :"";
        String secouttime = club.getSecOutTime()!=null ? club.getSecOutTime() : "";
        holder.secIntime.setText(secintime);
        holder.secOuttime.setText(secouttime);
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.serialnumber.setText(club.getSerialNumber());
        holder.vehiclenumber.setText(club.getVehicleNumber());
        holder.kl.setText(String.valueOf(club.getKl()));
        holder.Transporter.setText(club.getTransportName());
        holder.Place.setText(club.getPlace());
        holder.mobilenumber.setText(club.getMobileNumber());
        holder.secRemark.setText(club.getSecInRemark());
        holder.vehiclepermit.setText(club.getVehiclePermit());
        holder.puc.setText(club.getPuc());
        holder.insurance.setText(club.getInsurance());
        holder.vehfitcert.setText(club.getVehicleFitnessCertificate());
        holder.driLicen.setText(club.getDriverLicenses());
        holder.rcbook.setText(club.getRcBook());
        holder.bilIntime.setText(club.getBilInTime());
        holder.bilOutTime.setText(club.getBilOutTime());
        holder.OAPOno.setText(club.getOAnumber());
        holder.custname.setText(club.getCustomerName());
        holder.proname.setText(club.getProductName());
        holder.howQty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.HowQtyUOM.setText(club.getHowMuchQTYUOM());
        holder.location.setText(club.getLocation());
        holder.bilremark.setText(club.getBillingInRemark());
        holder.weiInTime.setText(club.getWeiInTime());
        holder.weiOutTime.setText(club.getWeiOutTime());
        holder.tareweight.setText(club.getTareWeight());
        holder.weiremark.setText(club.getWeighmentInRemark());
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
        holder.ipprointime.setText(club.getIPFProInTime());
        holder.ipproouttime.setText(club.getIPFProOutTime());
        holder.packingstatus.setText(club.getPackingStatus());
        holder.risingstatus.setText(club.getRinsingStatus());
        holder.blendingmaterialstatus.setText(club.getBlendingMaterialStatus());
        holder.signofpro.setText(club.getSignatureofOfficer());
        holder.ipproremark.setText(club.getInProProRemark());
        holder.iplabintime.setText(club.getIPFLabInTime());
        holder.iplabouttime.setText(club.getIPFLabOutTime());
        holder.apperance.setText(club.getApperance());
        holder.condition.setText(club.getSampleCondition());
        holder.sampledt.setText(club.getSample_receiving());
        holder.releasedt.setText(club.getSample_Release_Date());
        holder.color.setText(club.getColor());
        holder.odor.setText(club.getOdour());
        holder.density29.setText(String.valueOf(club.getDensity_29_5C()));
        holder.kv40.setText(String.valueOf(club.get_40KV()));
        holder.kv100.setText(String.valueOf(club.get_100KV()));
        holder.viscosity.setText(String.valueOf(club.getViscosity_Index()));
        holder.tbntan.setText(club.getTBN_TAN());
        holder.aniline.setText(String.valueOf(club.getAnline_Point()));
        holder.breakdown.setText(String.valueOf(club.getBreakdownvoltage_BDV()));
        holder.ddf90.setText(club.getDDF_90C());
        holder.watercontent.setText(club.getWaterContent());
        holder.interfacial.setText(club.getInterfacialTension());
        holder.flashpt.setText(club.getFlash_Point());
        holder.pourpt.setText(club.getPourPoint());
        holder.rcstest.setText(club.getRcs_Test());
        holder.iplabremark.setText(club.getInProLabRemark());
        holder.correction.setText(club.getCorrectionRequired());
        holder.resistivity.setText(club.getRestivity());
        holder.infrared.setText(club.getInfra_Red());
        holder.bkprointime.setText(club.getBLFProInTime());
        holder.bkproouttime.setText(club.getBLFProOutTime());
        holder.dispatchsign.setText(club.getPsign());
        holder.bkproremark.setText(club.getInBulkProRemark());
        holder.bktankorblenderno.setText(club.getTankOrBlenderNo());
        holder.bkqty.setText(club.getBulkPqty());
        holder.bklabintime.setText(club.getBLFLabInTime());
        holder.bklabouttime.setText(club.getBLFLabOutTime());
        holder.batchno.setText(club.getBatchNo());
        holder.bklabremark.setText(club.getInBulkLabRemark());
        holder.outWeiIntime.setText(club.getOutWeiInTime());
        holder.outWeiOutTime.setText(club.getOutWeiOutTime());
        holder.netweight.setText(club.getNetWeight());
        holder.grossweight.setText(club.getGrossWeight());
        holder.sealnumber.setText(club.getSealNumber());
        holder.numberofpack.setText(club.getNumberofPack());
        holder.outWeiRemark.setText(club.getOutWRemark());
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
        holder.dataentryIntime.setText(club.getOutDataEntryInTime());
        holder.dataentryOutTime.setText(club.getOutDataEntryOutTime());
        holder.datantryremark.setText(club.getDataEntryRemark());
        holder.outbilInTime.setText(club.getOutBilInTime());
        holder.outBilOutTime.setText(club.getOutBilOutTime());
        holder.outtotalqty.setText(club.getOutTotalQty());
        holder.outTotqtyUom.setText(club.getUnitofmeasurementname());
        holder.outInvNo.setText(club.getOutInvoiceNumber());
        holder.outsecInTime.setText(club.getOutSecInTime());
        holder.outsecOutTime.setText(club.getOutSecOutTime());
        holder.outsecSign.setText(club.getSignature());
        holder.outsecRemark.setText(club.getOutSRemark());
        holder.sectranslrcopy.setText(club.getTransportLRcopy());
        holder.tremcard.setText(club.getTremcard());
        holder.ewaybill.setText(club.getEwaybill());
        holder.testreport.setText(club.getTest_Report());
        holder.invoice.setText(club.getInvoice());
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

    public class myviewHolder extends RecyclerView.ViewHolder{
        public TextView secIntime,secOuttime,date,serialnumber,vehiclenumber,kl,Transporter,Place,mobilenumber,secRemark,
                        vehiclepermit,puc,insurance,vehfitcert,driLicen,rcbook,
                        bilIntime,bilOutTime,OAPOno,custname,proname,howQty,HowQtyUOM,location,bilremark,
                        weiInTime,weiOutTime,tareweight,weiremark;
              ImageView invehicleimage,indriverimage;
              TextView ipprointime,ipproouttime,packingstatus,risingstatus,blendingmaterialstatus,
                       signofpro,ipproremark,
                       iplabintime,iplabouttime,apperance,condition,sampledt,releasedt,color,odor,density29,kv40,kv100,viscosity,
                       tbntan,aniline,breakdown,ddf90,watercontent,interfacial,flashpt,pourpt,rcstest,iplabremark,
                       correction,resistivity,infrared,
                       bkprointime,bkproouttime,dispatchsign,bkproremark,bktankorblenderno,bkqty,
                       bklabintime,bklabouttime,batchno,bklabremark;
              TextView outWeiIntime,outWeiOutTime,netweight,grossweight,sealnumber,numberofpack,outWeiRemark;
              ImageView outvehicleimage,outdriverimage;
              TextView dataentryIntime,dataentryOutTime,datantryremark,
                       outbilInTime,outBilOutTime,outtotalqty,outTotqtyUom,outInvNo,
                       outsecInTime,outsecOutTime,outsecSign,outsecRemark,sectranslrcopy,tremcard,ewaybill,testreport,invoice;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            secIntime= itemView.findViewById(R.id.otsecInTime);
            secOuttime= itemView.findViewById(R.id.otsecOutTime);
            date= itemView.findViewById(R.id.otDate);
            serialnumber= itemView.findViewById(R.id.otserialnumber);
            vehiclenumber= itemView.findViewById(R.id.otvehiclenumber);
            kl= itemView.findViewById(R.id.otkl);
            Transporter= itemView.findViewById(R.id.ottransport);
            Place= itemView.findViewById(R.id.otplace);
            mobilenumber= itemView.findViewById(R.id.otdrivermobileno);
            secRemark= itemView.findViewById(R.id.otsecRemark);
            vehiclepermit= itemView.findViewById(R.id.otvehiclepermit);
            puc= itemView.findViewById(R.id.otpuc);
            insurance= itemView.findViewById(R.id.otinsurance);
            vehfitcert= itemView.findViewById(R.id.otvehfitcert);
            driLicen= itemView.findViewById(R.id.otdriLicen);
            rcbook= itemView.findViewById(R.id.otrcbook);
            bilIntime= itemView.findViewById(R.id.otbilIntime);
            bilOutTime= itemView.findViewById(R.id.otbilOutTime);
            OAPOno= itemView.findViewById(R.id.otOAPOno);
            custname= itemView.findViewById(R.id.otcustname);
            proname= itemView.findViewById(R.id.otproname);
            howQty= itemView.findViewById(R.id.othowQty);
            HowQtyUOM= itemView.findViewById(R.id.otHowQtyUOM);
            location= itemView.findViewById(R.id.otlocation);
            bilremark= itemView.findViewById(R.id.otbilremark);
            weiInTime= itemView.findViewById(R.id.otweiInTime);
            weiOutTime= itemView.findViewById(R.id.otweiOutTime);
            tareweight= itemView.findViewById(R.id.ottareweight);
            weiremark= itemView.findViewById(R.id.otweiremark);
            invehicleimage= itemView.findViewById(R.id.otinvehicleimage);
            indriverimage= itemView.findViewById(R.id.otindriverimage);
            ipprointime= itemView.findViewById(R.id.otipprointime);
            ipproouttime= itemView.findViewById(R.id.otipproouttime);
            packingstatus= itemView.findViewById(R.id.otpackingstatus);
            risingstatus= itemView.findViewById(R.id.otrisingstatus);
            blendingmaterialstatus= itemView.findViewById(R.id.otblendingmaterialstatus);
            signofpro= itemView.findViewById(R.id.otsignofpro);
            ipproremark= itemView.findViewById(R.id.otipproremark);
            iplabintime= itemView.findViewById(R.id.otiplabintime);
            iplabouttime= itemView.findViewById(R.id.otiplabouttime);
            apperance= itemView.findViewById(R.id.otapperance);
            condition= itemView.findViewById(R.id.otcondition);
            sampledt= itemView.findViewById(R.id.otsampledt);
            releasedt= itemView.findViewById(R.id.otreleasedt);
            color= itemView.findViewById(R.id.otcolor);
            odor= itemView.findViewById(R.id.otodor);
            density29= itemView.findViewById(R.id.otdensity29);
            kv40= itemView.findViewById(R.id.otkv40);
            kv100= itemView.findViewById(R.id.otkv100);
            viscosity= itemView.findViewById(R.id.otviscosity);
            tbntan= itemView.findViewById(R.id.ottbntan);
            aniline= itemView.findViewById(R.id.otaniline);
            breakdown= itemView.findViewById(R.id.otbreakdown);
            ddf90= itemView.findViewById(R.id.otddf90);
            watercontent= itemView.findViewById(R.id.otwatercontent);
            interfacial= itemView.findViewById(R.id.otinterfacial);
            flashpt= itemView.findViewById(R.id.otflashpt);
            pourpt= itemView.findViewById(R.id.otpourpt);
            rcstest= itemView.findViewById(R.id.otrcstest);
            iplabremark= itemView.findViewById(R.id.otiplabremark);
            correction= itemView.findViewById(R.id.otcorrection);
            resistivity= itemView.findViewById(R.id.otresistivity);
            infrared= itemView.findViewById(R.id.otinfrared);
            bkprointime= itemView.findViewById(R.id.otbkprointime);
            bkproouttime= itemView.findViewById(R.id.otbkproouttime);
            dispatchsign= itemView.findViewById(R.id.otdispatchsign);
            bkproremark= itemView.findViewById(R.id.otbkproremark);
            bktankorblenderno= itemView.findViewById(R.id.otbktankorblenderno);
            bkqty= itemView.findViewById(R.id.otbkqty);
            bklabintime= itemView.findViewById(R.id.otbklabintime);
            bklabouttime= itemView.findViewById(R.id.otbklabouttime);
            batchno= itemView.findViewById(R.id.otbatchno);
            bklabremark= itemView.findViewById(R.id.bklabremark);
            outWeiIntime= itemView.findViewById(R.id.otoutWeiIntime);
            outWeiOutTime= itemView.findViewById(R.id.otoutWeiOutTime);
            netweight= itemView.findViewById(R.id.otnetweight);
            grossweight= itemView.findViewById(R.id.otgrossweight);
            sealnumber= itemView.findViewById(R.id.otsealnumber);
            numberofpack= itemView.findViewById(R.id.otnumberofpack);
            outWeiRemark= itemView.findViewById(R.id.otOutWeiRemark);
            outvehicleimage= itemView.findViewById(R.id.otoutvehicleimage);
            outdriverimage= itemView.findViewById(R.id.otoutdriverimage);
            dataentryIntime= itemView.findViewById(R.id.otdataentryIntime);
            dataentryOutTime= itemView.findViewById(R.id.otdataentryOutTime);
            datantryremark= itemView.findViewById(R.id.otdatantryremark);
            outbilInTime= itemView.findViewById(R.id.otoutbilInTime);
            outBilOutTime= itemView.findViewById(R.id.otoutBilOutTime);
            outtotalqty= itemView.findViewById(R.id.otouttotalqty);
            outTotqtyUom= itemView.findViewById(R.id.otoutTotqtyUom);
            outInvNo= itemView.findViewById(R.id.otoutInvNo);
            outsecInTime= itemView.findViewById(R.id.otoutsecInTime);
            outsecOutTime= itemView.findViewById(R.id.otoutsecOutTime);
            outsecSign= itemView.findViewById(R.id.otoutsecSign);
            outsecRemark= itemView.findViewById(R.id.otoutsecRemark);
            sectranslrcopy= itemView.findViewById(R.id.otsectranslrcopy);
            tremcard= itemView.findViewById(R.id.ottremcard);
            ewaybill= itemView.findViewById(R.id.otewaybill);
            testreport= itemView.findViewById(R.id.ottestreport);
            invoice= itemView.findViewById(R.id.otinvoice);
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
