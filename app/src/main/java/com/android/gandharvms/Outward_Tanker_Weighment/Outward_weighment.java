package com.android.gandharvms.Outward_Tanker_Weighment;

import com.android.gandharvms.Outward_Truck_Dispatch.Varified_Model;
import com.android.gandharvms.Outwardout_Tanker_Weighment.out_weighment_model;

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

    //outwardout weighment update olde
    @POST("api/OutwardWeighment/UpdateOutwardOutWeighmentDetails")
    Call<Boolean> updateoutwardoutweighment(@Body Model_OutwardOut_Weighment request);

    //for out weighment tanker new
    @POST("api/OutwardWeighment/UpdateOutwardOutWeighmentDetailsOutTanker")
    Call<Boolean> updateoutwardoutweighment_outTanker(@Body out_weighment_model request);

    @POST("api/OutwardWeighment/UpdateOutwardOutWeighmentDetails")
    Call<Boolean> updateoutwardouttruckweighment(@Body Model_OutwardOut_Truck_Weighment request);

    @POST("api/OutwardIndusSmallDispatch/AddOutwardIndusSmallDispatch")
    Call<Boolean> varifyindus(@Body Varified_Model request);
}
