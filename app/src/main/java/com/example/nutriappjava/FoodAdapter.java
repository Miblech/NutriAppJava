package com.example.nutriappjava;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.classes.Fooditem;
import com.example.nutriappjava.fragments.FoodDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<Fooditem> foodItemList; // Declare foodItemList
    private Context context;

    public FoodAdapter(Context context) {
        this.context = context;
        this.foodItemList = new ArrayList<>();
    }

    public FoodAdapter(List<Fooditem> foodItemList, Context context) {
        this.foodItemList = foodItemList!= null? new ArrayList<>(foodItemList) : new ArrayList<>(); // Use provided list if not null, otherwise initialize with an empty list
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fooditem foodItem = foodItemList.get(position);
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

        public void bind(Fooditem foodItem) {
            Log.d("FoodAdapter", "Name: " + foodItem.getName());
            Log.d("FoodAdapter", "Calories: " + foodItem.getCalories());
            Log.d("FoodAdapter", "Serving Size in grams : " + foodItem.getServingSizeG());
            Log.d("FoodAdapter", "Fat Total: " + foodItem.getFatTotalG());
            Log.d("FoodAdapter", "Fat Saturated: " + foodItem.getFatSaturatedG());
            Log.d("FoodAdapter", "Protein: " + foodItem.getProteinG());
            Log.d("FoodAdapter", "Sodium: " + foodItem.getSodiumMg());
            Log.d("FoodAdapter", "Potassium: " + foodItem.getPotassiumMg());
            Log.d("FoodAdapter", "Cholesterol: " + foodItem.getCholesterolMg());
            Log.d("FoodAdapter", "Carbohydrates Total: " + foodItem.getCarbohydratesTotalG());
            Log.d("FoodAdapter", "Fiber: " + foodItem.getFiberG());
            Log.d("FoodAdapter", "Sugar: " + foodItem.getSugarG());
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
            Fooditem clickedFoodItem = foodItemList.get(getAdapterPosition());

            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            FoodDetailFragment newInstance = FoodDetailFragment.newInstance(clickedFoodItem);
            transaction.replace(R.id.fragment_container, newInstance);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void updateData(List<Fooditem> foodItems) {
        this.foodItemList.clear();
        if (foodItems!= null &&!foodItems.isEmpty()) {
            this.foodItemList.addAll(foodItems);
        }
        notifyDataSetChanged();
    }
}


