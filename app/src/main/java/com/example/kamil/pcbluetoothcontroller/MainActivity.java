package com.example.kamil.pcbluetoothcontroller;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothConnectionThread connectionThread = new BluetoothConnectionThread();
        connectionThread.start();

        TouchpadFragment touchPadFragment = new TouchpadFragment();
        touchPadFragment.setManageConnectionThread(connectionThread.getManageConnectionThread());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, touchPadFragment);
        transaction.commit();
    }
}
