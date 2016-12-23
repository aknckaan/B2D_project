package com.b2d.b2d_project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kaan on 16/12/2016.
 */

public class Register extends AsyncTask<String, String, String> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/register.php";

    private String password;
    private String username;
    private String gender;
    private String name;
    private String surname;
    private String age;
    private String city;
    private String address;
    private String phone;
    private String country;
    private String type;
    public String result;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog p;
    Activity a;
    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;


    public Register(String gender, String name, String surname, String username, String password, String age, String city, String address, String phone, String country, String type, ProgressDialog p, Activity a)
    {
        this.gender = gender;
        this.name =  name;
        this.surname =surname;
        this.age = age;
        this.city=city;
        this.address=address;
        this.phone=phone;
        this.country = country;
        this.type = type;
        this.password=password;
        this.username=username;
        this.p=p;
        this.a =a;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        // TODO Auto-generated method stub
        // Check for success tag
        int success;

        if(!isNetworkConnected())
        {
            result = -1+"";

            return result;
        }



        // Building Parameters
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("gender", gender);
        params.put("name", name);
        params.put("surname", surname);
        params.put("age", age);
        params.put("city", city);
        params.put("address", address);
        params.put("phone", phone);
        params.put("country", country);
        params.put("account", type);
        params.put("username", username);
        params.put("password", password);

        JSONObject object = null;
        try {
            URL url = new URL(LOGIN_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osWriter = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter writer = new BufferedWriter(osWriter);
            writer.write(getPostData(params));

            writer.flush();
            writer.close();
            os.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                BufferedReader reader = new BufferedReader(isReader);

                result = "";
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                String suc = result.substring(0,result.indexOf(","));
                String []suc2= suc.split("\"");
                suc = suc2[3];

                result = suc;

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager)a.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void test() throws IOException {

    }

    public String getPostData(HashMap<String, String> values) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");
            try {
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {}
        }
        return builder.toString();
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
            //p.dismiss();


    }

}
