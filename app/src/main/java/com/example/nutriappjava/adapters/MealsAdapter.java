package com.example.nutriappjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsViewHolder> {
    private List<FoodItem> mealsList;
    private Context context;

    public MealsAdapter(List<FoodItem> mealsList, Context context) {
        this.mealsList = mealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        FoodItem meal = mealsList.get(position);
        holder.mealNameTextView.setText(meal.getName());
        holder.mealQuantityTextView.setText(String.valueOf(meal.getCalories()));
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}