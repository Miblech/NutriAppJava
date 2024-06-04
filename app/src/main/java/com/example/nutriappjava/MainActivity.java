package com.example.nutriappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

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

        dbHelper.clearTables(db);



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