package com.menga.Redeption.Frigments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.VerticalFollowersAdapter;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.databinding.FragmentVerticalFollowersBinding;

import java.util.ArrayList;



public class VerticalFollowersFragment extends Fragment {
    FragmentVerticalFollowersBinding binding;
FirebaseDatabase database;
FirebaseAuth auth;
ArrayList<FollowersModel>list=new ArrayList<>();


    public VerticalFollowersFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentVerticalFollowersBinding.inflate(inflater,container,false);
        binding.vsprogressbar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        VerticalFollowersAdapter adapter=new VerticalFollowersAdapter(list,getContext());
        binding.vsrv.setLayoutManager(layoutManager);
        binding.vsrv.setAdapter(adapter);
        database.getReference().child("Users").child(auth.getUid()).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FollowersModel followersModel=dataSnapshot.getValue(FollowersModel.class);
                    list.add(followersModel);
                }
                binding.vsprogressbar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        // Inflate the layout for this fragment


        return binding.getRoot();
    }
}