package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.RegisterwithAPI.RegRequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Weighment {
    @GET("api/InwardWeighment/GetWeighmentByFetchVehicleDetails")
    Call<InTanWeighResponseModel> getWeighbyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                         @Query("vehicleType") String vehicleType,
                                                         @Query("NextProcess") char NextProcess,
                                                         @Query("inOut") char inOut);
    @POST("api/InwardWeighment/Add")
    Call<Boolean> insertWeighData(@Body InTanWeighRequestModel insertweighmodel);
}
