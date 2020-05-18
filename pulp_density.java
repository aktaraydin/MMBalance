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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import static java.lang.Double.parseDouble;
import static java.lang.Double.valueOf;



public class pulp_density extends AppCompatActivity implements EditText.OnEditorActionListener {

    EditText oreDensity;
    EditText weightPercent;
    Button calculate;

    double oreDensityD;
    double weightPercentD;
    double pulpDensity_report;
    TextView pulpDensity_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulp_density);

        //real velues from editText
        oreDensity = findViewById(R.id.pulp_solid_density_edit);
        oreDensity.setOnEditorActionListener(this);
        nextEdit(oreDensity);

        weightPercent = findViewById(R.id.solid_percent_edit);
        weightPercent.setOnEditorActionListener(this);
        nextEdit(weightPercent);

        //pulp density report
        pulpDensity_text = findViewById(R.id.pulp_pulp_density_report);

        //calculate
        calculate =findViewById(R.id.pulp_density_calculate);

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (checkIsNull(oreDensity,weightPercent)) {
                    showpopUp(calculate);
                } else {

                    oreDensityD = parseDouble(oreDensity.getText().toString());
                    weightPercentD = parseDouble(weightPercent.getText().toString());
                    pulpDensity_report = oreDensityD/(oreDensityD +(weightPercentD/100)*(1-oreDensityD));
                    pulpDensity_text.setText(String.format("%.3f",valueOf(pulpDensity_report)));
                }
            }


        });

        // go home
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pulp_density.this, MainActivity.class);
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
