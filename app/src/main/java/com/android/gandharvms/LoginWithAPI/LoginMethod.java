package com.android.gandharvms.LoginWithAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginMethod {
    @POST("api/Users/Login")
    Call<List<ResponseModel>> postData(@Body RequestModel request);
}
