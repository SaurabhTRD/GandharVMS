package com.android.gandharvms.Inward_Truck_Weighment;

public class In_Truck_weigment_list {

    String In_Time, Serial_Number,Vehicle_Number,Supplier,Material,Driver_No,Customer,Oa_Number,Date,Gross_Weight,Tare_Weight,Net_Weight,Density,Batch_No,Sign_By,Date_Time,outTime;

    public In_Truck_weigment_list() {
    }

    public In_Truck_weigment_list( String in_Time, String serial_Number, String vehicle_Number, String supplier, String material,String driver_No, String customer,
                                  String oa_Number, String date, String gross_Weight, String tare_Weight, String net_Weight, String density, String batch_No, String sign_By, String date_Time,String outTime) {
        In_Time = in_Time;
        Serial_Number = serial_Number;
        Vehicle_Number = vehicle_Number;
        Supplier = supplier;
        Material = material;
       this.Driver_No = driver_No;
        Customer = customer;
       this.Oa_Number = oa_Number;
        Date = date;
        Gross_Weight = gross_Weight;
        Tare_Weight = tare_Weight;
        this.Net_Weight = net_Weight;
        Density = density;
        Batch_No = batch_No;
        this.Sign_By = sign_By;
        Date_Time = date_Time;
        this.outTime=outTime;
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

    public String getDriver_No(){
        return Driver_No;
    }

    public void setDriver(String driver_No){
        Driver_No = driver_No;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

//    public String getOA_Number() {
//        return OA_Number;
//    }
//
//    public void setOA_Number(String oa_Number) {
//        this.OA_Number = oa_Number;
//    }


    public String getOA_Number() {

        return Oa_Number;
    }

    public void setOA_Number(String OA_Number) {

        this.Oa_Number = OA_Number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getGross_Weight() {
        return Gross_Weight;
    }

    public void setGross_Weight(String gross_Weight) {
        Gross_Weight = gross_Weight;
    }

    public String getTare_Weight() {
        return Tare_Weight;
    }

    public void setTare_Weight(String tare_Weight) {
        Tare_Weight = tare_Weight;
    }

    public String getNet_Weight() {
        return Net_Weight;
    }

    public void setNet_Weight(String net_Weight) {
        Net_Weight = net_Weight;
    }

    public String getDensity() {
        return Density;
    }

    public void setDensity(String density) {
        Density = density;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public String getSign_By() {
        return Sign_By;
    }

    public void setSign_By(String sign_By) {
        Sign_By = sign_By;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String date_Time) {
        Date_Time = date_Time;
    }


    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
