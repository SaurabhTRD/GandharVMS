package com.android.gandharvms.Inward_Truck_Security;

public class In_Truck_security_list {

//    String etintime,etserialnumber,etvehicalnumber,etsinvocieno,etsdate,etssupplier,etsmaterial,etsqty,etsuom,etsnetwt,etsuom2;
    String Intime,serialnumber,VehicalNumber,invoicenumber,date,Supplier,Material,Qty,UOM,etsnetweight,UOM2,outTime,SelectRegister,lrcopy, deliverybill, taxinvoice, ewaybill,Reporting_Remark;

    public In_Truck_security_list() {
    }

    public In_Truck_security_list(String intime, String serialnumber, String vehicalNumber, String invoicenumber, String date, String supplier, String material, String qty, String UOM, String etsnetweight, String UOM2,String outTime,String selectRegister,String Lrcopy, String Deliverybill, String Taxinvoice, String Ewaybill,String reporting_Remark) {
        Intime = intime;
        this.serialnumber = serialnumber;
        VehicalNumber = vehicalNumber;
        this.invoicenumber = invoicenumber;
        this.date = date;
        Supplier = supplier;
        Material = material;
        Qty = qty;
        this.UOM = UOM;
        this.etsnetweight = etsnetweight;
        this.UOM2 = UOM2;
        this.outTime=outTime;
        this.SelectRegister = selectRegister;
        this.lrcopy = Lrcopy;
        this.deliverybill = Deliverybill;
        this.taxinvoice = Taxinvoice;
        this.ewaybill = Ewaybill;
        this.Reporting_Remark = reporting_Remark;
    }

    public String getIntime() {
        return Intime;
    }

    public void setIntime(String intime) {
        Intime = intime;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getVehicalNumber() {
        return VehicalNumber;
    }

    public void setVehicalNumber(String vehicalNumber) {
        VehicalNumber = vehicalNumber;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getEtsnetweight() {
        return etsnetweight;
    }

    public void setEtsnetweight(String etsnetweight) {
        this.etsnetweight = etsnetweight;
    }

    public String getUOM2() {
        return UOM2;
    }

    public void setUOM2(String UOM2) {
        this.UOM2 = UOM2;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getSelectRegister() {
        return SelectRegister;
    }

    public void setSelectRegister(String selectRegister) {
        SelectRegister = selectRegister;
    }

    public String getLrcopy() {
        return lrcopy;
    }

    public void setLrcopy(String Lrcopy) {
        this.lrcopy = Lrcopy;
    }

    public String getDeliverybill() {
        return deliverybill;
    }

    public void setDeliverybill(String Deliverybill) {
        this.deliverybill = Deliverybill;
    }

    public String getTaxinvoice() {
        return taxinvoice;
    }

    public void setTaxinvoice(String TaxInvoice) {
        this.taxinvoice = TaxInvoice;
    }

    public String getEwaybill() {
        return ewaybill;
    }

    public void setEwaybill(String Ewaybill) {
        this.ewaybill = Ewaybill;
    }

    public String getReporting_Remark() {
        return Reporting_Remark;
    }

    public void setReporting_Remark(String reporting_Remark) {
        Reporting_Remark = reporting_Remark;
    }

    //    public In_Truck_security_list() {
//    }
//
//    public In_Truck_security_list(String etintime, String etserialnumber, String etvehicalnumber, String etsinvocieno, String etsdate, String etssupplier, String etsmaterial, String etsqty, String etsuom, String etsnetwt, String etsuom2) {
//        this.etintime = etintime;
//        this.etserialnumber = etserialnumber;
//        this.etvehicalnumber = etvehicalnumber;
//        this.etsinvocieno = etsinvocieno;
//        this.etsdate = etsdate;
//        this.etssupplier = etssupplier;
//        this.etsmaterial = etsmaterial;
//        this.etsqty = etsqty;
//        this.etsuom = etsuom;
//        this.etsnetwt = etsnetwt;
//        this.etsuom2 = etsuom2;
//    }
//
//    public String getEtintime() {
//        return etintime;
//    }
//
//    public void setEtintime(String etintime) {
//        this.etintime = etintime;
//    }
//
//    public String getEtserialnumber() {
//        return etserialnumber;
//    }
//
//    public void setEtserialnumber(String etserialnumber) {
//        this.etserialnumber = etserialnumber;
//    }
//
//    public String getEtvehicalnumber() {
//        return etvehicalnumber;
//    }
//
//    public void setEtvehicalnumber(String etvehicalnumber) {
//        this.etvehicalnumber = etvehicalnumber;
//    }
//
//    public String getEtsinvocieno() {
//        return etsinvocieno;
//    }
//
//    public void setEtsinvocieno(String etsinvocieno) {
//        this.etsinvocieno = etsinvocieno;
//    }
//
//    public String getEtsdate() {
//        return etsdate;
//    }
//
//    public void setEtsdate(String etsdate) {
//        this.etsdate = etsdate;
//    }
//
//    public String getEtssupplier() {
//        return etssupplier;
//    }
//
//    public void setEtssupplier(String etssupplier) {
//        this.etssupplier = etssupplier;
//    }
//
//    public String getEtsmaterial() {
//        return etsmaterial;
//    }
//
//    public void setEtsmaterial(String etsmaterial) {
//        this.etsmaterial = etsmaterial;
//    }
//
//    public String getEtsqty() {
//        return etsqty;
//    }
//
//    public void setEtsqty(String etsqty) {
//        this.etsqty = etsqty;
//    }
//
//    public String getEtsuom() {
//        return etsuom;
//    }
//
//    public void setEtsuom(String etsuom) {
//        this.etsuom = etsuom;
//    }
//
//    public String getEtsnetwt() {
//        return etsnetwt;
//    }
//
//    public void setEtsnetwt(String etsnetwt) {
//        this.etsnetwt = etsnetwt;
//    }
//
//    public String getEtsuom2() {
//        return etsuom2;
//    }
//
//    public void setEtsuom2(String etsuom2) {
//        this.etsuom2 = etsuom2;
//    }
}
