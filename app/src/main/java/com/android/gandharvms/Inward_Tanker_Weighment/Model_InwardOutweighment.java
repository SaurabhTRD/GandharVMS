package com.android.gandharvms.Inward_Tanker_Weighment;

public class Model_InwardOutweighment {
    private int InwardId;
    private String GrossWeight;
    private String NetWeight;
    private String TareWeight;
    private String OutVehicleImage;
    private String OutDriverImage;
    private char Nextprocess;
    private char I_O;
    private String VehicleType;
    private String OutInTime;

    public Model_InwardOutweighment(int inwardId, String grossWeight, String netWeight, String tareWeight, String outVehicleImage, String outDriverImage, char nextprocess, char i_O, String vehicleType, String outInTime) {
        InwardId = inwardId;
        GrossWeight = grossWeight;
        NetWeight = netWeight;
        TareWeight = tareWeight;
        OutVehicleImage = outVehicleImage;
        OutDriverImage = outDriverImage;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        OutInTime = outInTime;
    }
}
