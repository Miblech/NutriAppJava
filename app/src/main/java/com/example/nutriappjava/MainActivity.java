package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
        /*
        System.out.println("FOOD TABLE");
        dbHelper.logTableStructure(db, "food");
        System.out.println("FOOD DIARY TABLE");
        dbHelper.logTableStructure(db, "food_diary");
        System.out.println("FOOD DIARY CAL EATEN TABLE");
        dbHelper.logTableStructure(db, "food_diary_cal_eaten");*/
        dbHelper.clearFoodTable();

        String initialQuery = "brisket and tomato";
        fetchAndStoreNutritionInfo(initialQuery);

    }

    private void fetchAndStoreNutritionInfo(String query) {
        nutritionInfoFetcher.execute(query);
    }
}