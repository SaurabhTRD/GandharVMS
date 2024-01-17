package com.android.gandharvms.Inward_Tanker_Security;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class In_Tanker_Security_list {

    String SerialNumber,intime,invoiceno,material,netweight,partyname,qty,uom,vehicalnumber,outTime,qtyuom,netweightuom,extramaterials,Remark,OA_PO_Number,Driver_Mobile_No,Reporting_Remark;
    Timestamp date;
    public In_Tanker_Security_list(){

    }

    public In_Tanker_Security_list(String serialNumber, Timestamp date, String intime, String supplier, String invoiceno, String material, String netweight, String qty, String uom, String vehicalnumber , String outTime, String qtyuom, String netweightuom, String extramaterials, String partyname, String remark, String OA_PO_Number, String driver_Mobile_No, String reporting_Remark) {

        this.SerialNumber = serialNumber;
        this.date = date;
        this.intime = intime;
        this.invoiceno = invoiceno;
        this.material = material;
        this.netweight = netweight;
        this.partyname = partyname;
        this.qty = qty;
        this.uom = uom;
        this.vehicalnumber = vehicalnumber;
        this.outTime = outTime;
        this.qtyuom=qtyuom;
        this.netweightuom=netweightuom;
        this.extramaterials=extramaterials;

        this.Remark=remark;
        this.OA_PO_Number=OA_PO_Number;
        this.Driver_Mobile_No = driver_Mobile_No;
        this.Reporting_Remark = reporting_Remark;
    }



    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNetweight() {
        return netweight;
    }

    public void setNetweight(String netweight) {
        this.netweight = netweight;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getVehicalnumber() {
        return vehicalnumber;
    }

    public void setVehicalnumber(String vehicalnumber) {
        this.vehicalnumber = vehicalnumber;
    }

    public String getOuttime() { return outTime; }
    public void setOuttime(String outTime){ this.outTime=outTime; }

    public String getQtyuom(){ return qtyuom; }
    public void setQtyuom(String qtyuom){ this.qtyuom=qtyuom; }

    public String getNetweightuom(){return netweightuom;}
    public void setNetweightuom(String netweightuom){ this.netweightuom=netweightuom; }

    public String getExtramaterials(){return  extramaterials;}

    public void setExtramaterials(String extraMaterials){this.extramaterials=extraMaterials;}

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getOA_PO_Number() {
        return OA_PO_Number;
    }

    public void setOA_PO_Number(String OA_PO_Number) {
        this.OA_PO_Number = OA_PO_Number;
    }

    public String getDriver_Mobile_No() {
        return Driver_Mobile_No;
    }

    public void setDriver_Mobile_No(String driver_Mobile_No) {
        Driver_Mobile_No = driver_Mobile_No;
    }

    public String getReporting_Remark() {
        return Reporting_Remark;
    }

    public void setReporting_Remark(String reporting_Remark) {
        Reporting_Remark = reporting_Remark;
    }
}
