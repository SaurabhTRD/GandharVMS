package com.android.gandharvms.Outward_Truck_Dispatch;

import com.android.gandharvms.Outward_Tanker_Weighment.Tanker_verification_model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Outward_Truck_interface {

    @GET("api/OutwardTruckBillingDispatch/GetOutwardTruckBillingDispatchByFetchVehicleDetails")
    Call<Model_Outward_Truck_Dispatch> fetchdispatch(
            @Query("vehicleNo")String vehicleNo,
            @Query("vehicleType")String vehicleType,
            @Query("NextProcess")char NextProcess,
            @Query("inOut")char inOut
    );

    @POST("api/OutwardTruckBillingDispatch/AddOutwardDispatch")
    Call<Boolean> insertdispatch(@Body Model_Outward_Truck_Dispatch request);

    @GET("api/OutwardIndusSmallDispatch/GetOutwardIndusSmallDispatchByFetchVehicleDetails")
    Call<Model_industrial> fetchindusrtial( @Query("vehicleNo")String vehicleNo,
                                            @Query("vehicleType")String vehicleType,
                                            @Query("NextProcess")char NextProcess,
                                            @Query("inOut")char inOut
    );

    //indus insert
    @POST("api/OutwardIndusSmallDispatch/AddOutwardIndusPackLoadingDispatch")
    Call<Boolean> insertindustrial(@Body Model_industrial request);

    //indus update
    @POST("api/OutwardIndusSmallDispatch/UpdateOutwardIndusPackLoadingDispatch")
    Call<Boolean> updateindustrial(@Body Indus_update_Model request);


    @POST("api/OutwardIndusSmallDispatch/AddOutwardSmallPackLoadingDispatch")
    Call<Boolean> insertsmallpack(@Body SmallPcak_Model request);

    //small_pack_update
    @POST("api/OutwardIndusSmallDispatch/UpdateOutwardSmallPackLoadingDispatch")
    Call<Boolean> updatesmallpack(@Body Update_SmallPack_Model request);

    //weigmnet varify_Indus
    @POST("api/OutwardWeighment/UpdateOutwardWeighmnetVerifiedDestIl")
    Call<Boolean> weighmentvarified(@Body Varified_Model request);
    //weighment_varify_small
    @POST("api/OutwardWeighment/UpdateOutwardWeighmnetVerifiedDestSPL")
    Call<Boolean> smallpackvarified(@Body Verified_Small_pack request );

    //indus_small fetch for weighmengt
    @GET("api/OutwardIndusSmallDispatch/GetOutwardIndusSmallDispatchByFetchVehicleDetails")
    Call<Model_industrial> fetchidnussmall(@Query("vehicleNo")String vehicleNo,
                                           @Query("vehicleType")String vehicleType,
                                           @Query("NextProcess")char NextProcess,
                                           @Query("inOut")char inOut);


    @POST("api/OutwardWeighment/UpdateOutwardWeighmnetVerifiedTanker")
    Call<Boolean> Tanker_weighmentvarified(@Body Tanker_verification_model request);
}
