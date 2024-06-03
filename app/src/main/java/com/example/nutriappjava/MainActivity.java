package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private NutritionInfoFetcher nutritionInfoFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        nutritionInfoFetcher = new NutritionInfoFetcher(dbHelper, MainActivity.this);

        int numberRows = dbHelper.countRows(db, "users");

        if (numberRows < 1) {
            // Create new user
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        }

        //System.out.println("USERS TABLE");
        //dbHelper.logTableStructure(db, "users");
        //System.out.println("food_diary_cal_eaten TABLE");
        //dbHelper.logTableStructure(db, "food_diary_cal_eaten");
        //System.out.println("food_diary TABLE");
        //dbHelper.logTableStructure(db, "food_diary");
        //System.out.println("Food TABLE");
        //dbHelper.logTableStructure(db, "food");
        //System.out.println("FOOD DAILY INTAKE TABLE");
        //dbHelper.logTableStructure(db, "daily_activity_and_intake");
        //System.out.println("Activities TABLE");
        //dbHelper.logTableStructure(db, "activities");

        // Clear food table
        dbHelper.clearFoodTable();

    }

    private void fetchAndStoreNutritionInfo(String query) {
        nutritionInfoFetcher.execute(query);
    }
}