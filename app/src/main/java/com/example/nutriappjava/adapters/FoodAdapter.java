package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList = new ArrayList<>();
    private OnFoodClickListener onFoodClickListener;

    public interface OnFoodClickListener {
        void onFoodClick(Food food);
    }

    public void setOnFoodClickListener(OnFoodClickListener listener) {
        this.onFoodClickListener = listener;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.description.setText(food.getDescription());
        holder.category.setText("Category: " + food.getCategory());
        holder.protein.setText("Protein: " + food.getProtein() + "g");
        holder.carbohydrate.setText("Carbohydrate: " + food.getCarbohydrate() + "g");
        holder.fat.setText("Fat: " + food.getTotalLipid() + "g");
        holder.calories.setText("Calories: " + calculateCalories(food) + " kcal");

        holder.itemView.setOnClickListener(v -> {
            if (onFoodClickListener != null) {
                onFoodClickListener.onFoodClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private String calculateCalories(Food food) {
        float calories = (food.getProtein() * 4) + (food.getCarbohydrate() * 4) + (food.getTotalLipid() * 9);
        return String.valueOf(calories);
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView description, category, protein, carbohydrate, fat, calories;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.food_description);
            category = itemView.findViewById(R.id.food_category);
            protein = itemView.findViewById(R.id.food_protein);
            carbohydrate = itemView.findViewById(R.id.food_carbohydrate);
            fat = itemView.findViewById(R.id.food_fat);
            calories = itemView.findViewById(R.id.food_calories);
        }
    }
}
