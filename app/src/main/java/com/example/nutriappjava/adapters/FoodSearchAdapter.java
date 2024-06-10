package com.example.nutriappjava.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;
import com.example.nutriappjava.fragments.FoodDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class FoodSearchAdapter extends RecyclerView.Adapter<FoodSearchAdapter.ViewHolder> {

    private List<FoodItem> foodItemList;
    private Context context;

    public FoodSearchAdapter(Context context) {
        this.context = context;
        this.foodItemList = new ArrayList<>();
    }

    public FoodSearchAdapter(List<FoodItem> foodItemList, Context context) {
        this.foodItemList = foodItemList!= null? new ArrayList<>(foodItemList) : new ArrayList<>();
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
        FoodItem foodItem = foodItemList.get(position);
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
            nameTextView.setText(foodItem.getName());
            String caloriesText = String.format("Calories: %s calories", foodItem.getCalories());
            String servingSizeText = String.format("Serving Size: %.2f g", foodItem.getServingSizeG());
            String fatTotalText = String.format("Fat Total: %.2f g", foodItem.getFatTotalG());
            String fatSaturatedText = String.format("Fat Saturated: %.2f g", foodItem.getFatSaturatedG());
            String proteinText = String.format("Protein: %.2f g", foodItem.getProteinG());
            String sodiumText = String.format("Sodium: %d mg", foodItem.getSodiumMg());
            String potassiumText = String.format("Potassium: %d mg", foodItem.getPotassiumMg());
            String cholesterolText = String.format("Cholesterol: %d mg", foodItem.getCholesterolMg());
            String carbohydratesTotalText = String.format("Carbohydrates Total: %.2f g", foodItem.getCarbohydratesTotalG());
            String fiberText = String.format("Fiber: %.2f g", foodItem.getFiberG());
            String sugarText = String.format("Sugar: %.2f g", foodItem.getSugarG());


            caloriesTextView.setText(caloriesText);
            servingSizeTextView.setText(servingSizeText);
            fatTotalTextView.setText(fatTotalText);
            fatSaturatedTextView.setText(fatSaturatedText);
            proteinTextView.setText(proteinText);
            sodiumTextView.setText(sodiumText);
            potassiumTextView.setText(potassiumText);
            cholesterolTextView.setText(cholesterolText);
            carbohydratesTotalTextView.setText(carbohydratesTotalText);
            fiberTextView.setText(fiberText);
            sugarTextView.setText(sugarText);
        }

        @Override
        public void onClick(View v) {
            FoodItem clickedFoodItem = foodItemList.get(getAdapterPosition());

            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            FoodDetailFragment newInstance = FoodDetailFragment.newInstance(clickedFoodItem);
            transaction.replace(R.id.fragment_container, newInstance);
            transaction.addToBackStack(null);
            transaction.commit();
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


