package com.example.nutriappjava.classes;

public class LogEntry {
    private int logId;
    private int userId;
    private String logDate;
    private String logTime;
    private String mealType;
    private int foodItemId;
    private long timestamp;

    public LogEntry(int logId, int userId, String logDate, String logTime, String mealType, int foodItemId, long timestamp) {
        this.logId = logId;
        this.userId = userId;
        this.logDate = logDate;
        this.logTime = logTime;
        this.mealType = mealType;
        this.foodItemId = foodItemId;
        this.timestamp = timestamp;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
