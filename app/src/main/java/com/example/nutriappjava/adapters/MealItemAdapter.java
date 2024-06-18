package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.Food;
import java.util.List;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.MealItemViewHolder> {

    private List<Food> foods;
    private List<Float> weights;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public MealItemAdapter(List<Food> foods, List<Float> weights) {
        this.foods = foods;
        this.weights = weights;
    }

    @NonNull
    @Override
    public MealItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealItemViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemViewHolder holder, int position) {
        Food food = foods.get(position);
        Float weight = weights.get(position);
        holder.foodDescription.setText(food.getDescription());
        holder.foodWeight.setText(String.format("%s g", weight));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class MealItemViewHolder extends RecyclerView.ViewHolder {
        public TextView foodDescription;
        public TextView foodWeight;

        public MealItemViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            foodDescription = itemView.findViewById(R.id.meal_name_text_view);
            foodWeight = itemView.findViewById(R.id.meal_serving_size_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void updateMealItems(List<Food> foods, List<Float> weights) {
        this.foods = foods;
        this.weights = weights;
        notifyDataSetChanged();
    }
}
