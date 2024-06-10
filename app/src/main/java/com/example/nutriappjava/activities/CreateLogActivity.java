package com.example.nutriappjava.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.LogFoodAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    private String getTimeFromTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePicker timePicker = findViewById(R.id.timePicker);
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(calendar.getTime());
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
        addMealButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateLogActivity.this, AddFoodActivity.class);
            addFoodActivityResultLauncher.launch(intent);
        });

        submitLog = findViewById(R.id.buttonSubmitLog);
        submitLog.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

            int userId = sharedPreferences.getInt("userId", -1);

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());

            String selectedTime = getTimeFromTimePicker();

            String selectedCategory = spinner.getSelectedItem().toString();

            DatabaseHelper dbHelper = new DatabaseHelper(CreateLogActivity.this);

            long logId = dbHelper.insertDailyLog(userId, currentDate, selectedTime, selectedCategory);

            if (logId != -1) {
                for (FoodItem item : selectedItems) {
                    int foodItemId = item.getId();
                    System.out.println("Food Item with Name = " + item.getName() + " and ID = " + foodItemId);
                    dbHelper.insertMealItem((int) logId, foodItemId);
                    Log.d("CreateLogActivity", "Added meal item: " + item.getName());
                    Log.d("CreateLogActivity", "Added meal calories: " + item.getCalories());
                }
                printLog(selectedItems);
                dbHelper.printAllDailyLogsForUserAndDate(userId, currentDate);
                finish();
            } else {
                Log.e("CreateLogActivity", "Failed to insert log entry");
            }
        });
    }
    private void printLog(List<FoodItem> items) {
        for (FoodItem item : items) {
            Log.d("LogItem", "Name: " + item.getName() + ", Calories: " + item.getCalories());
        }
    }
}
