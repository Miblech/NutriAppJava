package com.example.nutriappjava.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutriappjava.R;

/**
 * Displays information about the application, including links to privacy policies or other relevant documents.
 * This fragment contains a clickable text view that opens a YouTube video when clicked.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView tvPrivacyPolicy = view.findViewById(R.id.tv_privacy_policy);

        tvPrivacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }else{
                showToast("Unable to open link.");
            }
        });
        return view;
    }

    /**
     * Shows a toast message to the user.
     *
     * @param message The message to display
     */
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}