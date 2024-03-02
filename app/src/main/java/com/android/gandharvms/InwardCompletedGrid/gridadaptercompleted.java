package com.android.gandharvms.InwardCompletedGrid;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class gridadaptercompleted extends RecyclerView.Adapter<gridadaptercompleted.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    public gridadaptercompleted(List<CommonResponseModelForAllDepartment> respoModelInTankerSecurities) {
        this.Gridmodel = respoModelInTankerSecurities;
        this.filteredGridList = respoModelInTankerSecurities;
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_cell, viewGroup, false);
            return new gridadaptercompleted.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item,
                    viewGroup, false);
            return new gridadaptercompleted.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(gridadaptercompleted.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        if(nextProcess=='S')
        {
            holder.sernum.setText(club.getSerialNo());
            holder.vehiclenum.setText(club.getVehicleNo());
            holder.material.setText(club.getMaterial());
            int intimelength = club.getInTime().length();
            if (intimelength > 0) {
                holder.secintime.setText(club.getInTime().substring(12, intimelength));
            }
            int outtimelength = club.getOutTime().length();
            if (outtimelength > 0) {
                holder.secouttime.setText(club.getOutTime().substring(12, outtimelength));
            }
        }

/*
        holder.Status.setText(club.getCurrStatus());
*/

        /*holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListingResponse_InTankerSequrity club = filteredGridList.get(position);
                String vehitype=club.getVehicleType();
                String crst=club.getCurrStatus();
                char io = club.getI_O();
                Intent intent= new Intent();
                if(vehitype.equals("IT") && io=='I') {
                    if (crst.equals("Weighment") ) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Weighment.class);
                    } else if (crst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Security.class);
                    } else if (crst.equals("Sampling")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Sampling.class);
                    } else if (crst.equals("Laboratory")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Laboratory.class);
                    } else if (crst.equals("Production")) {
                        intent = new Intent(view.getContext(), Inward_Tanker_Production.class);
                    }
                } else if (vehitype.equals("IR") && io=='I') {
                    if (crst.equals("Weighment")) {
                        intent = new Intent(view.getContext(), Inward_Truck_weighment.class);
                    } else if (crst.equals("Security Reported")) {
                        intent = new Intent(view.getContext(), Inward_Truck_Security.class);
                    } else if (crst.equals("Store")) {
                        intent = new Intent(view.getContext(), Inward_Truck_Store.class);
                    }
                }else if (vehitype.equals("IT") && io=='O') {
                    if (crst.equals("OutSecurity")) {
                        intent = new Intent(view.getContext(), InwardOut_Tanker_Weighment.class);
                    } else if (crst.equals("VehicleOut")) {
                        intent = new Intent(view.getContext(), InwardOut_Tanker_Security.class);
                    }
                }else if (vehitype.equals("IR") && io=='O') {
                    if (crst.equals("OutSecurity")) {
                        intent = new Intent(view.getContext(), InwardOut_Truck_Weighment.class);
                    } else if (crst.equals("VehicleOut")) {
                        intent = new Intent(view.getContext(), InwardOut_Truck_Security.class);
                    }
                }
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SerialNumber",club.getSerialNo());
                intent.putExtra("VehicleNumber",club.getVehicleNo());
                intent.putExtra("CurrStatus",club.getCurrStatus());
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
        TextView sernum, vehiclenum, material, Status, secintime, secouttime,date,partyname,qty,netweight, reoprtingre,
                 qtyuom,netweightuom,extramaterials,remark,oapo,mob,Selectregister,IrCopy,DeliveryBill,TaxInvoice,
                 EwayBill,
                weiintime, weiouttime,grossweight,batchnumber,containerno, sighby,shortagedip,shortageweight,
                samintime, samouttime,

                labintime, labouttime,Apperance,Odor,Color,LQty,Density,RcsTest,AnLinePoint,FlashPoint,_40KV,_100KV,
                AdditionalTest,SampleTest,SignOf,DateAndTime,RemarkDescription,ViscosityIndex,

                prointime, proouttime,UnloadAboveMaterialInTK,ProductName,AboveMaterialIsUnloadInTK,OperatorName,
                ReceiveQTY,ReceiveQTYUOM,StoreExtramaterials;
        ImageView invehicleimage,indriverimage;

        public myviewHolder(View view) {
            super(view);
            sernum = view.findViewById(R.id.textSerialNumber);
            vehiclenum = view.findViewById(R.id.textVehicleNumber);
            material = view.findViewById(R.id.textMaterial);
            Status = view.findViewById(R.id.textStatus);
            secintime =view.findViewById(R.id.textcoSecurityInTime);
            secouttime=view.findViewById(R.id.textcoSecurityOutTime);
            weiintime =view.findViewById(R.id.textcoWeighmentInTime);
            weiouttime=view.findViewById(R.id.textcoWeighmentOutTime);
            samintime =view.findViewById(R.id.textcoSamplingInTime);
            samouttime=view.findViewById(R.id.textcoSamplingOutTime);
            labintime =view.findViewById(R.id.textcoLoboratoryInTime);
            labouttime=view.findViewById(R.id.textcoLoboratoryOutTime);
            prointime =view.findViewById(R.id.textcoProductionInTime);
            proouttime=view.findViewById(R.id.textcoProductionOutTime);
            date=view.findViewById(R.id.textcodate);
            partyname=view.findViewById(R.id.textcopartyname);
            qty=view.findViewById(R.id.textcoqty);
            netweight=view.findViewById(R.id.textconetweight);
            reoprtingre=view.findViewById(R.id.textcoreoprtingre);
            qtyuom=view.findViewById(R.id.textcoqtyuom);
            netweightuom=view.findViewById(R.id.textconetweightuom);
            extramaterials=view.findViewById(R.id.textcoextramaterials);
            remark=view.findViewById(R.id.textremark);
            oapo=view.findViewById(R.id.textoapo);
            mob=view.findViewById(R.id.textmob);
            grossweight=view.findViewById(R.id.textgrossweight);
            batchnumber=view.findViewById(R.id.textbatchnumber);
            containerno=view.findViewById(R.id.textcontainerno);
            Selectregister=view.findViewById(R.id.textSelectregister);
            IrCopy=view.findViewById(R.id.textIrCopy);
            DeliveryBill=view.findViewById(R.id.textDeliveryBill);
            TaxInvoice=view.findViewById(R.id.textTaxInvoice);
            EwayBill=view.findViewById(R.id.textEwayBill);
            sighby=view.findViewById(R.id.textsighby);
            shortagedip=view.findViewById(R.id.textshortagedip);
            shortageweight=view.findViewById(R.id.textshortageweight);
            Apperance=view.findViewById(R.id.textApperance);
            Odor=view.findViewById(R.id.textOdor);
            Color=view.findViewById(R.id.textColor);
            LQty=view.findViewById(R.id.textLQty);
            Density=view.findViewById(R.id.textDensity);
            RcsTest=view.findViewById(R.id.textRcsTest);
            AnLinePoint=view.findViewById(R.id.textAnLinePoint);
            FlashPoint=view.findViewById(R.id.textFlashPoint);
            _40KV=view.findViewById(R.id.text_40KV);
            _100KV=view.findViewById(R.id.text_100KV);
            AdditionalTest=view.findViewById(R.id.textAdditionalTest);
            SampleTest=view.findViewById(R.id.textSampleTest);
            SignOf=view.findViewById(R.id.textSignOf);
            DateAndTime=view.findViewById(R.id.textDateAndTime);
            RemarkDescription=view.findViewById(R.id.textRemarkDescription);
            ViscosityIndex=view.findViewById(R.id.textViscosityIndex);
            UnloadAboveMaterialInTK=view.findViewById(R.id.textUnloadAboveMaterialInTK);
            ProductName=view.findViewById(R.id.textProductName);
            AboveMaterialIsUnloadInTK=view.findViewById(R.id.textAboveMaterialIsUnloadInTK);
            OperatorName=view.findViewById(R.id.textOperatorName);
            ReceiveQTY=view.findViewById(R.id.textReceiveQTY);
            ReceiveQTYUOM=view.findViewById(R.id.textReceiveQTYUOM);
            StoreExtramaterials=view.findViewById(R.id.textStoreExtramaterials);
        }
    }
}
