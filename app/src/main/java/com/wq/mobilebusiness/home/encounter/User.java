package com.wq.mobilebusiness.home.encounter;

/**
 * Created by 王铨 on 2016/4/10.
 */
public class User {
    private String imgUrl;
    private String username;
    private String gender;
    private int age;
    private String signature;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User(String imgUrl, String username, String gender, int age, String signature) {
        this.imgUrl = imgUrl;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", signature='" + signature + '\'' +
                '}';
    }
}
