package com.example.nutriappjava.classes;

public class User {
    private int userId;
    private String userName;
    private String userHashPassword;
    private String userSalt;
    private String userEmail;

    private String userUsername;
    private String userDob;
    private int userGender;
    private double userHeight;
    private double userWeight;
    private double userTargetWeight;
    private long userLastSeen;

    public User() {
    }

    public User(int userId, String userName, String userHashPassword, String userSalt, String userEmail, String userUsername, String userDob, int userGender, double userHeight, double userWeight, double userTargetWeight, long userLastSeen) {
        this.userId = userId;
        this.userName = userName;
        this.userHashPassword = userHashPassword;
        this.userSalt = userSalt;
        this.userEmail = userEmail;
        this.userUsername = userUsername;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userTargetWeight = userTargetWeight;
        this.userLastSeen = userLastSeen;
    }

    public User(String userName, String userPassword, String userSalt, String userEmail, String userUsername, String userDateOfBirth, double userHeight, double userWeight, double userTargetWeight, int userGender) {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
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

    public double getUserTargetWeight() {
        return userTargetWeight;
    }

    public void setUserTargetWeight(double userTargetWeight) {
        this.userTargetWeight = userTargetWeight;
    }

    public long getUserLastSeen() {
        return userLastSeen;
    }

    public void setUserLastSeen(long userLastSeen) {
        this.userLastSeen = userLastSeen;
    }
}
