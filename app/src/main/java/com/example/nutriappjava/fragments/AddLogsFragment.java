package com.example.nutriappjava.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.nutriappjava.FoodListActivity;
import com.example.nutriappjava.R;
public class AddLogsFragment extends Fragment {


    private Button selectDateButton, selectTimeButton, addMealButton;
    private TextView textViewSelectedDate, textViewSelectedTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_logs, container, false);

        selectDateButton = view.findViewById(R.id.selectDateButton);
        selectTimeButton = view.findViewById(R.id.selectTimeButton);
        addMealButton = view.findViewById(R.id.addMealButton);
        textViewSelectedDate = view.findViewById(R.id.textViewSelectedDate);
        textViewSelectedTime = view.findViewById(R.id.textViewSelectedTime);

        selectDateButton.setOnClickListener(v -> showDatePickerDialog());
        selectTimeButton.setOnClickListener(v -> showTimePickerDialog());
        addMealButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FoodListActivity.class);
            startActivity(intent);
        });

        return view;


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