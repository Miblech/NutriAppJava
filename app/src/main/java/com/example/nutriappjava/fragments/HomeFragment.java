package com.example.nutriappjava.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.activities.AddDailyLogActivity;
import com.example.nutriappjava.activities.DailyLogDetailActivity;
import com.example.nutriappjava.adapters.DailyLogAdapter;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * @author nombr/Miblech
 * Represents the home fragment where users can view their daily logs.
 * This fragment fetches and displays the logs for the current day from the backend.
 * It also allows users to add new logs and view details of existing logs.
 */
public class HomeFragment extends Fragment implements DailyLogAdapter.OnItemClickListener {
    private TextView usernameTextView;
    private RecyclerView dailyLogRecyclerView;
    private SharedPreferences sharedPreferences;
    private DailyLogAdapter dailyLogAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize shared preferences and retrieve user details
        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "username");
        String token = sharedPreferences.getString("token", "token");

        // Initialize UI components
        usernameTextView = view.findViewById(R.id.home_username);
        Button addButton = view.findViewById(R.id.button_add_meal);
        dailyLogRecyclerView = view.findViewById(R.id.daily_log_recycler_view);

        // Set up RecyclerView for daily logs
        dailyLogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyLogAdapter = new DailyLogAdapter(token, this);
        dailyLogRecyclerView.setAdapter(dailyLogAdapter);

        // Update username TextView with user's name
        usernameTextView.setText("Hello " + username);

        // Set click listener for the "Add Meal" button
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddDailyLogActivity.class);
            startActivity(intent);
        });

        // Fetch and display daily logs for the current day
        checkDailyLogs(username, token);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Re-fetch and display daily logs when the fragment resumes
        String username = sharedPreferences.getString("username", "username");
        String token = sharedPreferences.getString("token", "token");
        checkDailyLogs(username, token);
    }

    /**
     * Fetches and displays the daily logs for the current day.
     * @see ApiService#getLogsForToday(String)
     * @param username The username of the current user
     * @param token The authentication token of the current user
     */
    public void checkDailyLogs(String username, String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<DailyLog>> call = apiService.getLogsForToday("Bearer " + token);

        call.enqueue(new Callback<List<DailyLog>>() {
            @Override
            public void onResponse(Call<List<DailyLog>> call, Response<List<DailyLog>> response) {
                if (response.isSuccessful()) {
                    List<DailyLog> logs = response.body();
                    if (logs.isEmpty()) {
                        usernameTextView.setText("Hello " + username + ", you don't have any logs for today.");
                        dailyLogRecyclerView.setVisibility(View.GONE);
                    } else {
                        usernameTextView.setText("Hello " + username + ", you have " + logs.size() + " logs for today.");
                        dailyLogRecyclerView.setVisibility(View.VISIBLE);
                        dailyLogAdapter.setDailyLogs(logs);
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

    @Override
    public void onItemClick(DailyLog dailyLog) {
        Intent intent = new Intent(getActivity(), DailyLogDetailActivity.class);
        intent.putExtra("logId", dailyLog.getLogId());
        startActivity(intent);
    }
}

