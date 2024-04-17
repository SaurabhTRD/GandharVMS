package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.gandharvms.R;

public class Outward_DesSmallPackLoading_Form extends AppCompatActivity {

    EditText sevenltr,sevenandhalfltr,eighthalfltr,elevenltr,twelltr,threteenltr,fifteenltr,tenltr,eighteenltr,twentyltr,twentysixltr,fiftyltr,twotenltr,boxbucket,totalqty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_small_pack_loading_form);

        sevenltr = findViewById(R.id.etdesindusloadpacking7ltrqty);
        sevenandhalfltr = findViewById(R.id.etdesindusloadpacking7halfltrqty);
        eighthalfltr = findViewById(R.id.etdesindusloadpacking8halfltrqty);
        elevenltr = findViewById(R.id.etdesindusloadpacking11ltrqty);
        twelltr = findViewById(R.id.etdesindusloadpacking12ltrqty);
        threteenltr = findViewById(R.id.etdesindusloadpacking13ltrqty);
        fifteenltr = findViewById(R.id.etdesindusloadpacking15ltrqty);
        tenltr = findViewById(R.id.etdesindusloadpacking10ltrqty);
        eighteenltr = findViewById(R.id.etdesindusloadpacking18ltrqty);
        twentyltr = findViewById(R.id.etdesindusloadpacking20ltrqty);
        twentysixltr = findViewById(R.id.etdesindusloadpacking26ltrqty);
        fiftyltr = findViewById(R.id.etdesindusloadpacking50ltrqty);
        twotenltr = findViewById(R.id.etdesindusloadpacking210ltrqty);
        boxbucket = findViewById(R.id.etdesindusloadpackingboxbucket);

        totalqty = findViewById(R.id.etdesindusloadTotalQuantitysmallpack);

        sevenltr.addTextChangedListener(textWatcher);
        sevenandhalfltr.addTextChangedListener(textWatcher);
        eighthalfltr.addTextChangedListener(textWatcher);
        elevenltr.addTextChangedListener(textWatcher);
        twelltr.addTextChangedListener(textWatcher);
        threteenltr.addTextChangedListener(textWatcher);
        fifteenltr.addTextChangedListener(textWatcher);
        tenltr.addTextChangedListener(textWatcher);
        eighteenltr.addTextChangedListener(textWatcher);
        twentyltr.addTextChangedListener(textWatcher);
        twentysixltr.addTextChangedListener(textWatcher);
        fiftyltr.addTextChangedListener(textWatcher);
        twotenltr.addTextChangedListener(textWatcher);
        boxbucket.addTextChangedListener(textWatcher);



    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            calculateTotal();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void calculateTotal() {
        int total = 0;
        total += getEditTextValue(sevenltr);
        total += getEditTextValue(sevenandhalfltr);
        total += getEditTextValue(eighthalfltr);
        total += getEditTextValue(elevenltr);
        total += getEditTextValue(twelltr);
        total += getEditTextValue(threteenltr);
        total += getEditTextValue(fifteenltr);
        total += getEditTextValue(tenltr);
        total += getEditTextValue(eighteenltr);
        total += getEditTextValue(twentyltr);
        total += getEditTextValue(twentysixltr);
        total += getEditTextValue(fiftyltr);
        total += getEditTextValue(twotenltr);
        total += getEditTextValue(boxbucket);

        totalqty.setText(String.valueOf(total));
    }

    private int getEditTextValue(EditText editText) {
        try {

            return Integer.parseInt(editText.getText().toString());
        }    catch (NumberFormatException e) {
            return 0; // Return 0 if EditText is empty or non-numeric

        }
    }
}