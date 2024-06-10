package com.example.nutriappjava.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.activities.CreateLogActivity;

import java.text.SimpleDateFormat;

public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;

    public HomeFragment() {
    }

    public HomeFragment(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private TextView home_username, home_date;
    private RecyclerView home_recyclerView;
    private Button home_add_meal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_username = view.findViewById(R.id.home_username);
        home_date = view.findViewById(R.id.home_date);
        home_recyclerView = view.findViewById(R.id.daily_log_meals);
        home_add_meal = view.findViewById(R.id.button_add_meal);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("userId", -1);

        if (currentUserId!= -1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new java.util.Date());
            boolean hasLogForToday = dbHelper.hasLogForToday(currentUserId, currentDate);

            if (hasLogForToday) {
                home_recyclerView.setVisibility(View.VISIBLE);
                home_add_meal.setVisibility(View.VISIBLE);
            } else {
                home_username.setText("Hello " + sharedPreferences.getString("username", ""));
                home_date.setText("You have no log made for today. Add a meal to get started.");
                home_recyclerView.setVisibility(View.GONE);
                home_add_meal.setVisibility(View.VISIBLE);
            }

        } else {
            home_recyclerView.setVisibility(View.GONE);
            home_add_meal.setVisibility(View.GONE);
        }

        home_add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateLogActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}