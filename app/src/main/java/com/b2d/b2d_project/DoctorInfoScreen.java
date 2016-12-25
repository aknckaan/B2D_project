package com.b2d.b2d_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;

//import java.util.ArrayList;
//import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

    Button viewAccount;
    Button viewNewPatients;
    Button viewCurrPatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_doctor_info);


        Intent i=getIntent();
        final int id;
        id=i.getIntExtra("ID",0);
        viewAccount = (Button) findViewById(R.id.btnViewAccount);
        viewNewPatients = (Button) findViewById(R.id.btnViewNewP);

        viewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorInfoScreen.this,DeviceScreen.class);

                startActivity(i);
            }
        });

        viewNewPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorInfoScreen.this,NewPatientList.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });


    }


}
