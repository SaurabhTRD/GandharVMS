package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Repet_update_Model {
    private int outwardId;

    public String PurposeProcess;
    private String compartment1;
    private String compartment2;
    private String compartment3;
    private String compartment4;
    private String compartment5;
    private String compartment6;

    public String SerialNumber;
    public String VehicleNumber;
    public char Nextprocess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;
//    public int CompartmentCount;
//    public boolean IsCheck;


    public Repet_update_Model(int outwardId, String purposeProcess, String compartment1, String compartment2, String compartment3, String compartment4, String compartment5, String compartment6, String serialNumber, String vehicleNumber, char nextprocess, char i_O, String vehicleType, String updatedBy) {
        this.outwardId = outwardId;
        PurposeProcess = purposeProcess;
        this.compartment1 = compartment1;
        this.compartment2 = compartment2;
        this.compartment3 = compartment3;
        this.compartment4 = compartment4;
        this.compartment5 = compartment5;
        this.compartment6 = compartment6;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
    }

    public int getOutwardId() {
        return outwardId;
    }

    public void setOutwardId(int outwardId) {
        this.outwardId = outwardId;
    }

    public String getCompartment1() {
        return compartment1;
    }

    public void setCompartment1(String compartment1) {
        this.compartment1 = compartment1;
    }

    public String getCompartment2() {
        return compartment2;
    }

    public void setCompartment2(String compartment2) {
        this.compartment2 = compartment2;
    }

    public String getCompartment3() {
        return compartment3;
    }

    public void setCompartment3(String compartment3) {
        this.compartment3 = compartment3;
    }

    public String getCompartment4() {
        return compartment4;
    }

    public void setCompartment4(String compartment4) {
        this.compartment4 = compartment4;
    }

    public String getCompartment5() {
        return compartment5;
    }

    public void setCompartment5(String compartment5) {
        this.compartment5 = compartment5;
    }

    public String getCompartment6() {
        return compartment6;
    }

    public void setCompartment6(String compartment6) {
        this.compartment6 = compartment6;
    }

 //------------------------------------------------------------------------------------
//    private int outwardId;
//    private String PurposeProcess;
//    private Compartment compartment1;
//    private Compartment compartment2;
//    private Compartment compartment3;
//    private Compartment compartment4;
//    private Compartment compartment5;
//    private Compartment compartment6;
//    public String SerialNumber;
//    public String VehicleNumber;
//    public char Nextprocess;
//    public char I_O;
//    public String VehicleType;
//    public String UpdatedBy;
//
//    public Repet_update_Model(int outwardId, String purposeProcess,
//                                        Compartment compartment1, Compartment compartment2,
//                                        Compartment compartment3, Compartment compartment4,
//                                        Compartment compartment5, Compartment compartment6,
//                              String SerialNumber, String VehicleNumber, char Nextprocess,
//                              char I_O, String VehicleType, String UpdatedBy) {
//        this.outwardId = outwardId;
//        this.PurposeProcess = purposeProcess;
//        this.compartment1 = compartment1;
//        this.compartment2 = compartment2;
//        this.compartment3 = compartment3;
//        this.compartment4 = compartment4;
//        this.compartment5 = compartment5;
//        this.compartment6 = compartment6;
//        this.SerialNumber = SerialNumber;
//        this.VehicleNumber = VehicleNumber;
//        this.Nextprocess = Nextprocess;
//        this.I_O = I_O;
//        this.VehicleType = VehicleType;
//        this.UpdatedBy = UpdatedBy;
//    }
}
