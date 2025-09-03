package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Production_forms.Adapter_OT_completed_bulkload_production;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.ProductOA_Adapter;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.CompartmnetBindClass;
import com.android.gandharvms.productlistwithoanumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_OT_completed_bulkload_laboratory extends RecyclerView.Adapter<Adapter_OT_completed_bulkload_laboratory.myviewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private final List<Common_Outward_model> Gridmodel;
    private List<Common_Outward_model> filteredGridList;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private Context context;
    String formattedDate;
    private CompartmnetBindClass compartmentbinder= new CompartmnetBindClass();
    public Adapter_OT_completed_bulkload_laboratory(List<Common_Outward_model> gridmodel) {
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
    public Adapter_OT_completed_bulkload_laboratory.myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulkloadlab_complete_tablecell,viewGroup,false);
            return new Adapter_OT_completed_bulkload_laboratory.myviewHolder(view);
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ot_in_bulklab_tableitem, viewGroup, false);
            return new Adapter_OT_completed_bulkload_laboratory.myviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OT_completed_bulkload_laboratory.myviewHolder holder, int position) {

        Common_Outward_model club = filteredGridList.get(position);
        int intimelength = club.getLabInTime()!= null ? club.getLabInTime().length() :0;
        int outtimelength = club.getLabOutTime()!=null ? club.getLabOutTime().length() : 0;
        if (intimelength > 0) {
            holder.intime.setText(club.getLabInTime().substring(12, intimelength));
        }
        if (outtimelength > 0) {
            holder.outtime.setText(club.getLabOutTime().substring(12, outtimelength));
        }
        holder.serialnum.setText(club.getSerialNumber());
        holder.vehiclenum.setText(club.getVehicleNumber());
        holder.productoano.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.productoano.setOnClickListener(new View.OnClickListener() {
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
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment1, club.getLabcompartment1(),"Compartment 1");
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment2, club.getLabcompartment2(),"Compartment 2");
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment4, club.getLabcompartment4(),"Compartment 4");
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment5, club.getLabcompartment5(),"Compartment 5");
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment3, club.getLabcompartment3(),"Compartment 3");
        compartmentbinder.bindLabCompartment(holder.txtlabcompartment6, club.getLabcompartment6(),"Compartment 6");
       /* holder.viscosityindex.setText(String.valueOf(club.getViscosity_Index()));
        holder.density29.setText(String.valueOf(club.getDensity_29_5C()));
        holder.batchno.setText(club.getBatchNo());
        holder.qcsign.setText(club.getSignatureofOfficer());*/
        holder.proremark.setText(club.getProRemark());
        holder.billremark.setText(club.getTankerBillingRemark());
        holder.remark.setText(club.getLabRemark());
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
        public TextView serialnum,vehiclenum,productoano,density29,transporter,qcsign,
                customer,location,viscosityindex,intime,outtime,batchno,remark,proremark,billremark;
        TextView txtlabcompartment1,txtlabcompartment2,txtlabcompartment3,txtlabcompartment4,txtlabcompartment5,txtlabcompartment6;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            intime = itemView.findViewById(R.id.otinbulklabintime);
            outtime = itemView.findViewById(R.id.otinbulklabouttime);
            serialnum = itemView.findViewById(R.id.otbulklabserial);
            vehiclenum= itemView.findViewById(R.id.otbilklabvehivle);
            productoano=itemView.findViewById(R.id.otbilklabinprodoano);
            //oanum = itemView.findViewById(R.id.otbulkprocoanum);
            transporter = itemView.findViewById(R.id.otbulkinlabctransporter);
            //product = itemView.findViewById(R.id.otbulkinlabcprodcut);
            //howqty = itemView.findViewById(R.id.otbulkinlabchowqty);
            customer = itemView.findViewById(R.id.otbulkinlabccustomer);
            location = itemView.findViewById(R.id.otbulkinlablocation);
            /*viscosityindex=itemView.findViewById(R.id.otbulkviscosityindex);
            density29 = itemView.findViewById(R.id.otbilklabdensity);
            batchno = itemView.findViewById(R.id.otinbulklabbatchno);
            qcsign=itemView.findViewById(R.id.otbulklabqcsign);*/
            proremark=itemView.findViewById(R.id.otbulkproremark);
            billremark=itemView.findViewById(R.id.otbulkbillremark);
            remark = itemView.findViewById(R.id.otinbulklabremark);
            txtlabcompartment1=itemView.findViewById(R.id.otbulkinlabcompartment1);
            txtlabcompartment2=itemView.findViewById(R.id.otbulkinlabcompartment2);
            txtlabcompartment3=itemView.findViewById(R.id.otbulkinlabcompartment3);
            txtlabcompartment4=itemView.findViewById(R.id.otbulkinlabcompartment4);
            txtlabcompartment5=itemView.findViewById(R.id.otbulkinlabcompartment5);
            txtlabcompartment6=itemView.findViewById(R.id.otbulkinlabcompartment6);
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
