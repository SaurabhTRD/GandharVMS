package com.android.gandharvms.Outward_Tanker_Security;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.OutwardOutDataEntryForm_Production.DataEntryForm_Production;
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBilling;
import com.android.gandharvms.OutwardOut_Tanker_Security.OutwardOut_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Outward_Tanker_Production;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_DesIndustriaLoading_Form;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_DesSmallPackLoading_Form;
import com.android.gandharvms.Outwardout_Tanker_Weighment.OutwardOut_Tanker_Weighment;
import com.android.gandharvms.OutwardOut_Truck_Billing.OutwardOut_Truck_Billing;
import com.android.gandharvms.OutwardOut_Truck_Security;
import com.android.gandharvms.OutwardOut_Truck_Weighment;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Production_forms.bulkloadingproduction;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_Dispatch;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production;
import com.android.gandharvms.Outward_Truck_Security.Outward_Truck_Security;
import com.android.gandharvms.Outward_Truck_Weighment.Outward_Truck_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.New_Outward_tanker_Lab;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Laboratory;
import com.android.gandharvms.outward_Tanker_Lab_forms.bulkloadinglaboratory;

import java.util.ArrayList;
import java.util.List;

public class Outward_GridAdapter extends RecyclerView.Adapter<Outward_GridAdapter.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    private final List<Response_Outward_Security_Fetching> outwardGridmodel;
    private List<Response_Outward_Security_Fetching> outwardfilteredGridList;
    private Context context;

    public Outward_GridAdapter(List<Response_Outward_Security_Fetching> responseoutwardgrid) {
        this.outwardGridmodel = responseoutwardgrid;
        this.outwardfilteredGridList = responseoutwardgrid;
    }

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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_table_cell, viewGroup, false);
            return new myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outward_card_item,
                    viewGroup, false);
            return new myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.trans.setText(club.getTransportName());
        holder.Status.setText(club.getCurrStatus());
        holder.date.setText(club.getDate().substring(0,12));
        int secintimelength = club.getSecInTime() != null ? club.getSecInTime().length() : 0;
        if (secintimelength > 0) {
            holder.secInTime.setText(club.getSecInTime());
        }

        int logintime = club.getLogInTime() != null ? club.getLogInTime().length() : 0;
        if (logintime > 0) {
            holder.logintime.setText(club.getLogInTime());
        }

        int bilInTimelength = club.getBilInTime() != null ? club.getBilInTime().length() : 0;
        if (bilInTimelength > 0) {
            holder.bilInTime.setText(club.getBilInTime());
        }

        int weiInTimelength = club.getWeiInTime() != null ? club.getWeiInTime().length() : 0;
        if (weiInTimelength > 0) {
            holder.weiInTime.setText(club.getWeiInTime());
        }

        int proTimelength = club.getBLFProInTime() != null ? club.getBLFProInTime().length() : 0;
        if (proTimelength > 0) {
            holder.proInTime.setText(club.getBLFProInTime());
        }

        int labTimelength = club.getIPFLabInTime() != null ? club.getIPFLabInTime().length() : 0;
        if (labTimelength > 0) {
            holder.labInTime.setText(club.getIPFLabInTime());
        }

        int induspackTimelength = club.getIndusTime() != null ? club.getIndusTime().length() : 0;
        if (induspackTimelength > 0) {
            holder.induspacktime.setText(club.getIndusTime());
        }

        int smallpacklength = club.getSamllPackTime() != null ? club.getSamllPackTime().length() : 0;
        if (smallpacklength > 0) {
            holder.smallpacktime.setText(club.getSamllPackTime());
        }

        int outweiTimelength = club.getOutWeiTime() != null ? club.getOutWeiTime().length() : 0;
        if (outweiTimelength > 0) {
            holder.outweitime.setText(club.getOutWeiTime());
        }

        int outtruckdataentrytimelength = club.getIRDataEntryTime() != null ? club.getIRDataEntryTime().length() : 0;
        int outdataentryTimelength = club.getOutDataEntryTime() != null ? club.getOutDataEntryTime().length() : 0;
        if(!club.getVehicleType().equals("OR"))
        {
            if (outdataentryTimelength > 0) {
                holder.outdataentrytime.setText(club.getOutDataEntryTime());
            }
        }
        else{
            if (outtruckdataentrytimelength > 0) {
                holder.outdataentrytime.setText(club.getIRDataEntryTime());
            }
        }

        int outbillTimelength = club.getOutBillTime() != null ? club.getOutBillTime().length() : 0;
        if (outbillTimelength > 0) {
            holder.outbilltime.setText(club.getOutBillTime());
        }
        int outsecTimelength = club.getOutSecTime() != null ? club.getOutSecTime().length() : 0;
        if (outsecTimelength > 0) {
            holder.outsectime.setText(club.getOutSecTime());
        }

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response_Outward_Security_Fetching club = outwardfilteredGridList.get(position);
                String vehicletype = club.getVehicleType();
                String currentst = club.getCurrStatus();
                char io = club.getI_O();
                Intent intent = new Intent();
                if (vehicletype.equals("OT") && io == 'I') {
                    if (currentst.equals("SECURITY REPORTED")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Security.class);
                    } else if (currentst.equals("BILLING")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_Billing.class);
                    } else if (currentst.equals("WEIGHMENT")) {
                        intent = new Intent(view.getContext(), Outward_Tanker_weighment.class);
                    } else if (currentst.equals("PRODUCTION")) {
                        intent = new Intent(view.getContext(), New_Outward_Tanker_Production.class);
                    } else if (currentst.equals("LABORATORY")) {
                        intent = new Intent(view.getContext(), New_Outward_tanker_Lab.class);
                    }
