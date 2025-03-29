package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static List<Product> productList;
    private List<Compartment> compartmentDataList;
    private int expandedPosition = -1; // Tracks which item is expanded

    public ProductAdapter(List<Product> productList,List<Compartment> compartmentDataList) {
        this.productList = productList;
        this.compartmentDataList = compartmentDataList;
        this.expandedPosition = expandedPosition; // ✅ Initialize expandedPosition from constructor
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position); // ✅ Use position directly

        holder.txtProductName.setText(product.getProductName());
        if (position < compartmentDataList.size()) {
            Compartment compartment = compartmentDataList.get(position);
            holder.etBlenderNumber.setText(compartment.getBlenderNumber());
//            holder.etBlenderNumber.setEnabled(false);
            holder.etOperator.setText(compartment.getOperatorSign());
//            holder.etOperator.setEnabled(false);
            holder.etRemark.setText(compartment.getRemark());
//            holder.etRemark.setEnabled(false);
            holder.etintime.setText(compartment.getIntime());
            holder.etprodsign.setText(compartment.getProductionSign());
//            holder.etprodsign.setEnabled(false);
            Log.d("DEBUG", "Binding Procompartment to Product: " + product.getProductName());
        }else {
            // ✅ Keep empty if no procompartment available
            holder.etBlenderNumber.setText("");
            holder.etOperator.setText("");
            holder.etRemark.setText("");
            holder.etintime.setText("");
            holder.etprodsign.setText("");
            Log.d("DEBUG", "No Procompartment for Product: " + product.getProductName());
        }

        // ✅ Restore saved values (if any)
        holder.etBlenderNumber.setText(product.getBlenderNumber());
        holder.etprodsign.setText(product.getSignOfProduction());
        holder.etOperator.setText(product.getOperatorSign());
        holder.etRemark.setText(product.getRemark());
        holder.etintime.setText(product.getInTime());

        // ✅ Save user input while typing
        holder.etBlenderNumber.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { product.setBlenderNumber(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        holder.etprodsign.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { product.setSignOfProduction(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.etOperator.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { product.setOperatorSign(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        holder.etRemark.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { product.setRemark(s.toString()); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // ✅ Click event to set current time in `inTime` field
        holder.etintime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String time = format.format(calendar.getTime());
            holder.etintime.setText(time);
            product.setInTime(time);
            notifyDataSetChanged(); // ✅ Refresh UI to persist changes
        });

        // ✅ Ensure selected item is highlighted
        //holder.txtProductName.setBackgroundColor(product.isSelected() ? Color.LTGRAY : Color.TRANSPARENT);

        // ✅ Handle product selection & expansion in a **single click listener**
        holder.txtProductName.setOnClickListener(v -> {
            for (Product p : productList) {
                p.setSelected(false); // Deselect all
            }
            product.setSelected(true); // ✅ Mark selected product

            expandedPosition = (expandedPosition == position) ? -1 : position; // Toggle expansion
            notifyDataSetChanged();
        });

        // ✅ Expand/collapse logic
        holder.productDetailsLayout.setVisibility(expandedPosition == position ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ✅ Use an instance method, not static
    public static List<Product> getProductList() {
        return productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;
        LinearLayout productDetailsLayout;
        EditText etBlenderNumber, etOperator, etRemark, etintime,etprodsign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            productDetailsLayout = itemView.findViewById(R.id.productDetailsLayout);
            etBlenderNumber = itemView.findViewById(R.id.elnewblendingno);
            etOperator = itemView.findViewById(R.id.etnewsignofoprator);
            etRemark = itemView.findViewById(R.id.etnewremark);
            etintime = itemView.findViewById(R.id.etinewntime_prodcutdetails);
            etprodsign = itemView.findViewById(R.id.etsignprodcution);
        }
    }
}

