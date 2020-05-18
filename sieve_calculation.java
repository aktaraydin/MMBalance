package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mining.mmbalance.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.valueOf;

public class sieve_calculation extends AppCompatActivity {

    static int sieve = Integer.parseInt(sieve_analysis.sieveAmount.getText().toString());
    static double total_mass = Double.parseDouble(sieve_analysis.totalMass.getText().toString());
    //chart axis lists
    static double[] yAxis = new double[sieve+1];
    static double[] xAxis = new double[sieve];
    //weights list
    static double [] weights= new double[sieve+1];

    static double d80;
    static double d50;
    static double d30;

    TextView d80Text;
    TextView d50Text;
    TextView d30Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sieve_calculation);

        for (int i = 0; i < sieve_take_screens.sieveAmount_int; i++) {
            xAxis[i] = Double.parseDouble(sieve_take_screens.sieveList.get(i).getSieveName());
            weights[i] = Double.parseDouble(sieve_take_weight.weightList.get(i).getWeightName());
        }

        d80Text = findViewById(R.id.sieve_d80_result);
        d50Text = findViewById(R.id.sieve_d50_result);
        d30Text = findViewById(R.id.sieve_d30_result);

        double [] weights_with_pan  = calculations.pan_calculate(total_mass,weights);
        double [] weights_to_percent = calculations.mass_to_percent(weights_with_pan);
        double [] cumulative = calculations.cumulative(weights_to_percent);

        yAxis = cumulative;

        //d80
        d80 = calculations.trendCalculation(80,yAxis,xAxis);

        //d50
        d50 = calculations.trendCalculation(50,yAxis,xAxis);

        //d30
        d30 = calculations.trendCalculation(30,yAxis,xAxis);

        if (d80==0) {
            d80Text.setText(R.string.fine_particule);
        } else {
            d80Text.setText(String.format("%.2f", valueOf(d80)));
        }

        if (d50==0) {
            d50Text.setText(R.string.fine_particule);
        } else {
            d50Text.setText(String.format("%.2f", valueOf(d50)));
        }

        if (d30==0) {
            d30Text.setText(R.string.fine_particule);
        } else {
            d30Text.setText(String.format("%.2f", valueOf(d30)));
        }




        findViewById(R.id.goChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sieve_calculation.this, sieve_chart.class);
                startActivity(i);

            }
        });

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sieve_calculation.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

}

class calculations {

    static int sieve = sieve_calculation.sieve;
    static double total_mass = sieve_calculation.total_mass;

    static double[] pan_calculate(double total_mass, double[] weights) {
        double withoutPan = 0;
        double[] weights_with_pan = new double[sieve + 1];

        for (int i = 0; i < (sieve); i++) {

            withoutPan = withoutPan + weights[i];
            weights_with_pan[i] = weights[i];

        }

        double pan = total_mass - withoutPan;
        weights_with_pan[sieve] = pan;

        return weights_with_pan;
    }

    static double[] mass_to_percent(double[] weights) {
        double[] weights_to_percent = new double[sieve + 1];

        for (int i = 0; i < sieve + 1; i++) {
            weights_to_percent[i] = (weights[i] / total_mass) * 100;
        }

        return weights_to_percent;
    }

    static double[] cumulative(double[] weights_percent) {
        double[] cumulative = new double[sieve + 1];
        cumulative[sieve] = weights_percent[sieve];

        for (int i = sieve; i > 0; i--) {
            cumulative[i - 1] = weights_percent[i - 1] + cumulative[i];
        }
        return cumulative;
    }

    public static int trend_search(int y, double[] yAxis) {
        int idx = 0;
        List bigOFy = new ArrayList<>();
        //math.abs absolute value
        for (int c = 0; c < sieve; c++) {
            if (yAxis[c] == y) {
                idx = c;
            } else if (yAxis[c] > y) {
                bigOFy.add(Math.abs(y - yAxis[c]));
            }
        }
        List<Integer> sortedlist = new ArrayList<>(bigOFy);
        Collections.sort(sortedlist);

        for (int i = 0; i < bigOFy.size(); i++) {
            if (sortedlist.get(0) == bigOFy.get(i)) {
                idx = i;
            }
        }
        return idx;

    }

    static double trendCalculation(int v, double[] yAxis, double[] xAxis) {

        int small = trend_search(v, yAxis);
        int big = small + 1;
        //double [] dy = {yAxis[small], yAxis[big]};
        //double [] dx = {xAxis[small], xAxis[big]};
        double a, b, c, d, e, f, m, y_intercept;
        double x=0;
        double a1 = 0;
        double b1 = 0;
        double b2 = 0;
        double c1 = 0;

        if (big > xAxis.length) {
            return x;
        } else if (big<xAxis.length) {

            double[] dy = {yAxis[small], yAxis[big]};
            double[] dx = {xAxis[small], xAxis[big]};

            if (small == big) {

                x = xAxis[small];

            } else {
                for (int i = 0; i < dx.length; i++) {

                    a1 = a1 + dx[i] * dy[i];
                    b1 = b1 + dx[i];
                    b2 = b2 + dy[i];
                    c1 = c1 + dx[i] * dx[i];

                }
                a = dx.length * a1;
                b = b1 * b2;
                c = dx.length * c1;
                d = b1 * b1;
                m = (a - b) / (c - d);
                e = b2;
                f = m * b1;
                y_intercept = (e - f) / dx.length;

                x = (v - y_intercept) / m;

            }


        }
        return x;
    }
}















