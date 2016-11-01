package lvsandroid.bluetoothtrainingapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA;

    public void turnBluetoothOff(View view) {
        BA.disable();
        if (BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth could not be disabled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();
        }
    }

    public void findDiscoverableDevices(View view) {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(i);
    }

    public void viewPairedDevices(View view) {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();

        ListView pairedDevicesListview = (ListView)findViewById(R.id.pairedDevicesListview);

        ArrayList pairedDevicesArrayList = new ArrayList();
        for(BluetoothDevice bluetoothDevice : pairedDevices) {
            pairedDevicesArrayList.add(bluetoothDevice.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedDevicesArrayList);
        pairedDevicesListview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BA = BluetoothAdapter.getDefaultAdapter();

        if(BA == null) {
            Toast.makeText(getApplicationContext(), "No Bluetooth adapter on this device",Toast.LENGTH_LONG).show();
            return;
        }
        if(BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is on",Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);

            if(BA.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth turned on",Toast.LENGTH_LONG).show();
            }
        }
    }
}
