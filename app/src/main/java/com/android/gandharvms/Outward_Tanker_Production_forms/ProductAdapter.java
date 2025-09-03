package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static List<Product> productList;
    private List<Compartment> compartmentDataList;
    private int expandedPosition = -1; // Tracks which item is expanded
    private Outward_Tanker_Lab outwardTankerLab;
    public int outwardId;
    public String productdtls;
    public String Empid = Global_Var.getInstance().EmpId;
    private Map<Integer, Compartment> compartmentDataMap;

    public ProductAdapter(List<Product> proList,Map<Integer, Compartment> compartmentDataMap,int outwardId,String productdtls) {
        this.productList = proList;
//        this.compartmentDataList = compartmentDataList;
        this.compartmentDataMap = compartmentDataMap;
        this.expandedPosition = expandedPosition; // ✅ Initialize expandedPosition from constructor
        this.outwardId = outwardId;
        this.productdtls = productdtls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        outwardTankerLab = RetroApiClient.outwardTankerLab();
        Product product = productList.get(position); // ✅ Use position directly
        holder.txtProductName.setText(product.getProductName());
        holder.etBlenderNumber.setText(product.getBlenderNumber());
        holder.etprodsign.setText(product.getSignOfProduction());
        holder.etOperator.setText(product.getOperatorSign());
        holder.etRemark.setText(product.getRemark());
        holder.etintime.setText(product.getInTime());

        Compartment compartment = compartmentDataMap.get(position);
        if (compartment != null) {
//            Compartment compartment = compartmentDataList.get(position);
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

        holder.ivEditProduct.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_edit_product_name, null);
            EditText etNewProductName = dialogView.findViewById(R.id.etNewProductName);
            etNewProductName.setText(product.getProductName());

            new android.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Product Name")
                    .setView(dialogView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        String updatedName = etNewProductName.getText().toString().trim();
                        if (!updatedName.isEmpty()) {
                            try {
                                JSONArray productArray = new JSONArray(productdtls);

                                for (int i = 0; i < productArray.length(); i++) {
                                    JSONObject obj = productArray.getJSONObject(i);
                                    String oaNumber = obj.optString("OANumber");

                                    if (oaNumber.equals(product.getOANumber())) {
                                        obj.put("ProductName", updatedName);

                                        // ✅ Update local productList so UI also updates correctly
                                        product.setProductName(updatedName);
                                        break;
                                    }
                                }

                                String updatedProductJson = productArray.toString();
                                productdtls = updatedProductJson; // ✅ Important: update adapter field
                                Log.d("UpdatedJSON", updatedProductJson);

                                updateProductName(outwardId, updatedProductJson, Empid);
                                notifyItemChanged(holder.getAdapterPosition()); // Refresh UI

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("JSON_ERROR", "Failed to update product name: " + e.getMessage());
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });



    }

    private void updateProductName( int outwardId,String updatedProductJson, String Empid) {
        updateproduct updateproduct = new updateproduct(outwardId,updatedProductJson,Empid);
        Call<Boolean> call = outwardTankerLab.updateProductName(updateproduct);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && Boolean.TRUE.equals(response.body()) && response.body() != null && response.body() == true) {
                    Log.d("UpdateProduct", "Product name updated successfully");
                } else {
                    Log.e("UpdateProduct", "Failed to update product name");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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
        ImageView ivEditProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            productDetailsLayout = itemView.findViewById(R.id.productDetailsLayout);
            etBlenderNumber = itemView.findViewById(R.id.elnewblendingno);
            etOperator = itemView.findViewById(R.id.etnewsignofoprator);
            etRemark = itemView.findViewById(R.id.etnewremark);
            etintime = itemView.findViewById(R.id.etinewntime_prodcutdetails);
            etprodsign = itemView.findViewById(R.id.etsignprodcution);

            ivEditProduct = itemView.findViewById(R.id.ivEditProduct);
        }
    }
}

