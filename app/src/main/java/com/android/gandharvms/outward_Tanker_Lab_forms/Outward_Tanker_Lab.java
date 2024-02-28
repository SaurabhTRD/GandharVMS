package com.android.gandharvms.outward_Tanker_Lab_forms;

import com.android.gandharvms.Outward_Tanker_Production_forms.Production_Model_Outward;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_Tanker_Lab {

    @GET("api/OutwardProductionAndLaboratory/GetProductionAndLaboratoryByFetchVehicleDetails")
    Call<Lab_Model__Outward_Tanker> fetchlab(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut
    );
    @POST("api/OutwardProductionAndLaboratory/AddBlendingRatio")
    Call<Boolean> addblendingration(@Body Lab_Model__Outward_Tanker request);

    @POST("api/OutwardProductionAndLaboratory/UpdateProductionInProcessForm")
    Call<Boolean> insertinprocessproduction(@Body Production_Model_Outward request);

    @POST("api/OutwardProductionAndLaboratory/UpdateLaboratoryInProcessForm")
    Call<Boolean> insertinprocessLaboratory(@Body Lab_Model_insert_Outward_Tanker request);
}
