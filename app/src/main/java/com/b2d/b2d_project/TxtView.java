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
 * Created by Kaan on 25/12/2016.
 */

public class TxtView extends AsyncTask<String, String, ArrayList<Double>> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/readTxt.php";



    public String result;
    ArrayList<Double> values;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog p;

    String pId;
    String fileName;
    Object LOCK;


    public TxtView(String fileName, String pId, ProgressDialog p)
    {
        this.pId=pId;
        this.p=p;
        this.fileName=fileName;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Double> doInBackground(String... args) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("PId", ""+pId);
        params.put("FName",fileName);

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
                values= new ArrayList<Double>();
                while ((line = reader.readLine()) != null) {
                    //values.add(Float.parseFloat(line));
                    result += line;
                }
                result = result.substring(2,result.length()-1);
                String valueArr[]=result.split("n");
                for (int i =0;i<valueArr.length;i++)
                {
                    if(valueArr[i].indexOf("\\")>0)
                    {
                       String a=valueArr[i].substring(0,valueArr[i].length()-1);
                        valueArr[i]=a.substring(0,a.indexOf('.')+3);



                    }

                    valueArr[i]=valueArr[i];
                }

                Double a = Double.parseDouble(valueArr[0]);
                values.add(Double.parseDouble(valueArr[0]));

                System.out.println("Chart: ");
                for (int i =0;i<valueArr.length;i++)
                {

                    values.add(Double.parseDouble(valueArr[i]));
                    System.out.println("Chart: "+i);
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

        return values;
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
    protected void onPostExecute(ArrayList<Double> arr4) {

        super.onPostExecute(arr4);

        p.dismiss();
    }



}
