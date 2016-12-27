package com.b2d.b2d_project;

import android.app.ProgressDialog;
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
 * Created by Kaan on 26/12/2016.
 */

public class AcceptRequest extends AsyncTask<String, String, ArrayList<String>> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/acceptRequest.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private String password;
    private String username;
    public String result;
    ArrayList<String> arr3;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog p;
    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;
    String pId ;
    String dId;
    String value;


    public AcceptRequest(String value,String pId,String dId,ProgressDialog p)
    {
        this.pId = pId;
        this.dId=dId;
        this.p=p;
        this.value=value;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<String> doInBackground(String... args) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dId", ""+dId);
        params.put("pId", ""+pId);
        params.put("val",value);

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

                //buse <3

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr3;
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
    @Override
    protected void onPostExecute(ArrayList<String> arr4) {


        p.dismiss();
    }

}
