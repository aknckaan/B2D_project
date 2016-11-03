package com.b2d.b2d_project;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
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
    private Switch swBluethooth;
    private Button btnFindDevices;
    private ListView deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_screen);

        //init bt
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter ifilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bReceiver, ifilter);
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter(BTArrayAdapter);

        //init ui
        swBluethooth = (Switch) findViewById(R.id.swBluetooth);
        btnFindDevices = (Button) findViewById(R.id.btnFindDevice);
        deviceList = (ListView) findViewById(R.id.lvDevices);

        //

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
                    find(); // search for new devices
                    list(); //find registered devices
                }
            });

            swBluethooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btnFindDevices.setEnabled(swBluethooth.isEnabled());

                    if (swBluethooth.isChecked()) {

                        Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnOnIntent, 1);

                    } else {
                        myBluetoothAdapter.disable();
                        Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void find() {
        if (myBluetoothAdapter.isDiscovering()) {

            // the button is pressed when it discovers, so cancel the discovery
            Toast.makeText(getApplicationContext(), " Discover cancelled", Toast.LENGTH_LONG).show();
            myBluetoothAdapter.cancelDiscovery();

        } else {
            myBluetoothAdapter.startDiscovery();
            //Toast.makeText(getApplicationContext(), " discovering devices", Toast.LENGTH_LONG).show();
        }
    }


    public void list() {

        // get paired devices

        pairedDevices = myBluetoothAdapter.getBondedDevices();
        // put it's one to the adapter
        for (BluetoothDevice device : pairedDevices)
            BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        //Toast.makeText(getApplicationContext(),"Show Paired Devices",Toast.LENGTH_SHORT).show();
        BTArrayAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            // When discovery finds a device
            Toast.makeText(getApplicationContext(), " Discovered a device", Toast.LENGTH_LONG).show();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

                BTArrayAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), " Discovered a device", Toast.LENGTH_LONG).show();

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                deviceList.setAdapter(BTArrayAdapter);
                Toast.makeText(getApplicationContext(), " Discover finished", Toast.LENGTH_LONG).show();

            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }

}
