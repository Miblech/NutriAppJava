package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.FoodSearchAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.util.ArrayList;
import java.util.List;

    public class MealsListFragment extends Fragment{

        public void setOnMealSelectedListener(View.OnClickListener onClickListener) {

        }

        public interface OnMealSelectedListener{
            void onMealSelected(FoodItem item);
        }


        private List<FoodItem> mealsList = new ArrayList<>();
        private FoodSearchAdapter mealAdapter;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_meals_list, container, false);

            DatabaseHelper db = new DatabaseHelper(getContext());
            mealsList = db.getAllFoods();

            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMeals);
            mealAdapter = new FoodSearchAdapter(mealsList, requireContext(), new FoodSearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FoodItem item) {
                    // Handle the item click here
                    Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(mealAdapter);

            return view;
        }
    }