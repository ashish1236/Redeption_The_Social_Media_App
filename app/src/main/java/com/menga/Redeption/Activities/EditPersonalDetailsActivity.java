package com.menga.Redeption.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityEditPersonalDetailsBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class EditPersonalDetailsActivity extends AppCompatActivity {
    ActivityEditPersonalDetailsBinding binding;
    Context context;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String email = binding.Email.getText().toString();
        String password = binding.Password.getText().toString();
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = auth.getCurrentUser();
                AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("email").setValue(email);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("password").setValue(password);
                        } else {
                            new StyleableToast.Builder(getApplicationContext())
                                    .text(task.getException().getMessage())
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.GRAY)
                                    .cornerRadius(5)
                                    .iconStart(R.drawable.error)
                                    .show();
                        }
                    }
                });
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}