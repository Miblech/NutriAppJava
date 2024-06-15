package com.example.nutriappjava.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.services.ApiService;
import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.entities.NutrientSummary;
import com.example.nutriappjava.entities.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView usernameInfo;
    private TextView emailInfo;
    private TextView totalLogsInfo;
    private TextView bmiInfo;
    private PieChart mealLogsPieChart;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameInfo = view.findViewById(R.id.username_info);
        emailInfo = view.findViewById(R.id.email_info);
        totalLogsInfo = view.findViewById(R.id.tv_total_logs);
        bmiInfo = view.findViewById(R.id.tv_bmi);
        mealLogsPieChart = view.findViewById(R.id.mealLogsPieChart);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");

        fetchUserData(token);
        fetchUserLogsCount(token);
        fetchNutrientSummary(token);

        return view;
    }

    private void fetchUserData(String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<User> call = apiService.getCurrentUser("Bearer " + token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    usernameInfo.setText("Username: " + user.getUserUsername());
                    emailInfo.setText("Email: " + user.getUserEmail());
                    bmiInfo.setText("BMI: " + user.calculateBMIStatus());

                    // Update SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", user.getUserUsername());
                    editor.putString("email", user.getUserEmail());
                    editor.putString("dob", user.getUserDob());
                    editor.putFloat("height", (float) user.getUserHeight());
                    editor.putFloat("weight", (float) user.getUserWeight());
                    editor.putInt("gender", user.getUserGender());
                    editor.apply();
                } else {
                    Log.e("ProfileFragment", "Failed to fetch user data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ProfileFragment", "Error fetching user data: " + t.getMessage());
            }
        });
    }

    private void fetchUserLogsCount(String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<Long> call = apiService.getUserLogsCount("Bearer " + token);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Long logsCount = response.body();
                    totalLogsInfo.setText("Total Logs: " + logsCount);
                } else {
                    Log.e("ProfileFragment", "Failed to fetch logs count: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("ProfileFragment", "Error fetching logs count: " + t.getMessage());
            }
        });
    }

    private void fetchNutrientSummary(String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<NutrientSummary> call = apiService.getTotalNutrientSummaryForUser("Bearer " + token);

        call.enqueue(new Callback<NutrientSummary>() {
            @Override
            public void onResponse(Call<NutrientSummary> call, Response<NutrientSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NutrientSummary summary = response.body();
                    setupPieChart(summary);
                } else {
                    Log.e("ProfileFragment", "Failed to fetch nutrient summary: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NutrientSummary> call, Throwable t) {
                Log.e("ProfileFragment", "Error fetching nutrient summary: " + t.getMessage());
            }
        });

    }

    private void setupPieChart(NutrientSummary summary) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(summary.getCarbohydrate(), "Carbohydrates"));
        entries.add(new PieEntry(summary.getProtein(), "Proteins"));
        entries.add(new PieEntry(summary.getFat(), "Fats"));
        entries.add(new PieEntry(summary.getFiber(), "Fiber"));
        entries.add(new PieEntry(summary.getSugarTotal(), "Sugar"));

        PieDataSet dataSet = new PieDataSet(entries, "|||  : Nutrient Summary All Time");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData pieData = new PieData(dataSet);
        mealLogsPieChart.setData(pieData);
        mealLogsPieChart.invalidate();

        mealLogsPieChart.getDescription().setEnabled(false);
        mealLogsPieChart.setDrawHoleEnabled(true);
        mealLogsPieChart.setHoleColor(Color.WHITE);
        mealLogsPieChart.setTransparentCircleRadius(58f);
    }
}
