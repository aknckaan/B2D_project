package com.b2d.b2d_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientRegister extends AppCompatActivity {

    Spinner spGender;
    Switch sw ;
    TextView tvName;
    TextView tvSurname;
    TextView tvUsername;
    TextView tvAge;
    TextView tvCity;
    TextView tvPhone;
    TextView tvCountry;
    TextView tvAddress;
    TextView tvPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        Boolean n =getIntent().getBooleanExtra("New",false);


        spGender = (Spinner)findViewById(R.id.spGender);
        tvName = (TextView)findViewById(R.id.tvRegisterName);
        tvSurname = (TextView)findViewById(R.id.tvRegisterSurname);
        tvAge = (TextView)findViewById(R.id.tvAge);
        tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvCity = (TextView)findViewById(R.id.tvCity);
        tvCountry = (TextView)findViewById(R.id.tvCountry);
        tvPhone = (TextView)findViewById(R.id.tvPhoneNumber);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        sw = (Switch)findViewById(R.id.swEdit);

        List list= new ArrayList<String>();
        list.add("Male");
        list.add("Female");

        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spGender.setAdapter(spinnerArrayAdapter);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String gender= spGender.getSelectedItem().toString();
                String name= tvName.getText().toString();
                String surname= tvSurname.getText().toString();
                String username= tvUsername.getText().toString();
                String password= tvPassword.getText().toString();
                String age= tvAge.getText().toString();
                String city= tvCity.getText().toString();
                String address= tvAddress.getText().toString();
                String phone= tvPhone.getText().toString();
                String country=tvCountry.getText().toString();




                if(((Switch)view).isChecked())
                {
                    //if(G)
                }

            }
        });

    }
}
