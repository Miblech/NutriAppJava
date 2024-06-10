package com.example.nutriappjava.classes;

import com.example.nutriappjava.DatabaseHelper;

public class DailyLog {
    private int logId;
    private int userId;
    private String date;
    private String time;
    private String mealType;

    public DailyLog(int logId, int userId, String date, String time, String mealType) {
        this.logId = logId;
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.mealType = mealType;
    }

    public int getLogId() {
        return logId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMealType() {
        return mealType;
    }

}
