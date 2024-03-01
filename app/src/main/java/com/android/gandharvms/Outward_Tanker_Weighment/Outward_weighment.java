package com.android.gandharvms.Outward_Tanker_Weighment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_weighment {
    @GET("api/OutwardWeighment/GetOutwardWeighmentByFetchVehicleDetails")
    Call<Response_Outward_Tanker_Weighment> fetchweighment(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut);

    @POST("api/OutwardWeighment/Add")
    Call<Boolean> updateweighmentoutwardtanker(@Body Response_Outward_Tanker_Weighment request);

    //outwardout weighment update
    @POST("api/OutwardWeighment/UpdateOutwardOutWeighmentDetails")
    Call<Boolean> updateoutwardoutweighment(@Body Model_OutwardOut_Weighment request);
}
