package com.example.kamil.pcbluetoothcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnectionThread extends Thread {

    private BluetoothDevice device;
    private BluetoothSocket mSocket;

    private ConnectionCallback connectionCallback;

    private ManageConnectionThread manageConnectionThread = null;

    public BluetoothConnectionThread(ConnectionCallback callback) {

        this.connectionCallback = callback;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceName.equals("FAIRLADY")) {
                    Log.i("BluetoothTest", "Found: " + deviceName);
                    this.device = device;
                    bluetoothAdapter.cancelDiscovery();
                }
            }
        }

        BluetoothSocket tmp = null;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
        } catch (IOException e) {
            Log.i("BluetoothTest", "Socket's create() method failed", e);
        }

        mSocket = tmp;
    }

    @Override
    public void run() {
        Log.i("BluetoothTest", "Connecting...");
        if(mSocket != null){
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mSocket.close();
                } catch (IOException closeException) {
                    Log.i("BluetoothTest", "Could not close the client socket", closeException);
                }
                return;
            }
            connectionCallback.onConnected(new ManageConnectionThread(mSocket));
            Log.i("BluetoothTest", "Connected");
        }
    }
}
