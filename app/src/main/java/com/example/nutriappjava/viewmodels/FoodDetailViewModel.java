package com.example.nutriappjava.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailViewModel extends ViewModel {
    private MutableLiveData<Food> foodLiveData = new MutableLiveData<>();
    private long foodId;

    public void setFoodId(long foodId) {
        this.foodId = foodId;
        loadFoodById();
    }

    public LiveData<Food> getFoodLiveData() {
        return foodLiveData;
    }

    private void loadFoodById() {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<Food> call = apiService.getFoodById(foodId);
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
