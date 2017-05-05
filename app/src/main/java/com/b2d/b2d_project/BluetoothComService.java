package com.b2d.b2d_project;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.UUID;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class BluetoothComService extends IntentService {

    private HttpURLConnection conn;
    public Boolean connect;
    public String adress;
    public String result;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    private static final String NOTIF_URL = "http://kaanakinci.me/B2D/sendNotification.php";
    private static final String UPLOAD_URL = "http://kaanakinci.me/B2D/upload.php";
    static boolean registered=false;
    int id;
    String user;
    boolean server = false;
    BluetoothAdapter myBluetoothAdapter;

    public BluetoothComService() {

        super("BluetoothComService");
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }


    public void generateNoteOnSD(Context context, String sFileName, String[] sBody) {
        try {
            File myDir = new File(context.getCacheDir(), "B2D");
            myDir.mkdir();
            File fileWithinMyDir = new File(myDir, sFileName); //Getting a file within the dir.
            FileOutputStream out = new FileOutputStream(fileWithinMyDir);
            for(int i=0;i<sBody.length;i++)
            {
                out.write((sBody[i]+"\n").getBytes());//Use the stream as usual to write into the file.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(registered)
        {
            Toast.makeText(getApplicationContext(), "Already connected.", Toast.LENGTH_LONG).show();
            return;
        }

        else
            Toast.makeText(getApplicationContext(), "Connecting.", Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "Service registered", Toast.LENGTH_LONG).show();
        this.id=intent.getIntExtra("id",0);
        this.adress=intent.getStringExtra("adress");
        this.user=intent.getStringExtra("user");

        System.out.print("123");
        Toast.makeText(getApplicationContext(), "Service registered", Toast.LENGTH_LONG).show();
        final UUID SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothDevice device = myBluetoothAdapter.getRemoteDevice(adress);
        BluetoothSocket socket = null;
        OutputStream out = null;
        InputStream in = null;

        registered=!registered;

        try {
            socket = device.createRfcommSocketToServiceRecord(SERIAL_UUID);
        } catch (IOException e) {}

        try {


                socket.connect();
                out = socket.getOutputStream();
                in=socket.getInputStream();
                final DataInputStream mmInStream = new DataInputStream(in);
                byte[] buffer = new byte[256];

                while (socket.isConnected())
                {
                    //out.write("Hello from the other side".getBytes());
                    int bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);

                    if(!readMessage.equals("Hello"))
                        continue;

                    out.write("Hello from the other side".getBytes());
                    buffer = new byte[256];
                    bytes = mmInStream.read(buffer);
                    readMessage = new String(buffer, 0, bytes);
                    final String fileName=readMessage;
                    final String[] fileData=new String[15000];
                    out.write("1".getBytes());

                    for(int i=0;i<501;i++)
                    {
                        if (!socket.isConnected())
                            break;

                        buffer = new byte[256];
                        bytes = mmInStream.read(buffer);
                        readMessage = new String(buffer, 0, bytes);
                        result=readMessage;

                        if(result.equals("Bye"))
                            break;

                        String[] myArr=result.split("\n");

                        for(int k=0;k<30;k++)
                        {
                            fileData[i*30+k]=myArr[k];
                        }

                        System.out.print(i);


                        out.write("1".getBytes());

                    }
                    out.write("0".getBytes());
                    generateNoteOnSD(getApplicationContext(),fileName+".txt",fileData);

                    uploadData(id+"",user,fileName);
                }
                registered=!registered;
                return;



            //else if(socket.isConnected()&&!connect)
            //  socket.close();

            //now you can use out to send output via out.write
        } catch (IOException e) {
            e.printStackTrace();
        }

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
    }

    public void uploadData(String pid,String name,String fileName)
    {
        try {
        // Building Parameters
            if(fileName.charAt(fileName.indexOf(".")-1)=='1')
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("PId", pid);
                params.put("File", fileName);
                params.put("message", "Your patient "+name+" is having an epilepsy attack!");

                JSONObject object = null;

                URL url = new URL(NOTIF_URL);
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


                }
            }


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
                    FileBody fileBody = new FileBody(new File(getApplicationContext().getCacheDir(), "B2D/"+fileName+".txt"));
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
                        return;
                    //}

                    trimCache(getApplicationContext());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){

            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
