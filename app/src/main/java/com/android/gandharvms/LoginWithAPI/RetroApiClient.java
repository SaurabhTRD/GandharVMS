package com.android.gandharvms.LoginWithAPI;

import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingMethod;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingRequestModel;
import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;
import com.android.gandharvms.NotificationAlerts.NotificationAlertsInterface;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production_interface;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.api.Http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetroApiClient {
    public static Retrofit retrofit = null;
    //When Given for Production then use Below URL
    //public static String BASE_URL = "https://gandhardevapi.azurewebsites.net/";
    //For Developer Use below URL and Check
    public static String BASE_URL = "https://gandharuatapi.azurewebsites.net/";
//    private static String BASE_URL = "http://52.183.160.211:8097/";
    //public static String UploadImage_URL="https://gandhardevapi.azurewebsites.net/api/Common/Upload";

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
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
    public static NotificationAlertsInterface NotificationInterface(){return  getClient().create(NotificationAlertsInterface.class);}



               ////////// Outward Tanker Truck //////////

    public static Outward_Tanker insertoutwardtankersecurity(){
        return getClient().create(Outward_Tanker.class);
    }
    public static Outward_Tanker_Billinginterface outwardTankerBillinginterface(){
        return getClient().create(Outward_Tanker_Billinginterface.class);
    }

    public static Outward_weighment outwardWeighment(){
        return getClient().create(Outward_weighment.class);
    }
    /*public static Outward_weighment outwardWeighment(){
        return getClient().create(Outward_weighment.class);
    }*/
    public static Outward_Tanker_Lab outwardTankerLab(){
        return getClient().create(Outward_Tanker_Lab.class);
    }
    public static Outward_Truck_interface outwardtruckdispatch(){
        return getClient().create(Outward_Truck_interface.class);
    }

    public static Outward_Truck_Production_interface outwardTruckProductionInterface(){
        return getClient().create(Outward_Truck_Production_interface.class);
    }

}
