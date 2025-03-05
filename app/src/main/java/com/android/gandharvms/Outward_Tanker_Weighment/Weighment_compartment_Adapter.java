package com.android.gandharvms.Outward_Tanker_Weighment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.LabCompartmentAdapter;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_compartment_model;

import java.util.List;

public class Weighment_compartment_Adapter extends RecyclerView.Adapter<Weighment_compartment_Adapter.ViewHolder> {
    private List<Lab_compartment_model> compartmentList;
    private Context context;
    public Weighment_compartment_Adapter(Context context, List<Lab_compartment_model> compartmentList) {
        this.context = context;  // ✅ Assign context properly
        this.compartmentList = compartmentList;
    }

    @NonNull
    @Override
    public Weighment_compartment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weigh_compartment_item, parent, false);
        return new Weighment_compartment_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Weighment_compartment_Adapter.ViewHolder holder, int position) {
        Lab_compartment_model compartment = compartmentList.get(position);
        Log.d("AdapterBinding", "Binding compartment at position: " + position);

        // ✅ View-Only Production Data (Read-Only)
        holder.txtCompartmentNumber.setText("Compartment " + (position + 1));
        holder.txtBlender.setText("Blender: " + compartment.getBlenderNumber());
        holder.txtProductionSign.setText("Production Sign: " + compartment.getProductionSign());
        holder.txtOperatorSign.setText("Operator Sign: " + compartment.getOperatorSign());

        //holder.intime.setText(compartment.getWehintime());
        holder.tareweight.setText(String.valueOf(compartment.getTareweight()));
        holder.remark.setText(compartment.getWeighremark());

//        holder.intime.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) { compartment.setViscosity(s.toString()); }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
        holder.tareweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { compartment.setViscosity(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        holder.remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { compartment.setViscosity(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    @Override
    public int getItemCount() {
        return compartmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompartmentNumber, txtBlender, txtProductionSign, txtOperatorSign;
        EditText intime, tareweight, remark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCompartmentNumber = itemView.findViewById(R.id.txtCompartmentNumber);
            txtBlender = itemView.findViewById(R.id.txtBlender);
            txtProductionSign = itemView.findViewById(R.id.txtProductionSign);
            txtOperatorSign = itemView.findViewById(R.id.txtOperatorSign);

            intime = itemView.findViewById(R.id.etintime);
            tareweight = itemView.findViewById(R.id.ettareweight);
            remark = itemView.findViewById(R.id.etremark);
        }
    }
}
