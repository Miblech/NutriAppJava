package com.example.nutriappjava.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.R;
import com.example.nutriappjava.SecurityUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ChangePasswordFragment extends Fragment {

    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private Button buttonChangePassword;

    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        dbHelper = new DatabaseHelper(getContext());
        sharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        buttonChangePassword = view.findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(v -> {
            try {
                changePassword();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Toast.makeText(getContext(), "Error processing password", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
            return;
        }

        String storedSalt = dbHelper.getStoredSalt(userId);
        String storedHashedPassword = dbHelper.getStoredHashedPassword(userId);

        String hashedCurrentPassword = SecurityUtils.hashPassword(currentPassword, storedSalt);
        if (!storedHashedPassword.equals(hashedCurrentPassword)) {
            Toast.makeText(getContext(), "Incorrect current password", Toast.LENGTH_LONG).show();
            return;
        }

        String salt = SecurityUtils.generateSalt(16);
        String hashedNewPassword = SecurityUtils.hashPassword(newPassword, salt);

        dbHelper.updatePassword(userId, hashedNewPassword, salt);

        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
    }
}