package com.android.gandharvms.Inward_Truck_store;

public class In_Truck_Store_list {
    String In_Time,Serial_Number,Vehicle_Number,PO_No,Po_Date,Material_Rec_Date,Material,Qty,Oum,Remarks,outTime;

    public In_Truck_Store_list() {
    }

    public In_Truck_Store_list(String in_Time, String serial_Number, String vehicle_Number, String po_no, String po_Date, String material_Rec_Date, String material, String qty, String oum, String remarks,String outTime) {
        In_Time = in_Time;
        Serial_Number = serial_Number;
        Vehicle_Number = vehicle_Number;
        this.PO_No = po_no;
        Po_Date = po_Date;
        Material_Rec_Date = material_Rec_Date;
        Material = material;
        Qty = qty;
        Oum = oum;
        Remarks = remarks;
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

    public String getPO_No() {
        return PO_No;
    }

    public void setPO_No(String PO_No) {
        this.PO_No = PO_No;
    }

    public String getPo_Date() {

        return Po_Date;
    }

    public void setDate(String po_Date) {
        Po_Date = po_Date;
    }

    public String getMaterial_Rec_Date() {
        return Material_Rec_Date;
    }

    public void setSupplier(String material_Rec_Date) {
        Material_Rec_Date = material_Rec_Date;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getOum() {
        return Oum;
    }

    public void setOum(String oum) {
        Oum = oum;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
