package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nutriappjava.adapters.FoodListAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodListAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerView = findViewById(R.id.recyclerViewFoodItems);
        foodAdapter = new FoodListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodAdapter);


        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        List<FoodItem> FoodItems = dbHelper.getAllMeals();
        foodAdapter.setFoodItems(FoodItems);
    }
}