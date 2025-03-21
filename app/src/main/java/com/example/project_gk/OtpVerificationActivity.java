package com.example.project_gk;

// 22110340 Nong Quoc Hung
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_gk.api.ApiClient;
import com.example.project_gk.api.ApiResponse;
import com.example.project_gk.api.ApiService;
import com.example.project_gk.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {
    private EditText edtOtp;
    private Button btnVerify;
    private TextView tvOtpError;

    private String email;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        // Ánh xạ view
        edtOtp = findViewById(R.id.edtOtp);
        btnVerify = findViewById(R.id.btnVerify);
        tvOtpError = findViewById(R.id.tvOtpError);

        // Nhận dữ liệu từ RegisterActivity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        userId = intent.getIntExtra("userId", -1);

        sendOtp();

        // Bấm xác nhận OTP
        btnVerify.setOnClickListener(v -> verifyOtp());

    }

    private void sendOtp() {
        ApiService apiService = ApiClient.getApiService();
        Call<ApiResponse<User>> call = apiService.sendOtp(email);

        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    Toast.makeText(OtpVerificationActivity.this, "Mã OTP đã gửi đến email!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtpVerificationActivity.this, "Không thể gửi OTP!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Toast.makeText(OtpVerificationActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyOtp() {
        String otpCode = edtOtp.getText().toString().trim();

        if (otpCode.isEmpty()) {
            tvOtpError.setVisibility(View.VISIBLE);
            tvOtpError.setText("Mã OTP không hợp lệ!");
            return;
        }

        ApiService apiService = ApiClient.getApiService();
        Call<ApiResponse<User>> call = apiService.confirmOtp(userId, otpCode); // Sử dụng userId thay vì email

        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    Toast.makeText(OtpVerificationActivity.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(OtpVerificationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    tvOtpError.setVisibility(View.VISIBLE);
                    tvOtpError.setText("OTP không đúng, vui lòng thử lại!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Toast.makeText(OtpVerificationActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
