package com.android.gandharvms.Inward_Tanker_Sampling;

public class gridmodel_in_tanker_sampling {
    String Date,Vehicle_Number,Sample_Reciving_Time;

    public gridmodel_in_tanker_sampling() {
    }

    public gridmodel_in_tanker_sampling(String date, String vehicle_Number, String sample_Reciving_Time) {
        Date = date;
        Vehicle_Number = vehicle_Number;
        Sample_Reciving_Time = sample_Reciving_Time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }

    public String getSample_Reciving_Time() {
        return Sample_Reciving_Time;
    }

    public void setSample_Reciving_Time(String sample_Reciving_Time) {
        Sample_Reciving_Time = sample_Reciving_Time;
    }
}
