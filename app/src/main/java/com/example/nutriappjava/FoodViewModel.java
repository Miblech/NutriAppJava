package com.example.nutriappjava;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodViewModel extends ViewModel {

    private final MutableLiveData<List<Food>> foodListLiveData = new MutableLiveData<>();

    public LiveData<List<Food>> getFoodListLiveData() {
        return foodListLiveData;
    }

    public void loadFoods() {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<Food>> call = apiService.getAllFoods();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}