package com.example.nutriappjava;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ActivityFetcher extends AsyncTask<String, Void, List<Activity>> {

    private static final String API_KEY = "GzchbXFobnImhkxBWjLoQg==FOtbA37I0cIGxS7f";
    private static final String ACTIVITIES_API_URL = "https://api.api-ninjas.com/v1/caloriesburnedactivities";

    private Context context;

    public ActivityFetcher(Context context) {
        this.context = context;
    }
    @Override
    protected List<Activity> doInBackground(String... strings) {
        List<Activity> activities = new ArrayList<>();

        try{
            URL url = new URL(ACTIVITIES_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine())!= null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Activity>>(){}.getType();
                activities = gson.fromJson(response.toString(), listType);
            } else {
                Log.e("API CALL", "Failed : HTTP error code : " + connection.getResponseCode());
                Toast.makeText(context, "Error: " + connection.getResponseCode(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    protected void onPostExecute(List<Activity> activities) {
        super.onPostExecute(activities);

        if (!activities.isEmpty()) {
            for (Activity activity : activities) {
                System.out.printf(activity.toString());
            }
        } else {
            Toast.makeText(context, "No activities found", Toast.LENGTH_SHORT).show();
        }
    }
}
