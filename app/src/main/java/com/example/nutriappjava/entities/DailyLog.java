package com.example.nutriappjava.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DailyLog {
    private Long logId;
    private User user;
    private LocalDate date;
    private LocalDateTime timestamp;
    private String mealType;
    private List<MealItem> mealItems;

    public DailyLog() {
    }

    public DailyLog(Long logId, User user, LocalDate date, LocalDateTime timestamp, String mealType, List<MealItem> mealItems) {
        this.logId = logId;
        this.user = user;
        this.date = date;
        this.timestamp = timestamp;
        this.mealType = mealType;
        this.mealItems = mealItems;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public List<MealItem> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<MealItem> mealItems) {
        this.mealItems = mealItems;
    }
}
