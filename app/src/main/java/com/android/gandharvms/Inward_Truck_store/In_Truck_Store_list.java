package com.android.gandharvms.Inward_Truck_store;

public class In_Truck_Store_list {
    String In_Time,Inv_QuantityUom,Serial_Number,Vehicle_Number,PO_No,Po_Date,Material_Rec_Date,
            Material,Qty,ReceiveQTY_Uom,Remarks,outTime,Invoice_Quantity,Invoice_Date,Invoice_Number,extramaterials;

    public In_Truck_Store_list() {
    }

    public In_Truck_Store_list(String in_Time,String inv_quantityuom, String serial_Number, String vehicle_Number, String po_no,
                               String po_Date, String material_Rec_Date, String material, String qty,
                               String recqtyoum, String remarks, String outTime,String inv_Qty,String extramaterials,String inv_Date,String inv_Number) {
        In_Time = in_Time;
        this.Inv_QuantityUom=inv_quantityuom;
        Serial_Number = serial_Number;
        Vehicle_Number = vehicle_Number;
        this.PO_No = po_no;
        Po_Date = po_Date;
        Material_Rec_Date = material_Rec_Date;
        Material = material;
        Qty = qty;
        ReceiveQTY_Uom = recqtyoum;
        Remarks = remarks;
        this.outTime=outTime;
        this.Invoice_Quantity=inv_Qty;
        this.Invoice_Date=inv_Date;
        this.Invoice_Number=inv_Number;
        this.extramaterials=extramaterials;
    }

    public String getIn_Time() {
        return In_Time;
    }

    public void setIn_Time(String in_Time) {
        In_Time = in_Time;
    }

    public String getInv_QuantityUom(){return Inv_QuantityUom;}
    public void setInv_QuantityUom(String invqtyuom){this.Inv_QuantityUom=invqtyuom;}
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

    public String getReceiveQTY_Uom(){return ReceiveQTY_Uom;}
    public void setReceiveQTY_Uom(String recqtyUom){this.ReceiveQTY_Uom=recqtyUom;}

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

    public String getInvoice_Quantity(){return Invoice_Quantity;}
    public void setInvoice_Quantity(String invqty){this.Invoice_Quantity=invqty;}

    public String getInvoice_Date(){return Invoice_Date;}
    public void setInvoice_Date(String invdate){this.Invoice_Date=invdate;}

    public String getInvoice_Number(){return Invoice_Number;}
    public void setInvoice_Number(String invNum){this.Invoice_Number=invNum;}

    public String getExtramaterials(){return  extramaterials;}
    public void setExtramaterials(String extraMaterials){this.extramaterials=extraMaterials;}
}
