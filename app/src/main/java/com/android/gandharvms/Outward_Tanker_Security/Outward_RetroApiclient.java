package com.android.gandharvms.Outward_Tanker_Security;

import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production_interface;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Outward_RetroApiclient {

    private static Retrofit retrofit = null;
    //When Given for Production then use Below URL
    //public static String BASE_URL = "https://gandhardevapi.azurewebsites.net/";
    //For Developer Use below URL and Check
    public static String BASE_URL = "https://gandharuatapi.azurewebsites.net/";

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
