package com.android.gandharvms.Inward_Tanker_Production;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_In_Tanker_production {

    @GET("api/InwardProduction/GetProductionByFetchVehicleDetails")
    Call<Respo_Model_In_Tanker_Production> GetinTankerprodcutionByvehicle(
           @Query("vehicleNo") String vehicleNo,
           @Query("vehicleType") String vehicleType,
           @Query("NextProcess") char NextProcess,
           @Query("inOut") char inOut
    );

    @POST("api/InwardProduction/Add")
    Call<Boolean> insertproductionData(@Body Request_In_Tanker_Production requestInTankerProduction);

    @POST("api/InwardProduction/UpdateInwardTProductionByInwardId")
    Call<Boolean> updprobyinwardid(@Body It_Pro_UpdateByInwardid_req_model updproreqmodel);

    /*@GET("api/InwardProduction/GetProductionList")
    Call<List<ListingResponse_InTankerproduction>> getintankerproductionListdata(@Query("NextProcess")char nextProcess);*/

    @GET("api/InwardProduction/GetProductionList")
    Call<List<CommonResponseModelForAllDepartment>> getintankerproductionListdata(@Query("FromDate") String FromDate,
                                                                                  @Query("Todate") String Todate,
                                                                                  @Query("vehicleType") String vehicleType,
                                                                                  @Query("inout") char inout);
}
