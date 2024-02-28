package com.android.gandharvms.outward_Tanker_Lab_forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.gandharvms.R;

public class bulkloadinglaboratory extends AppCompatActivity {

    String[] remark = {"OK", "NOT OK"};
    AutoCompleteTextView OKNOTOK;
    ArrayAdapter<String> remarkarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkloadinglaboratory);

        OKNOTOK = findViewById(R.id.etoknotok);
        remarkarray = new ArrayAdapter<String>(this, R.layout.inout, remark);
        OKNOTOK.setAdapter(remarkarray);
        OKNOTOK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + "Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}