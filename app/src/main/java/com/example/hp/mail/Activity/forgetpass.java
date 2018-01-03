package com.example.hp.mail.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.mail.R;

public class forgetpass extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        sharedEditor=sharedPreferences.edit();


    }
}
