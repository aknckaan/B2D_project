package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.print.PrinterId;

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
import java.util.List;
import java.util.Map;

/**
 * Created by buseburcu on 26.11.2016.
 */
import java.util.Arrays;
public class SendRequest extends AsyncTask<String, String, String> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/setRequest.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public String result;
    ArrayList<String> arr3;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog p;
    int dId;
    int pId;
    /**
     * Before starting background thread Show Progress Dialog
     * */



    public SendRequest(int dId,int pId,ProgressDialog p)
    {
        this.dId=dId;
        this.pId=pId;
        this.p=p;

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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dId", ""+dId);
        params.put("pId", ""+pId);

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

                result= result.substring(result.indexOf(":")+1,result.indexOf(":")+2);
                /*String[] infoArr = result.split("\"");
                arr3 = new ArrayList<String>();
                int j = 0;
                for(int i =3; i < infoArr.length ; i+=4) {
                    System.out.println("Print..........................................." + infoArr[i]);
                    arr3.add(infoArr[i]);
                    j++;

                }*/

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
    protected void onPostExecute(String arr4) {


        p.dismiss();
    }

}




