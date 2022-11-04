package com.menga.Redeption.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iammert.library.readablebottombar.ReadableBottomBar;
import com.menga.Redeption.Frigments.AddPostFragment;
import com.menga.Redeption.Frigments.HomeFragment;
import com.menga.Redeption.Frigments.NotificationFragment;
import com.menga.Redeption.Frigments.ProfileFragment;
import com.menga.Redeption.Frigments.SearchFragment;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        status();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack("Home");
        transaction.replace(R.id.conteiner, new HomeFragment());
        transaction.commit();
        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.conteiner, new HomeFragment()).addToBackStack(null);
                        break;
                    case 1:
                        transaction.replace(R.id.conteiner, new SearchFragment()).addToBackStack(null);
                        break;
                    case 2:

                        transaction.replace(R.id.conteiner, new AddPostFragment()).addToBackStack(null);

                        break;
                    case 3:

                        transaction.replace(R.id.conteiner, new NotificationFragment()).addToBackStack(null);


                        break;
                    case 4:

                        transaction.replace(R.id.conteiner, new ProfileFragment()).addToBackStack(null);
                        break;

                }

                transaction.commit();

            }
        });


    }

    private void status() {
        DatabaseReference statusinfo = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("online");
        statusinfo.setValue("true");
        statusinfo.onDisconnect().removeValue();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        binding.readableBottomBar.selectItem(0);
    }

}
