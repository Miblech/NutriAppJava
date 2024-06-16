package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.FoodAdapter;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.FoodViewModel;

import java.util.List;

public class FoodFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private FoodViewModel foodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        foodAdapter = new FoodAdapter();
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getFoodListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setFoodList(foods);
            }
        });

        foodViewModel.loadFoods();

        return view;
    }
}