package com.example.hp.mail;

/**
 * Created by HP on 8/4/2017.
 */


public class StackItem {

    private String itemText;

    // "image1","image2",..
    private String imageName;

    public StackItem(String text, String imageName) {
        this.imageName = imageName;
        this.itemText = text;
    }

    public String getImageName() {
        return imageName;
    }


    public String getItemText() {
        return itemText;
    }

}