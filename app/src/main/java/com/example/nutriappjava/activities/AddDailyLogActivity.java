package com.example.nutriappjava.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.R;
import com.example.nutriappjava.adapters.MealItemAdapter;
import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.entities.User;
import com.example.nutriappjava.fragments.SearchFoodFragment;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyLogActivity extends AppCompatActivity implements SearchFoodFragment.OnFoodSelectedListener {

    private Spinner mealTypeSpinner;
    private TextView dateTextView;
    private Button datePickerButton;
    private Button searchFoodButton;
    private Button saveLogButton;
    private RecyclerView foodRecyclerView;
    private DailyLog dailyLog;
    private String selectedDate;
    private MealItemAdapter mealItemAdapter;
    private String token;
    private List<Food> selectedFoods;
    private List<Float> foodWeights;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_log);

        mealTypeSpinner = findViewById(R.id.meal_type_spinner);
        dateTextView = findViewById(R.id.DateTextView);
        datePickerButton = findViewById(R.id.DatePickerButton);
        searchFoodButton = findViewById(R.id.button_search_food);
        saveLogButton = findViewById(R.id.button_save_log);
        foodRecyclerView = findViewById(R.id.food_recycler_view);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        token = sharedPreferences.getString("token", "");

        dailyLog = new DailyLog();
        User user = new User();
        user.setUserId(userId);
        user.setUserUsername(username);
        user.setUserEmail(email);
        dailyLog.setUser(user);

        selectedFoods = new ArrayList<>();
        foodWeights = new ArrayList<>();

        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealItemAdapter = new MealItemAdapter(selectedFoods, foodWeights);
        foodRecyclerView.setAdapter(mealItemAdapter);
        foodRecyclerView.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meals_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(adapter);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        searchFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchFoodFragment();
            }
        });

        saveLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDailyLog();
            }
        });

        mealItemAdapter.setOnItemClickListener(new MealItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showDeleteFoodDialog(position);
            }
        });

        setDefaultDate();
    }

    private void showDeleteFoodDialog(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_delete);

        Button yesButton = dialog.findViewById(R.id.button_yes);
        Button noButton = dialog.findViewById(R.id.button_no);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFoods.remove(position);
                foodWeights.remove(position);
                mealItemAdapter.updateMealItems(selectedFoods, foodWeights);
                dialog.dismiss();
                updateRecyclerView();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        selectedDate = String.format("%d-%02d-%02d", year, month + 1, day);
        dateTextView.setText(selectedDate);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddDailyLogActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                        selectedDate = formattedDate;
                        dateTextView.setText(formattedDate);
                        Log.d("SELECTED DATE", formattedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void openSearchFoodFragment() {
        Fragment searchFoodFragment = new SearchFoodFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, searchFoodFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFoodSelected(Food food) {
        showAddFoodDialog(food);
    }

    private void showAddFoodDialog(Food food) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_food);

        TextView foodDescription = dialog.findViewById(R.id.food_description);
        EditText foodWeightInput = dialog.findViewById(R.id.food_weight_input);
        Button addFoodButton = dialog.findViewById(R.id.button_add_food);

        foodDescription.setText(food.getDescription());

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = foodWeightInput.getText().toString();
                if (!weightStr.isEmpty()) {
                    float weight = Float.parseFloat(weightStr);
                    selectedFoods.add(food);
                    foodWeights.add(weight);
                    dialog.dismiss();
                    updateRecyclerView();
                } else {
                    Toast.makeText(AddDailyLogActivity.this, "Please enter a weight", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void updateRecyclerView() {
        if (!selectedFoods.isEmpty()) {
            foodRecyclerView.setVisibility(View.VISIBLE);
            mealItemAdapter.updateMealItems(selectedFoods, foodWeights);
        }
    }

    private void saveDailyLog() {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);

        dailyLog.setDate(selectedDate);
        dailyLog.setMealType(mealTypeSpinner.getSelectedItem().toString());

        Call<DailyLog> call = apiService.createLog(dailyLog, "Bearer " + token);
        call.enqueue(new Callback<DailyLog>() {
            @Override
            public void onResponse(Call<DailyLog> call, Response<DailyLog> response) {
                if (response.isSuccessful() && response.body() != null) {
                    long dailyLogId = response.body().getLogId();
                    Log.d("AddDailyLogActivity", "Daily log created with ID: " + dailyLogId);
                    addFoodsToDailyLog(dailyLogId);
                } else {
                    try {
                        Log.e("AddDailyLogActivity", "Failed to save daily log: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("AddDailyLogActivity", "Failed to save daily log: " + response.message());
                    }
                    Toast.makeText(AddDailyLogActivity.this, "Failed to save daily log", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyLog> call, Throwable t) {
                Log.e("AddDailyLogActivity", "Error: " + t.getMessage());
                Toast.makeText(AddDailyLogActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFoodsToDailyLog(long dailyLogId) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        for (int i = 0; i < selectedFoods.size(); i++) {
            Food food = selectedFoods.get(i);
            Float weight = foodWeights.get(i);
            Call<DailyLog> call = apiService.addFoodToDailyLog(dailyLogId, food.getId(), weight);
            call.enqueue(new Callback<DailyLog>() {
                @Override
                public void onResponse(Call<DailyLog> call, Response<DailyLog> response) {
                    if (response.isSuccessful()) {
                        Log.d("AddDailyLogActivity", "Food added to daily log");
                    } else {
                        try {
                            Log.e("AddDailyLogActivity", "Failed to add food to daily log: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("AddDailyLogActivity", "Failed to add food to daily log: " + response.message());
                        }
                        Toast.makeText(AddDailyLogActivity.this, "Failed to add food to daily log", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DailyLog> call, Throwable t) {
                    Log.e("AddDailyLogActivity", "Error: " + t.getMessage());
                    Toast.makeText(AddDailyLogActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        finish();
    }
}
