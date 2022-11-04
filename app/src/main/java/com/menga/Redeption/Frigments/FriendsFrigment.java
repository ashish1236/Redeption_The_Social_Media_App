package com.menga.Redeption.Frigments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.ViewpagerAdapter;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentFriendsFrigmentBinding;


public class FriendsFrigment extends Fragment {
FragmentFriendsFrigmentBinding binding;

    public FriendsFrigment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentFriendsFrigmentBinding.inflate(inflater, container, false);



        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
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
binding.viewPager.setAdapter(new ViewpagerAdapter(getChildFragmentManager()));
binding.tabLayout.setupWithViewPager(binding.viewPager);
       //>>>>Code for Back Arrow <<<<<<<<</////
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentTransaction transaction=getFragmentManager().beginTransaction();
               transaction.replace(R.id.conteiner,new ProfileFragment());
               transaction.commit();
            }
        });




        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}