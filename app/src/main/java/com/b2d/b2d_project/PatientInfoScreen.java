package com.b2d.b2d_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by buseburcu on 20.10.2016.
 */

public class PatientInfoScreen extends AppCompatActivity{

    Button btnFindDoctor;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_patient_info);

        Intent i=getIntent();
        final int id;
        final String pw;
        final String user;
        id=i.getIntExtra("ID",0);

        btnInfo = (Button) findViewById(R.id.btnViewAccount);
        btnFindDoctor = (Button) findViewById(R.id.btnFindDoctor);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientInfoScreen.this,PatientRegister.class);
                i.putExtra("ID",id+"");
                startActivity(i);
            }
        });

        btnFindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientInfoScreen.this,DoctorList.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });
    }
}
