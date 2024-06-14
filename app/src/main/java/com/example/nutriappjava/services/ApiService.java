package com.example.nutriappjava.services;

import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.JwtRequest;
import com.example.nutriappjava.entities.JwtResponse;
import com.example.nutriappjava.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/authenticate")
    Call<JwtResponse> authenticate(@Body JwtRequest request);

    @POST("/api/users/register")
    Call<User> registerUser(@Body User user);

    @GET("api/logs/user/today")
    Call<List<DailyLog>> getLogsForToday(@Header("Authorization") String token);
}