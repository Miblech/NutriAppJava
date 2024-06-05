package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nutriappjava.classes.foodItem;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
        List<foodItem> foodItems = dbHelper.getAllFoods();

        for (foodItem foodItem : foodItems) {
            System.out.println(", Name: " + foodItem.getName() +
                    ", Calories: " + foodItem.getCalories() +
                    ", Protein: " + foodItem.getProteinG() +
                    ", Carbohydrates: " + foodItem.getCarbohydratesTotalG() +
                    ", Fat: " + foodItem.getFatSaturatedG()
            );
        }
    }
}