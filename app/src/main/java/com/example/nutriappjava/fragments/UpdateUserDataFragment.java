package com.example.nutriappjava.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.activities.Login;
import com.example.nutriappjava.activities.MainMenu;
import com.example.nutriappjava.entities.User;
import com.example.nutriappjava.services.ApiService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserDataFragment extends Fragment {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etHeight;
    private EditText etWeight;
    private RadioGroup radioGroupGender;
    private TextView etDob;
    private Button buttonSaveChanges;
    private Button datePickerButton;
    private SharedPreferences sharedPreferences;

    private String selectedDate;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user_data, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etHeight = view.findViewById(R.id.et_height);
        etWeight = view.findViewById(R.id.et_weight);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        etDob = view.findViewById(R.id.DateTextView);
        buttonSaveChanges = view.findViewById(R.id.button_save_changes);
        datePickerButton = view.findViewById(R.id.DatePickerButton);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");

        fetchCurrentUserData(token);

        buttonSaveChanges.setOnClickListener(v -> updateUserData(token));
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void fetchCurrentUserData(String token) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<User> call = apiService.getCurrentUser("Bearer " + token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentUser = response.body();
                    populateUserData(currentUser);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUserData(User user) {
        etUsername.setText(user.getUserUsername());
        etEmail.setText(user.getUserEmail());
        etHeight.setText(String.valueOf(user.getUserHeight()));
        etWeight.setText(String.valueOf(user.getUserWeight()));
        if (user.getUserGender() == 1) {
            radioGroupGender.check(R.id.radioButtonMale);
        } else {
            radioGroupGender.check(R.id.radioButtonFemale);
        }
        etDob.setText(user.getUserDob());
    }

    private void updateUserData(String token) {
        String username = etUsername.getText().toString().isEmpty() ? currentUser.getUserUsername() : etUsername.getText().toString();
        String email = etEmail.getText().toString().isEmpty() ? currentUser.getUserEmail() : etEmail.getText().toString();
        double height = etHeight.getText().toString().isEmpty() ? currentUser.getUserHeight() : Double.parseDouble(etHeight.getText().toString());
        double weight = etWeight.getText().toString().isEmpty() ? currentUser.getUserWeight() : Double.parseDouble(etWeight.getText().toString());
        int gender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? 1 : 0;
        String dob = etDob.getText().toString().isEmpty() ? currentUser.getUserDob() : etDob.getText().toString();

        User updatedUser = new User();
        updatedUser.setUserUsername(username);
        updatedUser.setUserEmail(email);
        updatedUser.setUserHeight(height);
        updatedUser.setUserWeight(weight);
        updatedUser.setUserGender(gender);
        updatedUser.setUserDob(dob);

        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<User> call = apiService.updateCurrentUser("Bearer " + token, updatedUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(getActivity(), "User data updated successfully", Toast.LENGTH_SHORT).show();

                    handleLogout();
                } else {
                    Toast.makeText(getActivity(), "Failed to update user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view, year1, month1, dayOfMonth) -> {
                    String formattedDate = String.format("%d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                    selectedDate = formattedDate;
                    etDob.setText(formattedDate);
                    Log.d("SELECTED DATE", formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
