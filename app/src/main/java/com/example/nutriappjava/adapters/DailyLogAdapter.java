package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.DailyLog;
import java.util.List;

public class DailyLogAdapter extends RecyclerView.Adapter<DailyLogAdapter.ViewHolder> {
    private List<DailyLog> dailyLogs;

    private DatabaseHelper dbHelper;

    public DailyLogAdapter(List<DailyLog> dailyLogs, DatabaseHelper dbHelper) {
        this.dailyLogs = dailyLogs;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyLog log = dailyLogs.get(position);
        holder.mealType.setText(log.getMealType());
        holder.time.setText(log.getTime());

        double totalCalories = dbHelper.getTotalCaloriesByLogId(log.getLogId());
        holder.totalCalories.setText("Calorias: " + String.valueOf(totalCalories));
    }

    @Override
    public int getItemCount() {
        return dailyLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealType;
        public TextView time;

        public TextView totalCalories;

        public ViewHolder(View itemView) {
            super(itemView);
            mealType = itemView.findViewById(R.id.log_meal_type);
            time = itemView.findViewById(R.id.log_time);
            totalCalories = itemView.findViewById(R.id.total_calories);
        }
    }
}