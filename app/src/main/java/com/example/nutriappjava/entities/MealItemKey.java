package com.example.nutriappjava.entities;

import java.io.Serializable;

public class MealItemKey implements Serializable {
    private Long logId;
    private Long foodItemId;

    public MealItemKey() {
    }

    public MealItemKey(Long logId, Long foodItemId) {
        this.logId = logId;
        this.foodItemId = foodItemId;
    }

    public Long getLogId() {
        return this.logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getFoodItemId() {
        return this.foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {

        this.foodItemId = foodItemId;
    }

}