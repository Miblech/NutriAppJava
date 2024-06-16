package com.example.nutriappjava.services;

import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.entities.JwtRequest;
import com.example.nutriappjava.entities.JwtResponse;
import com.example.nutriappjava.entities.NutrientSummary;
import com.example.nutriappjava.entities.PasswordChangeRequest;
import com.example.nutriappjava.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/authenticate")
    Call<JwtResponse> authenticate(@Body JwtRequest request);

    @POST("/api/users/register")
    Call<User> registerUser(@Body User user);

    @GET("api/logs/user/today")
    Call<List<DailyLog>> getLogsForToday(@Header("Authorization") String token);

    @GET("/api/users/me")
    Call<User> getCurrentUser(@Header("Authorization") String token);

    @PUT("/api/users/me/update")
    Call<User> updateCurrentUser(@Header("Authorization") String token, @Body User user);

    @DELETE("/api/users/me/delete")
    Call<Void> deleteCurrentUser(@Header("Authorization") String token);

    @PUT("/api/users/me/renew-password")
    Call<Boolean> renewPassword(@Header("Authorization") String token, @Body PasswordChangeRequest request);

    @GET("/api/user/logs/count")
    Call<Long> getUserLogsCount(@Header("Authorization") String token);

    @GET("/api/logs/user/summary")
    Call<NutrientSummary> getTotalNutrientSummaryForUser(@Header("Authorization") String token);

    @DELETE("/api/logs/user")
    Call<Void> deleteAllUserLogs(@Header("Authorization") String token);

    @GET("/api/foods")
    Call<List<Food>> getAllFoods();
}