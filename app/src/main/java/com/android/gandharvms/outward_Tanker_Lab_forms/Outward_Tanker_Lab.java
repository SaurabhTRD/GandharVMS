package com.android.gandharvms.outward_Tanker_Lab_forms;

import com.android.gandharvms.OutwardOutDataEntryForm_Production.otoutDataEntryProduction_RequestModel;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Production_Model_Outward;
import com.android.gandharvms.Outward_Tanker_Production_forms.Production_Model_Outward;
import com.android.gandharvms.Outward_Tanker_Production_forms.Production_Model_Update;
import com.android.gandharvms.Outward_Tanker_Production_forms.Production_bulkloading_model;
import com.android.gandharvms.Outward_Tanker_Production_forms.Request_Model_blendflush;

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

    @POST("api/OutwardBulkProductionAndLaboratory/AddOutwardBulkProductionForm")
    Call<Boolean> insertbulkloadingproduction(@Body Production_bulkloading_model request);


    //bulkloading form lab outward tanker
    @GET("api/OutwardBulkProductionAndLaboratory/GetProductionAndLaboratoryByFetchVehicleDetails")
    Call<Lab_Model_Bulkloading> fetchlabbulkloding(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut
    );

    // update bulk loading outward tanker lab
    @POST("api/OutwardBulkProductionAndLaboratory/UpdateOutwardBulkLabBatchNo")
    Call<Boolean> updatebulkloadingform(@Body Lab_Model_Bulkloading request);

    //update production bulkloading form
    @POST("api/OutwardBulkProductionAndLaboratory/UpdateOutwardBulkProductionFormOperatorDetails")
    Call<Boolean> updatebulkloadingproduction(@Body Production_Model_Update request);

    //request_flush_blending_production_to_lab
    @POST("api/OutwardProductionAndLaboratory/BlendingnFlushingReq")
    Call<Boolean> requestflushblend(@Body Request_Model_blendflush request);

    //update_flush_blending_production_to_lab
    @POST("api/OutwardProductionAndLaboratory/UpdateBlendingnFlushingStatus")
    Call<Boolean> updateflushblend(@Body Blending_Flushing_Model request);

    @POST("api/OutwardProductionAndLaboratory/UpdateDataEntryProductionData")
    Call<Boolean> updateDataEntryFormProduction(@Body otoutDataEntryProduction_RequestModel request);

    //new outward tanker production data insert
    @POST("api/OutwardBulkProductionAndLaboratory/InsertOutwardOTProduction")
    Call<Boolean> newOutwardTankerProduction(@Body New_Production_Model_Outward request);

    //new outward tanker Laboratory data insert
    @POST("api/OutwardProductionAndLaboratory/InsertOutwardOTLaboratory")
    Call<Boolean> newOutwardTankerLaboratory(@Body New_Lab_Model_OutwardTanker request);
}
