package com.example.nutriappjava.classes;

import java.util.Date;

public class User {
    private String userUsername;
    private String userEmail;
    private String userSalt;
    private String userHashPassword;
    private Date userDob;
    private int userGender;
    private double userHeight;
    private double userWeight;

    public User() {

    }
    public User(String userUsername, String userEmail, String userSalt, String userHashPassword, Date userDob, int userGender, double userHeight, double userWeight) {
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userSalt = userSalt;
        this.userHashPassword = userHashPassword;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserHashPassword() {
        return userHashPassword;
    }

    public void setUserHashPassword(String userHashPassword) {
        this.userHashPassword = userHashPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public Date getUserDob() {
        return userDob;
    }

    public void setUserDob(Date userDob) {
        this.userDob = userDob;
    }

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(double userHeight) {
        this.userHeight = userHeight;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }
}
