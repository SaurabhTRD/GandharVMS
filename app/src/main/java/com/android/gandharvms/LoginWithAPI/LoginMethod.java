package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Truck_store.InTruckStoreResponseModel;
import com.android.gandharvms.RegisterwithAPI.RegRequestModel;
import com.android.gandharvms.RegisterwithAPI.RegResponseModel;

import org.apache.commons.collections4.Get;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginMethod {
    @GET("api/Inward/GetMaxSerialNumber")
    Call<String> getMaxSerialNumber(@Query("FormattedDate") String formattedDate);
    @GET("api/Users/GetUsersList")
    Call<List<ResponseModel>> getUsersListData();
    @POST("api/Users/Login")
    Call<List<ResponseModel>> postData(@Body RequestModel request);

    @POST("api/Users/Add")
    Call<Boolean> postregData(@Body RegRequestModel regRequestModel);
}
