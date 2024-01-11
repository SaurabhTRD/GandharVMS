package com.android.gandharvms.Inward_Tanker_Weighment;

public class In_Tanker_Weighment_list {

    String   In_Time,serial_number,vehicle_number,supplier_name,material_name,Driver_Number,OA_number,Date,
            Gross_Weight,Tare_Weight,Net_Weight,Batch_Number,Sign_By,Container_No,shortage_Dip,shortage_weight,outTime,InVehicleImage,InDriverImage;

    public In_Tanker_Weighment_list() {
    }

    public In_Tanker_Weighment_list( String in_Time,  String serial_number, String vehicle_number, String supplier_name, String material_name, String customer_Name, String driver_Number, String OA_number, String date, String gross_Weight, String tare_Weight, String net_Weight, String density, String batch_Number, String sign_By, String we_Date_Time, String container_No, String shortage_Dip, String shortage_weight ,String outTime
    ,String InVehicleImage,String InDriverImage) {
        this.In_Time = in_Time;
        this.serial_number = serial_number;
        this.vehicle_number = vehicle_number;
        this.supplier_name = supplier_name;
        this.material_name = material_name;
        this.Driver_Number = driver_Number;
        this.OA_number = OA_number;
        this.Date = date;
        this.Gross_Weight = gross_Weight;
        this.Tare_Weight = tare_Weight;
        this.Net_Weight = net_Weight;
        this.Batch_Number = batch_Number;
        this.Sign_By = sign_By;
        this.Container_No = container_No;
        this.shortage_Dip = shortage_Dip;
        this.shortage_weight = shortage_weight;
        this.outTime=outTime;
        this.InVehicleImage=InVehicleImage;
        this.InDriverImage=InDriverImage;
    }

    public String getIn_Time(){
        return In_Time;
    }
    public void setIn_Time(String in_Time){
        this.In_Time = in_Time;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getDriver_Number() {
        return Driver_Number;
    }

    public void setDriver_Number(String driver_Number) {
        Driver_Number = driver_Number;
    }

    public String getOA_number() {
        return OA_number;
    }

    public void setOA_number(String OA_number) {
        this.OA_number = OA_number;
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

    public String getBatch_Number() {
        return Batch_Number;
    }

    public void setBatch_Number(String batch_Number) {
        Batch_Number = batch_Number;
    }

    public String getSign_By() {
        return Sign_By;
    }

    public void setSign_By(String sign_By) {
        Sign_By = sign_By;
    }

    public String getContainer_No() {
        return Container_No;
    }

    public void setContainer_No(String container_No) {
        Container_No = container_No;
    }

    public String getShortage_Dip() {
        return shortage_Dip;
    }

    public void setShortage_Dip(String shortage_Dip) {
        this.shortage_Dip = shortage_Dip;
    }

    public String getShortage_weight() {
        return shortage_weight;
    }

    public void setShortage_weight(String shortage_weight) {
        this.shortage_weight = shortage_weight;
    }
    public String getOuttime() { return outTime; }
    public void setOuttime(String outTime){ this.outTime=outTime; }

    public String getInVehicleImage(){return InVehicleImage;}
    public void setInVehicleImage(String invehicleImage){this.InVehicleImage=invehicleImage;}

    public String getInDriverImage(){return  InDriverImage;}
    public void setInDriverImage(String indriverImage){this.InDriverImage=indriverImage;}
}
