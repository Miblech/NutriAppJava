package com.example.nutriappjava.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutriappjava.R;
import com.example.nutriappjava.SharedViewModel;
import com.example.nutriappjava.adapters.MealsAdapter;
import com.example.nutriappjava.classes.FoodItem;

import java.util.ArrayList;

public class AddLogsFragment extends Fragment implements MealsListFragment.OnMealSelectedListener {

    private Button selectDateButton, selectTimeButton, addMealButton;
    private TextView textViewSelectedDate, textViewSelectedTime;

    private MealsAdapter mealsAdapter;
    private SharedViewModel sharedViewModel;
    private ArrayList<FoodItem> mealsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_logs, container, false);

        mealsAdapter = new MealsAdapter(mealsList, requireContext());
        selectDateButton = view.findViewById(R.id.selectDateButton);
        selectTimeButton = view.findViewById(R.id.selectTimeButton);
        addMealButton = view.findViewById(R.id.addMealButton);
        textViewSelectedDate = view.findViewById(R.id.textViewSelectedDate);
        textViewSelectedTime = view.findViewById(R.id.textViewSelectedTime);
        RecyclerView mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealsRecyclerView.setAdapter(mealsAdapter);

        selectDateButton.setOnClickListener(v -> showDatePickerDialog());
        selectTimeButton.setOnClickListener(v -> showTimePickerDialog());
        addMealButton.setOnClickListener(v -> {
            MealsListFragment mealsListFragment = new MealsListFragment();
            mealsListFragment.setOnMealSelectedListener((View.OnClickListener) this); // Explicit cast
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mealsListFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onMealSelected(FoodItem foodItem) {
        mealsList.add(foodItem);
        mealsAdapter.notifyDataSetChanged();
    }

    public interface OnMealSelectedListener{
        void onMealSelected(FoodItem item);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);

                        TextView dateTextView = getView().findViewById(R.id.textViewSelectedDate);
                        dateTextView.setText(formattedDate);

                        // Log the selected date if needed
                        Log.d("SELECTED DATE ", formattedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                (view, hourOfDay, minuteFromPicker) -> {
                    String formattedTime = String.format("%02d:%02d", hourOfDay, minuteFromPicker);
                    textViewSelectedTime.setText("Selected Time: " + formattedTime);
                    Log.d("SELECTED TIME ", formattedTime);
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }
}