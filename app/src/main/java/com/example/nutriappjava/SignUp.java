package com.example.nutriappjava;

import static com.example.nutriappjava.SecurityUtils.hashPassword;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutriappjava.classes.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private TextView dateTextView;
    private Button datePickerButton;

    private Button signUpButton;
    private String selectedDate;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dateTextView = findViewById(R.id.DateTextView);
        datePickerButton = findViewById(R.id.DatePickerButton);
        signUpButton = findViewById(R.id.buttonSignUp);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUpUser();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignUp.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                        selectedDate = formattedDate;
                        dateTextView.setText(formattedDate);
                        Log.d("SELECTED DATE ", formattedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void signUpUser() throws NoSuchAlgorithmException, InvalidKeySpecException {

        try {

            EditText editTextPassword = findViewById(R.id.editTextPassword);
            EditText editTextUsername = findViewById(R.id.editTextUsername);
            EditText editTextUserEmail = findViewById(R.id.editTextUserEmail);
            TextView editTextDateOfBirth = findViewById(R.id.DateTextView);
            EditText editTextUserHeight = findViewById(R.id.editTextUserHeight);
            EditText editTextUserWeight = findViewById(R.id.editTextUserWeight);
            RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
            RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
            RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
            String userPassword = editTextPassword.getText().toString();


            System.out.println("Introducing New User...");

            String userUsername = editTextUsername.getText().toString();
            System.out.println("Username: " + userUsername);
            String userEmail = editTextUserEmail.getText().toString();
            System.out.println("Email: " + userEmail);
            String userSalt = SecurityUtils.generateSalt(16);
            System.out.println("Salt : " + userSalt.toString());
            String userHashPassword = hashPassword(userPassword, userSalt);
            System.out.println("Password Hashed : " + userHashPassword.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date userDateOfBirth;
            try {
                userDateOfBirth = dateFormat.parse(editTextDateOfBirth.getText().toString());
                System.out.println("Date of birth " + userDateOfBirth.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println("Date of birth " + editTextDateOfBirth.getText().toString());
            int userGender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? 0 : 1;
            System.out.println(userGender);
            double userHeight = Double.parseDouble(editTextUserHeight.getText().toString());
            System.out.println("Height: " + userHeight);
            double userWeight = Double.parseDouble(editTextUserWeight.getText().toString());
            System.out.println("Weight: " + userWeight);


            User user = new User(userUsername, userEmail, userSalt, userHashPassword, userDateOfBirth, userGender, userHeight, userWeight);

            insertUserIntoDatabase(user);

            Toast.makeText(this, "User created successfully!", Toast.LENGTH_LONG).show();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred during signup. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void insertUserIntoDatabase(User user){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_username", user.getUserUsername());
        values.put("user_email", user.getUserEmail());
        values.put("user_salt", user.getUserSalt());
        values.put("user_password", user.getUserHashPassword());
        values.put("user_dob", user.getUserDob().getTime());
        values.put("user_gender", user.getUserGender());
        values.put("user_height", user.getUserHeight());
        values.put("user_weight", user.getUserWeight());
        long newRowId = db.insert("users", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show();
            System.out.println("User username : " + user.getUserUsername());
            System.out.println("User email : " + user.getUserEmail());
            System.out.println("User password : " + user.getUserHashPassword());
            System.out.println("User salt : " + user.getUserSalt());
            System.out.println("User dob : " + user.getUserDob());
            System.out.println("User gender : " + user.getUserGender());
            System.out.println("User height : " + user.getUserHeight());
            System.out.println("User weight : " + user.getUserWeight());

            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Failed to insert user", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}



