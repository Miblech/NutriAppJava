package com.example.nutriappjava.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private TextView usernameTextView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "username");
        String token = sharedPreferences.getString("token", "token");

        usernameTextView = view.findViewById(R.id.home_username);

        checkDailyLogs(username, token);

        return view;
    }

    private void checkDailyLogs(String username, String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<DailyLog>> call = apiService.getLogsForToday("Bearer " + token);

        call.enqueue(new Callback<List<DailyLog>>() {
            @Override
            public void onResponse(Call<List<DailyLog>> call, Response<List<DailyLog>> response) {
                if (response.isSuccessful()) {
                    List<DailyLog> logs = response.body();
                    if (logs != null && logs.isEmpty()) {
                        usernameTextView.setText("Hello " + username + ", you don't have any logs for today.");
                    } else {
                        usernameTextView.setText("Hello " + username);
                        // TODO Create Recycler View to display logs
                    }
                } else {
                    Log.e("HomeFragment", "Failed to fetch logs: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DailyLog>> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching logs: " + t.getMessage());
            }
        });
    }
}