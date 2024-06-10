package com.example.nutriappjava.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.LogsAdapter;
import com.example.nutriappjava.classes.DailyLogSummary;

import java.util.List;

public class LogsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LogsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logs, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the current user ID
        int userId = getCurrentUserId();

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        List<DailyLogSummary> summaries = dbHelper.getDailyLogSummaries(userId);

        if (summaries.isEmpty()) {
            view.findViewById(R.id.noLogsTextView).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.noLogsTextView).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new LogsAdapter(summaries);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }
}