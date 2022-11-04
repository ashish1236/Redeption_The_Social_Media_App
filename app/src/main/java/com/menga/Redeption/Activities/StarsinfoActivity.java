package com.menga.Redeption.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.menga.Redeption.databinding.ActivityStarsinfoBinding;

public class StarsinfoActivity extends AppCompatActivity {
    ActivityStarsinfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStarsinfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}