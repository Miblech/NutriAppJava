package com.example.nutriappjava.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.LogFoodAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CreateLogActivity extends AppCompatActivity {

    Spinner spinner;
    Button addMealButton, submitLog;
    Resources resources;
    RecyclerView recyclerView;
    List<FoodItem> selectedItems = new ArrayList<>();

    private final ActivityResultLauncher<Intent> addFoodActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    FoodItem selectedFoodItem = result.getData().getParcelableExtra("foodItem");

                    if (selectedFoodItem != null) {
                        Log.d("CreateLogActivity", "Received Food item: " + selectedFoodItem.getName());
                        selectedItems.add(selectedFoodItem);
                        updateUI(selectedItems);
                    }
                }
            });

    private void updateUI(List<FoodItem> selectedItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LogFoodAdapter adapter = new LogFoodAdapter(selectedItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);

        resources = getResources();
        spinner = findViewById(R.id.spinnerMealCategory);
        recyclerView = findViewById(R.id.recyclerViewMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LogFoodAdapter adapterLog = new LogFoodAdapter(selectedItems);
        recyclerView.setAdapter(adapterLog);

        String[] categories = resources.getStringArray(R.array.meals_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addMealButton = findViewById(R.id.buttonAddMeal);
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateLogActivity.this, AddFoodActivity.class);
                addFoodActivityResultLauncher.launch(intent);
            }
        });

        submitLog = findViewById(R.id.buttonSubmitLog);
        submitLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateLogActivity.this, MainMenu.class));
            }
        });
    }
}
