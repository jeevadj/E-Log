package com.example.hp.mail.adapterclass;

import android.location.Location;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by HP on 6/26/2017.
 */

public class Adapter {
    //public static String str1;


    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }



    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String desp;

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    String sd;
    String ed;


    String Location;

    public Adapter(){}

    public Adapter(String n,String f,String s,String e,String loc) {

        name = n;
        desp=f;
        sd=s;
        ed=e;
        Location=loc;
        //android_image_url=e;
    }


    String name;



    String url;





}
