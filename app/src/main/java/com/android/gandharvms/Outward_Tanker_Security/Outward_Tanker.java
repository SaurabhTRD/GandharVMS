package com.android.gandharvms.Outward_Tanker_Security;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Outward_Tanker {

    @POST("api/OutwardSecurity/Add")
    Call<Boolean> outwardtankerinsert(@Body Request_Model_Outward_Tanker_Security request);
}
