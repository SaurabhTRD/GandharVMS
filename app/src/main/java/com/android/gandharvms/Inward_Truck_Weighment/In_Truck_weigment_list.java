package com.android.gandharvms.Inward_Truck_Weighment;
import com.google.firebase.Timestamp;
public class In_Truck_weigment_list {

    String In_Time, Serial_Number, Vehicle_Number, Supplier, Material,
            Driver_No,remark,Container_No, Oa_Number, Gross_Weight, Sign_By, outTime,InVehicleImage,InDriverImage;
    Timestamp Date;
    public In_Truck_weigment_list() {
    }

    public In_Truck_weigment_list(String in_Time, String serial_Number, String vehicle_Number, String supplier, String material, String driver_No,
                                  String oa_Number, Timestamp date,String containerno, String gross_Weight,
                                  String sign_By,String Remark, String outTime,String InVehicleImage,String InDriverImage) {
        In_Time = in_Time;
        Serial_Number = serial_Number;
        Vehicle_Number = vehicle_Number;
        Supplier = supplier;
        Material = material;
        this.Driver_No = driver_No;
        this.Oa_Number = oa_Number;
        Date = date;
        Gross_Weight = gross_Weight;
        /*Tare_Weight = tare_Weight;
        this.Net_Weight = net_Weight;*/
        this.Sign_By = sign_By;
        this.outTime = outTime;
        this.InVehicleImage=InVehicleImage;
        this.InDriverImage=InDriverImage;
        this.Container_No=containerno;
        this.remark=Remark;
    }

    public String getIn_Time() {
        return In_Time;
    }

    public void setIn_Time(String in_Time) {
        In_Time = in_Time;
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

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getDriver_No() {
        return Driver_No;
    }

    public void setDriver(String driver_No) {
        Driver_No = driver_No;
    }


    public String getOA_Number() {

        return Oa_Number;
    }

    public void setOA_Number(String OA_Number) {

        this.Oa_Number = OA_Number;
    }

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp date) {
        Date = date;
    }

    public String getGross_Weight() {
        return Gross_Weight;
    }

    public void setGross_Weight(String gross_Weight) {
        Gross_Weight = gross_Weight;
    }

    public String getSign_By() {
        return Sign_By;
    }

    public void setSign_By(String sign_By) {
        Sign_By = sign_By;
    }


    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
    public String getInVehicleImage(){return InVehicleImage;}
    public void setInVehicleImage(String invehicleImage){this.InVehicleImage=invehicleImage;}

    public String getInDriverImage(){return  InDriverImage;}
    public void setInDriverImage(String indriverImage){this.InDriverImage=indriverImage;}

    public String getContainer_No(){return Container_No;}
    public void setContainer_No(String container){this.Container_No=container;}

    public String getRemark(){return remark;}
    public void setRemark(String weremark){this.remark=weremark;}
}
