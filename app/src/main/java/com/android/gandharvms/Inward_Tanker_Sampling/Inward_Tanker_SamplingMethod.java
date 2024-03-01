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

    @GET("api/InwardWeighment/GetInwardWeighmentList")
    Call<List<Inward_Tanker_SamplingResponseModel>> getIntankSamplingListingData(@Query("FromDate") String FromDate,
                                                                  @Query("Todate") String Todate,
                                                                  @Query("vehicleType") String vehicleType,
                                                                  @Query("inout") char inout);
    @POST("api/InwardSampling/Add")
    Call<Boolean> insertSamplingData(@Body Inward_Tanker_SamplingRequestModel inward_Tanker_SamplingRequestModel);

    @GET("api/InwardSampling/GetInwardSampling")
    Call<List<Inward_Tanker_SamplingResponseModel>> GetInwardSampling();

    @GET("api/InwardSampling/GetInwardSampling")
    Call<List<gridmodel_in_tanker_sampling>> GetInwardSamplinggrid();

    @GET("api/InwardSampling/GetSamplingList")
    Call<List<Inward_Tanker_SamplingResponseModel>> GetSamplingList(@Query("NextProcess") char NextProcess);

    @GET("api/InwardSampling/GetSamplingByFetchVehicleDetails")
    Call<InTanSamplingResponseModel> GetSamplingByFetchVehicleDetails(
            @Query("vehicleNo") String VehicleNo, @Query("vehicleType") String vehicleType,
            @Query("NextProcess") char NextProcess, @Query("inOut") char inOut);

}
