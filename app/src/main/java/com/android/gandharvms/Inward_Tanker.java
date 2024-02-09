package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_Sampling;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Inward_Tanker extends AppCompatActivity {

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole="default";
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker);
        btnlogout=findViewById(R.id.btn_logoutButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String receivedEmplid = sharedPreferences.getString("EMPLID_KEY", "");

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               /* if (snapshot.child(receivedEmplid).child("role").exists()) {
                    userRole = snapshot.child(receivedEmplid).child("role").getValue(String.class);
                } else {
                    Toast.makeText(Inward_Tanker.this, "EmplId with this Role is Not Available", Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker.this, Login.class));
            }
        });
    }


    public void sequirityinwardT(View view) {
        /*if(userRole.equals("Admin") || userRole.equals("Security")){
            Global_Var.getInstance().DeptType="S";
            startActivity(new Intent(this, Inward_Tanker_Security.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }*/
        if(true){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, Inward_Tanker_Security.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Weighmentclick(View view) {
        /*if(userRole.equals("Admin") || userRole.equals("Weighment")){
            Global_Var.getInstance().DeptType="W";
            startActivity(new Intent(this, Inward_Tanker_Weighment.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }*/

        if(true){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Inward_Tanker_Weighment.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void samplicgclick(View view) {
        /*if(userRole.equals("Admin") || userRole.equals("Sampling")){
            Global_Var.getInstance().DeptType="M";
            startActivity(new Intent(this, Inward_Tanker_Sampling.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Sampling Department", Toast.LENGTH_SHORT).show();
        }*/

        if(true){
            Global_Var.getInstance().DeptType='M';
            startActivity(new Intent(this, Inward_Tanker_Sampling.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Sampling Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void productionclick(View view) {
        /*if(userRole.equals("Admin") || userRole.equals("Production")){
            Global_Var.getInstance().DeptType="P";
            startActivity(new Intent(this, Inward_Tanker_Production.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Production Department", Toast.LENGTH_SHORT).show();
        }*/
        if(true){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, Inward_Tanker_Production.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Production Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Laboratoryclick(View view) {
        /*if(userRole.equals("Admin") || userRole.equals("Laboratory")){
            Global_Var.getInstance().DeptType="L";
            startActivity(new Intent(this, Inward_Tanker_Laboratory.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Laboratory Department", Toast.LENGTH_SHORT).show();
        }*/
        if(true){
            Global_Var.getInstance().DeptType='L';
            startActivity(new Intent(this, Inward_Tanker_Laboratory.class));
        } else {
            Toast.makeText(Inward_Tanker.this, "You are not in Laboratory Department", Toast.LENGTH_SHORT).show();
        }
    }


//    public void expandable(View view) {
//        int v = (one.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//        int a = (two.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//        int b = (three.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//        int c = (four.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//        int d = (five.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//        int e = (five.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//
//
//        int f = (wone.getVisibility()== View.GONE)? view.VISIBLE: view.GONE;
//
//        TransitionManager.beginDelayedTransition(linearLayout,new AutoTransition());
//        one.setVisibility(v);
//        two.setVisibility(a);
//        three.setVisibility(b);
//        four.setVisibility(c);
//        five.setVisibility(d);
//        six.setVisibility(e);
//
//        wone.setVisibility(f);
//    }
//    two.getVisibility();three.getVisibility();four.getVisibility();five.getVisibility() == view.gone)
}