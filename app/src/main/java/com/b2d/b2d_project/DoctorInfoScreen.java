package com.b2d.b2d_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        String fileName;
        String epilepsy;
        String pId;

            id = i.getIntExtra("ID", 0);
            fileName = i.getStringExtra("File");
            epilepsy = i.getStringExtra("Epilepsy");
            pId = i.getStringExtra("PId");
            //epilepsy="1";
            //fileName="DW_16-39_0.txt";
            //pId="63";
        try {
            if (epilepsy.equals("1")) {
                Intent intent = new Intent(DoctorInfoScreen.this, DataChart.class);
                intent.putExtra("File", fileName);
                System.out.println("-------------------------------------");
                intent.putExtra("PId", pId);
                startActivity(intent);
            }
        }catch (Exception e)
        {

        }
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
