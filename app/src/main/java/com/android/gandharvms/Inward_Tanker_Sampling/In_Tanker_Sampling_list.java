package com.android.gandharvms.Inward_Tanker_Sampling;

public class In_Tanker_Sampling_list {

    String Date,Sample_Reciving_Time,Sample_Submitted_Time,Vehicle_Number;

    public In_Tanker_Sampling_list() {

    }

    public In_Tanker_Sampling_list(String date, String sample_Reciving_Time, String sample_Submitted_Time, String vehicle_Number) {
        this.Date = date;
        this.Sample_Reciving_Time = sample_Reciving_Time;
        this.Sample_Submitted_Time = sample_Submitted_Time;
        this.Vehicle_Number = vehicle_Number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSample_Reciving_Time() {
        return Sample_Reciving_Time;
    }

    public void setSample_Reciving_Time(String sample_Reciving_Time) {
        Sample_Reciving_Time = sample_Reciving_Time;
    }

    public String getSample_Submitted_Time() {
        return Sample_Submitted_Time;
    }

    public void setSample_Submitted_Time(String sample_Submitted_Time) {
        Sample_Submitted_Time = sample_Submitted_Time;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }
}
