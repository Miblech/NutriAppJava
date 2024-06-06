package com.example.nutriappjava.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;

public class MealsViewHolder extends RecyclerView.ViewHolder {
    TextView mealNameTextView;
    TextView mealQuantityTextView;

    public MealsViewHolder(@NonNull View itemView) {
        super(itemView);
        mealNameTextView = itemView.findViewById(R.id.meal_name_text_view);
        mealQuantityTextView = itemView.findViewById(R.id.meal_calories_text_view);
    }
}
