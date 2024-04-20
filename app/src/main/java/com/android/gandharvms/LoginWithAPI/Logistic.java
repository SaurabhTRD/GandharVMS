package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabRequestModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Outward_Truck_Logistic.InTrLogisticRequestModel;
import com.android.gandharvms.Outward_Truck_Logistic.InTrLogisticResponseModel;
import com.android.gandharvms.Outward_Truck_Logistic.Update_Request_Model_Outward_Logistic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Logistic {
    @GET("api/OutwardLogistic/GetOutwardLogisticList")
    Call<List<InTrLogisticResponseModel>> getIntankLogisticListData(@Query("NextProcess") char nextProcess);
    @GET("api/OutwardLogistic/GetProductionAndLaboratoryByFetchVehicleDetails")
    Call<InTrLogisticResponseModel> getLogisticbyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                              @Query("vehicleType") String vehicleType,
                                                              @Query("NextProcess") char NextProcess,
                                                              @Query("inOut") char inOut);
    @POST("api/OutwardLogistic/Add")
    Call<Boolean> insertLogisticData(@Body InTrLogisticRequestModel insertLogisticmodel);

    //update outward logistic
    @POST("api/OutwardLogistic/UpdateLogisticOACustTransOutwardId")
    Call<Boolean> updateoutwardlogistic(@Body Update_Request_Model_Outward_Logistic updateRequestModelOutwardLogistic);
}
