package com.example.hp.mail;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.zip.Inflater;

/**
 * Created by HP on 8/7/2017.
 */

public class scanqr extends Fragment {
    public scanqr() {

    }
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    final int RequestCameraPermissionID = 1001;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.scanqr,container,false);
        cameraView = (SurfaceView)view.findViewById(R.id.surface_view);
        textView = (TextView)view.findViewById(R.id.text_view);
        barcodeDetector=new BarcodeDetector.Builder(view.getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource=new CameraSource.Builder(view.getContext(),barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> items = detections.getDetectedItems();
                if(items.size()!=0)
                {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator=(Vibrator)getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(100);
                            textView.setText(items.valueAt(0).displayValue);
                        }
                    });
                }

            }
        });


        return view;
    }
}
