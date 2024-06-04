package com.example.nutriappjava;

import static com.example.nutriappjava.SecurityUtils.hashPassword;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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

            EditText editTextFullName = findViewById(R.id.editTextFullName);
            EditText editTextPassword = findViewById(R.id.editTextPassword);
            EditText editTextUsername = findViewById(R.id.editTextUsername);
            EditText editTextUserEmail = findViewById(R.id.editTextUserEmail);
            EditText editTextUserHeight = findViewById(R.id.editTextUserHeight);
            EditText editTextUserWeight = findViewById(R.id.editTextUserWeight);
            EditText editTextUserTargetWeight = findViewById(R.id.editTextUserTargetWeight);
            RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
            RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
            RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);

            int userGender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? 0 : 1;

            double userHeight = Double.parseDouble(editTextUserHeight.getText().toString());
            double userWeight = Double.parseDouble(editTextUserWeight.getText().toString());
            double userTargetWeight = Double.parseDouble(editTextUserTargetWeight.getText().toString());

            String userName = editTextFullName.getText().toString();
            String userPassword = editTextPassword.getText().toString();
            String userSalt = SecurityUtils.generateSalt(16);
            String userHashPassword = hashPassword(userPassword, userSalt);
            String userEmail = editTextUserEmail.getText().toString();
            String userUsername = editTextUsername.getText().toString();
            String userDateOfBirth = selectedDate;

            User user = new User(userName, userHashPassword, userSalt, userEmail, userUsername, userDateOfBirth, userHeight, userWeight, userTargetWeight, userGender);

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
        values.put("user_name", user.getUserName());
        values.put("user_password", user.getUserHashPassword());
        values.put("user_salt", user.getUserSalt());
        values.put("user_email", user.getUserEmail());
        values.put("user_dob", user.getUserDob());
        values.put("user_gender", user.getUserGender());
        values.put("user_height", user.getUserHeight());
        values.put("user_weight", user.getUserWeight());
        values.put("user_target_weight", user.getUserTargetWeight());
        values.put("user_last_seen", user.getUserLastSeen());
        long newRowId = db.insert("users", null, values);

        db.close();
    }
}



