package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Tanker_Production_forms.Compartment;
import com.android.gandharvms.R;

import java.util.List;

public class First_LabCompartmentAdapter extends RecyclerView.Adapter<First_LabCompartmentAdapter.ViewHolder> {
    private List<Compartment> compartmentList;
    private Context context;

    public First_LabCompartmentAdapter(List<Compartment> compartmentList, Context context) {
        this.compartmentList = compartmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lab_compartment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compartment compartment = compartmentList.get(position);
        holder.txtCompartmentNumber.setText("Compartment " + (position + 2)); // 🔹 Start from 2nd
        holder.txtBlender.setText("Blender: " + compartment.getBlenderNumber());
        holder.txtProductionSign.setText("Production Sign: " + compartment.getProductionSign());
        holder.txtOperatorSign.setText("Operator Sign: " + compartment.getOperatorSign());
    }

    @Override
    public int getItemCount() {
        return compartmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompartmentNumber, txtBlender, txtProductionSign, txtOperatorSign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCompartmentNumber = itemView.findViewById(R.id.txtCompartmentNumber);
            txtBlender = itemView.findViewById(R.id.txtBlender);
            txtProductionSign = itemView.findViewById(R.id.txtProductionSign);
            txtOperatorSign = itemView.findViewById(R.id.txtOperatorSign);
        }
    }
}


