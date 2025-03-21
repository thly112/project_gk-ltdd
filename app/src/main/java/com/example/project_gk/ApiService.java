package com.example.project_gk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/account/login")
    Call<ApiResponse<User>> login(@Body LoginRequest request);
}

