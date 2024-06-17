package com.example.nutriappjava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodViewModel extends ViewModel {

    private final MutableLiveData<List<Food>> foodListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> categoriesLiveData = new MutableLiveData<>();

    public LiveData<List<Food>> getFoodListLiveData() {
        return foodListLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public LiveData<List<String>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public void loadFoods() {
        isLoadingLiveData.setValue(true);
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<Food>> call = apiService.getAllFoods();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    foodListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                // Handle failure
            }
        });
    }

    public void loadFoodsSortedBy(String nutrient) {
        isLoadingLiveData.setValue(true);
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<Food>> call;

        switch (nutrient) {
            case "protein":
                call = apiService.getAllFoodsSortedByProteinDesc();
                break;
            case "carbohydrate":
                call = apiService.getAllFoodsSortedByCarbohydrateDesc();
                break;
            case "fat":
                call = apiService.getAllFoodsSortedBySaturatedFatDesc();
                break;
            default:
                return;
        }

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    foodListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                // Handle failure
            }
        });
    }

    public void loadCategories() {
        isLoadingLiveData.setValue(true);
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<String>> call = apiService.getCategories();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    categoriesLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
            }
        });
    }

    public void loadFoodsByCategory(String category) {
        isLoadingLiveData.setValue(true);
        ApiService apiService = ApiClient.getRetrofitInstance(false).create(ApiService.class);

        Call<List<Food>> call = apiService.getFoodsByCategory(category);

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    foodListLiveData.setValue(response.body());
                } else {
                    Log.e("Error", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                Log.e("Error", "API call failed", t);
            }
        });
    }
}
