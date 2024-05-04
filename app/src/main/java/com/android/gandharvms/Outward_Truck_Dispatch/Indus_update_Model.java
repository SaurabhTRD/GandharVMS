package com.android.gandharvms.Outward_Truck_Dispatch;

public class Indus_update_Model {
    public int Id ;
    public int OutwardId ;
    public String ilInTime ;
    public String ilOutTime ;
    public int ilpackof10ltrqty ;
    public int ilpackof18ltrqty ;
    public int ilpackof20ltrqty ;
    public int ilpackof26ltrqty ;
    public int ilpackof50ltrqty ;
    public int ilpackof210ltrqty ;
    public int ilpackofboxbuxketltrqty ;
    public String iltotqty ;
    public String ilweight ;
    public String ilsign ;
    public String ilRemark ;
    public String splInTime ;
    public String splOutTime ;
    public int splpackof7ltrqty ;
    public int splpackof7_5ltrqty ;
    public int splpackof8_5ltrqty ;
    public int splpackof10ltrqty ;
    public int splpackof11ltrqty ;
    public int splpackof12ltrqty ;
    public int splpackof13ltrqty ;
    public int splpackof15ltrqty ;
    public int splpackof18ltrqty ;
    public int splpackof20ltrqty ;
    public int splpackof26ltrqty ;
    public int splpackof50ltrqty ;
    public int splpackof210ltrqty ;
    public int splpackofboxbuxketltrqty ;
    public String spltotqty ;
    public String splweight ;
    public String splsign ;
    public String splRemark ;
    public boolean IsActive ;
    public String CreatedBy ;
    public String CreatedDate ;
    public String UpdatedBy ;
    public String UpdatedDate ;
    public String SerialNumber ;
    public String VehicleNumber ;
    public String TransportName ;
    public String MobileNumber ;
    public String CapacityVehicle ;
    public String ConditionOfVehicle ;
    public String Date ;
    public String MaterialName ;
    public String CustomerName ;
    public String OAnumber ;
    public int TankerNumber ;
    public String ProductName ;
    public int HowMuchQuantityFilled ;
    public String Location ;
    public char NextProcess ;
    public char I_O ;
    public String VehicleType ;
    public String PurposeProcess;

    public Indus_update_Model(int outwardId, String ilInTime, String ilOutTime, int ilpackof10ltrqty, int ilpackof18ltrqty, int ilpackof20ltrqty, int ilpackof26ltrqty, int ilpackof50ltrqty, int ilpackof210ltrqty, int ilpackofboxbuxketltrqty, String iltotqty, String ilweight, String ilsign, String ilRemark, String createdBy,
                           String VehicleType,String updatedBy, char nextProcess, char i_O,String purposeProcess) {
        OutwardId = outwardId;
        this.ilInTime = ilInTime;
        this.ilOutTime = ilOutTime;
        this.ilpackof10ltrqty = ilpackof10ltrqty;
        this.ilpackof18ltrqty = ilpackof18ltrqty;
        this.ilpackof20ltrqty = ilpackof20ltrqty;
        this.ilpackof26ltrqty = ilpackof26ltrqty;
        this.ilpackof50ltrqty = ilpackof50ltrqty;
        this.ilpackof210ltrqty = ilpackof210ltrqty;
        this.ilpackofboxbuxketltrqty = ilpackofboxbuxketltrqty;
        this.iltotqty = iltotqty;
        this.ilweight = ilweight;
        this.ilsign = ilsign;
        this.ilRemark = ilRemark;
        CreatedBy = createdBy;
        this.VehicleType = VehicleType;
        this.UpdatedBy = updatedBy;
        this.NextProcess = nextProcess;
        this.I_O = i_O;
        this.PurposeProcess = purposeProcess;

    }
}
