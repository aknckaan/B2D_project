package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {


    String usr;
    String pw;//will be retrieved from database
    EditText password;
    EditText username;
    Button btnLogin;
    CheckBox cbRemember;
    String a;
    CheckBox cbAuto;
    ProgressDialog pd;
    SharedPreferences.Editor editor;
    private static final String TAG = "LoginActivity";
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        try {
                a = getIntent().getExtras().get("pId").toString();
        }catch (Exception e)
        {

        }

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
                    final Login lgn = new Login(username.getText().toString(),password.getText().toString(),pd);
                    pd.show();
                    lgn.execute();

                    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            int id= Integer.parseInt(lgn.result.substring(0,lgn.result.length()-1));
                            if(lgn.result.indexOf("P")>0&&id>0)
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
                                startActivity(i);
                            }
                            if(lgn.result.indexOf("D")>0&&id>0)
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
