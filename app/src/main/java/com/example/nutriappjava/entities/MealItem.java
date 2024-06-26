package com.example.nutriappjava.entities;

public class MealItem {
    private MealItemKey id;
    private DailyLog dailyLog;
    private Food food;
    private Float weight;


    public MealItem() {
    }

    public MealItem( Food food, Float weight) {
        this.food = food;
        this.weight = weight;
    }

    public MealItem(MealItemKey mealItemKey, DailyLog dailyLog, Food food, float weight) {
    }

    public MealItemKey getId() {
        return id;
    }

    public void setId(MealItemKey id) {
        this.id = id;
    }

    public DailyLog getDailyLog() {
        return dailyLog;
    }

    public void setDailyLog(DailyLog dailyLog) {
        this.dailyLog = dailyLog;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}