package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PatientRegister extends AppCompatActivity {

    Spinner spGender;
    Switch sw ;
    EditText etName;
    EditText etSurname;
    EditText etUsername;
    EditText etAge;
    EditText etCity;
    EditText etPhone;
    EditText etCountry;
    EditText etAddress;
    EditText etPassword;
    Button save;
    Spinner spType;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        Boolean n =getIntent().getBooleanExtra("New",false);



        spGender = (Spinner)findViewById(R.id.spGender);
        etName = (EditText)findViewById(R.id.etRegisterName);
        etSurname = (EditText)findViewById(R.id.etSurname);
        etAge = (EditText)findViewById(R.id.etRegisterAge);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etCity = (EditText)findViewById(R.id.etCity);
        etCountry = (EditText)findViewById(R.id.etRegisterCountry);
        etPhone = (EditText)findViewById(R.id.etPhoneNumber);
        etAddress = (EditText)findViewById(R.id.etRegisterAddress);
        etPassword = (EditText)findViewById(R.id.tvPassword);
        sw = (Switch)findViewById(R.id.swEdit);
        save = (Button) findViewById(R.id.btnSave);
        spType = (Spinner) findViewById(R.id.spType);


        List list= new ArrayList<String>();
        list.add("Male");
        list.add("Female");

        List list2 = new ArrayList<String>();
        list2.add("Patient");
        list2.add("Doctor");

        spType.setSelection(0);

        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spGender.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<Integer> spinnerArrayAdapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, list2);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spType.setAdapter(spinnerArrayAdapter2);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(((Switch)view).isChecked())
                {
                    try {
                       startRegistration();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender= spGender.getSelectedItem().toString();
                String name= etName.getText().toString();
                String surname= etSurname.getText().toString();
                String username= etUsername.getText().toString();
                String password= etPassword.getText().toString();
                String age= etAge.getText().toString();
                String city= etCity.getText().toString();
                String address= etAddress.getText().toString();
                String phone= etPhone.getText().toString();
                String country=etCountry.getText().toString();
                String type= spType.getSelectedItem().toString();


                if(gender.length()>0 & name.length()>0 &surname.length()>0 & username.length()>0 & password.length()>0 & age.length()>0 & city.length()>0 & address.length()>0 & phone.length()>0 & country.length()>0 & type.length()>0)
                {
                    ProgressDialog pd;
                    pd= new ProgressDialog(PatientRegister.this);
                    pd.setMessage("Registering...");
                    final Register reg = new Register(gender,name,surname,username,password,age,city,address,phone,country,type,pd,PatientRegister.this);

                    reg.execute();

                    /*pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {


                            if(reg.result == null)
                                Toast.makeText(PatientRegister.this,"Check your internet connection and try again!",Toast.LENGTH_LONG).show();

                            if(reg.result.equals("-1"))
                            {
                                Toast.makeText(PatientRegister.this,"Check your internet connection and try again!",Toast.LENGTH_LONG).show();
                                //finish();

                            }
                            else if(reg.result.equals("0"))
                            {
                                Toast.makeText(PatientRegister.this,"Registration Failed! This username already exists!",Toast.LENGTH_LONG).show();
                                //finish();
                            }
                            else if(reg.result.equals("1"))
                            {
                                Toast.makeText(PatientRegister.this,"Registration successfull!",Toast.LENGTH_LONG).show();
                                //finish();
                            }
                            //finish();
                        }

                    });
                    pd.show();
                    reg.wait();*/
                    //pd.dismiss();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the fields!",Toast.LENGTH_LONG).show();
                }

                //startRegistration();


            }
        });

        if(n)
        {
            sw.setEnabled(false);
            sw.setActivated(false);
            sw.setVisibility(View.GONE);
        }
        else
        {
            save.setEnabled(false);
            save.setVisibility(View.GONE);
        }


    }

    public void startRegistration () throws InterruptedException {


        String gender= spGender.getSelectedItem().toString();
        String name= etName.getText().toString();
        String surname= etSurname.getText().toString();
        String username= etUsername.getText().toString();
        String password= etPassword.getText().toString();
        String age= etAge.getText().toString();
        String city= etCity.getText().toString();
        String address= etAddress.getText().toString();
        String phone= etPhone.getText().toString();
        String country=etCountry.getText().toString();
        String type= spType.getSelectedItem().toString();



        //return false;
    }
}
