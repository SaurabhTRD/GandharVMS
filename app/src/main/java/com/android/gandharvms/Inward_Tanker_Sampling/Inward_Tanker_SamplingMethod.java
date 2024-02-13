package com.android.gandharvms.Inward_Tanker_Sampling;

import com.android.gandharvms.Inward_Tanker_Security.Request_Model_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.RegisterwithAPI.RegRequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Inward_Tanker_SamplingMethod {
    @POST("api/InwardSampling/Add")
    Call<Boolean> postAdd(@Body Inward_Tanker_SamplingRequestModel inward_Tanker_SamplingRequestModel);

    @POST("api/InwardSampling/Add")
    Call<Boolean> postUpdate(@Body Inward_Tanker_SamplingRequestModel inward_Tanker_SamplingRequestModel);

    @POST("api/InwardSampling/Delete")
    Call<Boolean> postDelete(@Body Inward_Tanker_SamplingRequestModel inward_Tanker_SamplingRequestModel);

    @GET("api/InwardSampling/GetInwardSampling")
    Call<List<Respo_Model_In_Tanker_security>> GetInwardSampling();

    @GET("api/InwardSampling/GetInwardSamplingById")
    Call<List<Respo_Model_In_Tanker_security>> GetInwardSamplingById(@Query("id") int id);

    @GET("api/InwardSampling/GetSamplingList")
    Call<List<Respo_Model_In_Tanker_security>> GetSamplingList(@Query("NextProcess") char NextProcess);

    @GET("api/InwardSampling/GetSamplingPendingList")
    Call<List<Respo_Model_In_Tanker_security>> GetSamplingPendingList(@Query("vehicleNo") String VehicleNo,
                                                                      @Query("NextProcess") char NextProcess);

    @GET("api/InwardSampling/GetSamplingByFetchVehicleDetails")
    Call<List<Respo_Model_In_Tanker_security>> GetSamplingByFetchVehicleDetails(
            @Query("vehicleNo") String VehicleNo, @Query("vehicleType") String vehicleType,
            @Query("NextProcess") char NextProcess, @Query("inOut") char inOut);

}
