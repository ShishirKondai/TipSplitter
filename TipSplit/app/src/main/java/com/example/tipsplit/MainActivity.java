package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText totalBill, numOfPeople;
    private TextView tipAmount, totalWithTip, totalPerPerson, overage;
    private RadioGroup radioGroup;
    private String total_bill_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBill = findViewById(R.id.original_bill);
        numOfPeople = findViewById(R.id.num_of_people);
        tipAmount = findViewById(R.id.tip_output);
        totalWithTip = findViewById(R.id.total_with_tip_output);
        totalPerPerson = findViewById(R.id.total_per_person_output);
        overage = findViewById(R.id.overage_output);
        radioGroup = findViewById(R.id.radio_group);
    }
    public void onClickRadioButton (View v) {
        String originalBill = totalBill.getText().toString();
        if (originalBill.isEmpty()) {
            radioGroup.clearCheck();
            return;
        }
        double originalBill_d = Double.parseDouble(originalBill);
        double tip_percent = 0;
        if (v.getId() == R.id.radio_button_12) {
            tip_percent = 0.12;
        }
        else if (v.getId() == R.id.radio_button_15) {
            tip_percent = 0.15;
        }
        else if (v.getId() == R.id.radio_button_18) {
            tip_percent = 0.18;
        }
        else if (v.getId() == R.id.radio_button_20) {
            tip_percent = 0.20;
        }
        double tip = tip_percent * originalBill_d;
        double total_bill = originalBill_d + tip;
        total_bill_string = String.format("%.2f", total_bill);

        tipAmount.setText(String.format("$" + "%.2f", tip));
        totalWithTip.setText("$" +  total_bill_string);
    }
    public void onClickGo(View v) {
        String people_count = numOfPeople.getText().toString();
        if (people_count.isEmpty()) {
            return;
        }
        double people_count_d = Double.parseDouble(people_count);
        if (people_count_d == 0) {
            return;
        }

        double total_bill_rounded = Double.parseDouble(total_bill_string);
        double total_per_person = total_bill_rounded / people_count_d;
        double rounded_val = Math.ceil(total_per_person * 100.00) / 100.00;

        double new_total = rounded_val * people_count_d;
        double overage_d = (new_total - total_bill_rounded);

        totalPerPerson.setText(String.format("$" + "%.2f", rounded_val));
        overage.setText(String.format("$" + "%.2f", overage_d));
    }
    public void onClickClear(View v) {
        totalBill.setText("");
        numOfPeople.setText("");
        tipAmount.setText("");
        totalWithTip.setText("");
        totalPerPerson.setText("");
        overage.setText("");
        radioGroup.clearCheck();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("TIP", tipAmount.getText().toString());
        outState.putString("TOTAL_WITH_TIP", totalWithTip.getText().toString());
        outState.putString("TOTAL_PER_PERSON", totalPerPerson.getText().toString());
        outState.putString("OVERAGE", overage.getText().toString());

        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        tipAmount.setText(savedInstanceState.getString("TIP"));
        totalWithTip.setText(savedInstanceState.getString("TOTAL_WITH_TIP"));
        totalPerPerson.setText(savedInstanceState.getString("TOTAL_PER_PERSON"));
        overage.setText(savedInstanceState.getString("OVERAGE"));
    }
}