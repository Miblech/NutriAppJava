package com.example.nutriappjava.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutriappjava.R;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private TextView dateTextView;
    private Button datePickerButton;

    private Button signUpButton;
    private String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dateTextView = findViewById(R.id.DateTextView);
        datePickerButton = findViewById(R.id.DatePickerButton);
        signUpButton = findViewById(R.id.buttonSignUp);


        signUpButton.setOnClickListener(v -> {});

        datePickerButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignUp.this,
                (view, year1, month1, dayOfMonth) -> {
                    String formattedDate = String.format("%d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                    selectedDate = formattedDate;
                    dateTextView.setText(formattedDate);
                    Log.d("SELECTED DATE ", formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}



