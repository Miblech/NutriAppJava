package com.example.nutriappjava.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.User;
import com.example.nutriappjava.services.ApiService;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private TextView dateTextView;
    private Button datePickerButton;
    private Button signUpButton;
    private EditText editTextUsername, editTextPassword, editTextUserEmail, editTextUserHeight, editTextUserWeight;
    private RadioGroup radioGroupGender;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dateTextView = findViewById(R.id.DateTextView);
        datePickerButton = findViewById(R.id.DatePickerButton);
        signUpButton = findViewById(R.id.buttonSignUp);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        editTextUserHeight = findViewById(R.id.editTextUserHeight);
        editTextUserWeight = findViewById(R.id.editTextUserWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        datePickerButton.setOnClickListener(v -> showDatePickerDialog());
        signUpButton.setOnClickListener(v -> performSignUp());
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

    private void performSignUp() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextUserEmail.getText().toString().trim();
        String heightStr = editTextUserHeight.getText().toString().trim();
        String weightStr = editTextUserWeight.getText().toString().trim();
        int gender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? 0 : 1;

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double height;
        double weight;
        try {
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Height and Weight must be valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            User user = new User(username, email, password, height, weight, gender, selectedDate);
            registerUser(user);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(User user) {
        ApiService apiService = ApiClient.getRetrofitInstance(false).create(ApiService.class);
        Call<User> call = apiService.registerUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUp.this, "Registration failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
