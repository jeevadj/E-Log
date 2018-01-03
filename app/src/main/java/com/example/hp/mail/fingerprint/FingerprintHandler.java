package com.example.hp.mail.fingerprint;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import com.example.hp.mail.Activity.secondLogin;
import com.example.hp.mail.adapterclass.Adapter;

/**
 * Created by reale on 25/11/2016.
 */
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
//    private Context context2=get;
    public Adapter ap;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cenCancellationSignal = new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return;
        fingerprintManager.authenticate(cryptoObject,cenCancellationSignal,0,this,null);

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

      Intent intent=new Intent(context,secondLogin.class);
      context.startActivity(intent);



            //DT.setValidkey(false);
       // Toast.makeText(context.getApplicationContext(), "Not valid"+DT.isValidkey(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        //context.startActivity(new Intent(context,MainActivity.class));
        //DT.setValidkey(true);

    }
}
