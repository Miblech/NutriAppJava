package com.example.nutriappjava.services;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class ApiClient {
    private static final String BASE_URL_EMULATOR = "http://10.0.2.2:8080";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(boolean isEmulator) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            String baseUrl = isEmulator ? BASE_URL_EMULATOR : BASE_URL_EMULATOR;

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}