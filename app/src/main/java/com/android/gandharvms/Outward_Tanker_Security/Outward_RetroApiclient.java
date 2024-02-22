package com.android.gandharvms.Outward_Tanker_Security;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Outward_RetroApiclient {

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
    public static Outward_Tanker insertoutwardtankersecurity(){
        return getClient().create(Outward_Tanker.class);
    }
}
