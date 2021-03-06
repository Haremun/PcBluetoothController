package com.example.kamil.pcbluetoothcontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TouchPadSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private TouchPadUpdate touchPadUpdate;
    private Paint paint;

    private float deltaX;
    private float deltaY;
    private float oldX = 0.0f;
    private float oldY = 0.0f;

    private ManageConnectionThread manageConnectionThread;

    private String message = "empty";
    private long singleClickTime = 0;

    //long licznik = 0;

    public TouchPadSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setManageConnectionThread(ManageConnectionThread manageConnectionThread) {
        this.manageConnectionThread = manageConnectionThread;
    }

    public void MyGameSurfaceView_OnResume() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        surfaceHolder = getHolder();
        getHolder().addCallback(this);

        //Create and start background Thread
        touchPadUpdate = new TouchPadUpdate(this, 16);
        touchPadUpdate.setRunning(true);
        touchPadUpdate.start();

    }

    public void MyGameSurfaceView_OnPause() {
        //Kill the background Thread
        boolean retry = true;
        touchPadUpdate.setRunning(false);

        while (retry) {
            try {
                touchPadUpdate.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                oldX = event.getX();
                oldY = event.getY();
                singleClickTime = System.currentTimeMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                deltaX = 0.0f;
                deltaY = 0.0f;
                long currentTime = System.currentTimeMillis();
                if (currentTime - singleClickTime < 200)
                    message = "CLICK";
                /*if (System.currentTimeMillis() - doubleClickTime < delayInMillis) {
                    message = "DOUBLE_CLICK";
                    doubleClickTime = -delayInMillis;

                } else {
                    doubleClickTime = System.currentTimeMillis();
                }*/

                //message = "UP";

                break;
            }
            case MotionEvent.ACTION_MOVE: {

                if (message.equals("")) {
                    deltaX = (oldX - event.getX()) * -1;
                    deltaY = (oldY - event.getY()) * -1;
                    oldX = event.getX();
                    oldY = event.getY();
                    message = this.deltaX + "$" + this.deltaY;
                }
                break;
            }
        }
        return true;
    }

    private void Draw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(50);

        paint.setColor(Color.RED);
        //paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
        //canvas.drawCircle(x, y, 6, paint);
        canvas.drawCircle(deltaX, deltaY, 6, paint);
    }

    public void Update() {
        Canvas canvas = null;

        try {
            canvas = surfaceHolder.lockCanvas();

            synchronized (surfaceHolder) {
                if (canvas != null) {
                    Draw(canvas);

                    if (!message.equals("-0.0$-0.0") && !message.equals("")) {
                        //licznik++;
                        if (manageConnectionThread != null) {
                            if (manageConnectionThread.isConnected())
                                manageConnectionThread.write(message);
                            Log.i("TouchPad", "m");
                        }


                        Log.i("TouchPad", message);
                        message = "";
                    } else {
                        message = "";
                    }
                }
            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
