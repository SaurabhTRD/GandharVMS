package com.android.gandharvms.Outward_Tanker_Billing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_Tanker_Billinginterface {
    @GET("api/OutwardBilling/GetBillingByFetchVehicleDetails")
    Call<Respons_Outward_Tanker_Billing>outwardbillingfetching(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut);

    @POST("api/OutwardBilling/Add")
    Call<Boolean> updatebillingoanumber(@Body Respons_Outward_Tanker_Billing request);
}
