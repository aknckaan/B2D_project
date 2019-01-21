package com.b2d.b2d_project;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;

public class DeviceScreen extends AppCompatActivity {

    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private ArrayAdapter<String> BTArrayAdapterPaired;
    private Switch swBluethooth;
    private Button btnFindDevices;
    private ListView deviceList;
    private ListView deviceListPaired;
    private ProgressDialog pd;
    private final int MY_BT_PERMISSION=1;
    private boolean connect=false;
    private int id;
    private String user;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                Toast.makeText(getApplicationContext(), "Discovery Started", Toast.LENGTH_LONG).show();
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                Toast.makeText(getApplicationContext(), "Discovery finished", Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BTArrayAdapter.add("Discovered Devices\n");
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Device found", Toast.LENGTH_LONG).show();

            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_BT_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_screen);
        ActivityCompat.requestPermissions(DeviceScreen.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_BT_PERMISSION);

        Intent i =getIntent();
        id=i.getIntExtra("ID",0);
        user=i.getStringExtra("Username");

        //init bt
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //init ui
        swBluethooth = (Switch) findViewById(R.id.swBluetooth);
        btnFindDevices = (Button) findViewById(R.id.btnFindDevice);
        deviceList = (ListView) findViewById(R.id.lvDevices);
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        BTArrayAdapterPaired = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter(BTArrayAdapter);


        if(myBluetoothAdapter.isEnabled())
        {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
            swBluethooth.setChecked(true);
        }
        else
            swBluethooth.setChecked(false);


        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = (deviceList.getItemAtPosition(i)).toString();
                String data[]=item.split("\n");
                //pd=new ProgressDialog(DeviceScreen.this);
                //pd.setTitle("Connecting...");
                //pd.show();
                final Intent intent = new Intent(DeviceScreen.this,BluetoothComService.class);
                //intent.putExtra("messenger",new Messenger(handler));
                intent.putExtra("adress",data[1]);
                intent.putExtra("id",id);
                intent.putExtra("user",user);
                startService(intent);

               /* pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        stopService(intent);
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });*/

            }
        });

        if (myBluetoothAdapter == null) {

            swBluethooth.setEnabled(false);
            btnFindDevices.setEnabled(false);

            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        } else {

            swBluethooth.setChecked(myBluetoothAdapter.isEnabled());
            btnFindDevices.setEnabled(swBluethooth.isEnabled());


            btnFindDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BTArrayAdapter.clear();
                    BTArrayAdapter.notifyDataSetChanged();

                    Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();

                    BTArrayAdapter.add("Paired Devices\n");
                    if (pairedDevices.size() > 0) {
                            // There are paired devices. Get the name and address of each paired device.
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress(); // MAC address
                            BTArrayAdapter.add(deviceName+" \n"+deviceHardwareAddress);

                        }
                    }

                    BTArrayAdapter.notifyDataSetChanged();

                    myBluetoothAdapter.startDiscovery();




                }
            });

            swBluethooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btnFindDevices.setEnabled(swBluethooth.isEnabled());

                    if (swBluethooth.isChecked()) {
                        myBluetoothAdapter.enable();
                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(mReceiver, filter);

                        Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnOnIntent, 1);

                        Intent discoverableIntent =
                                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(discoverableIntent);

                    } else {

                        unregisterReceiver(mReceiver);
                        if (myBluetoothAdapter.isEnabled()) {
                            myBluetoothAdapter.disable();
                        }
                        Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.


    /*Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle reply = msg.getData();
            String s=(String)reply.get("name");
            if(s.equals("Connected"))
            {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Connected.", Toast.LENGTH_LONG).show();
            }else if(s.equals("Already Connected"))
            {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Already Connected..", Toast.LENGTH_LONG).show();
            }

        }
    };*/

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
