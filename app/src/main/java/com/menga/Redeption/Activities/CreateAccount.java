package com.menga.Redeption.Activities;

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
import com.google.firebase.database.FirebaseDatabase;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.Privacyactivity;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityCreateAccountBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class CreateAccount extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase userdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        userdatabase = FirebaseDatabase.getInstance();
        binding.signupbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (binding.nameedittext.length() > 0 && binding.passwordedittext.length() > 0 && binding.emailedittext.length() > 0) {
                    binding.lodingbar.setVisibility(View.VISIBLE);
                    String email = binding.emailedittext.getText().toString();
                    String password = binding.passwordedittext.getText().toString();
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //for get and set data to firebase make an model for user data in thi program the model name is userdata
                            if (task.isSuccessful()) {
                                Userdata userdata = new Userdata(binding.nameedittext.getText().toString(), email, password);
                                String Userid = task.getResult().getUser().getUid();
                                userdatabase.getReference().child("Users").child(Userid).setValue(userdata);
                                new StyleableToast.Builder(getApplicationContext())
                                        .text("Congratulation Account Created")
                                        .backgroundColor(Color.GRAY)
                                        .cornerRadius(5)
                                        .iconStart(R.drawable.congrates)
                                        .show();

                                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                                startActivity(intent);
                                binding.lodingbar.setVisibility(View.INVISIBLE);

                            } else {
                                binding.lodingbar.setVisibility(View.GONE);
                                new StyleableToast.Builder(getApplicationContext())
                                        .text(task.getException().getMessage())
                                        .textColor(Color.WHITE)
                                        .backgroundColor(Color.GRAY)
                                        .cornerRadius(5)
                                        .iconStart(R.drawable.error)
                                        .show();
                                //     Intent intent=new Intent(CreateAccount.this,Login.class);
                                //      startActivity(intent);
                            }
                        }
                    });
                } else {
                    if (binding.nameedittext.length() == 0) {
                        new StyleableToast.Builder(getApplicationContext())
                                .text("Please Enter Your Name")
                                .textColor(Color.WHITE)
                                .iconStart(R.drawable.name)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        binding.nameedittext.setError("Please Fill This");
                    }
                    if (binding.passwordedittext.length() == 0) {
                        new StyleableToast.Builder(getApplicationContext())
                                .text("Please Make Password")
                                .textColor(Color.WHITE)
                                .iconStart(R.drawable.password)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        binding.passwordedittext.setError("Please Fill This");
                    }
                    if (binding.emailedittext.length() == 0) {
                        new StyleableToast.Builder(getApplicationContext())
                                .text("Please Enter Your Email")
                                .textColor(Color.WHITE)
                                .iconStart(R.drawable.email)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        binding.emailedittext.setError("Please Fill This");
                    }
                }


            }
        });
        binding.logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, Login.class);
                startActivity(intent);
            }
        });
binding.termsandconditions.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(CreateAccount.this, Privacyactivity.class);
        startActivity(intent);
    }
});

    }


}