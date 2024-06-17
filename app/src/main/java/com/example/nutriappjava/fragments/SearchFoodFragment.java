package com.example.nutriappjava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.FoodAdapter;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.viewmodels.SearchFoodViewModel;

import java.util.List;

public class SearchFoodFragment extends Fragment {

    private EditText searchInput;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private SearchFoodViewModel searchFoodViewModel;
    private OnFoodSelectedListener onFoodSelectedListener;

    public interface OnFoodSelectedListener {
        void onFoodSelected(Food food);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFoodSelectedListener) {
            onFoodSelectedListener = (OnFoodSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFoodSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_food, container, false);

        searchInput = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.button_search);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter();
        recyclerView.setAdapter(foodAdapter);

        searchFoodViewModel = new ViewModelProvider(this).get(SearchFoodViewModel.class);
        searchFoodViewModel.getFoodListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setFoodList(foods);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = searchInput.getText().toString();
                if (!description.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    searchFoodViewModel.searchFoodsByDescription(description);
                }
            }
        });

        foodAdapter.setOnFoodClickListener(new FoodAdapter.OnFoodClickListener() {
            @Override
            public void onFoodClick(Food food) {
                if (onFoodSelectedListener != null) {
                    onFoodSelectedListener.onFoodSelected(food);
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFoodSelectedListener = null;
    }
}
