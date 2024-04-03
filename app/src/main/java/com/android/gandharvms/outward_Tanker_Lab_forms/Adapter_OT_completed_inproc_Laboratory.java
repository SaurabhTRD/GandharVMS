package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Production_forms.Adapter_OT_completed_inproc_prodcuction;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_inproc_Laboratory extends RecyclerView.Adapter<Adapter_OT_completed_inproc_Laboratory.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;
    public Adapter_OT_completed_inproc_Laboratory(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_inproc_Laboratory.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_inproclab_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_inproc_Laboratory.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_proclab_tableitem, viewGroup, false);
            return new Adapter_OT_completed_inproc_Laboratory.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_inproc_Laboratory.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getLabInTime()!= null ? club.getLabInTime().length() :0;
        int outtimelength = club.getLabOutTime()!=null ? club.getLabOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getLabInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outime.setText(club.getLabOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
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
        holder.ddf90.setText(String.valueOf(club.getDDF_90C()));
        holder.watercontent.setText(club.getWaterContent());
        holder.interfacial.setText(club.getInterfacialTension());
        holder.flashpt.setText(club.getFlash_Point());
        holder.pourpt.setText(club.getPourPoint());
        holder.rcstest.setText(club.getRcs_Test());
        holder.remark.setText(club.getLabRemark());
        holder.correction.setText(club.getCorrectionRequired());
        holder.resistivity.setText(club.getRestivity());
        holder.infrared.setText(club.getInfra_Red());
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

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView intime,outime,serialnum,vehiclenum,apperance,condition,sampledt,releasedt,color,odor,density29,kv40,kv100,viscosity,tbntan,aniline,
        breakdown,ddf90,watercontent,interfacial,flashpt,pourpt,rcstest,remark,correction,resistivity,infrared;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.otinproclabintime);
            outime = itemView.findViewById(R.id.otinproclabouttime);
            serialnum = itemView.findViewById(R.id.otinproclabserial);
            vehiclenum = itemView.findViewById(R.id.otinproclabvehicle);
            apperance = itemView.findViewById(R.id.otinproclabapperance);
            condition = itemView.findViewById(R.id.otinproclabcondsample);
            sampledt = itemView.findViewById(R.id.otinproclabsampledt);
            releasedt = itemView.findViewById(R.id.otinproclabrelesedt);
            color = itemView.findViewById(R.id.otinproclabcolor);
            odor = itemView.findViewById(R.id.otinproclabodor);
            density29 = itemView.findViewById(R.id.otinproclab29);
            kv40 = itemView.findViewById(R.id.otinproclab40c);
            kv100 = itemView.findViewById(R.id.otinproclab100c);
            viscosity = itemView.findViewById(R.id.otinproclabviscosity);
            tbntan = itemView.findViewById(R.id.otinproclabtabtan);
            aniline = itemView.findViewById(R.id.otinproclabanline);
            breakdown = itemView.findViewById(R.id.otinproclabbreakdown);
            ddf90 = itemView.findViewById(R.id.otinproclabddf90);
            watercontent = itemView.findViewById(R.id.otinprocwatercontent);
            interfacial = itemView.findViewById(R.id.otinprocinterfacialtension);
            flashpt = itemView.findViewById(R.id.otinproclabflashpt);
            pourpt = itemView.findViewById(R.id.otinproclabpourpoint);
            rcstest = itemView.findViewById(R.id.otinproclabrcstest);
            remark = itemView.findViewById(R.id.otinproclabremark);
            correction = itemView.findViewById(R.id.otinproclabcorrectionreqried);
            resistivity = itemView.findViewById(R.id.otinproclabresistivity);
            infrared = itemView.findViewById(R.id.otinproclabinfra);
        }
    }
}
