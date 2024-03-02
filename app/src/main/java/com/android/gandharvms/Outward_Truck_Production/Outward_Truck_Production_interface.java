package com.android.gandharvms.Outward_Truck_Production;

import com.android.gandharvms.Outward_Truck_Billing.Model_Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Laboratory.Model_Outward_Truck_Lab;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Outward_Truck_Production_interface {

    @POST("api/OutwardTruckBillingDispatch/UpdateOutwardTruckProduction")
    Call<Boolean> updateouttruckproduction(@Body Outward_Truck_Production_Model request);

    @POST("api/OutwardTruckBillingDispatch/UpdateOutwardTruckBilling")
    Call<Boolean> updateoutwardoutbilling(@Body Model_Outward_Truck_Billing request);

    @POST("api/OutwardTruckBillingDispatch/UpdateOutwardTruckLaboratory")
    Call<Boolean> updateoutwardtrucklab(@Body Model_Outward_Truck_Lab request);
}
