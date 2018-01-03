package com.example.hp.mail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.mail.R;

import java.util.ArrayList;

/**
 * Created by HP on 8/5/2017.
 */

public class contacts2 extends AppCompatActivity
{
    public contacts2() {

    }
    public ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);
        EnableRuntimePermission();

        final ListView listView=(ListView)findViewById(R.id.listView);
        StoreContacts = new ArrayList<String>();


        GetContactsIntoArrayList();

        arrayAdapter = new ArrayAdapter<String>(
               this,
                R.layout.share_1, StoreContacts
        );
        System.out.print("Store"+StoreContacts.size());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(contacts2.this, "Clicked"+position, Toast.LENGTH_SHORT).show();
            }
        });


    }



        public void GetContactsIntoArrayList () {
            try {
                cursor =getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

                while (cursor.moveToNext()) {

                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    StoreContacts.add(name + " " + ":" + " " + phonenumber);
                }

                cursor.close();
            }
            catch (Exception e) {

                Toast toast = Toast.makeText(this,"Enable permissions in settings", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }



    }


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }



}



