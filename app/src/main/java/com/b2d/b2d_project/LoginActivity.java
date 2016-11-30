package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {


    String usr="Ali";//will be retrieved from database
    String pw="123";//will be retrieved from database
    EditText password;
    EditText username;
    Button btnLogin;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseManager fm=new FirebaseManager();

        password =(EditText) findViewById(R.id.etLoginPassword);
        username = (EditText)findViewById(R.id.etLoginUserName);
        btnLogin= (Button) findViewById(R.id.btnLoginLog_In);

        Log.d(TAG, "Got token: " +  FirebaseInstanceId.getInstance().getToken());

        //delete
        username.setText(usr);
        password.setText(pw);
        //

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    ProgressDialog pd= new ProgressDialog(LoginActivity.this);

                    usr=username.getText().toString();
                    pw=password.getText().toString();
                    final Login lgn = new Login(username.getText().toString(),password.getText().toString(),pd);
                    pd.show();
                    lgn.execute();

                    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(lgn.result.equals("1P"))
                            {
                                TokenManager tkn= new TokenManager(usr,pw,FirebaseInstanceId.getInstance().getToken());
                                tkn.execute();

                                Intent i = new Intent(LoginActivity.this,PatientInfoScreen.class);
                                startActivity(i);
                            }
                            if(lgn.result.equals("1D"))
                            {
                                TokenManager tkn= new TokenManager(usr,pw,FirebaseInstanceId.getInstance().getToken());
                                tkn.execute();

                                Intent i = new Intent(LoginActivity.this,DoctorInfoScreen.class);
                                startActivity(i);
                            }

                            else if (lgn.result.equals("0"))
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
        });

    }
}
