package com.example.kamil.pcbluetoothcontroller;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ConnectionCallback {

    private TouchpadFragment touchPadFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothConnectionThread connectionThread = new BluetoothConnectionThread(this);
        connectionThread.start();

        touchPadFragment = new TouchpadFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, touchPadFragment);
        transaction.commit();
    }

    @Override
    public void onConnected(ManageConnectionThread manageConnectionThread) {
        touchPadFragment.setManageConnectionThread(manageConnectionThread);
    }
}
