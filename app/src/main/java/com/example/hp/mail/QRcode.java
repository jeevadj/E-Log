package com.example.hp.mail;

/**
 * Created by HP on 7/15/2017.
 */
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;


public class QRcode extends AppCompatActivity  {
    String qrInputText;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
//        Button button1 = (Button) findViewById(R.id.button1);
//        button1.setOnClickListener(this);
         qrInputText=getIntent().getStringExtra("QR").toString();
        Toast.makeText(this,qrInputText , Toast.LENGTH_SHORT).show();



        Log.v("LOG_TAG", qrInputText);

        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
        null,
        Contents.Type.TEXT,
        BarcodeFormat.QR_CODE.toString(),
        smallerDimension);
        try {
        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
        ImageView myImage = (ImageView)findViewById(R.id.imageView4);
        myImage.setImageBitmap(bitmap);

        } catch (Exception e) {
        e.printStackTrace();
        }




        // More buttons go here (if any) ...

        }
        }




