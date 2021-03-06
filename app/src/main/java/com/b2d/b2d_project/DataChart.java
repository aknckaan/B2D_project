package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DataChart extends AppCompatActivity {

    ProgressDialog pd;
    String pId;
    MenuItem item;
    ArrayList arr=new ArrayList();
    LineChart lc;
    TxtView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_chart);

        String fileName=getIntent().getStringExtra("File");
        pId=getIntent().getStringExtra("PId");
        item = (MenuItem) findViewById(R.id.action_name);
        String epilepsy = getIntent().getStringExtra("Epilepsy");

        pd = new ProgressDialog(DataChart.this);
        pd.show();
        lc = (LineChart) findViewById(R.id.chart);

        myView = new TxtView(fileName,pId,pd, this);
        myView.execute();
        /*try {
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
        lc.getDescription().setText("See patient data.");
        lc.animateX(2000);
        lc.getLegend().setEnabled(false);
        lc.setScaleYEnabled(false);

        lc.invalidate();

       */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.layout_menu, menu);

        return true;

    }

    public void fillArray(ArrayList arr2)
    {
        for(int i=0;i<arr2.size();i++)
        {
            arr.add(arr2.get(i));
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
        lc.getDescription().setText("See patient data.");
        lc.animateX(2000);
        lc.getLegend().setEnabled(false);
        lc.setScaleYEnabled(false);

        lc.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_name:
                if(getIntent().getStringExtra("Epilepsy")==null)
                    return true;

                Intent i = new Intent(DataChart.this,PatientFiles.class);
                i.putExtra("PId",pId);
                startActivity(i);
                finish();
                break;

            default:
                break;
        }

        return true;
    }
}
