package com.example.kamil.pcbluetoothcontroller;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ManageConnectionThread extends Thread {

    private final BluetoothSocket mSocket;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    private byte[] mBuffer;

    private boolean connected = true;

    public ManageConnectionThread(BluetoothSocket socket) {
        mSocket = socket;


        BufferedInputStream inTmp = null;
        OutputStream outTmp = null;
        try {
            inTmp = new BufferedInputStream(mSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outTmp = mSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mInputStream = inTmp;
        mOutputStream = outTmp;

    }

    public void run() {
        while (connected) {
            if (!mSocket.isConnected())
                connected = false;
        }

    }

    public void write(String message) {

        try {
            byte[] temp = (message + "\n").getBytes();
            mOutputStream.write(temp);
            Log.i("BluetoothTest", temp[0] + "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getData() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));

            Log.i("Test", "waiting...");
            String string = reader.readLine();

            int size = 10000000;
            if (!string.equals(""))
                size = Integer.parseInt(string);
            Log.i("Test", "size get");

            write("OK");

            Log.i("Test", "Response send");
            byte[] array = new byte[size];
            byte[] buffer = new byte[size];
            int i = 0;
            int limit = 1000000;
            int bufferSize;
            while (i < size) {

                Log.i("Test", "loop1");
                bufferSize = mInputStream.read(buffer);
                Log.i("Test", "loop2");

                for (int k = 0; k < bufferSize; k++) {
                    if ((k + i) < size)
                        array[k + i] = buffer[k];
                }
                i += bufferSize;

            }
            Log.i("Test", "Screen loaded");
            write("OK2");
            return array;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
