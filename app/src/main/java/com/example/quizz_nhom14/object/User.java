package com.example.quizz_nhom14.object;


import java.io.Serializable;

public class User implements Serializable {
    private String UID;
    private int userID=-1;
    private boolean classsify; //true:gv__false:sv
    private int gender;//1:nam__2:nu
    private String fullname;//Tên đầy đủ
    private String phone,email;//sdt,email

    public User() {
    }

    public User(boolean classsify, int gender, String fullname,String phone,String email) {
        this.classsify = classsify;
        this.gender = gender;
        this.fullname = fullname;
        this.email=email;
        this.phone=phone;
    }

    public User(String UID, int userID, boolean classsify, int gender, String fullname, String phone, String email) {
        this.UID = UID;
        this.userID = userID;
        this.classsify = classsify;
        this.gender = gender;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
    }

    public User(int gender, String fullname) {
        this.gender = gender;
        this.fullname = fullname;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isClasssify() {
        return classsify;
    }

    public void setClasssify(boolean classsify) {
        this.classsify = classsify;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
