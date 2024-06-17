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

public class HomeFragment extends Fragment implements DailyLogAdapter.OnItemClickListener {
    private TextView usernameTextView;
    private RecyclerView dailyLogRecyclerView;
    private SharedPreferences sharedPreferences;
    private DailyLogAdapter dailyLogAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "username");
        String token = sharedPreferences.getString("token", "token");

        usernameTextView = view.findViewById(R.id.home_username);
        Button addButton = view.findViewById(R.id.button_add_meal);
        dailyLogRecyclerView = view.findViewById(R.id.daily_log_recycler_view);

        dailyLogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyLogAdapter = new DailyLogAdapter(token, this);
        dailyLogRecyclerView.setAdapter(dailyLogAdapter);

        usernameTextView.setText("Hello " + username);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddDailyLogActivity.class);
            startActivity(intent);
        });

        checkDailyLogs(username, token);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String username = sharedPreferences.getString("username", "username");
        String token = sharedPreferences.getString("token", "token");
        checkDailyLogs(username, token);
    }

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

