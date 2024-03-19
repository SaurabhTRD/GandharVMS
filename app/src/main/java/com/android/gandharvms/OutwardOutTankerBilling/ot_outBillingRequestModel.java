package com.android.gandharvms.OutwardOutTankerBilling;

public class ot_outBillingRequestModel {

    public int OutwardId;
    public String OutInTime;
    public String OutOutTime;
    public String OutRemark;
    public String OutTotalQty;
    public int OutTotalQtyUOM;
    public String OutInvoiceNumber;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;

    public ot_outBillingRequestModel(int outwardId, String outInTime, String outOutTime,String outtotqty,
                                     int outtotqtyuom, String outinvnumber, String outRemark,
                                     char nextProcess, char i_O, String vehicleType, String updatedBy) {
        OutwardId = outwardId;
        OutInTime = outInTime;
        OutOutTime = outOutTime;
        OutTotalQty=outtotqty;
        OutTotalQtyUOM=outtotqtyuom;
        OutInvoiceNumber=outinvnumber;
        OutRemark = outRemark;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
    }
}
