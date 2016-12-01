package com.b2d.b2d_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by buseburcu on 20.10.2016.
 */

public class PatientInfoScreen extends AppCompatActivity{

    Button btnDevice;
    Button btnFindDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_patient_info);

        Intent i=getIntent();
        final int id;
        id=i.getIntExtra("ID",0);
        btnDevice = (Button) findViewById(R.id.btnGoToDevice);
        btnFindDoctor = (Button) findViewById(R.id.btnFindDoctor);

        btnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientInfoScreen.this,DeviceScreen.class);

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
