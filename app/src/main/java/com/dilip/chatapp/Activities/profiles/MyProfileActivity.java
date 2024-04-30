package com.dilip.chatapp.Activities.profiles;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dilip.chatapp.R;
import com.dilip.chatapp.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity {

    ActivityMyProfileBinding binding;
    private ImageView imageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initProfile();
        initClicks();
    }

    private void initProfile() {

    }

    private void initClicks() {
    }


}