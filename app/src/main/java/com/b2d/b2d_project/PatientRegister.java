package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import java.util.concurrent.ExecutionException;

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
        String uname = getIntent().getStringExtra("ID");

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


        if(!n)
        {
            sw.setChecked(false);
            ProgressDialog pd = new ProgressDialog(this);
            pd.show();
            pd.setMessage("Loading information");
            GetInfo gi = new GetInfo(uname,pd);
            gi.execute();

            try {

                ArrayList arr=gi.get();
                pd.dismiss();
                etName.setText((String)arr.get(1));
                etSurname.setText((String)arr.get(2));
                etUsername.setText((String)arr.get(3));
                etPassword.setText((String)arr.get(4));

                if(((String)spGender.getItemAtPosition(0)).equals((String)arr.get(5)))
                {
                    spGender.setSelection(0);
                }
                else
                    spGender.setSelection(1);

                etAge.setText((String)arr.get(6));
                etCountry.setText((String)arr.get(7));
                etCity.setText((String)arr.get(8));
                etAddress.setText((String)arr.get(9));
                etPhone.setText((String)arr.get(10));

                spGender.setEnabled(false);
                etName.setEnabled(false);
                etSurname.setEnabled(false);
                etUsername.setEnabled(false);
                etPassword.setEnabled(false);
                etAge.setEnabled(false);
                etCity.setEnabled(false);
                etAddress.setEnabled(false);
                etPhone.setEnabled(false);
                etCountry.setEnabled(false);
                spType.setEnabled(false);



            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!((Switch)view).isChecked())
                {
                    spGender.setEnabled(false);
                    etName.setEnabled(false);
                    etSurname.setEnabled(false);
                    etUsername.setEnabled(false);
                    etPassword.setEnabled(false);
                    etAge.setEnabled(false);
                    etCity.setEnabled(false);
                    etAddress.setEnabled(false);
                    etPhone.setEnabled(false);
                    etCountry.setEnabled(false);
                    spType.setEnabled(false);

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
                                pd.setMessage("Updating...");
                                final UpdateInfo ui = new UpdateInfo(gender,username,password,age,city,address,phone,country,type,pd,PatientRegister.this);

                                ui.execute();
                                Toast.makeText(getApplicationContext(),"Updating...",Toast.LENGTH_LONG).show();

                                try {
                                    ui.get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(),"Information Updated!",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please fill all the fields!",Toast.LENGTH_LONG).show();
                            }



                }
                else
                {
                    spGender.setEnabled(true);
                    etName.setEnabled(false);
                    etSurname.setEnabled(false);
                    etUsername.setEnabled(false);
                    etPassword.setEnabled(true);
                    etAge.setEnabled(true);
                    etCity.setEnabled(true);
                    etAddress.setEnabled(true);
                    etPhone.setEnabled(true);
                    etCountry.setEnabled(true);
                    spType.setEnabled(false);
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
                    Toast.makeText(getApplicationContext(),"Registering...",Toast.LENGTH_LONG).show();

                    try {
                        reg.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if(type.equals("Doctor"))
                    {

                        AlertDialog alertDialog = new AlertDialog.Builder(PatientRegister.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Register complete! After sending relative information to our company, doctor account will be activated. For more information please visit www.diaware.com");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Register complete!",Toast.LENGTH_LONG).show();
                        finish();
                    }


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
}
