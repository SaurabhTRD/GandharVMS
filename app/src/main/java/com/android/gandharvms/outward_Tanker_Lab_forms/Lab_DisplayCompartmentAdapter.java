package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class Lab_DisplayCompartmentAdapter extends RecyclerView.Adapter<Lab_DisplayCompartmentAdapter.ViewHolder> {
    private List<Display_LabCompartmentModel> list;
    private Context context;
    public Lab_DisplayCompartmentAdapter(Context context, List<Display_LabCompartmentModel> list) {
        this.context = context;  // ✅ Assign context properly
        this.list = list;
    }
    @NonNull
    @Override
    public Lab_DisplayCompartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.labdata_listing, parent, false);
        return new Lab_DisplayCompartmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Lab_DisplayCompartmentAdapter.ViewHolder holder, int position) {

        Display_LabCompartmentModel model = list.get(position);

        holder.tvTitle.setText("Compartment " + model.getCompartmentNumber() + " Details");
        //holder.tvBlender.setText("Blender: " + model.getBlender());
//        holder.tvProdSign.setText("Production Sign: " + model.getProductionSign());
//        holder.tvOperatorSign.setText("Operator Sign: " + model.getOperatorSign());
        holder.tvViscosity.setText("Viscosity: " + model.getViscosity());
        holder.tvDensity.setText("Density: " + model.getDensity());
        holder.tvBatch.setText("Batch Number: " + model.getBatchNumber());
        holder.tvQC.setText("QC Officer: " + model.getQcOfficer());
        holder.tvRemark.setText("Remark: " + model.getRemark());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvViscosity, tvDensity, tvBatch, tvQC, tvRemark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txtCompartmentNumber);
//            tvBlender = itemView.findViewById(R.id.tvBlender);
//            tvProdSign = itemView.findViewById(R.id.tvProdSign);
//            tvOperatorSign = itemView.findViewById(R.id.tvOperatorSign);
            tvViscosity = itemView.findViewById(R.id.txtvisco);
            tvDensity = itemView.findViewById(R.id.txtdensity);
            tvBatch = itemView.findViewById(R.id.txtbatchnumber);
            tvQC = itemView.findViewById(R.id.txtqcofficer);
            tvRemark = itemView.findViewById(R.id.txtremark);
        }
    }
}
