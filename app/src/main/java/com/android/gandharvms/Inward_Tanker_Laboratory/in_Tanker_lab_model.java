package com.android.gandharvms.Inward_Tanker_Laboratory;

public class in_Tanker_lab_model {
    String Material,Date_and_Time,Vehicle_Number;

    public in_Tanker_lab_model() {
    }

    public in_Tanker_lab_model(String material, String date_and_Time, String vehicle_Number) {
        Material = material;
        Date_and_Time = date_and_Time;
        Vehicle_Number = vehicle_Number;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getDate_and_Time() {
        return Date_and_Time;
    }

    public void setDate_and_Time(String date_and_Time) {
        Date_and_Time = date_and_Time;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }
}
