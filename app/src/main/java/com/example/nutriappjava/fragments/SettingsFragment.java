package com.example.nutriappjava.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.R;
import com.example.nutriappjava.activities.Login;
import com.example.nutriappjava.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Provides settings options for users, including changing their password, purging their logs, deleting their user account, or updating their user data.
 * This fragment interacts with a backend service to perform actions like deleting all user logs or deleting the user account.
 */
public class SettingsFragment extends Fragment {

    private Button buttonChangePassword;
    private Button buttonPurgeLogs;
    private Button buttonDeleteUserAccount;
    private Button buttonUpdateUserData;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);

        buttonChangePassword = view.findViewById(R.id.button_change_password);
        buttonPurgeLogs = view.findViewById(R.id.button_purge_logs);
        buttonDeleteUserAccount = view.findViewById(R.id.button_delete_user_account);
        buttonUpdateUserData = view.findViewById(R.id.button_update_user_data);

        buttonChangePassword.setOnClickListener(v -> openChangePasswordFragment());

        buttonPurgeLogs.setOnClickListener(v -> purgeLogs());

        buttonDeleteUserAccount.setOnClickListener(v -> deleteUserAccount());

        buttonUpdateUserData.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UpdateUserDataFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    /**
     * Opens the Change Password fragment.
     * @see ChangePasswordFragment
     */
    private void openChangePasswordFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ChangePasswordFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Deletes all user logs from the server.
     * @see ApiService#deleteAllUserLogs(String)
     */
    private void purgeLogs() {
        String token = sharedPreferences.getString("token", "token");

        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<Void> call = apiService.deleteAllUserLogs("Bearer " + token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "All logs deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to delete logs: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Deletes the current user account from the server.
     * @see ApiService#deleteCurrentUser(String)
     */
    private void deleteUserAccount() {
        String token = sharedPreferences.getString("token", "token");

        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<Void> call = apiService.deleteCurrentUser("Bearer " + token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "User account deleted successfully", Toast.LENGTH_SHORT).show();
                    redirectToLogin();
                } else {
                    Toast.makeText(getActivity(), "Failed to delete user account: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Redirects the user to the login screen.
     * Clears the shared preferences and starts the login activity.
     */
    private void redirectToLogin() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();
    }
}
