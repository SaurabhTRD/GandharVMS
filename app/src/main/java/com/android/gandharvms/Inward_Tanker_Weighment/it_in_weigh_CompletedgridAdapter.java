package com.android.gandharvms.Inward_Tanker_Weighment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.gridadaptercompleted;
import com.android.gandharvms.Inward_Truck_Security.Material_Adapter;
import com.android.gandharvms.Inward_Truck_Security.matriallist;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class it_in_weigh_CompletedgridAdapter extends RecyclerView.Adapter<it_in_weigh_CompletedgridAdapter.myviewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<CommonResponseModelForAllDepartment> Gridmodel;
    private List<CommonResponseModelForAllDepartment> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    private OnImageClickListener imageClickListener;
    String formattedDate;
    Inward_Tanker_Weighment itw = new Inward_Tanker_Weighment();

    public it_in_weigh_CompletedgridAdapter(List<CommonResponseModelForAllDepartment> inwardcomresponsemodel) {
        this.Gridmodel = inwardcomresponsemodel;
        this.filteredGridList = inwardcomresponsemodel;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @NonNull
    @Override
    public it_in_weigh_CompletedgridAdapter.myviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_in_weigh_completedgrid_table_cell, viewGroup, false);
            return new it_in_weigh_CompletedgridAdapter.myviewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.it_in_weigh_completedgrid_card_item,
                    viewGroup, false);
            return new it_in_weigh_CompletedgridAdapter.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(it_in_weigh_CompletedgridAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommonResponseModelForAllDepartment club = filteredGridList.get(position);
        int intimelength = club.getInTime() != null ? club.getInTime().length() : 0;
        int outtimelength = club.getOutTime() != null ? club.getOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getOutTime().substring(12, outtimelength));
        }
        holder.sernum.setText(club.getSerialNo());
        holder.vehiclenum.setText(club.getVehicleNo());
        holder.vehiclenum.setTextColor(Color.BLUE);
        holder.vehiclenum.setPaintFlags(holder.vehiclenum.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //holder.material.setText(club.getMaterial());
        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, Inward_Tanker_Weighment.class);
                intent.putExtra("vehicle_number", club.getVehicleNo());
                intent.putExtra("mode", "update"); // Important flag
                context.startActivity(intent);
            }
        });

        holder.partyname.setText(club.getPartyName());
        formattedDate = formatDate(club.getDate());
        holder.date.setText(formattedDate);
        holder.oapo.setText(club.getOA_PO_number());
        holder.mob.setText(club.getDriver_MobileNo());
        holder.grossweight.setText(String.valueOf(club.getGrossWeight()));
        holder.remark.setText(club.getRemark());
        holder.containerno.setText(club.getContainerNo());
        holder.sighby.setText(club.getSignBy());
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getInVehicleImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120, 120)
                .centerCrop().into(holder.invehicleimage);
        Picasso.get()
                .load(RetroApiClient.BASE_URL + club.getInDriverImage())
                .placeholder(R.drawable.gandhar)
                .error(R.drawable.gandhar2)
                .noFade().resize(120, 120)
                .centerCrop().into(holder.indriverimage);
        holder.invehicleimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onImageClick(RetroApiClient.BASE_URL + club.getInVehicleImage());
                }
            }
        });

        // Set click listener for the indriverimage
        holder.indriverimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onImageClick(RetroApiClient.BASE_URL + club.getInDriverImage());
                }
            }
        });
    }

    public int getItemCount() {
        return Gridmodel.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<CommonResponseModelForAllDepartment> filteredList = new ArrayList<>();
                    for (CommonResponseModelForAllDepartment club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNo().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNo().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(club);
                        }
                    }
                    filteredGridList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredGridList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredGridList = (ArrayList<CommonResponseModelForAllDepartment>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface OnImageClickListener {
        void onImageClick(String imageUrl);
    }

    public void setImageClickListener(OnImageClickListener listener) {
        this.imageClickListener = listener;
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public
        TextView sernum, vehiclenum, material, intime, outtime, date, partyname, remark, oapo, mob,
                grossweight, containerno, sighby;
        ImageView invehicleimage, indriverimage;

        public myviewHolder(View view) {
            super(view);
            sernum = view.findViewById(R.id.itinweitextcoSerialNumber);
            vehiclenum = view.findViewById(R.id.itinweitextcoVehicleNumber);
            material = view.findViewById(R.id.itinweitextcoMaterial);
            material.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            intime = view.findViewById(R.id.itinweitextcoInTime);
            outtime = view.findViewById(R.id.itinweitextcoOutTime);
            date = view.findViewById(R.id.itinweitextcodate);
            partyname = view.findViewById(R.id.itinweitextcopartyname);
            remark = view.findViewById(R.id.itinweitextcoremark);
            oapo = view.findViewById(R.id.itinweitextcooapo);
            mob = view.findViewById(R.id.itinweitextcomob);
            grossweight = view.findViewById(R.id.itinweitextcogrossweight);
            containerno = view.findViewById(R.id.itinweitextcocontainerno);
            invehicleimage = view.findViewById(R.id.itinweitextInVehicleImage);
            indriverimage = view.findViewById(R.id.itinweitextInDriverImage);
            sighby = view.findViewById(R.id.itinweitextcosighby);
        }
    }

    private void showMaterialDialog_InwardTanker(View view, String jsonMaterials) {
        // Parse the JSON list of extra materials
        List<matriallist> materialList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonMaterials);
            for (int i = 0; i < jsonArray.length(); i++) {
//                materialList.add(jsonArray.getString(i));
                JSONObject materialObject = jsonArray.getJSONObject(i);
                String Material = materialObject.getString("Material");
                int Qty = materialObject.getInt("Qty");
                String Qtyuom = materialObject.getString("Qtyuom");
                materialList.add(new matriallist(Material, Qty, Qtyuom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a dialog to show the list of materials
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Extra Materials");

        // Inflate the layout with a RecyclerView
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.material_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDialog);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Material_Adapter adapter = new Material_Adapter(materialList); // Pass the material list
        recyclerView.setAdapter(adapter);

        // Set the view and show the dialog
        builder.setView(dialogView);
        builder.setPositiveButton("Close", null);
        builder.show();
    }

    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    private void showUpdateDialog(View view, int position, String currentVehicleNo, String serialNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Update Vehicle Number");

// Create a vertical LinearLayout to hold both views
        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10); // Optional: Padding for nicer layout

        // Create and add a TextView for displaying the Serial Number
        TextView serialLabel = new TextView(view.getContext());
        serialLabel.setText("Serial No: " + serialNo);
        serialLabel.setTextSize(16);
        layout.addView(serialLabel);

        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(currentVehicleNo);
        input.setSelection(currentVehicleNo.length());
        layout.addView(input);
        builder.setView(layout);

        // Gross Weight input
        final EditText grossWeightInput = new EditText(view.getContext());
        grossWeightInput.setHint("Gross Weight");
        grossWeightInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        grossWeightInput.setText(String.valueOf(filteredGridList.get(position).getGrossWeight()));
        layout.addView(grossWeightInput);
        // Remarks input
        final EditText remarksInput = new EditText(view.getContext());
        remarksInput.setHint("Remarks (Reason for Update)");
        remarksInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        layout.addView(remarksInput);
        builder.setView(layout);

//        EditText hiddenSerialNo = new EditText(view.getContext());
//        hiddenSerialNo.setText(serialNo);
//        hiddenSerialNo.setVisibility(View.VISIBLE);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedVehicleNo = input.getText().toString().trim();
//            String hidden_SerialNo = hiddenSerialNo.getText().toString().trim();
            if (!updatedVehicleNo.isEmpty()) {
                // Update the filtered list
                filteredGridList.get(position).setVehicleNo(updatedVehicleNo);

                // Also update the original list
                for (CommonResponseModelForAllDepartment model : Gridmodel) {
                    if (model.getSerialNo().equals(filteredGridList.get(position).getSerialNo())) {
                        model.setVehicleNo(updatedVehicleNo);
                        break;
                    }
                }

                notifyItemChanged(position);

            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}
