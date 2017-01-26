package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    String fileName;
    String epilepsy="";
    String usr;
    String pw;//will be retrieved from database
    EditText password;
    EditText username;
    Button btnLogin;
    CheckBox cbRemember;
    String pId;
    CheckBox cbAuto;
    ProgressDialog pd;
    SharedPreferences.Editor editor;
    private static final String TAG = "LoginActivity";
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
                epilepsy=getIntent().getStringExtra("Epilepsy");


                if(epilepsy.equals("1"))
                {
                    pId = getIntent().getStringExtra("PId");
                    fileName= getIntent().getStringExtra("File");

                }
        }catch (Exception e)
        {

        }

        System.out.println("LOGIN: "+epilepsy+" "+pId+" "+fileName);

        password =(EditText) findViewById(R.id.etLoginPassword);
        username = (EditText)findViewById(R.id.etLoginUserName);
        btnLogin= (Button) findViewById(R.id.btnLoginLog_In);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbAuto = (CheckBox) findViewById(R.id.cbAutoLogin);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        cbRemember.setChecked(true);

        editor = getSharedPreferences("Login_Pref", MODE_PRIVATE).edit();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    pd= new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Loading...");
                    usr=username.getText().toString();
                    pw=password.getText().toString();
                    final Login lgn = new Login(username.getText().toString(),password.getText().toString(),pd,LoginActivity.this);
                    pd.show();
                    String result ="";
                    lgn.execute();

                try {
                    result=lgn.get();
                    pd.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                    final String finalResult = result;

                            if(finalResult==null)
                            {
                                Toast.makeText(getApplicationContext(),"Check your internet connection and try again!",Toast.LENGTH_LONG).show();
                                return;
                            }

                            int id=0;
                            if(!finalResult.equals("0"))
                                id= Integer.parseInt(lgn.result.substring(0,lgn.result.length()-1));

                            if(finalResult.indexOf("P")>0&&id>0)
                            {
                                TokenManager tkn= new TokenManager(usr,pw,FirebaseInstanceId.getInstance().getToken());
                                tkn.execute();
                                if(cbRemember.isChecked())
                                {
                                    editor = getSharedPreferences("Login_Pref", MODE_PRIVATE).edit();
                                    editor.putString("UName", usr);
                                    editor.putString("Password", pw);
                                    editor.putBoolean("Auto",cbAuto.isChecked());
                                    editor.commit();
                                }

                                Intent i = new Intent(LoginActivity.this,PatientInfoScreen.class);
                                i.putExtra("ID",id);
                                i.putExtra("Password",pw);
                                i.putExtra("Username",usr);
                                startActivity(i);
                            }
                            if(finalResult.indexOf("D")>0&&id>0)
                            {
                                TokenManager tkn= new TokenManager(usr,pw,FirebaseInstanceId.getInstance().getToken());
                                tkn.execute();
                                if(cbRemember.isChecked())
                                {
                                    editor = getSharedPreferences("Login_Pref", MODE_PRIVATE).edit();
                                    editor.putString("UName", usr);
                                    editor.putString("Password", pw);
                                    editor.putBoolean("Auto",cbAuto.isChecked());
                                    editor.commit();
                                }

                                Intent i = new Intent(LoginActivity.this,DoctorInfoScreen.class);

                                try {
                                    if (epilepsy.equals("1")) {
                                        i.putExtra("Epilepsy", epilepsy);
                                        i.putExtra("File", fileName);
                                        i.putExtra("PId", pId);
                                    }
                                } catch (Exception e)
                                {

                                }

                                i.putExtra("ID",id);
                                i.putExtra("Password",pw);
                                i.putExtra("Username",usr);
                                startActivity(i);
                            }

                            else if (finalResult.equals("0"))
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,PatientRegister.class);
                i.putExtra("New",true);
                startActivity(i);
            }
        });

        getPrefs();
        if(cbAuto.isChecked())
        {

            btnLogin.performClick();
        }
    }

    public void getPrefs()
    {
        usr=getSharedPreferences("Login_Pref", MODE_PRIVATE).getString("UName",null);
        pw=getSharedPreferences("Login_Pref", MODE_PRIVATE).getString("Password",null);
        Boolean auto = getSharedPreferences("Login_Pref",MODE_PRIVATE).getBoolean("Auto",false);
        if(usr!=null)
            username.setText(usr);
        if(pw!=null)
            password.setText(pw);
        if(cbAuto!=null)
        {
            cbAuto.setChecked(auto);
        }
    }
}
