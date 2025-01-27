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
    public String UpdatedBy;
    public String ShortageDip;
    public String ShortageWeight ;
    public String shortagedipinliter;
    public String shortageweightinkg;
    public Model_InwardOutweighment(int inwardId, String grossWeight, String netWeight, String tareWeight,
                String outVehicleImage, String outDriverImage, char nextprocess, char i_O, String vehicleType,
                String outInTime,String updatedBy,String shortageDip,String shortageWeight,String dipinliter,String weightinkg) {
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
        UpdatedBy=updatedBy;
        ShortageDip =shortageDip;
        ShortageWeight = shortageWeight;
        shortagedipinliter=dipinliter;
        shortageweightinkg=weightinkg;
    }
}
