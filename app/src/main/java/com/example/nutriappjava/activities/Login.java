package com.example.nutriappjava.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.JwtRequest;
import com.example.nutriappjava.entities.JwtResponse;
import com.example.nutriappjava.services.ApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText usernameEditText; // Input field for username or email
    private EditText passwordEditText; // Input field for password

    private Button buttonLogin; // Login button
    private TextView signUpButton; // Navigate to sign-up screen

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        usernameEditText = findViewById(R.id.usernameOrEmailInput);
        passwordEditText = findViewById(R.id.passwordInput);
        buttonLogin = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.createAccountText);

        // Set click listener for the login button
        buttonLogin.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString(); // Get username or email
            String password = passwordEditText.getText().toString(); // Get password

            // Validate input fields
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(username, password); // Perform login operation
            }
        });

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent); // Navigate to sign-up activity
        });
    }

    /**
     * Performs the login operation by sending a request to the backend.
     * @author nombr
     * @param username Username or email entered by the user
     * @param password Password entered by the user
     */
    private void performLogin(String username, String password) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        JwtRequest loginRequest = new JwtRequest(username.trim(), password.trim()); // Create login request

        // Make a POST request to authenticate the user
        Call<JwtResponse> call = apiService.authenticate(loginRequest);
        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    JwtResponse jwtResponse = response.body(); // Parse the response
                    String token = jwtResponse.getToken(); // Extract JWT token
                    Long userId = jwtResponse.getUserId(); // Extract user ID
                    String email = jwtResponse.getEmail(); // Extract email
                    String username = jwtResponse.getUsername(); // Extract username

                    // Store user details in shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("token", token);
                    editor.putLong("userId", userId);
                    editor.putString("email", email);
                    editor.apply();

                    // Navigate to the main menu activity
                    Intent intent = new Intent(Login.this, MainMenu.class);
                    startActivity(intent);
                    finish(); // Finish the login activity
                } else {
                    // Handle login failure
                    String errorMessage = "Login failed";
                    if (response.errorBody()!= null) {
                        try {
                            errorMessage = response.errorBody().string(); // Extract error message
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                // Display error message on failure
                Toast.makeText(Login.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}