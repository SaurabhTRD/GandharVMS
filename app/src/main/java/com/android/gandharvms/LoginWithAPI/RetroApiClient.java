package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingMethod;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingRequestModel;
import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroApiClient {
    public static Retrofit retrofit = null;
    public static String BASE_URL = "https://gandhardevapi.azurewebsites.net/";
//    private static String BASE_URL = "http://52.183.160.211:8097/";
    //public static String UploadImage_URL="https://gandhardevapi.azurewebsites.net/api/Common/Upload";

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
