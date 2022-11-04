package com.menga.Redeption.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityLoginBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    FirebaseUser currentuser;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser();

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.emailedittext.length() > 0 && binding.passwordedittext.length() > 0) {
                    binding.lodingbar.setVisibility(View.VISIBLE);
                    String email = binding.emailedittext.getText().toString();
                    String password = binding.passwordedittext.getText().toString();
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                binding.lodingbar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                binding.lodingbar.setVisibility(View.GONE);
                                if (binding.emailedittext.length() == 0) {

                                    new StyleableToast.Builder(getApplicationContext())
                                            .text("Please Enter Your Email")
                                            .iconStart(R.drawable.email)
                                            .textColor(Color.WHITE)
                                            .cornerRadius(5)
                                            .backgroundColor(Color.GRAY)
                                            .show();

                                    binding.emailedittext.setError("Please Fill This");
                                }else
                                {
                                    new StyleableToast.Builder(getApplicationContext())
                                            .text(task.getException().getMessage())
                                            .iconStart(R.drawable.error)
                                            .textColor(Color.WHITE)
                                            .cornerRadius(5)
                                            .backgroundColor(Color.GRAY)
                                            .show();
                                }
                                if (binding.passwordedittext.length() == 0) {


                                    new StyleableToast.Builder(getApplicationContext())
                                            .text("Please Enter Your Password")
                                            .iconStart(R.drawable.email)
                                            .textColor(Color.WHITE)
                                            .cornerRadius(5)
                                            .backgroundColor(Color.GRAY)
                                            .show();
                                    binding.passwordedittext.setError("Please Fill This");
                                }
                            }
                        }
                    });
                }else{
                    if (binding.emailedittext.length() == 0) {

                        new StyleableToast.Builder(getApplicationContext())
                                .text("Please Enter Your Email")
                                .iconStart(R.drawable.email)
                                .textColor(Color.WHITE)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();

                        binding.emailedittext.setError("Please Fill This");
                    }
                    if (binding.passwordedittext.length() == 0) {


                        new StyleableToast.Builder(getApplicationContext())
                                .text("Please Enter Your Password")
                                .iconStart(R.drawable.email)
                                .textColor(Color.WHITE)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        binding.passwordedittext.setError("Please Fill This");
                    }
                }
            }
            });

////////////////////////////////////////////////// Here a code for forgot password text////////////////////////////////////////////////////////////////////////////////////////////
binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
Intent intent=new Intent(Login.this,ForgotPassword.class);
startActivity(intent);

    }
});




 binding.signuptext.setOnClickListener(new View.OnClickListener() {

        @Override

        public void onClick(View v) {
            Intent intent = new Intent(Login.this, CreateAccount.class);
            startActivity(intent);
        }
 });}
    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser!=null){
            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}