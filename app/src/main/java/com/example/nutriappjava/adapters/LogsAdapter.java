package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.DailyLogSummary;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder> {
    private List<DailyLogSummary> summaries;

    public LogsAdapter(List<DailyLogSummary> summaries) {
        this.summaries = summaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyLogSummary summary = summaries.get(position);
        holder.bind(summary);
    }

    @Override
    public int getItemCount() {
        return summaries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, logCount, totalCalories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.log_meal_type);
            logCount = itemView.findViewById(R.id.log_time);
            totalCalories = itemView.findViewById(R.id.total_calories);
        }

        public void bind(DailyLogSummary summary) {
            date.setText("Date: " + summary.getDate());
            logCount.setText("Meals eaten that day: " + String.valueOf(summary.getLogCount()));
            totalCalories.setText("Total Calories: " + String.valueOf(summary.getTotalCalories()));
        }
    }
}