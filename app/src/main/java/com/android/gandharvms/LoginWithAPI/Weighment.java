package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Model_InwardOutweighment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Weighment {
   /* @GET("api/InwardWeighment/GetWeighmentList")
    Call<List<InTanWeighResponseModel>> getIntankWeighListData(@Query("NextProcess") char nextProcess);*/

    @GET("api/InwardWeighment/GetInwardWeighmentList")
    Call<List<CommonResponseModelForAllDepartment>> getIntankWeighListingData(@Query("FromDate") String FromDate,
                                                                              @Query("Todate") String Todate,
                                                                              @Query("vehicleType") String vehicleType,
                                                                              @Query("vehicleno")String vehicleno,
                                                                              @Query("inout") char inout);
    @GET("api/InwardWeighment/GetWeighmentByFetchVehicleDetails")
    Call<InTanWeighResponseModel> getWeighbyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                         @Query("vehicleType") String vehicleType,
                                                         @Query("NextProcess") char NextProcess,
                                                         @Query("inOut") char inOut);
    @POST("api/InwardWeighment/Add")
    Call<Boolean> insertWeighData(@Body InTanWeighRequestModel insertweighmodel);

    @POST("api/InwardWeighment/UpdateOutWeighmentDetails")
    Call<Boolean> inwardoutweighment(@Body Model_InwardOutweighment modelInwardOutweighment);
}
