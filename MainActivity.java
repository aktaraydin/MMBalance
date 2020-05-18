package com.mining.mmbalance;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;



public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.pulp_calculationButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), pulp_calculation.class);
                startActivity(i);
            }
        });


        findViewById(R.id.sieve_analysisButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), sieve_analysis.class);
                startActivity(i);
            }
        });

        findViewById(R.id.chemical_consumptionButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), chemical_consumption.class);
                startActivity(i);
            }
        });

       // introducing = findViewById(R.id.youtube);

        //introducing.setOnClickListener(new View.OnClickListener() {

       //     public void onClick(View v) {

      //          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=kNEqP1_qBqY&feature=youtu.be")));
      //          Log.i("Video", "Video Playing....");

        //    }
      //  });


    }

}




