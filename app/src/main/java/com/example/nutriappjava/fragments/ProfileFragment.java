package com.example.nutriappjava.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.User;
import com.example.nutriappjava.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView usernameInfo;
    private TextView emailInfo;
    private TextView totalLogsInfo;
    private TextView bmiInfo;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameInfo = view.findViewById(R.id.username_info);
        emailInfo = view.findViewById(R.id.email_info);
        totalLogsInfo = view.findViewById(R.id.tv_total_logs);
        bmiInfo = view.findViewById(R.id.tv_bmi);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);
        String token = sharedPreferences.getString("token", "token");

        fetchUserData(userId, token);
        fetchUserLogsCount(token);

        return view;
    }

    private void fetchUserData(Long userId, String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<User> call = apiService.getUserById(userId, "Bearer " + token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    usernameInfo.setText("Username: " + user.getUserUsername());
                    emailInfo.setText("Email: " + user.getUserEmail());
                    bmiInfo.setText("BMI: " + user.calculateBMIStatus());
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
}
