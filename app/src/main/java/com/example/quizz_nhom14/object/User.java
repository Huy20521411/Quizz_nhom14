package com.example.quizz_nhom14.object;


public class User {
    private int userID=-1;
    private boolean classsify; //true:gv__false:sv
    private int gender;//1:nam__2:nu
    private String fullname;//Tên đầy đủ
    private String username,password;//Tên đăng nhập , mật khẩu

    public User() {
    }

    public User(boolean classsify, int gender, String fullname, String username, String password) {
        this.classsify = classsify;
        this.gender = gender;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
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

    public String getUserame() {
        return username;
    }

    public void setUserame(String userame) {
        this.username = userame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}