package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;




public class sieve_analysis extends AppCompatActivity implements EditText.OnEditorActionListener {

    //alinan degerler
    static EditText sieveAmount;
    static EditText totalMass;

    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sieve_analysis);

        //read values
        sieveAmount = findViewById(R.id.sieve_sieveAmountEnter);
        sieveAmount.setOnEditorActionListener(this);
        nextEdit(sieveAmount);

        totalMass = findViewById(R.id.sieve_totalMass);
        totalMass.setOnEditorActionListener(this);
        nextEdit(totalMass);

        //enter values button
        enter = findViewById(R.id.sieve_enterValuesButton);
        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (checkIsNull(sieveAmount, totalMass)) {
                    showpopUp(enter);
                } else {
                    Intent i = new Intent(sieve_analysis.this, sieve_take_screens.class);
                    startActivity(i);
                }
            }


        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
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
