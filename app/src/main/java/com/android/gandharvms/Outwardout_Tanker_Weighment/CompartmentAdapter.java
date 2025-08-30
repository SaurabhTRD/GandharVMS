package com.android.gandharvms.Outwardout_Tanker_Weighment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class CompartmentAdapter extends RecyclerView.Adapter<CompartmentAdapter.ViewHolder> {

    private List<CompartmentData> compartmentDataList;

    public CompartmentAdapter(List<CompartmentData> compartmentList){
        this.compartmentDataList = compartmentList;
    }
    @NonNull
    @Override
    public CompartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compartment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompartmentAdapter.ViewHolder holder, int position) {
        CompartmentData data = compartmentDataList.get(position);

        holder.tvBlender.setText(data.getBlender());
        holder.tvProductionSign.setText("Production Sign: " + data.getProductionSign());
        holder.tvOperatorSign.setText("Operator Sign: " + data.getOperatorSign());
        holder.tvTareWeight.setText("Tare Weight: " + data.getTareWeight());
        holder.tvVerificationRemark.setText("Verification Remark: " + data.getVerificationRemark());

    }

    @Override
    public int getItemCount() {
        return compartmentDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBlender, tvProductionSign, tvOperatorSign, tvTareWeight, tvVerificationRemark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBlender = itemView.findViewById(R.id.tvBlender);
            tvProductionSign = itemView.findViewById(R.id.tvProductionSign);
            tvOperatorSign = itemView.findViewById(R.id.tvOperatorSign);
            tvTareWeight = itemView.findViewById(R.id.tvTareWeight);
            tvVerificationRemark = itemView.findViewById(R.id.tvVerificationRemark);
        }
    }
}
