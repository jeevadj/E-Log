package com.example.hp.mail;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HP on 7/24/2017.
 */

public class contacts extends Fragment {
    public contacts() {

    }
    public ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.share,container,false);
        //StoreContacts.add("DHAMU");
//        ar.add("JEEVA");
//        ar.add("NAVEEN");
        cursor = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        ListView listView=(ListView)view.findViewById(R.id.listView);
        StoreContacts = new ArrayList<String>();


        GetContactsIntoArrayList();

        arrayAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.share_1, StoreContacts
        );
        System.out.print("Store"+StoreContacts.size());
        listView.setAdapter(arrayAdapter);

        return view;
    }
    public void GetContactsIntoArrayList(){




        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }

        cursor.close();


    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(),
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(getActivity(),"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(),"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getActivity(),"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
