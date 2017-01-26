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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kaan on 27/12/2016.
 */

public class GetInfo extends AsyncTask<String, String, ArrayList> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/retreiveInfo.php";

    private String pId;

    public String result;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog p;
    Activity a;
    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;


    public GetInfo(String pId,ProgressDialog p)
    {
        this.pId=pId;
        this.p=p;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList doInBackground(String... args) {
        // TODO Auto-generated method stub
        // Check for success tag
      ArrayList arr = new ArrayList();

        // Building Parameters
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("PId", pId);

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

                //String suc = result.substring(0,result.indexOf(","));
                String []suc2= result.split("\"");

                for(int i =3;i<suc2.length;i=i+4)
                {
                    arr.add(suc2[i]);
                }


            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
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
