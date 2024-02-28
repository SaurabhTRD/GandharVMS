package com.android.gandharvms.Outward_Tanker_Security;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_Tanker {

    @GET("api/Outward/GetMaxSerialNumberOfOutward")
    Call<String> getmaxserialnumberoutward(@Query("FormattedDate") String formattedDate);
    @POST("api/OutwardSecurity/Add")
    Call<Boolean> outwardtankerinsert(@Body Request_Model_Outward_Tanker_Security request);
    @GET("api/OutwardSecurity/GetOutwardSecurityByFetchVehicleDetails")
    Call<List<Response_Outward_Security_Fetching>> outwardsecurityfetching(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut);
    @POST("api/OutwardSecurity/UpdateReportingDetails")
    Call<Boolean> updateoutwardsecurity(@Body Isreportingupdate_Security_model request);



}
