package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.util.List;

public class CompartmentAdapter extends RecyclerView.Adapter<CompartmentAdapter.ViewHolder> {
    private List<Compartment> compartmentList;
    private Context context;
    public CompartmentAdapter(Context context, List<Compartment> compartmentList) {
        this.context = context;
        this.compartmentList = compartmentList;
    }
    @NonNull
    @Override
    public CompartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompartmentAdapter.ViewHolder holder, int position) {
        Compartment compartment = compartmentList.get(position);

        // Set compartment number dynamically (starting from 1)
        holder.txtCompartmentNumber.setText("Compartment #" + (position + 1));

        holder.txtBlender.setText("Blender: " + compartment.getBlenderNumber());
        holder.txtProduction.setText("Production Sign: " + compartment.getProductionSign());
        holder.txtOperator.setText("Operator: " + compartment.getOperatorSign());
        holder.txtproduct.setText("ProductName: " + compartment.getProductname());

        // Set text color and size for better visibility
        holder.txtBlender.setTextColor(Color.BLACK);
        holder.txtProduction.setTextColor(Color.DKGRAY);
        holder.txtOperator.setTextColor(Color.DKGRAY);
        holder.txtproduct.setTextColor(Color.DKGRAY);
    }

    @Override
    public int getItemCount() {
        return compartmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBlender, txtProduction, txtOperator,txtCompartmentNumber,txtproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCompartmentNumber = itemView.findViewById(R.id.txtCompartmentNumber);
            txtBlender = itemView.findViewById(R.id.txtBlender);
            txtProduction = itemView.findViewById(R.id.txtProduction);
            txtOperator = itemView.findViewById(R.id.txtOperator);
            txtproduct = itemView.findViewById(R.id.txtproduct);
        }
    }
}
