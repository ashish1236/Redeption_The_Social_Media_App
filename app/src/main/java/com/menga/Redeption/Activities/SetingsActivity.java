package com.menga.Redeption.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.menga.Redeption.Bottom_sheets.ThemebottomsheetFragment;
import com.menga.Redeption.Bottom_sheets.Visibilty_settong_bottomsheet;
import com.menga.Redeption.Frigments.BlockedusersFragment;
import com.menga.Redeption.Privacyactivity;
import com.menga.Redeption.databinding.ActivitySetingsBinding;

public class SetingsActivity extends AppCompatActivity {
ActivitySetingsBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        binding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SetingsActivity.this,AboutusActivity.class);
                startActivity(intent);
            }
        });

        // <<<<<<<<<<<<<<<<<<<<<<codde for sign User sign out >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                ProcessPhoenix.triggerRebirth(getApplicationContext());

            }
        });
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>codde for sign User sign out  >>>>>>>>>>>>>>>>>><<<<<<<<<<<<<>>>>>>>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>>>>>>>

        // <<<<<<<<<<<<<<<<<<<<<<codde for back button  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<codde for back button  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

binding.deletedpost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(SetingsActivity.this,Deletedposts.class);
        startActivity(intent);
    }
});

binding.account.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(SetingsActivity.this,EditAccountActivity.class);
        startActivity(intent);
    }
});
binding.Blocking.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        BlockedusersFragment blockedusersFragment=new BlockedusersFragment();
        blockedusersFragment.show(getSupportFragmentManager(),blockedusersFragment.getTag());
    }
});

binding.theme.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ThemebottomsheetFragment themebottomsheetFragment=new ThemebottomsheetFragment();
        themebottomsheetFragment.show(getSupportFragmentManager(),themebottomsheetFragment.getTag());
    }
});
//binding.star.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Intent intent =new Intent(SetingsActivity.this,StarsinfoActivity.class);
//        startActivity(intent);
//    }
//});


binding.savedposts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
Intent intent=new Intent(SetingsActivity.this,Saved_posts_Activity.class);
startActivity(intent);
    }
});
binding.visibilty.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Visibilty_settong_bottomsheet visibilty_settong_bottomsheet=new Visibilty_settong_bottomsheet();
        visibilty_settong_bottomsheet.show(getSupportFragmentManager(),visibilty_settong_bottomsheet.getTag());
    }
});
        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetingsActivity.this, Privacyactivity.class);
                startActivity(intent);
            }
        });
    }
}