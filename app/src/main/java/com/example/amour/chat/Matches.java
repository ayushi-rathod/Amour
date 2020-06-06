package com.example.amour.chat;


public class Matches {
    public String i_am, image_link;

    public Matches()
    {

    }

    public Matches(String i_am, String image_link) {
        this.i_am = i_am;

        this.image_link = image_link;
    }

    public String getI_am() {
        return i_am;
    }

    public void setI_am(String i_am) {
        this.i_am = i_am;
    }


    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
