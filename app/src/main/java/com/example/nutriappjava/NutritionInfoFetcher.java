package com.example.nutriappjava;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.nutriappjava.adapters.FoodSearchAdapter;
import com.example.nutriappjava.classes.FoodItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class NutritionInfoFetcher extends AsyncTask<String, Void, List<FoodItem>> {
    private DatabaseHelper dbHelper;
    private FoodSearchAdapter foodSearchAdapter;


    public NutritionInfoFetcher(FoodSearchAdapter foodSearchAdapter) {
        this.foodSearchAdapter = foodSearchAdapter;
    }


    private static final String API_KEY = "GzchbXFobnImhkxBWjLoQg==FOtbA37I0cIGxS7f";
    private static final String NUTRITION_API_URL = "https://api.api-ninjas.com/v1/nutrition";

    @Override
    protected List<FoodItem> doInBackground(String... params) {
        List<FoodItem> FoodItems = new ArrayList<>();
        try {

            String query = params[0];
            String fullUrl = NUTRITION_API_URL + "?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString());

            HttpURLConnection connection = (HttpURLConnection) new URL(fullUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine())!= null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();

                JSONArray jsonArray = new JSONArray(content.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject food = jsonArray.getJSONObject(i);
                    FoodItem foodItem = new FoodItem(
                            food.getString("name"),
                            food.getDouble("calories"),
                            food.getDouble("serving_size_g"),
                            food.getDouble("fat_total_g"),
                            food.getDouble("fat_saturated_g"),
                            food.getDouble("protein_g"),
                            food.getInt("sodium_mg"),
                            food.getInt("potassium_mg"),
                            food.getInt("cholesterol_mg"),
                            food.getDouble("carbohydrates_total_g"),
                            food.getDouble("fiber_g"),
                            food.getDouble("sugar_g")
                    );
                    FoodItems.add(foodItem);
                }
            } else {
                Log.e("API CALL", "Failed to fetch nutrition data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            Log.e("API CALL", "Error fetching nutrition data", e);
        }
        return FoodItems;
    }

    @Override
    protected void onPostExecute(List<FoodItem> FoodItems) {
        super.onPostExecute(FoodItems);
        if (FoodItems != null &&!FoodItems.isEmpty()) {
            foodSearchAdapter.updateData(FoodItems);
        } else {
            Toast.makeText(foodSearchAdapter.getContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
    }
}