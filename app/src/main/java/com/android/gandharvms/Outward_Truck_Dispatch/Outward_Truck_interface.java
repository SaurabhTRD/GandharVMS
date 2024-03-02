package com.android.gandharvms.Outward_Truck_Dispatch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_Truck_interface {

    @GET("api/OutwardTruckBillingDispatch/GetOutwardTruckBillingDispatchByFetchVehicleDetails")
    Call<Model_Outward_Truck_Dispatch> fetchdispatch(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut
    );

    @POST("api/OutwardTruckBillingDispatch/AddOutwardDispatch")
    Call<Boolean> insertdispatch(@Body Model_Outward_Truck_Dispatch request);
}
