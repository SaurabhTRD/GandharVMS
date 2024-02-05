package com.android.gandharvms.RegisterwithAPI;

import com.google.type.DateTime;

public class RegRequestModel {
    private String Name;
    private String EmpId;
    private String EmailId;
    private int PhoneNo;
    private String Password;
    private String Token;
    private int DeptId;
    private String CreatedBy;

    public RegRequestModel(String name, String empId, String emailId, int phoneNo, String password, String token, int deptId, String createdBy) {
        Name = name;
        EmpId = empId;
        EmailId = emailId;
        PhoneNo = phoneNo;
        Password = password;
        Token = token;
        DeptId = deptId;
        CreatedBy = createdBy;
    }
/*public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        CreatedDate = createdDate;
    }

    private String CreatedBy;
    private DateTime CreatedDate;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public int getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int deptId) {
        DeptId = deptId;
    }*/
}
