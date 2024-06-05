package com.example.nutriappjava.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutriappjava.R;
import com.example.nutriappjava.SportAdapter;
import com.example.nutriappjava.SportInfoFetcher;

public class SportFragment extends Fragment {

    private SportAdapter sportAdapter;
    private RecyclerView recyclerView;

    private EditText sportSearchInput;
    private Button sportSearchButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        sportAdapter = new SportAdapter( getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sportAdapter);


        sportSearchInput = view.findViewById(R.id.sport_search_input);
        sportSearchButton = view.findViewById(R.id.sport_search_button);
        sportSearchButton.setOnClickListener(v -> {
            String query = sportSearchInput.getText().toString();
            System.out.println("QUERY SEARCH : " + query);
            if (!query.isEmpty()) {
                SportInfoFetcher sportInfoFetcher = new SportInfoFetcher(sportAdapter);
                sportInfoFetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
            } else {
                Toast.makeText(getContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}