package com.android.gandharvms.Outward_Tanker_Security;

public class Update_outwardTanker {
    private int outwardId;
    private String serialNumber;
    private String vehicleNumber;
    private String transportName;
    private String mobileNumber;
    private String date;
    private int kl;
    private String place;
    private String vehiclePermit;
    private int puc; // Assuming it's integer as you're converting it using Convert.ToInt32
    private String insurance;
    private String vehicleFitnessCertificate;
    private String driverLicenses;
    private String rcBook;
    private String remark;
    private String updatedBy;

    public Update_outwardTanker(int outwardId, String serialNumber, String vehicleNumber, String transportName, String mobileNumber, String date, int kl, String place, String vehiclePermit, int puc, String insurance, String vehicleFitnessCertificate, String driverLicenses, String rcBook, String remark, String updatedBy) {
        this.outwardId = outwardId;
        this.serialNumber = serialNumber;
        this.vehicleNumber = vehicleNumber;
        this.transportName = transportName;
        this.mobileNumber = mobileNumber;
        this.date = date;
        this.kl = kl;
        this.place = place;
        this.vehiclePermit = vehiclePermit;
        this.puc = puc;
        this.insurance = insurance;
        this.vehicleFitnessCertificate = vehicleFitnessCertificate;
        this.driverLicenses = driverLicenses;
        this.rcBook = rcBook;
        this.remark = remark;
        this.updatedBy = updatedBy;
    }
}
