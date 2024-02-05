package com.android.gandharvms.Inward_Tanker_Security;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API_In_Tanker_Security {

    @POST("api/InwardSecurity/Add")
    Call<Boolean> postData(@Body Request_Model_In_Tanker_Security request);
}
