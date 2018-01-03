package com.example.hp.mail.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.mail.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 8/8/2017.
 */

public class secondLogin extends AppCompatActivity {
    Button button,fpass;
    EditText ed;
    TextView tv,incorect;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_login_view);
        button=(Button)findViewById(R.id.buttonLogin);
        ed=(EditText)findViewById(R.id.editTextPassword);
        tv=(TextView)findViewById(R.id.name);
        incorect=(TextView)findViewById(R.id.Incorrect);
        imageView=(CircleImageView)findViewById(R.id.profile_image);
        fpass=(Button)findViewById(R.id.pass_forget);

        final String name=getIntent().getExtras().getString("username");
        tv.setText(name);

        final String url=getIntent().getExtras().getString("url");

        Glide.with(getApplicationContext()).load(url).into(imageView);

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(secondLogin.this,forgetpass.class));
            }
        });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
//                prefEditor.putString("pass", ed.getText().toString());
//                prefEditor.commit();
                String pas=ed.getText().toString();
                String pass=getIntent().getExtras().getString("password");
                if(pas.equals(pass)){
                    Toast.makeText(secondLogin.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(secondLogin.this,MainActivity.class);
                    intent.putExtra("username",name);
                    intent.putExtra("url",url);


                    startActivity(intent);
                    finish();
                }
                else{

                    incorect.setText("Incorrect Password");
                }
            }
        });

    }
}
