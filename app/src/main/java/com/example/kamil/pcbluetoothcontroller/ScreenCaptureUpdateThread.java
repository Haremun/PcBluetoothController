package com.example.kamil.pcbluetoothcontroller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

public class ScreenCaptureUpdateThread extends Thread {

    private ManageConnectionThread manageConnectionThread;
    private UpdateFragment updateFragment;

    private boolean play = true;

    public ScreenCaptureUpdateThread(UpdateFragment updateFragment) {
        this.updateFragment = updateFragment;
    }

    @Override
    public void run() {
        while (play) {

            if (manageConnectionThread != null) {
                while (manageConnectionThread.isConnected() && play) {
                    byte[] array = manageConnectionThread.getData();
                    if (array != null) {
                        Log.i("Bitmap", "Start decoding");
                        Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
                        if (bmp != null) {
                            //Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
                            //Canvas canvas = new Canvas(mutableBitmap);
                            //RectF rectangle = new RectF(0, 0, 200, 100);
                            //canvas.drawRect(rectangle, new Paint());

                            updateFragment.onUpdate(bmp);
                            Log.i("Bitmap", bmp.getHeight() + " " + bmp.getWidth());
                        }
                        //play = false;
                    }
                }
                Log.i("Connection", "No connection");
            }

        }

    }

    public void setManageConnectionThread(ManageConnectionThread manageConnectionThread) {
        this.manageConnectionThread = manageConnectionThread;
    }
}
