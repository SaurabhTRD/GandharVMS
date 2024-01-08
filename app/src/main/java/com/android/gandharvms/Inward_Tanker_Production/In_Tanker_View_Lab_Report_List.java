package com.android.gandharvms.Inward_Tanker_Production;

public class In_Tanker_View_Lab_Report_List {
    String Material,sample_reciving,Vehicle_Number,apperance,odor,color,Qty,density,Remark;
    public In_Tanker_View_Lab_Report_List(){

    }
    public In_Tanker_View_Lab_Report_List(String Material, String sample_reciving, String vehicle_Number, String apperance, String odor, String color, String qty, String density, String remark){

        this.Material=Material;
        this.sample_reciving = sample_reciving;
        this.Vehicle_Number = vehicle_Number;
        this.apperance = apperance;
        this.odor = odor;
        this.color = color;
        this.Qty = qty;
        this.density = density;
        this.Remark = remark;

    }

    public String getMaterial(){ return Material; }
    public void setMaterial(String material) { this.Material=Material;}
    public String getSample_reciving() {
        return sample_reciving;
    }
    public void setSample_reciving(String sample_reciving) { this.sample_reciving=sample_reciving;}
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
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }


}