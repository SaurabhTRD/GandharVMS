package com.android.gandharvms.Outward_Tanker_Security;

import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.Outward_Truck_Security.Model_OutwardOut_Truck_Security;

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

    //outwardout tanker security
    @POST("api/OutwardSecurity/UpdateOutwardOutSecurityDetails")
    Call<Boolean> updateOutwardoutsecurity(@Body Model_OutwardOut_Security request);

    //outwardout_security update
    @POST("api/OutwardSecurity/UpdateOutwardOutSecurityDetails")
    Call<Boolean> updateout_Truck_wardoutsecurity(@Body Model_OutwardOut_Truck_Security request);


    //outwardtruck_completed
    @GET("api/OutwardSecurity/GetOutwardSecurityList")
    Call<List<Common_Outward_model>> getintrucksecuritycompleted(@Query("FromDate")String FromDate,
                                                                  @Query("Todate") String Todate,
                                                                 @Query("vehicletype") String vehicletype,
                                                                 @Query("nextprocess") char nextprocess,
                                                                 @Query("inout") char inout);


    //OR_Logi
    @GET("api/OutwardLogistic/GetOutwardLogisticList")
    Call<List<Common_Outward_model>> getintrucklogicomplete(@Query("FromDate")String FromDate,
                                                            @Query("Todate") String Todate,
                                                            @Query("vehicletype") String vehicletype,
                                                            @Query("nextprocess") char nextprocess,
                                                            @Query("inout") char inout);


    //OR_Weigh_complete
    @GET("api/OutwardWeighment/GetOutwardWeighmentList")
    Call<List<Common_Outward_model>> gettruckweighcomplete(@Query("FromDate")String FromDate,
                                                           @Query("Todate") String Todate,
                                                           @Query("vehicletype") String vehicletype,
                                                           @Query("nextprocess") char nextprocess,
                                                           @Query("inout") char inout);

    //OR_Desp_Complete
    @GET("api/OutwardTruckBillingDispatch/GetOutwardTruckBillingDispatchList")
    Call<List<Common_Outward_model>> gettruckdespatchcomplete(@Query("FromDate")String FromDate,
                                                              @Query("Todate") String Todate,
                                                              @Query("vehicletype") String vehicletype,
                                                              @Query("nextprocess") char nextprocess,
                                                              @Query("inout") char inout);

    //OR_Billing
    @GET("api/OutwardTruckBillingDispatch/GetOutwardTruckBillingDispatchList")
    Call<List<Common_Outward_model>> gettruckoutbillingcomplete(@Query("FromDate")String FromDate,
                                                                @Query("Todate") String Todate,
                                                                @Query("vehicletype") String vehicletype,
                                                                @Query("nextprocess") char nextprocess,
                                                                @Query("inout") char inout);
}
