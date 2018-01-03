package com.example.hp.mail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.List;

public class help extends Fragment {

    private StackView stackView;
    private Button buttonPrevious;
    private Button buttonNext;

    private final String[] IMAGE_NAMES= {"image1","image2", "image3"};







    public help() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.stackitem,container,false);
        stackView = (StackView)view.findViewById(R.id.stackview);



        List<StackItem> items = new ArrayList<StackItem>();

        for(String imageName: IMAGE_NAMES) {
            items.add(new StackItem(imageName+".png", imageName));
        }
        StackAdapter adapt = new StackAdapter(view.getContext(), R.layout.stackfunc, items);

        stackView.setAdapter(adapt);
        stackView.setHorizontalScrollBarEnabled(true);
        //stackView.setBackgroundColor(Color.rgb(230, 255, 255));
        return view;
    }






}
