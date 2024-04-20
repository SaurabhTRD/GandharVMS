package com.android.gandharvms.Outward_Truck_Logistic;

public class Update_Request_Model_Outward_Logistic {
    private int OutwardId;
    private String TransportName;
    private String OAnumber;
    private String CustomerName;
    private String UpdatedBy;

    public Update_Request_Model_Outward_Logistic(int outwardId, String transportName, String OAnumber, String customerName, String updatedBy) {
        OutwardId = outwardId;
        TransportName = transportName;
        this.OAnumber = OAnumber;
        CustomerName = customerName;
        UpdatedBy = updatedBy;
    }
}
