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

public class pulp_mix extends AppCompatActivity implements EditText.OnEditorActionListener {

    EditText first_sg, second_sg, first_volume, second_volume;
    TextView result;
    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulp_mix);

        //Define edit texts
        first_sg = findViewById(R.id.mix_first_sg_edit);
        first_sg.setOnEditorActionListener(this);
        nextEdit(first_sg);

        second_sg = findViewById(R.id.mix_second_pulp_sg_edit);
        second_sg.setOnEditorActionListener(this);
        nextEdit(second_sg);

        first_volume = findViewById(R.id.mix_first_pulp_volume);
        first_volume.setOnEditorActionListener(this);
        nextEdit(first_volume);

        second_volume = findViewById(R.id.mix_pulp_second_volume_edit);
        second_volume.setOnEditorActionListener(this);
        nextEdit(second_volume);

        //report
        result = findViewById(R.id.mixed_pulp_result);

        //calculate
        calculate = findViewById(R.id.pulp_mix_calculate);

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (checkIsNull(first_sg,second_sg,first_volume,second_volume)) {
                    showpopUp(calculate);
                } else {

                   result.setText(String.format("%.3f",valueOf(mixed_sg(first_sg,first_volume,second_sg,second_volume))));
                }
            }


        });

        // go home
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pulp_mix.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //calculate

   double mixed_sg (EditText sg1,EditText volume1,EditText sg2,EditText volume2){
        //edit text to double
        double sg1D = parseDouble(sg1.getText().toString());
        double volume1D = parseDouble(volume1.getText().toString());
        double sg2D = parseDouble(sg2.getText().toString());
        double volume2D = parseDouble(volume2.getText().toString());

        //calculate mass
        double m1 = (sg1D * volume1D);
        double m2 = (sg2D * volume2D);

        //calculate mixed sg
        double mixed_sg = ((m1 + m2) / (volume1D + volume2D));

        return mixed_sg;
    }

    @Override
    public boolean onEditorAction(TextView tv, int x, KeyEvent ke) {
        return true;

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
}
