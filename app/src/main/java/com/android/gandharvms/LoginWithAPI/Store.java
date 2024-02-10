package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabRequestModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Inward_Truck_store.InTruckStoreRequestModel;
import com.android.gandharvms.Inward_Truck_store.InTruckStoreResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Store {
    @GET("api/InwardStore/GetStoreList")
    Call<List<InTruckStoreResponseModel>> getInTruckStoreListData(@Query("NextProcess") char nextProcess);
    @GET("api/InwardStore/GetStoreByFetchVehicleDetails")
    Call<InTruckStoreResponseModel> getstorebyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                     @Query("vehicleType") String vehicleType,
                                                     @Query("NextProcess") char NextProcess,
                                                     @Query("inOut") char inOut);
    @POST("api/InwardStore/Add")
    Call<Boolean> insertStoreData(@Body InTruckStoreRequestModel insertstoremodel);
}
