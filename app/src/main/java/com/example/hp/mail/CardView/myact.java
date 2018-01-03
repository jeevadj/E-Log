package com.example.hp.mail.CardView;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;
import com.example.hp.mail.recycleradapters.itemAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by HP on 7/13/2017.
 */

public class myact extends Fragment {
    public ArrayList<Adapter> adapters=new ArrayList<Adapter>();
    public itemAdapter itemArrayAdapter ;
    RecyclerView recyclerView1;



    Firebase fb;
    String url="";
    DatabaseReference databaseReference;
    StorageReference storageReference;
    public myact()
    {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Events");
        url=databaseReference.toString();
        storageReference= FirebaseStorage.getInstance().getReference();

        Toast.makeText(getContext(),url, Toast.LENGTH_SHORT).show();
        Firebase.setAndroidContext(getContext());
        fb=new Firebase(url);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment,container,false);
        Toast toast = Toast.makeText(view.getContext(),"Sample Activities", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
       recyclerView1=(RecyclerView)view.findViewById(R.id.recyclerView);
       recyclerView1.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        //recyclerView.addItemDecoration(itemDecoration);
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new MyTask().execute();

        adapters.add(new Adapter("BirthDay","Am organizing a party ","2/7/2017","5/7/2017","Puducherry"));
       adapters.add(new Adapter("Long Drive","Lets Rock","3/7/2017","11/7/2017","Puducherry"));
       adapters.add(new Adapter("Trainer Meeting","Regarding training skills","21/7/2017","5/8/2017","Puducherry"));
       recyclerView1.setAdapter(itemArrayAdapter);
        return view ;

    }
    public class MyTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                       System.out.println(child.getKey().toString()+"bow");
                        Adapter adapter = child.getValue(Adapter.class);
//                    Adapter adapter = dataSnapshot.getValue(Adapter.class);
                        adapters.add(0, adapter);
                        System.out.println(adapters.get(0).getName() + "bow");
//                        adapters.add(adapter);
                        System.out.println("child: " + dataSnapshot.getKey());
                   }
                    itemArrayAdapter = new itemAdapter(R.layout.row, adapters);
                    recyclerView1.setAdapter(itemArrayAdapter);

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
        }
    }

}
