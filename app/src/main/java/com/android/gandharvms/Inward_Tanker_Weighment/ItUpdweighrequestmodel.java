package com.android.gandharvms.Inward_Tanker_Weighment;

    public class ItUpdweighrequestmodel {
    int InwardId;
    String InTime ;
    String OutTime ;
    String GrossWeight;
    String NetWeight;
    String TareWeight ;
    //    String ShortageDip;
//    String ShortageWeight ;
    String Remark ;
    String SignBy ;
    String ContainerNo ;
    String InVehicleImage ;
    String InDriverImage ;
    boolean IsActive ;
    String CreatedBy ;
    String SerialNo ;
    String VehicleNo;
    String Date;
    String PartyName;
    String Material;
    String OA_PO_number;
    String Driver_MobileNo ;
    char Nextprocess;
    char I_O;
    String VehicleType;
    String UpdatedBy;
    String OutVehicleImage;
    String OutDriverImage;
    public String OutInTime;
    public int WeighQty ;
    public int WeightQtyUOM;
    public String ShortageDip;
    public String ShortageWeight;

        public ItUpdweighrequestmodel(int inwardId, String grossWeight, String remark, String signBy, String containerNo, String shortageWeight, String shortageDip, String updatedBy, String driver_MobileNo, String OA_PO_number, String vehicleNo, String partyName) {
            InwardId = inwardId;
            GrossWeight = grossWeight;
            Remark = remark;
            SignBy = signBy;
            ContainerNo = containerNo;
            ShortageWeight = shortageWeight;
            ShortageDip = shortageDip;
            UpdatedBy = updatedBy;
            Driver_MobileNo = driver_MobileNo;
            this.OA_PO_number = OA_PO_number;
            VehicleNo = vehicleNo;
            PartyName = partyName;
        }

        public int getInwardId() {
            return InwardId;
        }

        public void setInwardId(int inwardId) {
            InwardId = inwardId;
        }

        public String getGrossWeight() {
            return GrossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            GrossWeight = grossWeight;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getSignBy() {
            return SignBy;
        }

        public void setSignBy(String signBy) {
            SignBy = signBy;
        }

        public String getContainerNo() {
            return ContainerNo;
        }

        public void setContainerNo(String containerNo) {
            ContainerNo = containerNo;
        }

        public String getUpdatedBy() {
            return UpdatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            UpdatedBy = updatedBy;
        }

        public String getVehicleNo() {
            return VehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            VehicleNo = vehicleNo;
        }

        public String getPartyName() {
            return PartyName;
        }

        public void setPartyName(String partyName) {
            PartyName = partyName;
        }

        public String getOA_PO_number() {
            return OA_PO_number;
        }

        public void setOA_PO_number(String OA_PO_number) {
            this.OA_PO_number = OA_PO_number;
        }

        public String getDriver_MobileNo() {
            return Driver_MobileNo;
        }

        public void setDriver_MobileNo(String driver_MobileNo) {
            Driver_MobileNo = driver_MobileNo;
        }
    }
