package com.example.nutriappjava.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import com.google.gson.annotations.SerializedName;

public class User {
    private Long userId;

    @SerializedName("userUsername")
    private String userUsername;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPassword")
    private String userPassword;

    @SerializedName("userHeight")
    private double userHeight;

    @SerializedName("userWeight")
    private double userWeight;

    @SerializedName("userGender")
    private int userGender;

    @SerializedName("userDob")
    private String userDob;

    public User() {
    }

    public User(String userUsername, String userEmail, String userPassword, double userHeight, double userWeight, int userGender, String userDob) {
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userGender = userGender;
        this.userDob = userDob;
    }

    public User(Long userId, String userUsername, String userEmail, String userPassword, double userHeight, double userWeight, int userGender, String userDob) {
        this.userId = userId;
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userGender = userGender;
        this.userDob = userDob;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }
}