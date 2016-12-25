package com.b2d.b2d_project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buseburcu on 20.10.2016.
 */

public class DoctorInfoScreen extends AppCompatActivity {

    public DoctorInfoScreen() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_doctor_info);

        LineChart lc = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        double j =0;
        for(int i =0;i<15000;i++)
        {
                j=j+0.004;
            entries.add(new Entry(i,(float)j));
        }


        LineDataSet dataSet = new LineDataSet(entries, "EEG data"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.GRAY);
        dataSet.setValueTextSize(10);

        LineData lineData = new LineData(dataSet);
        lc.setData(lineData);
        lc.setScaleYEnabled(false);
        lc.invalidate();


    }


}
