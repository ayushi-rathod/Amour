package com.example.amour.Util;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class RegistrationFormHandler implements Serializable {
    String age, height, i_am_text, i_appreciate_text, i_like_text, username, pref_gender, image_link;
    int pre_age_min_val, pref_age_max_val, pref_height_min_val, pref_height_max_val;

    private FirebaseAuth mAuth =FirebaseAuth.getInstance();

    public RegistrationFormHandler(String age, String height, String i_am, String i_appreciate,
                                   String i_like, int pre_age_min_val, int pref_age_max_val,
                                   int pref_height_min_val, int pref_height_max_val,
                                   String pref_gender, String imageLink) {
        this.age = age;
        this.height = height;
        this.i_am_text = i_am;
        this.i_appreciate_text = i_appreciate;
        this.i_like_text = i_like;
        this.pre_age_min_val = pre_age_min_val;
        this.pref_age_max_val = pref_age_max_val;
        this.username = mAuth.getCurrentUser().getDisplayName();
        this.pref_gender = pref_gender;
        this.pref_height_min_val = pref_height_min_val;
        this.pref_height_max_val = pref_height_max_val;
        this.image_link = imageLink;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public String getI_am() {
        return i_am_text;
    }

    public String getI_appreciate() {
        return i_appreciate_text;
    }

    public String getI_like() {
        return i_like_text;
    }

    public Number getPre_age_min_val() {
        return pre_age_min_val;
    }

    public Number getPref_age_max_val() {
        return pref_age_max_val;
    }

    public String getPref_gender() {
        return pref_gender;
    }

    public String getImage_link() {
        return image_link;
    }

    public int getPref_height_min_val() {
        return pref_height_min_val;
    }

    public int getPref_height_max_val() {
        return pref_height_max_val;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setI_am_text(String i_am_text) {
        this.i_am_text = i_am_text;
    }

    public void setI_appreciate_text(String i_appreciate_text) {
        this.i_appreciate_text = i_appreciate_text;
    }

    public void setI_like_text(String i_like_text) {
        this.i_like_text = i_like_text;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPref_gender(String pref_gender) {
        this.pref_gender = pref_gender;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public void setPre_age_min_val(int pre_age_min_val) {
        this.pre_age_min_val = pre_age_min_val;
    }

    public void setPref_age_max_val(int pref_age_max_val) {
        this.pref_age_max_val = pref_age_max_val;
    }

    public void setPref_height_min_val(int pref_height_min_val) {
        this.pref_height_min_val = pref_height_min_val;
    }

    public void setPref_height_max_val(int pref_height_max_val) {
        this.pref_height_max_val = pref_height_max_val;
    }
}
