package com.example.kamil.pcbluetoothcontroller;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenCaptureFragment extends Fragment implements UpdateFragment {

    private ScreenCaptureUpdateThread screenCaptureUpdateThread;

    private TextView textView;
    private ImageView imageView;

    private int i = 0;

    public ScreenCaptureFragment() {
        // Required empty public constructor
    }

    public void setManageConnectionThread(ManageConnectionThread manageConnectionThread) {
        screenCaptureUpdateThread.setManageConnectionThread(manageConnectionThread);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen_capture, container, false);
        textView = view.findViewById(R.id.text);
        imageView = view.findViewById(R.id.imageView);

        screenCaptureUpdateThread = new ScreenCaptureUpdateThread(this);
        screenCaptureUpdateThread.start();

        return view;
    }

    @Override
    public void onUpdate(Bitmap bmp) {

        final Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 1920, 1080, true);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(i + "");

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap);
            }
        });
        i++;

    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
