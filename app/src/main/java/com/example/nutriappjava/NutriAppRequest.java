package com.example.nutriappjava;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutriAppRequest implements CustomNetworkRequest {
    private static final String API_KEY = "GzchbXFobnImhkxBWjLoQg==FOtbA37I0cIGxS7f";
    private static final String API_URL = "https://api.api-ninjas.com/v1/nutrition?query=";


    @Override
    public String execute(String query, String weight) throws Exception {
        try{
            if (weight == null || weight.isEmpty()) {
                weight = "100g";
            } else if (!weight.matches(".*[g|kg]$")){
                weight += "g";
            }

            String urlString = API_URL + weight + " " + query;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                return  jsonResponse.getString("fact");
            } else {
                throw new Exception("Failed to get response from server. Response code: " + responseCode);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
