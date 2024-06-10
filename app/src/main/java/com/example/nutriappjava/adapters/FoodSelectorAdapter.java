package com.example.nutriappjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;
import java.util.List;

public class FoodSelectorAdapter extends RecyclerView.Adapter<FoodSelectorAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItem> foodItemList;

    private OnItemClickListener listener;


    public FoodSelectorAdapter(Context context, List<FoodItem> foodItemList, OnItemClickListener listener) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem currentItem = foodItemList.get(position);
        holder.name.setText(currentItem.getName());
        holder.servingSize.setText(String.valueOf(currentItem.getServingSizeG()) + " g");

        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentItem));
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, servingSize;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.meal_name_text_view);
            servingSize = itemView.findViewById(R.id.meal_serving_size_text_view);


        }
    }

    public interface OnItemClickListener {

        void onItemClick(FoodItem foodItem);
    }
}