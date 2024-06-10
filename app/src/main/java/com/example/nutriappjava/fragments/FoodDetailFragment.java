package com.example.nutriappjava.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;

public class FoodDetailFragment extends Fragment {
    private FoodItem foodItem;
    private TextView nameTextView, caloriesTextView, servingSizeTextView, fatTotalTextView, fatSaturatedTextView, proteinTextView, sodiumTextView, potassiumTextView, cholesterolTextView, carbohydratesTotalTextView, fiberTextView, sugarTextView;
    private Button saveButton;



    public FoodDetailFragment() {
    }

    public static FoodDetailFragment newInstance(FoodItem foodItem) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("FoodItem", foodItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        nameTextView = view.findViewById(R.id.detail_nameTextView);
        caloriesTextView = view.findViewById(R.id.detail_caloriesTextView);
        servingSizeTextView = view.findViewById(R.id.detail_servingSizeTextView);
        fatTotalTextView = view.findViewById(R.id.detail_fatTotalTextView);
        fatSaturatedTextView = view.findViewById(R.id.detail_fatSaturatedTextView);
        proteinTextView = view.findViewById(R.id.detail_proteinTextView);
        sodiumTextView = view.findViewById(R.id.detail_sodiumTextView);
        potassiumTextView = view.findViewById(R.id.detail_potassiumTextView);
        cholesterolTextView = view.findViewById(R.id.detail_cholesterolTextView);
        carbohydratesTotalTextView = view.findViewById(R.id.detail_carbohydratesTotalTextView);
        fiberTextView = view.findViewById(R.id.detail_fiberTextView);
        sugarTextView = view.findViewById(R.id.detail_sugarTextView);
        saveButton = view.findViewById(R.id.detail_food_save_button);


        if (getArguments()!= null) {
            foodItem = getArguments().getParcelable("FoodItem");

            setupUI();
        }


        saveButton.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getContext());
            long newId = db.insertFoodItem(foodItem);
            foodItem.setId((int) newId);
            Toast.makeText(getContext(), "Food item saved", Toast.LENGTH_SHORT).show();
            db.close();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        return view;
    }

    private void setupUI() {

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


}
