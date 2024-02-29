package com.android.gandharvms.Outward_Tanker_Security;

import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;

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
}
