package com.android.gandharvms.OR_CompletedReport;

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
import com.android.gandharvms.OT_CompletedReport.Outward_Tanker_CompReportAdapter;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Outward_Truck_CompletedReportAdapter extends RecyclerView.Adapter<Outward_Truck_CompletedReportAdapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Outward_Truck_CompletedReportAdapter(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_truck_compreport_carditem,viewGroup,false);
            return new Outward_Truck_CompletedReportAdapter.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_truck_compreport_tablecell,
                    viewGroup, false);
            return new Outward_Truck_CompletedReportAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        String secintime = club.getSecInTime()!= null ? club.getSecInTime():"";
        String secouttime = club.getSecOutTime()!=null ? club.getSecOutTime() : "";
        holder.secIntime.setText(secintime);
        holder.secOuttime.setText(secouttime);
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.serialnumber.setText(club.getSerialNumber());
        holder.vehiclenumber.setText(club.getVehicleNumber());
        holder.Transporter.setText(club.getTransportName());
        holder.translrcopy.setText(club.getTransportLRcopy());
        holder.custname.setText(club.getCustomerName());
        holder.Place.setText(club.getPlace());
        holder.mobilenumber.setText(club.getMobileNumber());
        holder.vehiclepermit.setText(club.getVehiclePermit());
        holder.puc.setText(club.getPuc());
        holder.insurance.setText(club.getInsurance());
        holder.vehfitcert.setText(club.getVehicleFitnessCertificate());
        holder.driLicen.setText(club.getDriverLicenses());
        holder.rcbook.setText(club.getRcBook());
        holder.secRemark.setText(club.getSecInRemark());
        String logintime = club.getLogInTime()!= null ? club.getLogInTime() :"";
        String logouttime = club.getLogOutTime()!=null ? club.getLogOutTime() : "";
        holder.logIntime.setText(logintime);
        holder.logOutTime.setText(logouttime);
        holder.OAPOno.setText(club.getOAnumber());
        holder.howQty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.HowQtyUOM.setText(club.getHowMuchQTYUOM());
        holder.logremark.setText(club.getLogisticRemark());
        String weiintime = club.getWeiInTime()!= null ? club.getWeiInTime() :"";
        String weiouttime = club.getWeiOutTime()!=null ? club.getWeiOutTime() : "";
        holder.weiInTime.setText(weiintime);
        holder.weiOutTime.setText(weiouttime);
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
        String ilintime = club.getIlInTime()!= null ? club.getIlInTime() :"";
        String ilouttime = club.getIlOutTime()!=null ? club.getIlOutTime() : "";
        holder.ilintime.setText(ilintime);
        holder.ilouttime.setText(ilouttime);
        holder.ilpackof10ltr.setText(String.valueOf(club.getIlpackof10ltrqty()));
        holder.ilpackof18ltr.setText(String.valueOf(club.getIlpackof18ltrqty()));
        holder.ilpackof20ltr.setText(String.valueOf(club.getIlpackof20ltrqty()));
        holder.ilpackof26ltr.setText(String.valueOf(club.getIlpackof26ltrqty()));
        holder.ilpackof50ltr.setText(String.valueOf(club.getIlpackof50ltrqty()));
        holder.ilpackof210ltr.setText(String.valueOf(club.getIlpackof210ltrqty()));
        holder.ilpackofboxbuxket.setText(String.valueOf(club.getIlpackofboxbuxketltrqty()));
        holder.iltotalqty.setText(club.getIltotqty());
        holder.ilweight.setText(club.getIlweight());
        holder.ilindustrialsign.setText(club.getIlsign());
        holder.ilnextdepartment.setText(club.getPurposeProcess());
        holder.ilremark.setText(club.getIlRemark());
        String splintime = club.getSplInTime()!= null ? club.getSplInTime() :"";
        String splouttime = club.getSplOutTime()!=null ? club.getSplOutTime() : "";
        holder.splintime.setText(splintime);
        holder.splouttime.setText(splouttime);
        holder.splpackof7ltr.setText(String.valueOf(club.getSplpackof7ltrqty()));
        holder.splpackof7_5ltr.setText(String.valueOf(club.getSplpackof7_5ltrqty()));
        holder.splpackof8_5ltr.setText(String.valueOf(club.getSplpackof8_5ltrqty()));
        holder.splpackof10ltr.setText(String.valueOf(club.getSplpackof10ltrqty()));
        holder.splpackof11ltr.setText(String.valueOf(club.getSplpackof11ltrqty()));
        holder.splpackof12ltr.setText(String.valueOf(club.getSplpackof12ltrqty()));
        holder.splpackof13ltr.setText(String.valueOf(club.getSplpackof13ltrqty()));
        holder.splpackof15ltr.setText(String.valueOf(club.getSplpackof15ltrqty()));
        holder.splpackof18ltr.setText(String.valueOf(club.getSplpackof18ltrqty()));
        holder.splpackof20ltr.setText(String.valueOf(club.getSplpackof20ltrqty()));
        holder.splpackof26ltr.setText(String.valueOf(club.getSplpackof26ltrqty()));
        holder.splpackof50ltr.setText(String.valueOf(club.getSplpackof50ltrqty()));
        holder.splpackof210ltr.setText(String.valueOf(club.getSplpackof210ltrqty()));
        holder.splpackofboxbuxket.setText(String.valueOf(club.getSplpackofboxbuxketltrqty()));
        holder.spltotalqty.setText(club.getSpltotqty());
        holder.splweight.setText(club.getSplweight());
        holder.splindustrialsign.setText(club.getSplsign());
        holder.splnextdepartment.setText(club.getPurposeProcess());
        holder.splremark.setText(club.getSplRemark());
        String outweiintime = club.getOutWeiInTime()!= null ? club.getOutWeiInTime() :"";
        String outweiouttime = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime(): "";
        holder.outWeiIntime.setText(outweiintime);
        holder.outWeiOutTime.setText(outweiouttime);
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
        String dataentryintime = club.getOutDataEntryInTime()!= null ? club.getOutDataEntryInTime() :"";
        String dataentryouttime = club.getOutDataEntryOutTime()!=null ? club.getOutDataEntryOutTime(): "";
        holder.dataentryIntime.setText(dataentryintime);
        holder.dataentryOutTime.setText(dataentryouttime);
        holder.datantryremark.setText(club.getDataEntryRemark());
        String outbilintime = club.getOutBilInTime()!= null ? club.getOutBilInTime() :"";
        String outbillouttime = club.getOutBilOutTime()!=null ? club.getOutBilOutTime() : "";
        holder.outbilInTime.setText(outbilintime);
        holder.outBilOutTime.setText(outbillouttime);
        holder.outbillremark.setText(club.getBillingOutRemark());
        String outsecintime = club.getOutSecInTime()!= null ? club.getOutSecInTime() :"";
        String outsecouttime = club.getOutSecOutTime()!=null ? club.getOutSecOutTime() : "";
        holder.outsecInTime.setText(outsecintime);
        holder.outsecOutTime.setText(outsecouttime);
        holder.tremcard.setText(club.getTremcard());
        holder.ewaybill.setText(club.getEwaybill());
        holder.testreport.setText(club.getTest_Report());
        holder.outsecinvoice.setText(club.getInvoice());
        holder.outsecSign.setText(club.getSignature());
        holder.outsecRemark.setText(club.getOutSRemark());
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder{
        public TextView
                date,serialnumber,vehiclenumber,secIntime,secOuttime,Transporter,custname,Place,mobilenumber,
                vehiclepermit,translrcopy,puc,insurance,vehfitcert,driLicen,rcbook,secRemark;

        TextView logIntime,logOutTime,OAPOno,howQty,HowQtyUOM,logremark;

        TextView  weiInTime,weiOutTime,tareweight,weiremark;
        ImageView invehicleimage,indriverimage;

        TextView ilintime,ilouttime,ilpackof10ltr,ilpackof18ltr,ilpackof20ltr,
                 ilpackof26ltr,ilpackof50ltr,ilpackof210ltr,ilpackofboxbuxket,iltotalqty,ilweight,ilindustrialsign,ilnextdepartment,ilremark;

        TextView splintime,splouttime,splpackof7ltr,splpackof7_5ltr,splpackof8_5ltr,splpackof10ltr,splpackof11ltr,
                 splpackof12ltr,splpackof13ltr,splpackof15ltr,splpackof18ltr,splpackof20ltr,splpackof26ltr,splpackof50ltr,
                 splpackof210ltr,splpackofboxbuxket,spltotalqty,splweight,splindustrialsign,splnextdepartment,splremark;

        TextView outWeiIntime,outWeiOutTime,netweight,grossweight,sealnumber,numberofpack,shweight,shdip,outWeiRemark;
        ImageView outvehicleimage,outdriverimage;

         TextView dataentryIntime,dataentryOutTime,datantryremark;

        TextView outbilInTime,outBilOutTime,outbillremark;

        TextView outsecInTime,outsecOutTime,sectranslrcopy,tremcard,ewaybill,testreport,outsecinvoice,outsecSign,outsecRemark;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.orcompvehrepdate);
            serialnumber= itemView.findViewById(R.id.orcompvehrepserialno);
            vehiclenumber= itemView.findViewById(R.id.orcompvehrepvehiclenum);
            secIntime= itemView.findViewById(R.id.orcompvehrepsecintime);
            secOuttime= itemView.findViewById(R.id.orcompvehrepsecouttime);
            Transporter= itemView.findViewById(R.id.orcompvehreptransporter);
            Place= itemView.findViewById(R.id.orcompvehrepplace);
            mobilenumber= itemView.findViewById(R.id.orcompvehrepmob);
            vehiclepermit= itemView.findViewById(R.id.orcompvehrepvehiclepermit);
            translrcopy= itemView.findViewById(R.id.orcompvehreptransportlrcopy);
            puc= itemView.findViewById(R.id.orcompvehreppuc);
            insurance= itemView.findViewById(R.id.orcompvehrepinsurance);
            vehfitcert= itemView.findViewById(R.id.orcompvehrepvehfitnessculture);
            driLicen= itemView.findViewById(R.id.orcompvehrepdriverlicenses);
            rcbook= itemView.findViewById(R.id.orcompvehreprcbook);
            secRemark= itemView.findViewById(R.id.orcompvehrepremark);
            logIntime= itemView.findViewById(R.id.orcompvehreplogintime);
            logOutTime= itemView.findViewById(R.id.orcompvehreplogouttime);
            OAPOno= itemView.findViewById(R.id.orcompvehrepoanum);
            custname= itemView.findViewById(R.id.orcompvehrepcustname);
            howQty= itemView.findViewById(R.id.orcompvehreploadedqty);
            HowQtyUOM= itemView.findViewById(R.id.orcompvehreplodedqtyuom);
            logremark= itemView.findViewById(R.id.orcompvehreplogremark);
            weiInTime= itemView.findViewById(R.id.orcompvehrepweintime);
            weiOutTime= itemView.findViewById(R.id.orcompvehrepweouttime);
            tareweight= itemView.findViewById(R.id.orcompvehrepweintare);
            weiremark= itemView.findViewById(R.id.orcompvehrepweinremark);
            invehicleimage= itemView.findViewById(R.id.orcompvehrepweinInDriverImage);
            indriverimage= itemView.findViewById(R.id.orcompvehrepweinInVehicleImage);
            ilintime = itemView.findViewById(R.id.orcompvehrepilintime);
            ilouttime = itemView.findViewById(R.id.orcompvehrepilouttime);
            ilpackof10ltr = itemView.findViewById(R.id.orcompvehrepil10ltr);
            ilpackof18ltr=itemView.findViewById(R.id.orcompvehrepil18ltr);
            ilpackof20ltr=itemView.findViewById(R.id.orcompvehrepil20ltr);
            ilpackof26ltr=itemView.findViewById(R.id.orcompvehrepil26ltr);
            ilpackof50ltr=itemView.findViewById(R.id.orcompvehrepil50ltr);
            ilpackof210ltr=itemView.findViewById(R.id.orcompvehrepil210ltr);
            ilpackofboxbuxket=itemView.findViewById(R.id.orcompvehrepilBoxbucket);
            iltotalqty=itemView.findViewById(R.id.orcompvehrepiltotqty);
            ilweight=itemView.findViewById(R.id.orcompvehrepilweight);
            ilindustrialsign=itemView.findViewById(R.id.orcompvehrepilsign);
            ilnextdepartment=itemView.findViewById(R.id.orcompvehrepilnextdept);
            ilremark = itemView.findViewById(R.id.orcompvehrepilremark);
            splintime = itemView.findViewById(R.id.orcompvehrepsplintime);
            splouttime = itemView.findViewById(R.id.orcompvehrepsplouttime);
            splpackof7ltr = itemView.findViewById(R.id.orcompvehrepspl7ltr);
            splpackof7_5ltr = itemView.findViewById(R.id.orcompvehrepspl7_5ltr);
            splpackof8_5ltr = itemView.findViewById(R.id.orcompvehrepspl8_5ltr);
            splpackof10ltr = itemView.findViewById(R.id.orcompvehrepspl10ltr);
            splpackof11ltr = itemView.findViewById(R.id.orcompvehrepspl11ltr);
            splpackof12ltr = itemView.findViewById(R.id.orcompvehrepspl12ltr);
            splpackof13ltr = itemView.findViewById(R.id.orcompvehrepspl13ltr);
            splpackof15ltr = itemView.findViewById(R.id.orcompvehrepspl15ltr);
            splpackof18ltr=itemView.findViewById(R.id.orcompvehrepspl18ltr);
            splpackof20ltr=itemView.findViewById(R.id.orcompvehrepspl20ltr);
            splpackof26ltr=itemView.findViewById(R.id.orcompvehrepspl26ltr);
            splpackof50ltr=itemView.findViewById(R.id.orcompvehrepspl50ltr);
            splpackof210ltr=itemView.findViewById(R.id.orcompvehrepspl210ltr);
            splpackofboxbuxket=itemView.findViewById(R.id.orcompvehrepsplBoxbucket);
            spltotalqty=itemView.findViewById(R.id.orcompvehrepspltotqty);
            splweight=itemView.findViewById(R.id.orcompvehrepsplweight);
            splindustrialsign=itemView.findViewById(R.id.orcompvehrepspltrialsign);
            splnextdepartment=itemView.findViewById(R.id.orcompvehrepsplnextdept);
            splremark = itemView.findViewById(R.id.orcompvehrepsplremark);
            outWeiIntime= itemView.findViewById(R.id.orcompvehrepweioutintime);
            outWeiOutTime= itemView.findViewById(R.id.orcompvehrepweoutouttime);
            netweight= itemView.findViewById(R.id.orcompvehrepoutnetwe);
            grossweight= itemView.findViewById(R.id.orcompvehrepoutgrswt);
            sealnumber= itemView.findViewById(R.id.orcompvehrepsealnum);
            numberofpack= itemView.findViewById(R.id.orcompvehrepoutnopck);
            shweight= itemView.findViewById(R.id.orcompvehrepoutshdip);
            shdip= itemView.findViewById(R.id.orcompvehrepoutshwe);
            outWeiRemark= itemView.findViewById(R.id.orcompvehrepweioutremark);
            outvehicleimage= itemView.findViewById(R.id.orcompvehrepOutInVehicleImage);
            outdriverimage= itemView.findViewById(R.id.orcompvehrepoutInDriverImage);
            dataentryIntime= itemView.findViewById(R.id.orcompvehrepoutdtprointime);
            dataentryOutTime= itemView.findViewById(R.id.orcompvehrepoutdtproouttime);
            datantryremark= itemView.findViewById(R.id.orcompvehrepoutdtproremark);
            outbilInTime= itemView.findViewById(R.id.orcompvehrepbillintime);
            outBilOutTime= itemView.findViewById(R.id.orcompvehrepbillouttime);
            outbillremark= itemView.findViewById(R.id.orcompvehrepbillremark);
            outsecInTime= itemView.findViewById(R.id.orcompvehrepoutsecintime);
            outsecOutTime= itemView.findViewById(R.id.orcompvehrepoutsecouttime);
            //sectranslrcopy= itemView.findViewById(R.id.orcompvehrepoutsectransportlrcopy);
            tremcard= itemView.findViewById(R.id.orcompvehrepoutsectremcard);
            ewaybill= itemView.findViewById(R.id.orcompvehrepoutsecewaybill);
            testreport= itemView.findViewById(R.id.orcompvehrepoutsectestreport);
            outsecinvoice= itemView.findViewById(R.id.orcompvehrepoutsecinvoice);
            outsecSign= itemView.findViewById(R.id.orcompvehrepoutsecsign);
            outsecRemark= itemView.findViewById(R.id.orcompvehrepsecremark);
        }
    }

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
