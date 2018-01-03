package com.example.hp.mail.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.credupdate;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by HP on 8/18/2017.
 */

public class signup extends AppCompatActivity {
    Firebase fb;
   public FirebaseStorage storage;
    String url="https://fir-18ecb.firebaseio.com/";
    String storeurl="gs://fir-18ecb.appspot.com/";
   public StorageReference storageReference;

   DatabaseReference databaseReference;
    public Activity c;
    public Context context;
    public Dialog g;
    public TextView textView;
    Button button;
    EditText uname,ph,email,pass,cpass;
    ImageView imageView;
    CharSequence[] items = {"Take Photo", "Choose from library", "Cancel"};
    Uri imageData;
    protected String m;
    public Bitmap bitmap;
    public int a=0;
    public String  picturePath;
    String un,phone,em,p,cp;
    Uri imageUri,selectedImageUri;
    private static final int PICK_Camera_IMAGE=2;
    private static final int PICK_IMAGE=1;
    FirebaseApp apps;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        button = (Button) findViewById(R.id.button);
        uname=(EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        ph=(EditText)findViewById(R.id.phonenumber);
        pass=(EditText)findViewById(R.id.password);
        cpass=(EditText)findViewById(R.id.currentpassword);
        Firebase.setAndroidContext(this);




        apps=FirebaseApp.getInstance();
        System.out.println("application context"+apps);

        storage=FirebaseStorage.getInstance(apps);


        storageReference=storage.getReference();


        imageView = (ImageView) findViewById(R.id.profile_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un=uname.getText().toString();
                em=email.getText().toString();
                phone=ph.getText().toString();
                p=pass.getText().toString();
                cp=cpass.getText().toString();
                if(un.isEmpty()&&em.isEmpty()&&phone.isEmpty()&&p.isEmpty()&&cp.isEmpty()){
                    Toast.makeText(signup.this, "Enter all the credentials required...", Toast.LENGTH_SHORT).show();
                }
                else if(a==0){
                    Toast.makeText(signup.this, "Select an Image for the propic...", Toast.LENGTH_SHORT).show();
                }
                else{
                    new MyTask().execute();
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=1;
                selectImage();

            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
        builder.setTitle("Choose picture..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    imageUri = signup.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            PICK_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {

            if (requestCode == PICK_Camera_IMAGE) {
                if (resultCode == this.RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;

                } else if (resultCode == this.RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                try {
                    Uri tmpUri=Uri.fromFile(new File(ImageCompressor.with(getApplicationContext()).compress(selectedImageUri.toString())));
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , tmpUri);
                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == PICK_IMAGE) {
                if (resultCode == this.RESULT_OK) {
                    selectedImageUri = data.getData();
                } else if (resultCode == this.RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , selectedImageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }}}


    public class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            fb=new Firebase(url);

            String[] ii=em.split("@");
            final String m=ii[0];

            System.out.println("Bowwwww"+m);
           // System.out.println("ref is "+storageReference);
            StorageReference s=storageReference.child("users").child("Propic").child(m);
            s.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri=taskSnapshot.getDownloadUrl();
                    String downrul=uri.toString();
                    credupdate credUpdate=new credupdate();
                    credUpdate.setUsername(un);
                    credUpdate.setEmail(em);
                    credUpdate.setPhone(phone);
                    credUpdate.setPass(p);
                    credUpdate.setUrl(downrul);

                    System.out.println("Bowwwww"+ credUpdate);




                    fb.child("Users").child(m).setValue(credUpdate);

                    Toast.makeText(signup.this, "Accounts Created...", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(signup.this,firstLogin.class);
                    startActivity(intent);
                    a=0;
                    finish();
                }
            });
            return null;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(signup.this);
            progressDialog.setMessage("Creating Account...");
            progressDialog.setCancelable(false);
            progressDialog.show();




        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
