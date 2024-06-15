package com.example.nutriappjava.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setUserUsername(userUsername);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
        setUserHeight(userHeight);
        setUserWeight(userWeight);
        setUserGender(userGender);
        setUserDob(userDob);
    }

    public User(Long userId, String userUsername, String userEmail, String userPassword, double userHeight, double userWeight, int userGender, String userDob) {
        this.userId = userId;
        setUserUsername(userUsername);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
        setUserHeight(userHeight);
        setUserWeight(userWeight);
        setUserGender(userGender);
        setUserDob(userDob);
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
        if (userUsername == null || userUsername.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.userUsername = userUsername;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        if (!isValidEmail(userEmail)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        if (userPassword == null || userPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.userPassword = userPassword;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(double userHeight) {
        if (userHeight <= 0.5 || userHeight > 2.5) { // reasonable height range in meters
            throw new IllegalArgumentException("Height must be between 0.5 and 2.5 meters");
        }
        this.userHeight = userHeight;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        if (userWeight <= 2.0 || userWeight > 300.0) { // reasonable weight range in kg
            throw new IllegalArgumentException("Weight must be between 2.0 and 300.0 kg");
        }
        this.userWeight = userWeight;
    }

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        if (userGender < 0 || userGender > 2) { // assuming 0: unknown, 1: male, 2: female
            throw new IllegalArgumentException("Gender must be 0 (unknown), 1 (male), or 2 (female)");
        }
        this.userGender = userGender;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        if (!isValidAge(userDob)) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }
        this.userDob = userDob;
    }

    public String calculateBMIStatus() {
        if (userHeight == 0 || userWeight == 0) {
            return "Invalid height or weight";
        }

        double heightInMeters = userHeight;
        double bmi = userWeight / (heightInMeters * heightInMeters);

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidAge(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        return age >= 18;
    }
}