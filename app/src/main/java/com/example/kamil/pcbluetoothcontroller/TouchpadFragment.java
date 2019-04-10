package com.example.kamil.pcbluetoothcontroller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TouchpadFragment extends Fragment {

    private TouchPadSurfaceView touchPadSurfaceView;
    private ManageConnectionThread manageConnectionThread = null;

    public TouchpadFragment() {
        // Required empty public constructor
    }

    public void setManageConnectionThread(ManageConnectionThread manageConnectionThread) {
        this.manageConnectionThread = manageConnectionThread;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_touchpad, container, false);
        touchPadSurfaceView = view.findViewById(R.id.touch_pad);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //tcpConnection.start();
        touchPadSurfaceView.MyGameSurfaceView_OnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //tcpConnection.closeConnection();
        touchPadSurfaceView.MyGameSurfaceView_OnPause();
    }

}
