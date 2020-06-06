package com.example.amour.Util;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class User implements Serializable {
    String email, age, height, i_am, i_appreciate, i_like, username, pref_gender, image_link, sex;
    int pre_age_min_val, pref_age_max_val, pref_height_min_val, pref_height_max_val;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public User() {
    }

    public User(String username, String age, String height, String i_am, String i_appreciate,
                String i_like, int pre_age_min_val, int pref_age_max_val,
                int pref_height_min_val, int pref_height_max_val,
                String pref_gender, String imageLink, String sex) {
        this.username = username;
        this.age = age;
        this.height = height;
        this.i_am = i_am;
        this.i_appreciate = i_appreciate;
        this.i_like = i_like;
        this.pre_age_min_val = pre_age_min_val;
        this.pref_age_max_val = pref_age_max_val;
        this.pref_gender = pref_gender;
        this.pref_height_min_val = pref_height_min_val;
        this.pref_height_max_val = pref_height_max_val;
        this.image_link = imageLink;
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public String getI_am() {
        return i_am;
    }

    public String getI_appreciate() {
        return i_appreciate;
    }

    public String getI_like() {
        return i_like;
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

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setI_am_text(String i_am) {
        this.i_am = i_am;
    }

    public void setI_appreciate_text(String i_appreciate) {
        this.i_appreciate = i_appreciate;
    }

    public void setI_like_text(String i_like) {
        this.i_like = i_like;
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
