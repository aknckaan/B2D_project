package com.b2d.b2d_project;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

/**
 * Created by Kaan on 16/12/2016.
 */

public class EpilepsyTrigger extends AsyncTask<String, String, String> {
    private static final String LOGIN_URL = "http://kaanakinci.me/B2D/sendNotification.php";
    private static final String UPLOAD_URL = "http://kaanakinci.me/B2D/upload.php";

    private String[] fileIndex;
    private String pid;
    private String name;
    private String fileName;
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    Activity a;
    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;


    public EpilepsyTrigger(String pid,String name,Activity a,String fileName)
    {
        this.pid = pid;
        this.name =  name;
        this.a =a;
        this.fileName=fileName;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {


        // Building Parameters
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("PId", pid);
        params.put("name", name);
        params.put("message", "Your patient "+name+" is having an epilepsy attack!");

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

            try {
                URL url2 = new URL(UPLOAD_URL);
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("POST");


                HttpClient client;
                client= HttpClientBuilder.create().build();

                HttpPost post = new HttpPost("http://kaanakinci.me/B2D/upload.php");
                try {
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    FileBody fileBody = new FileBody(new File(a.getApplicationContext().getCacheDir(), "B2D/"+fileName+".txt"));
                    builder.addPart("fileToUpload", fileBody);
                    builder.addTextBody("username",name, ContentType.TEXT_PLAIN);


                    post.setEntity(builder.build());

                    HttpResponse response = client.execute(post);


                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                    String line = "";
                    //while ((line = rd.readLine()) != null) {
                    line=rd.readLine();
                    System.out.println(line);

                    if(line.indexOf("not")>0)
                        return "";
                    //}

                    trimCache(a.getApplicationContext());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                return "error";
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
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

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
