package com.example.hp.mail.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.credupdate;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by HP on 8/8/2017.
 */

public class firstLogin extends AppCompatActivity {
    Firebase fb;
    String url="https://fir-18ecb.firebaseio.com/";
    Button button,buttonRegister;
    EditText email;
    String str;
    ProgressDialog progressDialog;
    public final int requestPermission=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_view);
        button=(Button)findViewById(R.id.buttonNext);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        email=(EditText)findViewById(R.id.email);
        Firebase.setAndroidContext(this);
        url=url+"Users/";
        SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        int number = sharedPref.getInt("isLogged",0);
        System.out.println("bow"+number);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        boolean islog=false;
        islog=sharedPref.getBoolean("loggedin",false);
        if(islog){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        if(checkpermission()){
            Toast.makeText(this, "All Permission Granted...", Toast.LENGTH_SHORT).show();
        }
        else{
            requestPermission();
        }


//        fb=new Firebase(url);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                str=email.getText().toString();

                String[] em=str.split("@");
                str=em[0];



                System.out.println("Sticky"+url);
                fb=new Firebase(url+str+"/");
                new MyTask().execute();



            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(firstLogin.this,signup.class));
            }
        });
    }
    public class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {


            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                        if (dataSnapshot.exists()) {
                            credupdate credUpdate = dataSnapshot.getValue(credupdate.class);
                            String eee=email.getText().toString();
                            if (eee.equals(credUpdate.getEmail())) {
                                Intent intent = new Intent(firstLogin.this, secondLogin.class);
                                intent.putExtra("password", credUpdate.getPass());
                                intent.putExtra("username", credUpdate.getUsername());
                                intent.putExtra("url",credUpdate.getUrl());
                                progressDialog.dismiss();
                                SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = sharedPref.edit();
                                prefEditor.putBoolean("loggedin",true);
                                prefEditor.putString("email", credUpdate.getEmail());
                                prefEditor.putString("user",credUpdate.getUsername());
                                prefEditor.putString("url",credUpdate.getUrl());
                                prefEditor.putString("pass",credUpdate.getPass());
                                prefEditor.commit();
                                startActivity(intent);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(firstLogin.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(firstLogin.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                            System.out.println(e);
                        }


                        }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });




            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(firstLogin.this);
            progressDialog.setMessage("Logging in...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
    public boolean checkpermission(){
        int camera= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int location=ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);
        int storage=ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int microphone=ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
        int contacts= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_CONTACTS);
        return  camera== PackageManager.PERMISSION_GRANTED && location == PackageManager.PERMISSION_GRANTED && storage==PackageManager.PERMISSION_GRANTED &&microphone==PackageManager.PERMISSION_GRANTED&&contacts==PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermission(){
        ActivityCompat.requestPermissions(firstLogin.this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO
        },requestPermission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case requestPermission :
                if(grantResults.length>0){
                    boolean camera=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean location=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    boolean clocation=grantResults[2]==PackageManager.PERMISSION_GRANTED;
                    boolean storage= grantResults[3]==PackageManager.PERMISSION_GRANTED;
                    boolean contacts=grantResults[4]==PackageManager.PERMISSION_GRANTED;
                    boolean audio=grantResults[5]==PackageManager.PERMISSION_GRANTED;
                    if(camera&&location&&storage&&contacts&&audio&&clocation){
                        Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
