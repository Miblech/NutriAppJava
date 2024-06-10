package com.example.nutriappjava.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");

        TextView tvUsername = view.findViewById(R.id.username_info);

        tvUsername.setText("Username: " + username);

        String email = sharedPreferences.getString("email", "");

        TextView tvEmail = view.findViewById(R.id.email_info);

        tvEmail.setText("Email: " + email);

        int userId = sharedPreferences.getInt("userId", -1);

        db = new DatabaseHelper(getContext());

        int totalLogs = db.getReadableDatabase().query("daily_log", null, "user_id =?", new String[]{String.valueOf(userId)}, null, null, null).getCount();

        TextView tvTotalLogs = view.findViewById(R.id.tv_total_logs);

        tvTotalLogs.setText("Total Logs: " + totalLogs);

        return view;
    }
}