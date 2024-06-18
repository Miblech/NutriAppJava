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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //USER
    @POST("/api/authenticate")
    Call<JwtResponse> authenticate(@Body JwtRequest request);
    @POST("/api/users/register")
    Call<User> registerUser(@Body User user);
    @GET("/api/users/me")
    Call<User> getCurrentUser(@Header("Authorization") String token);
    @PUT("/api/users/me/update")
    Call<User> updateCurrentUser(@Header("Authorization") String token, @Body User user);
    @DELETE("/api/users/me/delete")
    Call<Void> deleteCurrentUser(@Header("Authorization") String token);
    @PUT("/api/users/me/renew-password")
    Call<Boolean> renewPassword(@Header("Authorization") String token, @Body PasswordChangeRequest request);

    // FOOD
    @GET("/api/foods")
    Call<List<Food>> getAllFoods();
    @GET("/api/foods/sort/protein/desc")
    Call<List<Food>> getAllFoodsSortedByProteinDesc();
    @GET("/api/foods/sort/carbohydrate/desc")
    Call<List<Food>> getAllFoodsSortedByCarbohydrateDesc();
    @GET("/api/foods/sort/saturated-fat/desc")
    Call<List<Food>> getAllFoodsSortedBySaturatedFatDesc();
    @GET("/api/foods/categories")
    Call<List<String>> getCategories();
    @GET("/api/foods/category/{category}")
    Call<List<Food>> getFoodsByCategory(@Path("category") String category);
    @GET("/api/foods/{id}")
    Call<Food> getFoodById(@Path("id") Long id);
    @GET("/api/foods/search/{description}")
    Call<List<Food>> searchFoodsByDescription(@Path("description") String description);

    // LOGS
    @GET("/api/logs/user/today")
    Call<List<DailyLog>> getLogsForToday(@Header("Authorization") String token);

    @GET("/api/logs/search/{id}")
    Call<DailyLog> getDailyLogDetails(@Header("Authorization") String token, @Path("id") long id);
    @GET("/api/logs/summary/{id}")
    Call<NutrientSummary> getLogSummary(@Path("id") Long logId, @Header("Authorization") String token);
    @GET("/api/logs/user/logs/count")
    Call<Long> getUserLogsCount(@Header("Authorization") String token);
    @GET("/api/logs/user/summary")
    Call<NutrientSummary> getTotalNutrientSummaryForUser(@Header("Authorization") String token);
    @DELETE("/api/logs/user/delete")
    Call<Void> deleteAllUserLogs(@Header("Authorization") String token);
    @POST("/api/logs")
    Call<DailyLog> createLog(@Body DailyLog log, @Header("Authorization") String token);
    @POST("/api/logs/{dailyLogId}/addFood")
    Call<DailyLog> addFoodToDailyLog(
            @Path("dailyLogId") Long dailyLogId,
            @Query("foodId") Long foodId,
            @Query("weight") Float weight);

    @GET("/api/logs/user/summary/{date}")
    Call<NutrientSummary> getLogSummaryForDate(@Header("Authorization") String token, @Path("date") String date);
    @GET("/api/logs/user/summary/period/{period}")
    Call<NutrientSummary> getLogSummaryForPeriod(@Header("Authorization") String token, @Path("period") String period);

    @GET("/api/logs/user/logs/{period}")
    Call<List<DailyLog>> getLogsForPeriod(@Header("Authorization") String token, @Path("period") String period);

    @GET("/api/logs/user/log/{date}")
    Call<List<DailyLog>> getLogsForDate(@Header("Authorization") String token, @Path("date") String date);
}
