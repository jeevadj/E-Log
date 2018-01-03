package com.example.hp.mail.CardView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;
import com.example.hp.mail.recycleradapters.itemAdapter2;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by HP on 7/16/2017.
 */

public class newsfeed extends Fragment {
    public ArrayList<Adapter> adapters=new ArrayList<Adapter>();
    public itemAdapter2 itemArrayAdapter2 = new itemAdapter2(R.layout.row2,adapters);
    RecyclerView recyclerView3;
    Firebase fb;
    String url="";
    DatabaseReference databaseReference;
    public newsfeed() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Events");
        url=databaseReference.toString();
        fb=new Firebase(url);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.newsfeed,container,false);
        Toast toast = Toast.makeText(view.getContext(),"Sample NewsFeed", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        recyclerView3=(RecyclerView)view.findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(view.getContext()));


//        //recyclerView.addItemDecoration(itemDecoration);
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new MyTask().execute();
//
        adapters.add(new Adapter("BirthDay","Am organizing a party ","2/7/2017","5/7/2017","Puducherry"));
        adapters.add(new Adapter("Long Drive","Lets Rock","3/7/2017","11/7/2017","Puducherry"));
        adapters.add(new Adapter("Trainer Meeting","Regarding training skills","21/7/2017","5/8/2017","Puducherry"));
        recyclerView3.setAdapter(itemArrayAdapter2);
        return view ;
    }
    public  class MyTask extends AsyncTask<String ,Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        System.out.println(child.getKey().toString()+"bow");
                        Adapter adapter = child.getValue(Adapter.class);
                        adapters.add(0, adapter);
                        System.out.println(adapters.get(0).getName() + "bow");
//                        adapters.add(adapter);
                        System.out.println("child: " + dataSnapshot.getKey());
                    }
                    itemArrayAdapter2 = new itemAdapter2(R.layout.row2, adapters);
                    recyclerView3.setAdapter(itemArrayAdapter2);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
}
