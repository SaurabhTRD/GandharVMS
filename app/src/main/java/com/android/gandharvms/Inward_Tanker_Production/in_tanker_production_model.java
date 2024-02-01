package com.android.gandharvms.Inward_Tanker_Production;

public class in_tanker_production_model {

    String Serial_Number,Vehicle_Number,Material;

    public in_tanker_production_model() {
    }

    public in_tanker_production_model(String serial_Number, String vehicle_Number, String material) {
        Serial_Number = serial_Number;
        Vehicle_Number = vehicle_Number;
        Material = material;
    }

    public String getSerial_Number() {
        return Serial_Number;
    }

    public void setSerial_Number(String serial_Number) {
        Serial_Number = serial_Number;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }
}
