package com.android.gandharvms.LoginWithAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroApiClient {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "http://172.20.22.90/gandharvms/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static LoginMethod getLoginApi() {
        return getClient().create(LoginMethod.class);
    }
}
