package com.example.nutriappjava.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.nutriappjava.R;

public class SettingsFragment extends Fragment {

    private Button buttonChangePassword;
    private Button buttonPurgeLogs;
    private Button buttonDeleteUserAccount;
    private Button buttonUpdateUserData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        buttonChangePassword = view.findViewById(R.id.button_change_password);
        buttonPurgeLogs = view.findViewById(R.id.button_purge_logs);
        buttonDeleteUserAccount = view.findViewById(R.id.button_delete_user_account);
        buttonUpdateUserData = view.findViewById(R.id.button_update_user_data);

        buttonChangePassword.setOnClickListener(v -> {
            // TODO Handle change password
        });

        buttonPurgeLogs.setOnClickListener(v -> {
            // TODO Handle purge logs
        });

        buttonDeleteUserAccount.setOnClickListener(v -> {
            // TODO Handle delete user account
        });

        buttonUpdateUserData.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UpdateUserDataFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
