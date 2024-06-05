package com.example.nutriappjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button buttonLogin;
    private TextView signUpButton;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        usernameEditText = findViewById(R.id.usernameOrEmailInput);
        passwordEditText = findViewById(R.id.passwordInput);
        buttonLogin = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.createAccountText);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attempLogin();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void attempLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String usernameOrEmail = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(usernameOrEmail) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] selectionArgs = {usernameOrEmail};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",                     // Table name
                null,                        // Columns to select
                "user_username=? OR user_email=?", // Selection clause
                selectionArgs,               // Selection arguments
                null,                        // Group by
                null,                        // Having
                null                         // Order by
        );

        if (cursor!= null && cursor.moveToFirst()) {
            @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex("user_password"));
            @SuppressLint("Range") String storedSalt = cursor.getString(cursor.getColumnIndex("user_salt"));

            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("user_email"));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("user_username"));

            String HashedPassword = SecurityUtils.hashPassword(password, storedSalt);

            if (HashedPassword.equals(storedPassword)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.apply();

                Intent intent = new Intent(Login.this, MainMenu.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }

        // List all users in the database
        listAllUsers();

        if (cursor!= null) {
            cursor.close();
        }
    }

    private void listAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",                     // Table name
                null,                        // Columns to select (null means select all columns)
                null,                        // Selection clause (null means no filter)
                null,                        // Selection arguments (not used since we're not filtering)
                null,                        // Group by (not applicable here)
                null,                        // Having (not applicable here)
                null                         // Order by (null means no order)
        );

        if (cursor!= null) {
            while (cursor.moveToNext()) {
                // Extracting user details
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("user_id")); // Assuming 'id' is the primary key
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("user_username"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("user_email"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("user_password"));
                @SuppressLint("Range") String salt = cursor.getString(cursor.getColumnIndex("user_salt"));

                // Displaying user details (for demonstration purposes)
                Log.d("UserDetails", "ID: " + id + ", Username: " + username + ", Email: " + email + ", Password: " + password + ", Salt: " + salt);
            }
            cursor.close();
        }
    }

}
