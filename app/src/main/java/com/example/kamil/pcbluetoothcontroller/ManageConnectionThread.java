package com.example.kamil.pcbluetoothcontroller;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManageConnectionThread extends Thread{

    private final BluetoothSocket mSocket;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    private byte[] mBuffer;

    private boolean connected = true;

    public ManageConnectionThread(BluetoothSocket socket){
        mSocket = socket;


        InputStream inTmp = null;
        OutputStream outTmp = null;
        try {
            inTmp = mSocket.getInputStream();
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
    public void run(){
        while (connected){
            if(!mSocket.isConnected())
                connected = false;
        }

    }
    public void write(String message){

        try {
            byte[] temp = (message + "\n").getBytes();
            mOutputStream.write(temp);
            Log.i("BluetoothTest", temp[0] + "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnected() {
        return connected;
    }
}
