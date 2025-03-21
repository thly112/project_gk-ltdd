package com.example.project_gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//    22110375 Trần Thị Thảo Ly
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
                if(checkLoginState()){
                    return;
                };
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });
    }
    private boolean checkLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String username = sharedPreferences.getString("USERNAME", "");
            Toast.makeText(this, "Chào mừng trở lại, " + username, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return false;
    }
}
