package com.example.project_gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Kiểm tra xem người dùng đã đăng nhập chưa
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    // Nếu đã đăng nhập, chuyển đến MainActivity
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                } else {
                    // Nếu chưa đăng nhập, chuyển đến LoginActivity
                    startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                }
                finish();
            }
        });
    }
}
