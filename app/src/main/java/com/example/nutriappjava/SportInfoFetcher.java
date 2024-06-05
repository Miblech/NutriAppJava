package com.example.nutriappjava;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.nutriappjava.classes.sportItems;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SportInfoFetcher extends AsyncTask<String, Void, List<sportItems>> {

    private DatabaseHelper dbHelper;

    private SportAdapter activityAdapter;

    public SportInfoFetcher(SportAdapter activityAdapter){
        this.activityAdapter = activityAdapter;
    }

    private static final String API_KEY = "GzchbXFobnImhkxBWjLoQg==FOtbA37I0cIGxS7f";
    private static final String ACTIVITIES_API_URL = "https://api.api-ninjas.com/v1/caloriesburnedactivities";

    protected List<sportItems> doInBackground(String... strings) {
        List<sportItems> activities = new ArrayList<>();
        String query = strings[0].toLowerCase();
        try {
            URL url = new URL(ACTIVITIES_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();


                // Parse the JSON response
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                JsonArray activitiesArray = jsonResponse.getAsJsonArray("activities");

                // Filter activities based on the query
                for (JsonElement element : activitiesArray) {
                    String activityName = element.getAsString().toLowerCase();
                    if (activityName.contains(query)) {
                        // Add the activity to the list
                        activities.add(new sportItems(activityName));
                    }
                }

                for (sportItems activity : activities) {;
                }
            } else {
                Log.e("API CALL", "Failed : HTTP error code : " + connection.getResponseCode());
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    protected void onPostExecute(List<sportItems> sports) {
        super.onPostExecute(sports);

        if (sports != null && !sports.isEmpty()) {
            activityAdapter.updateData(sports);
        } else {
            Toast.makeText(activityAdapter.getContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
    }
}
