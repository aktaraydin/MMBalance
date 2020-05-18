package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static java.lang.Double.parseDouble;
import static java.lang.Double.valueOf;

public class chem_report extends AppCompatActivity {

    //EditText to double
    double plantFeedD;
    double tankVolumeD;
    double chemSolidD;
    double chemDensityD;
    double chemFeedD;

    //report variables
    double tankmL;
    double chemFeedH;
    double chemAmount;
    double chemCons;

    //report values
    TextView chemName;
    TextView chemReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_report);

        chemName = findViewById(R.id.chem_nameReport);
        chemName.setText(chemical_consumption.chemicalName.getText().toString());

        chemReport = findViewById(R.id.chem_gPerTonnesResult);

        //degerler sayfasindan girilen degerleri alip, double a cevirme
        plantFeedD = parseDouble(chemical_consumption.plantFeed.getText().toString());
        chemFeedD = parseDouble(chemical_consumption.chemFeed.getText().toString());

        //hesaplamalar

        if (chemical_consumption.chemDensity.getText().toString().matches("")) {

            chemSolidD = parseDouble(chemical_consumption.chemSolid.getText().toString());
            tankVolumeD = parseDouble(chemical_consumption.tankVolume.getText().toString());
            chemReport.setText(String.format("%.3f",valueOf(chemPrep())));

        } else if (chemical_consumption.chemSolid.getText().toString().matches("")) {

            chemDensityD = parseDouble(chemical_consumption.chemDensity.getText().toString());
            chemReport.setText(String.valueOf(chemSol()));

        }

        // go home
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(chem_report.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public double chemPrep (){

        chemFeedH = chemFeedD * 60;
        tankmL = tankVolumeD * 1000000;
        chemAmount = ((chemFeedH * chemSolidD)/tankmL)*1000;
        chemCons = chemAmount / plantFeedD;
        return chemCons;

    }

    public double chemSol () {

        chemFeedH = chemFeedD * 60;
        chemAmount = (chemFeedH * chemDensityD) / 100;
        chemCons = chemAmount / plantFeedD;
        return chemCons;
    }
}
