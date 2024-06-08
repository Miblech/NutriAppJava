package com.example.nutriappjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.classes.FoodItem;

import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (!db.isOpen()){
            db = dbHelper.getReadableDatabase();
        }


        listAllFoodsAndPrint();
        dbHelper.getFoodItem(4);
        //dbHelper.clearTables(db);



        int numberRows = dbHelper.countRows(db, "users");

        if (numberRows < 1) {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        db.close();

    }

    private void listAllFoodsAndPrint() {
        dbHelper = new DatabaseHelper(this);
        List<FoodItem> FoodItems = dbHelper.getAllFoods();

        for (FoodItem foodItem : FoodItems) {
            System.out.println(", Name: " + foodItem.getName() +
                    ", Calories: " + foodItem.getCalories() +
                    ", Protein: " + foodItem.getProteinG() +
                    ", Carbohydrates: " + foodItem.getCarbohydratesTotalG() +
                    ", Fat: " + foodItem.getFatSaturatedG()
            );
        }
    }
}