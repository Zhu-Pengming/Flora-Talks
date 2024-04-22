package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private static final String USER_INFO = "userinfo";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        start = findViewById(R.id.start_chat);
        start.setOnClickListener(v -> navigateBasedOnLoginStatus());
    }

    private void navigateBasedOnLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false);

        Intent intent;
        if (isLoggedIn) {
            // 用户已登录，跳转到聊天界面
            intent = new Intent(MainActivity.this, ChatActivity.class);
        } else {
            // 用户未登录，跳转到登录界面
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
    }
}
