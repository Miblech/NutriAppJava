package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.NutrientSummary;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyLogAdapter extends RecyclerView.Adapter<DailyLogAdapter.DailyLogViewHolder> {

    private List<DailyLog> dailyLogs = new ArrayList<>();
    private OnItemClickListener listener;
    private String token;

    public interface OnItemClickListener {
        void onItemClick(DailyLog dailyLog);
    }

    public DailyLogAdapter(String token, OnItemClickListener listener) {
        this.token = token;
        this.listener = listener;
    }

    public void setDailyLogs(List<DailyLog> dailyLogs) {
        this.dailyLogs = dailyLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_log, parent, false);
        return new DailyLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyLogViewHolder holder, int position) {
        DailyLog dailyLog = dailyLogs.get(position);
        holder.date.setText("Date: " + dailyLog.getDate().toString());
        holder.mealType.setText("Meal Type: " + dailyLog.getMealType());
        holder.logId.setText("ID: " + dailyLog.getLogId());

        // Fetch and display nutrient summary
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<NutrientSummary> call = apiService.getLogSummary(dailyLog.getLogId(), "Bearer " + token);
        call.enqueue(new Callback<NutrientSummary>() {
            @Override
            public void onResponse(Call<NutrientSummary> call, Response<NutrientSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NutrientSummary summary = response.body();
                    holder.nutrientSummary.setText("Nutrient Summary: " + summary.toString());
                } else {
                    holder.nutrientSummary.setText("Nutrient Summary: Not available");
                }
            }

            @Override
            public void onFailure(Call<NutrientSummary> call, Throwable t) {
                holder.nutrientSummary.setText("Nutrient Summary: Error fetching");
            }
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(dailyLog));
    }

    @Override
    public int getItemCount() {
        return dailyLogs.size();
    }

    static class DailyLogViewHolder extends RecyclerView.ViewHolder {
        TextView date, mealType, nutrientSummary, logId;

        public DailyLogViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.daily_log_date);
            mealType = itemView.findViewById(R.id.daily_log_meal_type);
            nutrientSummary = itemView.findViewById(R.id.daily_log_nutrient_summary);
            logId = itemView.findViewById(R.id.daily_log_id);
        }
    }
}
