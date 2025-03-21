package com.example.project_gk.api;

import com.example.project_gk.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/account/login")
    Call<ApiResponse<User>> login(@Query("Uname") String username, @Query("password") String password);
    @POST("api/product/getProductsByCategoryID")  // Dùng POST thay vì GET
    Call<ApiResponseProduct> getProductsByCategory(@Query("categoryID") int categoryId, @Body Object emptyBody);
    @GET("api/category")
    Call<ApiResponseCategory> getCategories();
}

