package com.example.project_gk;
//Trinh Ngoc Hieu-22110324
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_gk.adapter.CategoryAdapter;
import com.example.project_gk.adapter.ProductAdapter;
import com.example.project_gk.api.ApiResponseProduct;
import com.example.project_gk.api.ApiService;
import com.example.project_gk.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_gk.adapter.CategoryAdapter;
import com.example.project_gk.adapter.ProductAdapter;
import com.example.project_gk.api.ApiClient;
import com.example.project_gk.api.ApiResponseCategory;
import com.example.project_gk.api.ApiResponseProduct;
import com.example.project_gk.api.ApiService;
import com.example.project_gk.model.Category;
import com.example.project_gk.model.Product;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener{
    private TextView tvUsername;
    private ImageView imgProfile;
    private RecyclerView recyclerViewCategories;
    private GridView gridViewProducts;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "Người dùng");
        String profileImage = sharedPreferences.getString("profileImage", "");

        tvUsername.setText("Hi! " + username);

        if (!profileImage.isEmpty()) {
            imgProfile.setImageBitmap(decodeImage(profileImage));
        } else {
            imgProfile.setImageResource(R.drawable.ic_user_placeholder);
        }
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        gridViewProducts = findViewById(R.id.gridView);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadCategories();

    }
    private void setupCategoryRecyclerView(List<Category> categories) {
        categoryAdapter = new CategoryAdapter(this, categories, this);
        // Truyền đủ 3 tham số
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(categoryAdapter);
    }
    private Bitmap decodeImage(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // Đăng xuất
    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void loadCategories() {
        apiService.getCategories().enqueue(new Callback<ApiResponseCategory>() {
            @Override
            public void onResponse(Call<ApiResponseCategory> call, Response<ApiResponseCategory> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getBody();
                    if (categories != null && !categories.isEmpty()) {
                        setupCategoryRecyclerView(categories);
                        loadProducts(categories.get(0).getId()); // Tải sản phẩm của danh mục đầu tiên
                    } else {
                        Toast.makeText(MainActivity.this, "Không có danh mục!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi tải danh mục!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseCategory> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi kết nối", t);
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadProducts(int categoryId) {
        apiService.getProductsByCategory(categoryId, new Object()).enqueue(new Callback<ApiResponseProduct>() {
            @Override
            public void onResponse(Call<ApiResponseProduct> call, Response<ApiResponseProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productList = response.body().getBody();
                    if (productList != null && !productList.isEmpty()) {
                        productAdapter = new ProductAdapter(MainActivity.this, productList);
                        gridViewProducts.setAdapter(productAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi tải sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseProduct> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int categoryId) {
        loadProducts(categoryId);
    }
}
