package com.menga.Redeption.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.menga.Redeption.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Thread thread =new Thread(){
            @Override
            public void run() {

                try {
                  sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(IntroActivity.this,Login.class);
                    startActivity(intent);
                }
            }
        };thread.start();

    }
}