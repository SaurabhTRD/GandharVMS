package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Adapter_OT_completed_Weighment;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.ProductOA_Adapter;
import com.android.gandharvms.R;
import com.android.gandharvms.productlistwithoanumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_inproc_prodcuction extends RecyclerView.Adapter<Adapter_OT_completed_inproc_prodcuction.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;

    String formattedDate;
    public Adapter_OT_completed_inproc_prodcuction(List<Common_Outward_model> gridmodel) {
        Gridmodel = gridmodel;
        this.filteredGridList = gridmodel;
//        this.context = context;
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
    public Adapter_OT_completed_inproc_prodcuction.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_inproc_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_inproc_prodcuction.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_inproc_tableitem, viewGroup, false);
            return new Adapter_OT_completed_inproc_prodcuction.myviewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_inproc_prodcuction.myviewHolder holder, int position) {
        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getProInTime()!=null ? club.getProInTime().length() : 0;
        int outtimelength = club.getProOutTime()!=null ? club.getProOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getProInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getProOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());

        holder.vehiclenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common_Outward_model club = filteredGridList.get(position);
                Intent intent = new Intent(view.getContext(), New_Outward_Tanker_Production.class);
                intent.putExtra("VehicleNumber", club.getVehicleNumber());
                intent.putExtra("Action", "Up");
                view.getContext().startActivity(intent);
            }
        });

        holder.prodOaNo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.prodOaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog_OutwardTanker(view, club.getProductQTYUOMOA());
            }
        });
        //holder.oanum.setText(club.getOAnumber());
        holder.transporter.setText(club.getTransportName());
        //holder.product.setText(club.getProductName());
        //holder.howqty.setText(String.valueOf(club.getHowMuchQuantityFilled()));
        holder.customer.setText(club.getCustomerName());
        holder.location.setText(club.getLocation());
        holder.tankerorblenderno.setText(club.getTankOrBlenderNo());
        holder.psign.setText(club.getPsign());
        holder.operatorsign.setText(club.getOperatorSign());
        holder.remark.setText(club.getProRemark());
    }

    @Override
    public int getItemCount() {
        return Gridmodel.size();
    }
    @Override
    public Filter getFilter() {
        return  new Filter()    {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredGridList = Gridmodel;
                } else {
                    List<Common_Outward_model> filteredList = new ArrayList<>();
                    for (Common_Outward_model club : Gridmodel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.getSerialNumber().toLowerCase().contains(charString.toLowerCase()) || club.getVehicleNumber().toLowerCase().contains(charString.toLowerCase())) {
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
                filteredGridList = (ArrayList<Common_Outward_model>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public TextView intime, outtime,serialnum,vehiclenum,prodOaNo,transporter,customer,location,tankerorblenderno,psign,operatorsign,remark;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime =itemView.findViewById(R.id.otinprointime);
            outtime=itemView.findViewById(R.id.otinprocouttime);
            serialnum = itemView.findViewById(R.id.otinprocserial);
            vehiclenum = itemView.findViewById(R.id.otinprocvehicle);
            prodOaNo=itemView.findViewById(R.id.otinprocProOANo);
            //flushingno = itemView.findViewById(R.id.otinprocflushing);
            //oanum = itemView.findViewById(R.id.otinprocoanum);
            transporter = itemView.findViewById(R.id.otinproctransporter);
            //product = itemView.findViewById(R.id.otinprocprodcut);
            //howqty = itemView.findViewById(R.id.otinprochowqty);
            customer = itemView.findViewById(R.id.otinproccustomer);
            location = itemView.findViewById(R.id.otinproclocation);
            tankerorblenderno = itemView.findViewById(R.id.otinprocpackingstatus);
            psign = itemView.findViewById(R.id.otinprocblendingstatus);
            operatorsign = itemView.findViewById(R.id.otinprocsigh);
            remark = itemView.findViewById(R.id.otinprocremark);

        }
    }

    private void showMaterialDialog_OutwardTanker(View view, String jsonMaterials) {
        // Parse the JSON list of extra materials
        List<productlistwithoanumber> materialList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonMaterials);
            for (int i = 0; i < jsonArray.length(); i++) {
//                materialList.add(jsonArray.getString(i));
                JSONObject materialObject = jsonArray.getJSONObject(i);
                String OANumber = materialObject.getString("OANumber");
                String Product = materialObject.getString("ProductName");
                String Qty = materialObject.getString("ProductQty");
                String Qtyuom = materialObject.getString("ProductQtyuom");
                materialList.add(new productlistwithoanumber(OANumber,Product, Qty, Qtyuom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a dialog to show the list of materials
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Product With OANo");

        // Inflate the layout with a RecyclerView
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.material_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDialog);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ProductOA_Adapter adapter = new ProductOA_Adapter(materialList); // Pass the material list
        recyclerView.setAdapter(adapter);

        // Set the view and show the dialog
        builder.setView(dialogView);
        builder.setPositiveButton("Close", null);
        builder.show();
    }
}
