package com.example.nutriappjava.classes;

public class DailyLogSummary {
    private String date;
    private int logCount;
    private double totalCalories;

    public DailyLogSummary(String date, int logCount, double totalCalories) {
        this.date = date;
        this.logCount = logCount;
        this.totalCalories = totalCalories;
    }
    public String getDate() { return date; }
    public int getLogCount() { return logCount; }
    public double getTotalCalories() { return totalCalories; }
}