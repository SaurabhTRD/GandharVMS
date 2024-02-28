package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Production_forms.inprocessrequestform;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.outward_Tanker_Lab_forms.outwardlabforms;

public class Outward_Tanker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker);

    }
    public void sequirityoutwardTanker(View view){
        Global_Var.getInstance().DeptType='S';
        Intent intent = new Intent(this, Outward_Tanker_Security.class);
        startActivity(intent);
    }
    public void Weighmentouttankerclick(View view){
        Global_Var.getInstance().DeptType='W';
        Intent intent = new Intent(this, Outward_Tanker_weighment.class);
        startActivity(intent);
    }

//    public void inprocessrequestform(View view){
//        Intent intent = new Intent(this, inprocessrequestform.class);
//        startActivity(intent);
//    }

    public void labouttanker(View view){
        Global_Var.getInstance().DeptType='L';
        Intent intent = new Intent(this, outwardlabforms.class);
        startActivity(intent);
    }
    public void productionouttanker(View view){
        Global_Var.getInstance().DeptType='P';
        Intent intent = new Intent(this, inprocessrequestform.class);
        startActivity(intent);
    }
    public void samplingouttanker(View view){
        Global_Var.getInstance().DeptType='B';
        Intent intent = new Intent(this, Outward_Tanker_Billing.class);
        startActivity(intent);
    }
}