package com.example.nutriappjava;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutriappjava.classes.FoodItem;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<FoodItem> selectedFoodItem = new MutableLiveData<>();

    public LiveData<FoodItem> getSelectedFoodItem() {
        return selectedFoodItem;
    }

    public void setSelectedFoodItem(FoodItem foodItem) {
        selectedFoodItem.setValue(foodItem);
    }
}
