package com.example.nutriappjava.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.LogsAdapter;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.viewmodels.LogsViewModel;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import android.graphics.Color;
import com.example.nutriappjava.entities.NutrientSummary;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class LogsFragment extends Fragment {

    private LogsViewModel logsViewModel;
    private LogsAdapter logsAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView noLogsTextView;
    private CalendarView calendarView;
    private Spinner spinner;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logs, container, false);

        spinner = view.findViewById(R.id.spinner_logs_filter);
        calendarView = view.findViewById(R.id.calendar_View);
        progressBar = view.findViewById(R.id.progressBarLogs);
        recyclerView = view.findViewById(R.id.recyclerViewLogs);
        noLogsTextView = view.findViewById(R.id.noLogsTextView);
        pieChart = view.findViewById(R.id.mealLogsPieChart);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        logsAdapter = new LogsAdapter(getToken());
        recyclerView.setAdapter(logsAdapter);

        logsViewModel = new ViewModelProvider(this).get(LogsViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_logs_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPeriod = parent.getItemAtPosition(position).toString().toLowerCase();
                fetchLogsForSelectedPeriod(selectedPeriod);
                fetchSummaryForSelectedPeriod(selectedPeriod);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = formatDateString(year, month, dayOfMonth);
            fetchLogsForSelectedDate(selectedDate);
            fetchSummaryForSelectedDate(selectedDate); // Fetch the summary for the selected date
        });

        return view;
    }

    private void fetchLogsForSelectedDate(String date) {
        progressBar.setVisibility(View.VISIBLE);
        logsViewModel.getLogsForDate(getToken(), date).observe(getViewLifecycleOwner(), dailyLogs -> {
            progressBar.setVisibility(View.GONE);
            updateLogs(dailyLogs);
        });
    }

    private void fetchLogsForSelectedPeriod(String period) {
        progressBar.setVisibility(View.VISIBLE);
        logsViewModel.getLogsForPeriod(getToken(), period).observe(getViewLifecycleOwner(), dailyLogs -> {
            progressBar.setVisibility(View.GONE);
            updateLogs(dailyLogs);
        });
    }

    private void fetchSummaryForSelectedPeriod(String period) {
        logsViewModel.getLogSummaryForPeriod(getToken(), period).observe(getViewLifecycleOwner(), this::updatePieChart);
    }

    private void fetchSummaryForSelectedDate(String date) {
        logsViewModel.getLogSummaryForDate(getToken(), date).observe(getViewLifecycleOwner(), this::updatePieChart);
    }

    private void updateLogs(List<DailyLog> dailyLogs) {
        if (dailyLogs == null || dailyLogs.isEmpty()) {
            noLogsTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noLogsTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            logsAdapter.setDailyLogs(dailyLogs);
        }
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private String formatDateString(int year, int month, int dayOfMonth) {
        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private void updatePieChart(NutrientSummary summary) {
        if (summary == null) {
            pieChart.clear();
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(summary.getCarbohydrate(), "Carbohydrates"));
        entries.add(new PieEntry(summary.getProtein(), "Proteins"));
        entries.add(new PieEntry(summary.getFat(), "Fats"));
        entries.add(new PieEntry(summary.getFiber(), "Fiber"));
        entries.add(new PieEntry(summary.getSugarTotal(), "Sugar"));

        PieDataSet dataSet = new PieDataSet(entries, "Nutrient Summary");
        dataSet.setColors(new int[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA });
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setFormSize(12f);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
    }
}