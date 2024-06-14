package com.example.nutriappjava.services;

import com.example.nutriappjava.entities.JwtRequest;
import com.example.nutriappjava.entities.JwtResponse;
import com.example.nutriappjava.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/authenticate")
    Call<JwtResponse> authenticate(@Body JwtRequest request);

    @POST("/api/users/register")
    Call<User> registerUser(@Body User user);
}