package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataChart extends AppCompatActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_chart);

        String fileName=getIntent().getStringExtra("File");
        String pId=getIntent().getStringExtra("PId");

        pd = new ProgressDialog(DataChart.this);
        pd.show();
        LineChart lc = (LineChart) findViewById(R.id.chart);

        final TxtView myView = new TxtView(fileName,pId,pd);
        myView.execute();
        ArrayList arr = new ArrayList();
        try {
            arr=myView.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final List<Entry> entries = new ArrayList<Entry>();


        double j =0;
        for(int i =0;i<arr.size();i++)
        {
            j=j+0.004;
            entries.add(new Entry((float)j,(float)((double)myView.values.get(i))));
        }
        final LineDataSet dataSet = new LineDataSet(entries, "EEG data"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setValueTextSize(10);
        dataSet.setHighLightColor(Color.BLACK);
        dataSet.disableDashedLine();


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