//                    else if (currentst.equals("INPROCESSPRODUCTION")) {
//                        intent = new Intent(view.getContext(), Outward_Tanker_Production.class);
//                    }else if (currentst.equals("INPROCESSLABORATORY")) {
//                        intent = new Intent(view.getContext(), Outward_Tanker_Laboratory.class);
//                    }else if (currentst.equals("BULKPRODUCTION")) {
//                        intent = new Intent(view.getContext(), bulkloadingproduction.class);
//                    }else if (currentst.equals("BULKLABORATORY")) {
//                        intent = new Intent(view.getContext(), bulkloadinglaboratory.class);
//                    }
                } else if (vehicletype.equals("OT") && io == 'O') {
                    if (currentst.equals("OUTWEIGHMENT")) {
                        intent = new Intent(view.getContext(), OutwardOut_Tanker_Weighment.class);
                    } else if (currentst.equals("OUTDATAENTRY")) {
                        intent = new Intent(view.getContext(), DataEntryForm_Production.class);
                    } else if (currentst.equals("OUTBILLING")) {
                        intent = new Intent(view.getContext(), ot_outBilling.class);
                    } else if (currentst.equals("SECURITYVEHICLEOUT")) {
                        intent = new Intent(view.getContext(), OutwardOut_Tanker_Security.class);
                    }
                } else if (vehicletype.equals("OR") && io == 'I') {
                    if (currentst.equals("SECURITY REPORTED")) {
                        intent = new Intent(view.getContext(), Outward_Truck_Security.class);
                    } else if (currentst.equals("LOGISTIC")) {
                        intent = new Intent(view.getContext(), Outward_Truck_Logistics.class);
                    } else if (currentst.equals("WEIGHMENT")) {
                        intent = new Intent(view.getContext(), Outward_Truck_weighment.class);
                    } else if (currentst.equals("DISPATCH")) {
                        intent = new Intent(view.getContext(), Outward_Truck_Dispatch.class);
                    }
//                    else if (currentst.equals("DATAENTRY")) {
//                        intent = new Intent(view.getContext(), Outward_Truck_Production.class);
//                    }
                    else if (currentst.equals("SMALLPACK")) {
                        intent = new Intent(view.getContext(), Outward_DesSmallPackLoading_Form.class);
                    } else if (currentst.equals("INDUSTRIALPACK")) {
                        intent = new Intent(view.getContext(), Outward_DesIndustriaLoading_Form.class);
                    }

                } else if (vehicletype.equals("OR") && io == 'O') {
                    if (currentst.equals("OUTWEIGHMENT")) {
                        intent = new Intent(view.getContext(), OutwardOut_Truck_Weighment.class);
                    } else if (currentst.equals("DATAENTRY")) {
                        intent = new Intent(view.getContext(), Outward_Truck_Production.class);
                    } else if (currentst.equals("OUTBILLING")) {
                        intent = new Intent(view.getContext(), OutwardOut_Truck_Billing.class);
                    } else if (currentst.equals("SECURITYVEHICLEOUT")) {
                        intent = new Intent(view.getContext(), OutwardOut_Truck_Security.class);
                    }
                }
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("serialnumber", club.getSerialNumber());
                intent.putExtra("vehiclenum", club.getVehicleNumber());
                intent.putExtra("currstatus", club.getCurrStatus());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return outwardGridmodel.size();
    }

    public Filter getFilter() {
        return new Filter() {
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    outwardfilteredGridList = outwardGridmodel;
                } else {
                    List<Response_Outward_Security_Fetching> filteredList = new ArrayList<>();
                    for (Response_Outward_Security_Fetching club : outwardfilteredGridList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNumber().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNumber().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(club);
                        }
                    }
                    outwardfilteredGridList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = outwardfilteredGridList;
                return filterResults;
            }

            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                outwardfilteredGridList = (ArrayList<Response_Outward_Security_Fetching>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView vehiclenum, trans, Status, date, secInTime,
                bilInTime, logintime, weiInTime, proInTime, labInTime, induspacktime, smallpacktime, outweitime,
                outdataentrytime, outbilltime, outsectime;

        public myviewHolder(View view) {
            super(view);
            vehiclenum = view.findViewById(R.id.textoutwardgridVehicleNumber);
            trans = view.findViewById(R.id.textoutwardgridTransporter);
            Status = view.findViewById(R.id.textoutwardgridStatus);
            date = view.findViewById(R.id.textoutwardgriddate);
            secInTime = view.findViewById(R.id.textoutwardgridSecInTime);
            bilInTime = view.findViewById(R.id.textoutwardgridBilInTime);
            logintime = view.findViewById(R.id.textoutwardgridLogInTime);
            weiInTime = view.findViewById(R.id.textoutwardgridWeiInTime);
            proInTime = view.findViewById(R.id.textoutwardgridprotime);
            labInTime = view.findViewById(R.id.textoutwardgridlabtime);
            induspacktime = view.findViewById(R.id.textoutwardgridinduspacktime);
            smallpacktime = view.findViewById(R.id.textoutwardgridsmallpacktime);
            outweitime = view.findViewById(R.id.textoutwardgridoutweitime);
            outdataentrytime = view.findViewById(R.id.textoutwardgridoutdataentrytime);
            outbilltime = view.findViewById(R.id.textoutwardgridoutbilltime);
            outsectime = view.findViewById(R.id.textoutwardgridoutsectime);

        }
    }
}
