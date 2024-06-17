package com.example.nutriappjava.entities;

public class NutrientSummary {
    private float carbohydrate;
    private float protein;
    private float fat;
    private float fiber;
    private float sugarTotal;

    public NutrientSummary() {
    }

    public NutrientSummary(float carbohydrate, float protein, float fat, float fiber, float sugarTotal) {
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.sugarTotal = sugarTotal;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getSugarTotal() {
        return sugarTotal;
    }

    public void setSugarTotal(float sugarTotal) {
        this.sugarTotal = sugarTotal;
    }

    @Override
    public String toString() {
        return "Carbs: " + carbohydrate + "g, Protein: " + protein + "g, Fat: " + fat + "g, Fiber: " + fiber + "g, Sugar: " + sugarTotal + "g";
    }
}