package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.DatabaseHelper;
import com.example.nutriappjava.adapters.FoodAdapter;
import com.example.nutriappjava.NutritionInfoFetcher;
import com.example.nutriappjava.R;

public class FoodFragment extends Fragment {

    private EditText searchInput, quantityInput;
    private RecyclerView recyclerView;
    private Button searchButton;
    private DatabaseHelper dbHelper;
    private FoodAdapter foodAdapter;

    private Spinner unitSpinner;
    private String defaultUnit = "g";

    private String quantity = "100";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        dbHelper = new DatabaseHelper(getContext());

        quantityInput = view.findViewById(R.id.quantity_input);
        searchInput = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchButton = view.findViewById(R.id.search_button);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter(null, getContext());
        recyclerView.setAdapter(foodAdapter);

        unitSpinner = view.findViewById(R.id.unit_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                defaultUnit = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();

            checkForQuantity();

            String query = quantityInput.getText().toString() + defaultUnit + " " + searchTerm;

            System.out.println(query);

            if (!query.isEmpty()) {
                NutritionInfoFetcher nutritionInfoFetcher = new NutritionInfoFetcher(foodAdapter);
                nutritionInfoFetcher.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, query);
            } else {
                Toast.makeText(getContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void checkForQuantity() {
        String quantityText = quantityInput.getText().toString();


        if (quantityText.trim().isEmpty() || quantityText.equals("0")) {

            quantity = "100";
        } else {
            quantity = quantityText;
        }
    }
}