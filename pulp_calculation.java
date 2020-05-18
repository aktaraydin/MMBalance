package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class pulp_calculation extends AppCompatActivity {

    Button pulp_sg;
    Button pulp_mox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulp_calculation);

        findViewById(R.id.pulp_SG_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pulp_calculation.this, pulp_density.class);
                startActivity(i);
            }
        });

        findViewById(R.id.pulp_mix_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pulp_calculation.this, pulp_mix.class);
                startActivity(i);
            }
        });

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pulp_calculation.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}
