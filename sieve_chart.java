package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;
import java.util.Collections;

public class sieve_chart extends AppCompatActivity {

    int y1 = 80;
    int y2 = 50;
    int y3 = 30;

    int screen = sieve_take_screens.sieveAmount_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sieve_chart);

        //occur chart
        LineChart chart = (LineChart) findViewById(R.id.chart);

        //enter main data
        final ArrayList<Entry> mainData = new ArrayList<>();
        for (int i = 0; i < screen; i++) {
            mainData.add(new Entry((float) sieve_calculation.xAxis[i], (float) sieve_calculation.yAxis[i]));
        }

        //sort xAxis values
        Collections.sort(mainData, new EntryXComparator());

        // d80 point
        ArrayList<Entry> secondData = new ArrayList<>();
        secondData.add(new Entry((float) sieve_calculation.d80, (float) y1));
        // d50 point
        ArrayList<Entry> thirdData = new ArrayList<>();
        thirdData.add(new Entry((float) sieve_calculation.d50, (float) y2));
        // d30 point
        ArrayList<Entry> fothData = new ArrayList<>();
        fothData.add(new Entry((float) sieve_calculation.d30, (float) y3));

        //data add for line
        final LineData chartData = new LineData();

        //birinci verileri ekle
        LineDataSet set1 = new LineDataSet(mainData, "μm - %Cum.");
        chartData.addDataSet(set1);
        set1.setValueTextColor(Color.WHITE);
        set1.setColor(Color.rgb(214, 209, 211));
        set1.setCircleRadius(6);
        set1.setCircleColor(Color.rgb(214, 214, 214));
        set1.setValueTextSize(10);
        set1.getFormLineWidth();
        set1.setLineWidth(3);
        set1.setHighlightEnabled(true);
        set1.setDrawHighlightIndicators(true);
        set1.getHighLightColor();
        set1.setHighlightLineWidth(2f);


        //d80 data
        LineDataSet set2 = new LineDataSet(secondData, "d80");
        chartData.addDataSet(set2);
        set2.setColor(Color.rgb(226,227,204));
        set2.setValueTextColor(Color.rgb(226,227,204));
        set2.setCircleRadius(10);
        set2.setValueTextSize(0);
        set2.setCircleColor(Color.rgb(226,227,204));

        //d50 data
        LineDataSet set3 = new LineDataSet(thirdData, "d50");
        chartData.addDataSet(set3);
        set3.setColor(Color.rgb(7,68,130));
        set3.setValueTextColor(Color.WHITE);
        set3.setCircleRadius(10);
        set3.setValueTextSize(0);
        set3.setCircleColor(Color.rgb(7,68,130));

        //d30 data
        LineDataSet set4 = new LineDataSet(fothData, "d30");
        chartData.addDataSet(set4);
        set4.setColor(Color.rgb(224,132,34));
        set4.setValueTextColor(Color.WHITE);
        set4.setCircleRadius(10);
        set4.setValueTextSize(0);
        set4.setCircleColor(Color.rgb(224,132,34));

        // Controlling X axis
        final XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(16f); // minimum axis-step (interval) is 1
        xAxis.setTextColor(Color.WHITE);


        // yAxis on the right is gone
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(16f);
        yAxisLeft.setTextColor(Color.WHITE);

        // Setting Data
        chart.setData(chartData);
        chart.animateX(2500);
        chart.invalidate();
        chart.setBackgroundColor(Color.rgb(33, 33, 33));
        chart.getLegend().setTextColor(Color.WHITE);
        chart.setTouchEnabled(true);
        chart.getXAxis().setLabelCount(10, true);
        chart.setHighlightPerDragEnabled(true);
        chart.getDescription().setText("MMBalance Screen Analysis");
        chart.getDescription().setTextColor(Color.WHITE);

        //zoom
        ViewPortHandler handler = chart.getViewPortHandler();
        handler.fitScreen();

        // go home
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sieve_chart.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
