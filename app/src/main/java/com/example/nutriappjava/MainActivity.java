package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
        nutritionInfoFetcher = new NutritionInfoFetcher(dbHelper, MainActivity.this);

        dbHelper.clearFoodTable();

        String initialQuery = "brisket";
        fetchAndStoreNutritionInfo(initialQuery);
    }

    private void fetchAndStoreNutritionInfo(String query) {
        nutritionInfoFetcher.execute(query);
    }
}