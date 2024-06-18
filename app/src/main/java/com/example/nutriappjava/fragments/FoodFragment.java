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

/**
 * Displays a list of foods categorized by various categories.
 * This fragment uses a {@link FoodViewModel} to fetch and manage the data related to foods and categories.
 * It observes changes in the data and updates the UI accordingly, showing a progress bar during data loading.
 */
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

    /**
     * Handles category click events.
     * Loads foods belonging to the clicked category and updates the UI.
     * @see FoodViewModel#loadFoodsByCategory(String)
     * @param category The category clicked by the user
     */
    @Override
    public void onCategoryClick(String category) {
        progressBar.setVisibility(View.VISIBLE);
        foodViewModel.loadFoodsByCategory(category);
        recyclerView.setAdapter(foodAdapter);
    }

    /**
     * Handles individual food item click events.
     * Navigates to a detail screen for the clicked food item.
     * @param food The food item clicked by the user
     */
    @Override
    public void onFoodClick(Food food) {
        FoodDetailFragment foodDetailFragment = FoodDetailFragment.newInstance(food.getId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
