package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.FoodAdapter;
import com.example.nutriappjava.NutritionInfoFetcher;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.Fooditem;
import java.util.List;

public class FoodFragment extends Fragment {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private Button searchButton;
    private DatabaseHelper dbHelper;
    private FoodAdapter foodAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        dbHelper = new DatabaseHelper(getContext());

        searchInput = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchButton = view.findViewById(R.id.search_button);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter(null, getContext());
        recyclerView.setAdapter(foodAdapter);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            if (!query.isEmpty()) {

                NutritionInfoFetcher nutritionInfoFetcher = new NutritionInfoFetcher(foodAdapter);
                nutritionInfoFetcher.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, query);
            } else {
                Toast.makeText(getContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}