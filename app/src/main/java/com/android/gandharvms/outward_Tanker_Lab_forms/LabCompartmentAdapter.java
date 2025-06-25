package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Outward_Tanker_Production_forms.Compartment;
import com.android.gandharvms.R;
import com.google.android.material.internal.TextWatcherAdapter;

import java.util.List;

public class LabCompartmentAdapter extends RecyclerView.Adapter<LabCompartmentAdapter.ViewHolder> {
    private List<Lab_compartment_model> compartmentList;
    private Context context;
    public LabCompartmentAdapter(Context context, List<Lab_compartment_model> compartmentList) {
        this.context = context;  // ✅ Assign context properly
        this.compartmentList = compartmentList;
    }

    @NonNull
    @Override
    public LabCompartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_compartment_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
//    public void onBindViewHolder(@NonNull LabCompartmentAdapter.ViewHolder holder, int position) {
//        Lab_compartment_model compartment = compartmentList.get(position);
//        Log.d("AdapterBinding", "Binding compartment at position: " + position);
//
//        // ✅ View-Only Production Data (Read-Only)
//        // ✅ Combine Production & Lab Data in ONE Card
//        holder.txtCompartmentNumber.setText("Compartment " + (position + 1));
//        holder.txtBlender.setText("Blender: " + (compartment.getBlenderNumber() != null ? compartment.getBlenderNumber() : "N/A"));
//        holder.txtProductionSign.setText("Production Sign: " + (compartment.getProductionSign() != null ? compartment.getProductionSign() : "N/A"));
//        holder.txtOperatorSign.setText("Operator Sign: " + (compartment.getOperatorSign() != null ? compartment.getOperatorSign() : "N/A"));
//
//        holder.edtViscosity.setText(compartment.getIviscosity() != null ? "Viscosity: " + compartment.getIviscosity() : "");
//        holder.edtDensity.setText(compartment.getIdentinity() != null ? "Density: " + compartment.getIdentinity() : "");
//        holder.edtBatchNumber.setText(compartment.getBatchNumber() != null ? "Batch Number: " + compartment.getBatchNumber() : "");
//        holder.edtQCOfficer.setText(compartment.getIqcofficer() != null ? "Q.C Officer: " + compartment.getIqcofficer() : "");
//        holder.edtRemark.setText(compartment.getIremarks() != null ? "Remarks: " + compartment.getIremarks() : "");
//
////        // ✅ Set Existing Lab Data in EditText
////        holder.edtViscosity.setText(compartment.getViscosity());
////        holder.edtDensity.setText(compartment.getDensity());
////        holder.edtBatchNumber.setText(compartment.getBatchNumber());
////        holder.edtQCOfficer.setText(compartment.getQcOfficer());
////        holder.edtRemark.setText(compartment.getRemark());
//
//        // ✅ Use TextWatcher to Automatically Update `compartmentList`
//        holder.edtViscosity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setViscosity(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//        holder.edtDensity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setDensity(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//        holder.edtBatchNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setBatchNumber(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//        holder.edtQCOfficer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setQcOfficer(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//        holder.edtRemark.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setRemark(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//    }



    public void onBindViewHolder(@NonNull LabCompartmentAdapter.ViewHolder holder, int position) {
        Lab_compartment_model compartment = compartmentList.get(position);
        Log.d("AdapterBinding", "Binding compartment at position: " + position);


        holder.txtproduct.setText(compartment.getProductName() != null ? "productname: " + compartment.getProductName() : "productName: N/A");
        holder.txtBlender.setText(compartment.getBlenderNumber() != null ? "Blender: " + compartment.getBlenderNumber() : "Blender: N/A");
        holder.txtProductionSign.setText(compartment.getProductionSign() != null ? "Production Sign: " + compartment.getProductionSign() : "Production Sign: N/A");
        holder.txtOperatorSign.setText(compartment.getOperatorSign() != null ? "Operator Sign: " + compartment.getOperatorSign() : "Operator Sign: N/A");
        holder.productiomremark.setText(compartment.getRemark() != null ? "Production Remark: " + compartment.getRemark() : "Production Remark: N/A");

        holder.edtViscosity.setText(compartment.getViscosity() != null ? "Viscosity: " + compartment.getViscosity() : "");
        holder.edtDensity.setText(compartment.getDensity() != null ? "Density: " + compartment.getDensity() : "");
        holder.edtBatchNumber.setText(compartment.getBatchNumber() != null ? "Batch Number: " + compartment.getBatchNumber() : "");
        holder.edtQCOfficer.setText(compartment.getQcOfficer() != null ? "Q.C Officer: " + compartment.getQcOfficer() : "");
        holder.edtRemark.setText(compartment.getLabremark() != null ? "Remarks: " + compartment.getLabremark() : "");


        // ✅ Prevent Overwriting Data with TextWatcher
        holder.edtViscosity.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { compartment.setViscosity(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.edtDensity.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { compartment.setDensity(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.edtBatchNumber.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { compartment.setBatchNumber(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.edtQCOfficer.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { compartment.setQcOfficer(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.edtRemark.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { compartment.setLabremark(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }




    @Override
    public int getItemCount() {
        return compartmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompartmentNumber, txtBlender, txtProductionSign, txtOperatorSign,txtproduct,productiomremark;
        EditText edtViscosity, edtDensity, edtBatchNumber, edtQCOfficer, edtRemark;
        Button btnSaveCompartment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCompartmentNumber = itemView.findViewById(R.id.txtCompartmentNumber);
            txtproduct = itemView.findViewById(R.id.txtProduct);
            txtBlender = itemView.findViewById(R.id.txtBlender);
            txtProductionSign = itemView.findViewById(R.id.txtProductionSign);
            txtOperatorSign = itemView.findViewById(R.id.txtOperatorSign);
            productiomremark = itemView.findViewById(R.id.txtremark);
            // ✅ Initialize EditText fields
            edtViscosity = itemView.findViewById(R.id.edtViscosity);
            edtDensity = itemView.findViewById(R.id.edtDensity);
            edtBatchNumber = itemView.findViewById(R.id.edtBatchNumber);
            edtQCOfficer = itemView.findViewById(R.id.edtQCOfficer);
            edtRemark = itemView.findViewById(R.id.edtRemark);

            //btnSaveCompartment = itemView.findViewById(R.id.btnSaveCompartment);
        }
    }
    public void updateData(List<Lab_compartment_model> newData) {
        compartmentList.clear(); // Clear old data
        compartmentList.addAll(newData); // Add new data
        notifyDataSetChanged(); // Refresh RecyclerView
    }
}
