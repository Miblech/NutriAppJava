package com.example.nutriappjava.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.FoodAdapter;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.entities.MealItem;
import com.example.nutriappjava.entities.NutrientSummary;
import com.example.nutriappjava.fragments.FoodDetailFragment;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyLogDetailActivity extends AppCompatActivity {

    private TextView logDetailsTextView;
    private TextView nutrientSummaryTextView;
    private RecyclerView foodRecyclerView;
    private SharedPreferences sharedPreferences;
    private String token;
    private long logId;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log_detail);

        logDetailsTextView = findViewById(R.id.log_details_text_view);
        nutrientSummaryTextView = findViewById(R.id.nutrient_summary_text_view);
        foodRecyclerView = findViewById(R.id.food_recycler_view);

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "token");
        logId = getIntent().getLongExtra("logId", -1);

        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter();
        foodRecyclerView.setAdapter(foodAdapter);


        if (logId != -1) {
            fetchDailyLogDetails();
            fetchNutrientSummary();
        }
    }

    private void fetchDailyLogDetails() {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<DailyLog> call = apiService.getDailyLogDetails("Bearer " + token, logId);

        call.enqueue(new Callback<DailyLog>() {
            @Override
            public void onResponse(Call<DailyLog> call, Response<DailyLog> response) {
                if (response.isSuccessful()) {
                    DailyLog dailyLog = response.body();
                    if (dailyLog != null) {
                        logDetailsTextView.setText(
                                "Date: " + dailyLog.getDate() + "\n" +
                                        "Meal Type: " + dailyLog.getMealType() + "\n" +
                                        "Timestamp: " + dailyLog.getTimestamp()
                        );
                        List<Food> foods = new ArrayList<>();
                        for (MealItem mealItem : dailyLog.getMealItems()) {
                            foods.add(mealItem.getFood());
                        }
                        foodAdapter.setFoodList(foods);
                    }
                } else {
                    Log.e("DailyLogDetailActivity", "Failed to fetch log details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DailyLog> call, Throwable t) {
                Log.e("DailyLogDetailActivity", "Error fetching log details: " + t.getMessage());
            }
        });
    }

    private void fetchNutrientSummary() {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<NutrientSummary> call = apiService.getLogSummary(logId, "Bearer " + token);

        call.enqueue(new Callback<NutrientSummary>() {
            @Override
            public void onResponse(Call<NutrientSummary> call, Response<NutrientSummary> response) {
                if (response.isSuccessful()) {
                    NutrientSummary summary = response.body();
                    if (summary != null) {
                        nutrientSummaryTextView.setText(summary.toString());
                    }
                } else {
                    Log.e("DailyLogDetailActivity", "Failed to fetch nutrient summary: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NutrientSummary> call, Throwable t) {
                Log.e("DailyLogDetailActivity", "Error fetching nutrient summary: " + t.getMessage());
            }
        });
    }


}
