package com.b2d.b2d_project;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;

public class DeviceScreen extends AppCompatActivity {

    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private Switch swBluethooth;
    private Button btnFindDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_screen);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        swBluethooth = (Switch) findViewById(R.id.swBluetooth);
        btnFindDevices = (Button) findViewById(R.id.btnFindDevice);



        if(myBluetoothAdapter == null) {

            swBluethooth.setEnabled(false);
            btnFindDevices.setEnabled(false);

            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        }
        else
        {

            swBluethooth.setChecked(myBluetoothAdapter.isEnabled());

        }

    }
}
