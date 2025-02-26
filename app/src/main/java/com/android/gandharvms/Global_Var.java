package com.android.gandharvms;

/*public  class Global_Var {
    public String MenuType;
    public String InOutType;
    public String DeptType;
}*/

public class Global_Var {
    public String MenuType;//vehicletype
    public char InOutType;//Inward/Outward In out
    public char DeptType;//NextProcess
    public String EmpId;
    public String Department;
    public String Name;
    public String ProposeProcess;
    public String Token;
    public int InwardId;
    public String APKversion="1.0.9";
    // Getter/setter
    private static Global_Var instance;
    public static Global_Var getInstance() {
        if (instance == null)
            instance = new Global_Var();
        return instance;
    }
    private Global_Var() { }

    public void clear() {
        EmpId = null;
        Department = null;
        Name = null;
        ProposeProcess = null;
        Token = null;
        InwardId = 0;
    }
}