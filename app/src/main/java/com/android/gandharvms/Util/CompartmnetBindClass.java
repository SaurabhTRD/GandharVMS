package com.android.gandharvms.Util;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.gandharvms.R;

import org.json.JSONObject;

public class CompartmnetBindClass {
    public static void bindProCompartment(TextView textView, String compartmentJson, String title) {
        if (compartmentJson != null && !compartmentJson.trim().isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(compartmentJson);

                // Show only ProductName in TextView
                String productName = jsonObject.optString("ProductName", "");
                textView.setText(productName);
                textView.setClickable(true);

                textView.setOnClickListener(v -> {
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_compartment_details, null);

                    // Bind data to views
                    ((TextView) dialogView.findViewById(R.id.tvProductName)).setText(jsonObject.optString("ProductName", ""));
                    ((TextView) dialogView.findViewById(R.id.tvInTime)).setText(jsonObject.optString("InTime", ""));
                    ((TextView) dialogView.findViewById(R.id.tvBlender)).setText(jsonObject.optString("Blender", ""));
                    ((TextView) dialogView.findViewById(R.id.tvProductionSign)).setText(jsonObject.optString("ProductionSign", ""));
                    ((TextView) dialogView.findViewById(R.id.tvOperatorSign)).setText(jsonObject.optString("OperatorSign", ""));
                    ((TextView) dialogView.findViewById(R.id.tvRemark)).setText(jsonObject.optString("Remark", ""));

                    new AlertDialog.Builder(v.getContext())
                            .setTitle(title != null ? title : "Compartment Details")
                            .setView(dialogView) // use custom layout
                            .setPositiveButton("OK", null)
                            .show();
                });

            } catch (Exception e) {
                textView.setText("");
                textView.setClickable(false);
            }
        } else {
            textView.setText("");
            textView.setClickable(false);
        }
    }

    public static void bindLabCompartment(TextView textView, String compartmentJson, String title) {
        if (compartmentJson != null && !compartmentJson.trim().isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(compartmentJson);

                // Show only ProductName in TextView
                String density = jsonObject.optString("Density", "");
                if(density.toString().isEmpty())
                {
                    textView.setText("");
                    textView.setClickable(false);
                }else{
                    textView.setText("View Compartment");
                    textView.setClickable(true);
                }

                textView.setOnClickListener(v -> {
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_labcompartment_details, null);

                    // Bind data to views
                    ((TextView) dialogView.findViewById(R.id.tvViscosity)).setText(jsonObject.optString("Viscosity", ""));
                    ((TextView) dialogView.findViewById(R.id.tvIdentity)).setText(jsonObject.optString("Density", ""));
                    ((TextView) dialogView.findViewById(R.id.tvBatchNum)).setText(jsonObject.optString("BatchNumber", ""));
                    ((TextView) dialogView.findViewById(R.id.tvQCOfficer)).setText(jsonObject.optString("QcOfficer", ""));
                    ((TextView) dialogView.findViewById(R.id.tvRemarks)).setText(jsonObject.optString("Remark", ""));

                    new AlertDialog.Builder(v.getContext())
                            .setTitle(title != null ? title : "Compartment Details")
                            .setView(dialogView) // use custom layout
                            .setPositiveButton("OK", null)
                            .show();
                });

            } catch (Exception e) {
                textView.setText("");
                textView.setClickable(false);
            }
        } else {
            textView.setText("");
            textView.setClickable(false);
        }
    }
}
