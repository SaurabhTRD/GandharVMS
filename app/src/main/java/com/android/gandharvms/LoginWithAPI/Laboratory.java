package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabRequestModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Laboratory {
    @GET("api/InwardLaboratory/GetLaboratoryList")
    Call<List<InTanLabResponseModel>> getIntankLabListData(@Query("NextProcess") char nextProcess);
    @GET("api/InwardLaboratory/GetLaboratoryByFetchVehicleDetails")
    Call<InTanLabResponseModel> getLabbyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                         @Query("vehicleType") String vehicleType,
                                                         @Query("NextProcess") char NextProcess,
                                                         @Query("inOut") char inOut);
    @POST("api/InwardLaboratory/Add")
    Call<Boolean> insertLabData(@Body InTanLabRequestModel insertLabmodel);
}
