package com.example.nutriappjava.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;
import com.example.nutriappjava.entities.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFoodViewModel extends ViewModel {

    private final MutableLiveData<List<Food>> foodListLiveData = new MutableLiveData<>();

    public LiveData<List<Food>> getFoodListLiveData() {
        return foodListLiveData;
    }

    public void searchFoodsByDescription(String description) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<Food>> call = apiService.searchFoodsByDescription(description);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });
    }
}