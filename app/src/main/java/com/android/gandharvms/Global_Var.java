package com.android.gandharvms;

/*public  class Global_Var {
    public String MenuType;
    public String InOutType;
    public String DeptType;
}*/

public class Global_Var {
    public String MenuType;
    public String InOutType;
    public String DeptType;
    // Getter/setter
    private static Global_Var instance;
    public static Global_Var getInstance() {
        if (instance == null)
            instance = new Global_Var();
        return instance;
    }
    private Global_Var() { }
}