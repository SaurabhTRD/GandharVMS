package com.android.gandharvms.Inward_Tanker_Security;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Truck_Security.ir_in_updsecbyinwardid_re_model;
import com.android.gandharvms.VehicleExitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_In_Tanker_Security {

    @POST("api/InwardSecurity/Add")
    Call<Boolean> postData(@Body Request_Model_In_Tanker_Security request);

    @POST("api/InwardSecurity/UpdateReport")
    Call<Boolean> updatesecuritydata(@Body Update_Request_Model_Insequrity request);

    @POST("api/InwardSecurity/UpdateIRInSecurityByInwardId")
    Call<Boolean> irinupdsecbyinwardid(@Body ir_in_updsecbyinwardid_re_model irinsecupdbyinwardid);

    @GET("api/InwardSecurity/GetSecurityByFetchVehicleDetails")
    Call<List<Respo_Model_In_Tanker_security>> GetIntankerSecurityByVehicle(
            @Query("vehicleNo") String VehicleNo,
            @Query("vehicleType") String vehicleType,
            @Query("NextProcess") char NextProcess,
            @Query("inOut") char inOut);

    @GET("api/InwardCommon/GetInwardDataByDate")
    Call<List<Respo_Model_In_Tanker_security>> GetInwarddatebyfilterdatewise(
            @Query("FromDate") String fromdate,
            @Query("ToDate") String todate,
            @Query("vehicleNo") String VehicleNo,
            @Query("vehicleType") String vehicleType,
            @Query("NextProcess") char NextProcess,
            @Query("inOut") char inOut);

    @GET("api/InwardSecurity/GetInwardSecurityList")
    Call<List<CommonResponseModelForAllDepartment>> getintankersecurityListData(
            @Query("FromDate") String FromDate,
            @Query("Todate") String Todate,
            @Query("vehicletype") String vehicletype,
            @Query("inout") char inout);

    @GET("api/InwardCommon/GetInwardCompDepartmentWiseFactoryOutListing")
    Call<List<CommonResponseModelForAllDepartment>> getitreportdata(
            @Query("FromDate") String fromdate,
            @Query("ToDate") String todate,
            @Query("vehicleType") String vehicletype);

    @POST("api/InwardSecurity/UpdateOutSecurityDetails")
    Call<Boolean> intankersecurityoutupdate(@Body UpdateOutSecurityRequestModel updateOutSecurity);

    @POST("api/InwardSecurity/UpdateITInSecurityByInwardId")
    Call<Boolean> itinsecupd(@Body It_in_updsecbyinwardid_req_model updsecititin);

    @POST("api/InwardSecurity/verifyvehicleexits")
    Call<VehicleExitResponse> checkvehicleexits(@Query("vehicleNo") String vehicleNo,
                                                @Query("vehicleType") String vehicleType);
}



