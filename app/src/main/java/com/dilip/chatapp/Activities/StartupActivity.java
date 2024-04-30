package com.dilip.chatapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dilip.chatapp.R;
import com.dilip.chatapp.databinding.ActivityStartupBinding;

public class StartupActivity extends AppCompatActivity {

    ActivityStartupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toLogin();
    }

    private void toLogin() {
        startActivity(new Intent(StartupActivity.this, OTPActivity.class));
        finish();
    }
}