package com.example.nutriappjava.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {
    private String name;
    private double calories;
    private double servingSizeG;
    private double fatTotalG;
    private double fatSaturatedG;
    private double proteinG;
    private int sodiumMg;
    private int potassiumMg;
    private int cholesterolMg;
    private double carbohydratesTotalG;
    private double fiberG;
    private double sugarG;

    public FoodItem() {
    }

    public FoodItem(String name, double calories, double servingSizeG, double fatTotalG, double fatSaturatedG, double proteinG, int sodiumMg, int potassiumMg, int cholesterolMg, double carbohydratesTotalG, double fiberG, double sugarG) {
        this.name = name;
        this.calories = calories;
        this.servingSizeG = servingSizeG;
        this.fatTotalG = fatTotalG;
        this.fatSaturatedG = fatSaturatedG;
        this.proteinG = proteinG;
        this.sodiumMg = sodiumMg;
        this.potassiumMg = potassiumMg;
        this.cholesterolMg = cholesterolMg;
        this.carbohydratesTotalG = carbohydratesTotalG;
        this.fiberG = fiberG;
        this.sugarG = sugarG;
    }


    protected FoodItem(Parcel in) {
        name = in.readString();
        calories = in.readDouble();
        servingSizeG = in.readDouble();
        fatTotalG = in.readDouble();
        fatSaturatedG = in.readDouble();
        proteinG = in.readDouble();
        sodiumMg = in.readInt();
        potassiumMg = in.readInt();
        cholesterolMg = in.readInt();
        carbohydratesTotalG = in.readDouble();
        fiberG = in.readDouble();
        sugarG = in.readDouble();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    public FoodItem(int id, String name, float calories, float servingSizeG, float fatTotalG, float fatSaturatedG, float proteinG, int sodiumMg, int potassiumMg, int cholesterolMg, float carbohydratesTotalG, float fiberG, float sugarG) {
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(calories);
        dest.writeDouble(servingSizeG);
        dest.writeDouble(fatTotalG);
        dest.writeDouble(fatSaturatedG);
        dest.writeDouble(proteinG);
        dest.writeInt(sodiumMg);
        dest.writeInt(potassiumMg);
        dest.writeInt(cholesterolMg);
        dest.writeDouble(carbohydratesTotalG);
        dest.writeDouble(fiberG);
        dest.writeDouble(sugarG);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getServingSizeG() {
        return servingSizeG;
    }

    public void setServingSizeG(double servingSizeG) {
        this.servingSizeG = servingSizeG;
    }

    public double getFatTotalG() {
        return fatTotalG;
    }

    public void setFatTotalG(double fatTotalG) {
        this.fatTotalG = fatTotalG;
    }

    public double getFatSaturatedG() {
        return fatSaturatedG;
    }

    public void setFatSaturatedG(double fatSaturatedG) {
        this.fatSaturatedG = fatSaturatedG;
    }

    public double getProteinG() {
        return proteinG;
    }

    public void setProteinG(double proteinG) {
        this.proteinG = proteinG;
    }

    public int getSodiumMg() {
        return sodiumMg;
    }

    public void setSodiumMg(int sodiumMg) {
        this.sodiumMg = sodiumMg;
    }

    public int getPotassiumMg() {
        return potassiumMg;
    }

    public void setPotassiumMg(int potassiumMg) {
        this.potassiumMg = potassiumMg;
    }

    public int getCholesterolMg() {
        return cholesterolMg;
    }

    public void setCholesterolMg(int cholesterolMg) {
        this.cholesterolMg = cholesterolMg;
    }

    public double getCarbohydratesTotalG() {
        return carbohydratesTotalG;
    }

    public void setCarbohydratesTotalG(double carbohydratesTotalG) {
        this.carbohydratesTotalG = carbohydratesTotalG;
    }

    public double getFiberG() {
        return fiberG;
    }

    public void setFiberG(double fiberG) {
        this.fiberG = fiberG;
    }

    public double getSugarG() {
        return sugarG;
    }

    public void setSugarG(double sugarG) {
        this.sugarG = sugarG;
    }


}
