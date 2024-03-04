package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingMethod;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingRequestModel;
import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroApiClient {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://gandhar.azurewebsites.net/";

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
    public static API_In_Tanker_Security getserccrityveh(){
        return  getClient().create(API_In_Tanker_Security.class);
    }

    public static Inward_Tanker_SamplingMethod getInward_Tanker_Sampling(){
        return getClient().create(Inward_Tanker_SamplingMethod.class);
    }

    public static Weighment getWeighmentDetails(){
        return getClient().create(Weighment.class);
    }

    public static Laboratory getLabDetails(){
        return getClient().create(Laboratory.class);
    }

    public static Store getStoreDetails(){
        return getClient().create(Store.class);
    }

    public static Logistic getLogisticDetails() {return getClient().create(Logistic.class);}

}
