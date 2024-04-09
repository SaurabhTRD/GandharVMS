package com.android.gandharvms.Inward_Truck_store;

public class ir_updstorebyinwardid_req_model {
    int InwardId;
    String InTime;
    String OutTime;
    String CreatedBy;
    String UpdatedBy;
    String VehicleNo;
    String FactoryOut;
    String SerialNo;
    char Nextprocess;
    char I_O;
    String VehicleType;
    int ReceiveQTY ;
    int ReceiveQTYUOM ;
    String Remark ;
    String StoreExtramaterials;

    public ir_updstorebyinwardid_req_model(int inwardId, String updatedBy, int receiveQTY, int receiveQTYUOM, String remark, String storeExtramaterials) {
        InwardId = inwardId;
        UpdatedBy = updatedBy;
        ReceiveQTY = receiveQTY;
        ReceiveQTYUOM = receiveQTYUOM;
        Remark = remark;
        StoreExtramaterials = storeExtramaterials;
    }
}
