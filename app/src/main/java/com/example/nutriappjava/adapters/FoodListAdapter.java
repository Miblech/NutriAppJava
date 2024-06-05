package com.example.nutriappjava.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {
    private List<FoodItem> FoodItems;

    public static class FoodListViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView caloriesTextView;

        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.food_list_food_name);
            caloriesTextView = itemView.findViewById(R.id.food_list_food_calories);
        }
    }

    public FoodListAdapter() {
        this.FoodItems = new ArrayList<>();
    }

    public void setFoodItems(List<FoodItem> FoodItems) {
        this.FoodItems.clear();
        this.FoodItems.addAll(FoodItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_add_log, parent, false);
        return new FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        FoodItem currentItem = FoodItems.get(position);
        if (holder.nameTextView!= null && holder.caloriesTextView!= null) {
            holder.nameTextView.setText(currentItem.getName());
            holder.caloriesTextView.setText(String.valueOf(currentItem.getCalories()));
        } else {
            Log.e("FoodListAdapter", "ViewHolder or TextView is null");
        }
    }

    @Override
    public int getItemCount() {
        return FoodItems.size();
    }
}