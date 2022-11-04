package com.menga.Redeption.Activities;

import static com.menga.Redeption.Activities.UsersProfileActivity.USERID;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.usersfriendadapter;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.databinding.ActivityUsersfriendactivityBinding;

public class Usersfriendactivity extends AppCompatActivity {
ActivityUsersfriendactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUsersfriendactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new usersfriendadapter(getSupportFragmentManager()));

        FirebaseDatabase.getInstance().getReference().child("Users").child(USERID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Userdata userdata=snapshot.getValue(Userdata.class);
                    binding.username.setText(userdata.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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