package com.android.gandharvms.Inward_Tanker_Laboratory;

public class In_Tanker_lab_list {

    String In_Time, sample_reciving,Vehicle_Number,apperance,odor,color,Qty,density,Rcs_Test,Anline_Point,Flash_Point,Additional_test,sample_test,Remark,sign_of,Date_and_Time,outTime,Supplier,Remark_Discription,Viscosity_Index;
    String kv40Value= "40°KV", kv100value = "100°KV";

    public In_Tanker_lab_list() {

    }

    public In_Tanker_lab_list( String in_Time, String sample_reciving, String vehicle_Number, String apperance, String odor, String color, String qty, String density, String rcs_Test, String anline_Point, String flash_Point, String additional_test, String sample_test, String remark, String sign_of, String date_and_Time, String kv40Value, String kv100value,String outTime,String supplier,String remark_Discription,String viscosity_Index) {
        this.In_Time = in_Time;
        this.sample_reciving = sample_reciving;
        Vehicle_Number = vehicle_Number;
        this.apperance = apperance;
        this.odor = odor;
        this.color = color;
        Qty = qty;
        this.density = density;
        Rcs_Test = rcs_Test;
        Anline_Point = anline_Point;
        Flash_Point = flash_Point;
        Additional_test = additional_test;
        this.sample_test = sample_test;
        Remark = remark;
        this.sign_of = sign_of;
        Date_and_Time = date_and_Time;
        this.kv40Value = kv40Value;
        this.kv100value = kv100value;
        this.outTime=outTime;
        this.Supplier = supplier;
        this.Remark_Discription = remark_Discription;
        this.Viscosity_Index = viscosity_Index;
    }

    public String getIn_Time(){
        return In_Time;
    }

    public void setIn_Time(String in_Time) {
        In_Time = in_Time;
    }

    public String getSample_reciving() {
        return sample_reciving;
    }

    public void setSample_reciving(String sample_reciving) {
        this.sample_reciving = sample_reciving;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }

    public String getApperance() {
        return apperance;
    }

    public void setApperance(String apperance) {
        this.apperance = apperance;
    }

    public String getOdor() {
        return odor;
    }

    public void setOdor(String odor) {
        this.odor = odor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getRcs_Test() {
        return Rcs_Test;
    }

    public void setRcs_Test(String rcs_Test) {
        Rcs_Test = rcs_Test;
    }

    public String getAnline_Point() {
        return Anline_Point;
    }

    public void setAnline_Point(String anline_Point) {
        Anline_Point = anline_Point;
    }

    public String getFlash_Point() {
        return Flash_Point;
    }

    public void setFlash_Point(String flash_Point) {
        Flash_Point = flash_Point;
    }

    public String getAdditional_test() {
        return Additional_test;
    }

    public void setAdditional_test(String additional_test) {
        Additional_test = additional_test;
    }

    public String getSample_test() {
        return sample_test;
    }

    public void setSample_test(String sample_test) {
        this.sample_test = sample_test;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSign_of() {
        return sign_of;
    }

    public void setSign_of(String sign_of) {
        this.sign_of = sign_of;
    }

    public String getDate_and_Time() {
        return Date_and_Time;
    }

    public void setDate_and_Time(String date_and_Time) {
        Date_and_Time = date_and_Time;
    }

    public String getKv40Value() {
        return kv40Value;
    }

    public void setKv40Value(String kv40Value) {
        this.kv40Value = kv40Value;
    }

    public String getKv100value() {
        return kv100value;
    }

    public void setKv100value(String kv100value) {
        this.kv100value = kv100value;
    }

    public String getOuttime() { return outTime; }
    public void setOuttime(String outTime){ this.outTime=outTime; }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getRemark_Discription() {
        return Remark_Discription;
    }

    public void setRemark_Discription(String remark_Discription) {
        Remark_Discription = remark_Discription;
    }

    public String getViscosity_Index() {
        return Viscosity_Index;
    }

    public void setViscosity_Index(String viscosity_Index) {
        Viscosity_Index = viscosity_Index;
    }
}
