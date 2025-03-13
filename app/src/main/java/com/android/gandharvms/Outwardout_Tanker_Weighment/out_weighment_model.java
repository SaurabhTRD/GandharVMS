package com.android.gandharvms.Outwardout_Tanker_Weighment;

public class out_weighment_model {
    public int Id;
    public int OutwardId;
    public String Intime;
    public String OutTime;
    public String InDriverImage;
    public String InVehicleImage;
    public String OutDriverImage;
    public String OutVehicleImage;
    public String OutInTime;
    public String NetWeight;
    public String TareWeight;
    public String GrossWeight;
    public String NumberofPack;
    public String OutWRemark;
    public String SealNumber;
    public char CurrentProcess;
    public String Remark;
    public boolean IsActive;
    public String CreatedBy;
    public String CreatedDate;
    public String UpdatedBy;
    public String UpdatedDate;
    public String SerialNumber;
    public String VehicleNumber;
    public String TransportName;
    public String MobileNumber;
    public String CapacityVehicle;
    public String ConditionOfVehicle;
    public String Date;
    public String MaterialName;
    public String CustomerName;
    public String OAnumber;
    public int TankerNumber;
    public String ProductName;
    public int HowMuchQuantityFilled;
    public String Location;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    int ShortageDip;
    int ShortageWeight;

    public String compartment1;
    public String compartment2;
    public String compartment3;
    public String compartment4;
    public String compartment5;
    public String compartment6;


    public out_weighment_model(int outwardId, String outDriverImage, String outVehicleImage, String outInTime, String netWeight, String grossWeight, String numberofPack, String outWRemark, String sealNumber, String updatedBy, char nextProcess, char i_O, String vehicleType,int shortageDip,int shortageWeight,
                               String Compartment1,String Compartment2,String Compartment3,String Compartment4,String Compartment5,String Compartment6) {
        OutwardId = outwardId;
        OutDriverImage = outDriverImage;
        OutVehicleImage = outVehicleImage;
        OutInTime = outInTime;
        NetWeight = netWeight;
        GrossWeight = grossWeight;
        NumberofPack = numberofPack;
        OutWRemark = outWRemark;
        SealNumber = sealNumber;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        ShortageDip = shortageDip;
        ShortageWeight = shortageWeight;
        compartment1 = Compartment1;
        compartment2 = Compartment2;
        compartment3 = Compartment3;
        compartment4 = Compartment4;
        compartment5 = Compartment5;
        compartment6 = Compartment6;

    }

}
