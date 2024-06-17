package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.CategoryAdapter;
import com.example.nutriappjava.adapters.FoodAdapter;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.viewmodels.FoodViewModel;
import java.util.List;

public class FoodFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener, FoodAdapter.OnFoodClickListener {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private CategoryAdapter categoryAdapter;
    private FoodViewModel foodViewModel;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter();
        foodAdapter.setOnFoodClickListener(this);
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnCategoryClickListener(this);
        recyclerView.setAdapter(foodAdapter);

        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);

        foodViewModel.getFoodListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setFoodList(foods);
                recyclerView.setAdapter(foodAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        foodViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                categoryAdapter.setCategoryList(categories);
                recyclerView.setAdapter(categoryAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        foodViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCategoryClick(String category) {
        progressBar.setVisibility(View.VISIBLE);
        foodViewModel.loadFoodsByCategory(category);
        recyclerView.setAdapter(foodAdapter);
    }

    @Override
    public void onFoodClick(Food food) {
        FoodDetailFragment foodDetailFragment = FoodDetailFragment.newInstance(food.getId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
