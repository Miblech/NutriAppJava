package com.example.nutriappjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nutriappjava.R;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            // Create an Intent that will start the second activity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
}