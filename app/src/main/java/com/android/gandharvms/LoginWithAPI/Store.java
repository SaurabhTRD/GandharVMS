package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Truck_store.InTruckStoreRequestModel;
import com.android.gandharvms.Inward_Truck_store.InTruckStoreResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Store {
    @GET("api/InwardStore/GetInwardStoreList")
    Call<List<CommonResponseModelForAllDepartment>> getInTruckStoreListData(@Query("FromDate") String FromDate,
                                                                            @Query("Todate") String Todate,
                                                                            @Query("vehicleType") String vehicleType,
                                                                            @Query("inout") char inout);
    @GET("api/InwardStore/GetStoreByFetchVehicleDetails")
    Call<InTruckStoreResponseModel> getstorebyfetchVehData(@Query("vehicleNo") String vehicleNo,
                                                     @Query("vehicleType") String vehicleType,
                                                     @Query("NextProcess") char NextProcess,
                                                     @Query("inOut") char inOut);
    @POST("api/InwardStore/Add")
    Call<Boolean> insertStoreData(@Body InTruckStoreRequestModel insertstoremodel);
}
