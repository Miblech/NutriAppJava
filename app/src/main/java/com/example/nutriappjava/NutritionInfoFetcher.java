package com.example.nutriappjava;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.nutriappjava.foodItem; // Ensure you have a com.example.nutriappjava.foodItem model class defined

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
public class NutritionInfoFetcher extends AsyncTask<String, Void, List<foodItem>> {

    private DatabaseHelper dbHelper;

    public NutritionInfoFetcher(DatabaseHelper dbHelper, Context context){
        this.dbHelper = dbHelper;
        this.context = context;
    }

    private Context context;
    private static final String API_KEY = "GzchbXFobnImhkxBWjLoQg==FOtbA37I0cIGxS7f";
    private static final String NUTRITION_API_URL = "https://api.api-ninjas.com/v1/nutrition";

    @Override
    protected List<foodItem> doInBackground(String... strings) {
        List<foodItem> foodItems = new ArrayList<>();
        String query = strings[0];

        try {
            URL url = new URL(NUTRITION_API_URL + "?query=" + query);
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
                Type type = new TypeToken<List<foodItem>>(){}.getType();
                foodItems = gson.fromJson(response.toString(), type);
            } else {
                Log.e("API CALL", "Failed : HTTP error code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("API CALL", "IO Exception: ", e);
        }

        return foodItems;
    }

    @Override
    protected void onPostExecute(List<foodItem> foodItems) {
        super.onPostExecute(foodItems);

        if (!foodItems.isEmpty()) {

            for (foodItem foodItem : foodItems) {
                dbHelper.insertFoodItem(foodItem);
            }
            Toast.makeText(context , "Data fetched successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context , "No data found.", Toast.LENGTH_SHORT).show();
        }
    }
}
