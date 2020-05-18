package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class chemical_consumption extends AppCompatActivity implements EditText.OnEditorActionListener {

    static EditText chemicalName;
    static EditText plantFeed;
    static EditText tankVolume;
    static EditText chemSolid;
    static EditText chemDensity;
    static EditText chemFeed;

    Button calculate;
    CheckBox checkPrep;
    CheckBox checkSol;

    TextView tankVolume_text;
    TextView chemSolid_text;
    TextView chemDensity_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_consumption);

        //add check boxes
        checkPrep = findViewById(R.id.checkBox_ifPrep);
        checkSol = findViewById(R.id.checkBox_ifSol);

        //edit text ile degerleri okuma ve double forma cevirme
        chemicalName = findViewById(R.id.chem_chemNameEdit);
        chemicalName.setOnEditorActionListener(this);
        nextEdit(chemicalName);

        plantFeed = findViewById(R.id.chem_plantFeedEdit);
        plantFeed.setOnEditorActionListener(this);
        nextEdit(plantFeed);

        chemFeed = findViewById(R.id.chem_feedEdit);
        chemFeed.setOnEditorActionListener(this);
        nextEdit(chemFeed);

        tankVolume = findViewById(R.id.chem_tankVolumeEdit);
        tankVolume.setOnEditorActionListener(this);
        nextEdit(tankVolume);
        tankVolume_text = findViewById(R.id.chem_tankVolume);
        tankVolume.setVisibility(View.GONE);
        tankVolume_text.setVisibility(View.GONE);

        chemSolid = findViewById(R.id.chem_solidEdit);
        chemSolid.setOnEditorActionListener(this);
        nextEdit(chemSolid);
        chemSolid_text = findViewById(R.id.chem_chemicalSolid);
        chemSolid.setVisibility(View.GONE);
        chemSolid_text.setVisibility(View.GONE);

        chemDensity = findViewById(R.id.chem_densityEdit);
        chemDensity.setOnEditorActionListener(this);
        nextEdit(chemDensity);
        chemDensity_text = findViewById(R.id.chem_density);
        chemDensity.setVisibility(View.GONE);
        chemDensity_text.setVisibility(View.GONE);

        //what happens if checked
        checkPrep.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(checkPrep.isChecked()) {
                    Toast.makeText(getApplicationContext(), buttonView.getText(), Toast.LENGTH_SHORT).show();
                    tankVolume.setVisibility(View.VISIBLE);

                    tankVolume_text.setVisibility(View.VISIBLE);
                    chemSolid.setVisibility(View.VISIBLE);
                    chemSolid_text.setVisibility(View.VISIBLE);
                } else {
                    tankVolume.setVisibility(View.GONE);
                    tankVolume_text.setVisibility(View.GONE);
                    tankVolume.setText("");
                    chemSolid.setVisibility(View.GONE);
                    chemSolid.setText("");
                    chemSolid_text.setVisibility(View.GONE);

                }

            }
        });

        checkSol.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(checkSol.isChecked()) {
                    Toast.makeText(getApplicationContext(), buttonView.getText(), Toast.LENGTH_SHORT).show();
                    chemDensity.setVisibility(View.VISIBLE);
                    chemDensity_text.setVisibility(View.VISIBLE);
                } else {
                    chemDensity.setVisibility(View.GONE);
                    chemDensity.setText("");
                    chemDensity_text.setVisibility(View.GONE);
                }

            }
        });

        //calculate and go to report page
        calculate = findViewById(R.id.chemical_calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPrep.isChecked() & (checkSol.isChecked() == true)) {
                    showpopUp_check(calculate);
                } else {
                    if (checkIsNull(chemicalName, plantFeed, chemFeed)) {
                        showpopUp(calculate);
                    } else {
                        Intent i = new Intent(chemical_consumption.this, chem_report.class);
                        startActivity(i);
                    }


                }
            }
        });

        // go home
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(chemical_consumption.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onEditorAction(TextView tv, int x, KeyEvent ke) {
        return true;

    }

    public boolean checkIsNull(EditText... editTexts){
        for (EditText editText: editTexts){
            if(editText.getText().length() == 0){
                return true;
            }
        }
        return false;
    }

    public void showpopUp(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void showpopUp_check(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.chem_check, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    //enter for next edit text
    boolean nextEdit (final EditText editText) {


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == 0 || actionId== EditorInfo.IME_ACTION_DONE)
                {
                    editText.requestFocus();
                }
                return false;
            }
        });
        return true;
    }

}
