package com.example.project_gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsername;
    private ImageView imgProfile;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);
        etSearch = findViewById(R.id.etSearch);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Người dùng");
        String profileImage = sharedPreferences.getString("profileImage", "");

        // Hiển thị tên người dùng
        tvUsername.setText("Hi! " + username);

        // Hiển thị ảnh đại diện
        if (!profileImage.isEmpty()) {
            imgProfile.setImageBitmap(decodeImage(profileImage));
        } else {
            imgProfile.setImageResource(R.drawable.ic_user_placeholder);
        }

    }

    // Giải mã ảnh từ chuỗi Base64
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
}
