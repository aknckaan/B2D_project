package com.b2d.b2d_project;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    String usr="d123";//will be retrieved from database
    String usr2="p123";//will be retrieved from database
    String pw="123";//will be retrieved from database
    EditText password;
    EditText username;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseManager fm=new FirebaseManager();

        password =(EditText) findViewById(R.id.etLoginPassword);
        username = (EditText)findViewById(R.id.etLoginUserName);
        btnLogin= (Button) findViewById(R.id.btnLoginLog_In);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((usr.equals(username.getText().toString())&&pw.equals(password.getText().toString()))|| (usr2.equals(username.getText().toString())&&pw.equals(password.getText().toString())))
                {
                    Intent i=null;

                    if(username.getText().toString().charAt(0)=='p')
                    {
                        i=new Intent(LoginActivity.this,PatientInfoScreen.class);


                    }else if(username.getText().toString().charAt(0)=='d')
                    {
                        i=new Intent(LoginActivity.this,DoctorInfoScreen.class);
                    }
                    else
                        return;

                    startActivity(i);

                }
                else
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
                    dlgAlert.setMessage("wrong password or username");
                    dlgAlert.setTitle("Login Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }
        });




    }
}
