package com.example.nutriappjava.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;
import com.example.nutriappjava.fragments.AddLogsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodSearchAdapter extends RecyclerView.Adapter<FoodSearchAdapter.ViewHolder> {

    private List<FoodItem> foodItemList;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FoodItem item);
    }

    public FoodSearchAdapter(List<FoodItem> foodItemList, Context context, OnItemClickListener listener) {
        this.foodItemList = foodItemList!= null? new ArrayList<>(foodItemList) : new ArrayList<>();
        this.context = context;
        this.listener = listener; // Store the passed listener
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FoodItem foodItem = foodItemList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prepare to navigate to AddLogsFragment and pass the selected item
                Bundle args = new Bundle();
                args.putSerializable("selectedItem", (Serializable) foodItem);

                AddLogsFragment addLogsFragment = new AddLogsFragment();
                addLogsFragment.setArguments(args); // Pass the bundle to AddLogsFragment

                // Replace the current fragment with AddLogsFragment
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, addLogsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        holder.bind(foodItem);
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public Context getContext() {
        return context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView, caloriesTextView, servingSizeTextView, fatTotalTextView, fatSaturatedTextView, proteinTextView, sodiumTextView, potassiumTextView, cholesterolTextView, carbohydratesTotalTextView, fiberTextView, sugarTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            caloriesTextView = itemView.findViewById(R.id.caloriesTextView);
            servingSizeTextView = itemView.findViewById(R.id.servingSizeTextView);
            fatTotalTextView = itemView.findViewById(R.id.fatTotalTextView);
            fatSaturatedTextView = itemView.findViewById(R.id.fatSaturatedTextView);
            proteinTextView = itemView.findViewById(R.id.proteinTextView);
            sodiumTextView = itemView.findViewById(R.id.sodiumTextView);
            potassiumTextView = itemView.findViewById(R.id.potassiumTextView);
            cholesterolTextView = itemView.findViewById(R.id.cholesterolTextView);
            carbohydratesTotalTextView = itemView.findViewById(R.id.carbohydratesTotalTextView);
            fiberTextView = itemView.findViewById(R.id.fiberTextView);
            sugarTextView = itemView.findViewById(R.id.sugarTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(FoodItem foodItem) {
            Log.d("FoodSearchAdapter", "Name: " + foodItem.getName());
            Log.d("FoodSearchAdapter", "Calories: " + foodItem.getCalories());
            Log.d("FoodSearchAdapter", "Serving Size in grams : " + foodItem.getServingSizeG());
            Log.d("FoodSearchAdapter", "Fat Total: " + foodItem.getFatTotalG());
            Log.d("FoodSearchAdapter", "Fat Saturated: " + foodItem.getFatSaturatedG());
            Log.d("FoodSearchAdapter", "Protein: " + foodItem.getProteinG());
            Log.d("FoodSearchAdapter", "Sodium: " + foodItem.getSodiumMg());
            Log.d("FoodSearchAdapter", "Potassium: " + foodItem.getPotassiumMg());
            Log.d("FoodSearchAdapter", "Cholesterol: " + foodItem.getCholesterolMg());
            Log.d("FoodSearchAdapter", "Carbohydrates Total: " + foodItem.getCarbohydratesTotalG());
            Log.d("FoodSearchAdapter", "Fiber: " + foodItem.getFiberG());
            Log.d("FoodSearchAdapter", "Sugar: " + foodItem.getSugarG());
            nameTextView.setText(foodItem.getName());
            caloriesTextView.setText("Calories: " + String.valueOf(foodItem.getCalories()));
            servingSizeTextView.setText("Serving Size: " + String.valueOf(foodItem.getServingSizeG()));
            fatTotalTextView.setText("Fat Total: " + String.valueOf(foodItem.getFatTotalG()));
            fatSaturatedTextView.setText("Fat Saturated: " + String.valueOf(foodItem.getFatSaturatedG()));
            proteinTextView.setText("Protein: " + String.valueOf(foodItem.getProteinG()));
            sodiumTextView.setText("Sodium: " + String.valueOf(foodItem.getSodiumMg()));
            potassiumTextView.setText("Potassium: " + String.valueOf(foodItem.getPotassiumMg()));
            cholesterolTextView.setText("Cholesterol: " + String.valueOf(foodItem.getCholesterolMg()));
            carbohydratesTotalTextView.setText("Carbohydrates Total: " + String.valueOf(foodItem.getCarbohydratesTotalG()));
            fiberTextView.setText("Fiber: " + String.valueOf(foodItem.getFiberG()));
            sugarTextView.setText("Sugar: " + String.valueOf(foodItem.getSugarG()));
        }

        @Override
        public void onClick(View v) {
            FoodItem clickedFoodItem = foodItemList.get(getAdapterPosition());

        }
    }

    public void updateData(List<FoodItem> FoodItems) {
        this.foodItemList.clear();
        if (FoodItems != null &&!FoodItems.isEmpty()) {
            this.foodItemList.addAll(FoodItems);
        }
        notifyDataSetChanged();
    }
}