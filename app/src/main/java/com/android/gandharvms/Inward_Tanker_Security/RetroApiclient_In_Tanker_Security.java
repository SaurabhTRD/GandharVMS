package com.android.gandharvms.Inward_Tanker_Security;

import com.android.gandharvms.Inward_Tanker_Production.API_In_Tanker_production;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroApiclient_In_Tanker_Security {

    private static Retrofit retrofit = null;

    private static String BASE_URL = "http://52.183.160.211:8097/";

    public static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static API_In_Tanker_Security getinsecurityApi(){
        return getRetrofit().create(API_In_Tanker_Security.class);
    }
    public static API_In_Tanker_production getinproductionApi(){
        return getRetrofit().create(API_In_Tanker_production.class);
    }



}
