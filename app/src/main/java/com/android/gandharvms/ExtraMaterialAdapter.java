package com.android.gandharvms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;

import java.util.Arrays;
import java.util.List;

public class ExtraMaterialAdapter extends RecyclerView.Adapter<ExtraMaterialAdapter.ExtraMaterialViewHolder> {
    private List<ExtraMaterial> extraMaterials;

    public ExtraMaterialAdapter(List<ExtraMaterial> extraMaterials) {
        this.extraMaterials = extraMaterials;
    }

    @NonNull
    @Override
    public ExtraMaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmaterial, parent, false);
        return new ExtraMaterialViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExtraMaterialViewHolder holder, int position) {
        ExtraMaterial extraMaterial = extraMaterials.get(position);
        holder.materialEditText.setText(extraMaterial.getMaterial());
        holder.materialEditText.setEnabled(false);
        holder.qtyEditText.setText(extraMaterial.getQty());
        holder.qtyEditText.setEnabled(false);

        List<String> teamList = Arrays.asList("NA", "Ton", "Litre", "KL", "Kgs", "Pcs", "M3", "Meter", "Feet");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, teamList);
        holder.uomSpinner.setAdapter(arrayAdapter);
        holder.uomSpinner.setEnabled(false);
        setSpinnerValue(holder.uomSpinner, extraMaterial.getQtyuom());
    }

    @Override
    public int getItemCount() {
        return extraMaterials.size();
    }

    public static class ExtraMaterialViewHolder extends RecyclerView.ViewHolder {
        EditText materialEditText;
        EditText qtyEditText;
        Spinner uomSpinner;

        public ExtraMaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            materialEditText = itemView.findViewById(R.id.etallmaterialmet);
            qtyEditText = itemView.findViewById(R.id.etallmaterialqty);
            uomSpinner = itemView.findViewById(R.id.allmaterialspinner_team);
        }
    }

    private void setSpinnerValue(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(value)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }
}
