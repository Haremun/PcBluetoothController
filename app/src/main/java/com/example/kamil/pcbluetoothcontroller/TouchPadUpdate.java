package com.example.kamil.pcbluetoothcontroller;

public class TouchPadUpdate extends Thread {

    volatile boolean running;
    private TouchPadSurfaceView mTouchPadSurfaceView;
    private long mSleepTime;


    TouchPadUpdate(TouchPadSurfaceView touchPadSurfaceView, long sleepTime) {
        this.mTouchPadSurfaceView = touchPadSurfaceView;
        this.mSleepTime = sleepTime;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            try {
                sleep(mSleepTime);
                mTouchPadSurfaceView.Update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

