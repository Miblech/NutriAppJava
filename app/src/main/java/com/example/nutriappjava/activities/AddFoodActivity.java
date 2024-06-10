package com.example.nutriappjava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.FoodSelectorAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodSelectorAdapter adapter;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_add_food);

        recyclerView = findViewById(R.id.recyclerViewSelectorMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FoodItem> foodItems = dbHelper.getAllFoods();
        adapter = new FoodSelectorAdapter(this, foodItems, new FoodSelectorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodItem foodItem) {
                Intent intent = new Intent();
                intent.putExtra("foodItem", foodItem);
                Log.d("AddFoodActivity", "Sending food item: " + foodItem.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}