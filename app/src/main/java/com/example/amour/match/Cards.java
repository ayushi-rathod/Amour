package com.example.amour.match;

public class Cards {
    private String userId;
    private String name, profileImageUrl, age;

    public Cards(String userId, String name, String age, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
