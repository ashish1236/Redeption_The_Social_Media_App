package com.menga.Redeption.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityForgotPasswordBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        binding.sendlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        String email = binding.fgemailedittext.getText().toString();
        if (email.isEmpty()) {
            binding.fgemailedittext.setError("Enter email address");
        } else {
            forgotpassword();
        }
    }

    private void forgotpassword() {
        String email = binding.fgemailedittext.getText().toString();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    new StyleableToast.Builder(getApplicationContext())
                            .text("Link sent to " + email)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .cornerRadius(5)
                            .iconStart(R.drawable.link)
                            .show();
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    startActivity(intent);
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

}