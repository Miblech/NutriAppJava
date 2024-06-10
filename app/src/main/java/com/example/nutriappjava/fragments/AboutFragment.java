package com.example.nutriappjava.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutriappjava.R;

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
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                startActivity(intent);
            }
        });
        return view;
    }
}