package com.b2d.b2d_project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DataChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_chart);

        LineChart lc = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        double j =0;
        for(int i =0;i<15000;i++)
        {
            j=j+0.004;
            entries.add(new Entry((float)j,i));
        }


        LineDataSet dataSet = new LineDataSet(entries, "EEG data"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setValueTextSize(10);
        dataSet.setHighLightColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        lineData.setValueTextColor(Color.BLUE);
        dataSet.setHighlightEnabled(true);
        lc.setData(lineData);
        lc.getDescription().setText("Data for patient");
        lc.animateX(2000);
        lc.getLegend().setEnabled(false);
        lc.setScaleYEnabled(false);
        lc.invalidate();


    }
}
