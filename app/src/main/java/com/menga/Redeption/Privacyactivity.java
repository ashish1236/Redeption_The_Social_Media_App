package com.menga.Redeption;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.menga.Redeption.Adapters.ViewpagerAdapter;
import com.menga.Redeption.Adapters.privacyadapter;
import com.menga.Redeption.Frigments.ProfileFragment;
import com.menga.Redeption.databinding.ActivityPrivacyactivityBinding;

public class Privacyactivity extends AppCompatActivity {
ActivityPrivacyactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPrivacyactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.viewPager.setAdapter(new privacyadapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        //>>>>Code for Back Arrow <<<<<<<<</////
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}