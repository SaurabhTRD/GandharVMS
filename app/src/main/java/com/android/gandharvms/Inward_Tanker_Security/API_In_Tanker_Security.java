package com.android.gandharvms.Inward_Tanker_Security;

import android.graphics.ColorSpace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_In_Tanker_Security {

    @POST("api/InwardSecurity/Add")
    Call<Boolean> postData(@Body Request_Model_In_Tanker_Security request);

    @POST("api/InwardSecurity/UpdateReport")
    Call<Boolean> updatesecuritydata(@Body Update_Request_Model_Insequrity request);

    @GET("api/InwardSecurity/GetSecurityByFetchVehicleDetails")
    Call<List<Respo_Model_In_Tanker_security>> GetIntankerSecurityByVehicle(
            @Query("vehicleNo") String VehicleNo,
            @Query("vehicleType") String vehicleType,
            @Query("NextProcess") char NextProcess,
            @Query("inOut") char inOut);

    @GET("api/InwardSecurity/GetInwardSecurityList")
    Call<List<ListingResponse_InTankerSequrity>> getintankersecurityListData(
            @Query("FromDate") String FromDate,
            @Query("Todate") String Todate);


}



