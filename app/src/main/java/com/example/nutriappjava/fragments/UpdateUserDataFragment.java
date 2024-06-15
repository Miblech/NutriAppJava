package com.example.nutriappjava.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nutriappjava.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.User;
import com.example.nutriappjava.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserDataFragment extends Fragment {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etHeight;
    private EditText etWeight;
    private RadioGroup radioGroupGender;
    private EditText etDob;
    private Button buttonSaveChanges;
    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");

        buttonSaveChanges.setOnClickListener(v -> updateUserData(token));

        return view;
    }

    private void updateUserData(String token) {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        double height = Double.parseDouble(etHeight.getText().toString());
        double weight = Double.parseDouble(etWeight.getText().toString());
        int gender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? 1 : 0;
        String dob = etDob.getText().toString();

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
}
