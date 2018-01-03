package com.example.hp.mail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by HP on 7/13/2017.
 */

public class share extends Fragment{
    RecyclerView recyclerView;
    ListView listView;
    ArrayList<String> ar=new ArrayList<String>();
    public share() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.share,container,false);
        ar.add("DHAMU");
        ar.add("JEEVA");
        ar.add("NAVEEN");
        ArrayAdapter<String> a=new ArrayAdapter<String>(view.getContext(),R.layout.share_1,ar);
        listView=(ListView)view.findViewById(R.id.listView);
        listView.setAdapter(a);
//        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//       recyclerView.se
        return view;
    }
}
