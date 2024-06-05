package com.example.nutriappjava.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class sportItems implements Parcelable {
    private String name;
    private int caloriesPerHour;
    private int durationMinutes;
    private int totalCalories;

    public sportItems() {
    }

    public sportItems(String name, int caloriesPerHour, int durationMinutes, int totalCalories) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
        this.durationMinutes = durationMinutes;
        this.totalCalories = totalCalories;
    }

    public sportItems(Parcel in) {
        name = in.readString();
        caloriesPerHour = in.readInt();
        durationMinutes = in.readInt();
        totalCalories = in.readInt();
    }

    public static final Creator<sportItems> CREATOR = new Creator<sportItems>() {
        @Override
        public sportItems createFromParcel(Parcel source) {
            return new sportItems(source);
        }

        @Override
        public sportItems[] newArray(int size) {
            return new sportItems[size];
        }
    };

    public sportItems(String activityName) {
        name = activityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(caloriesPerHour);
        dest.writeInt(durationMinutes);
        dest.writeInt(totalCalories);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public void setCaloriesPerHour(int caloriesPerHour) {
        this.caloriesPerHour = caloriesPerHour;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }




}
