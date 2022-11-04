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
import com.menga.Redeption.Adapters.VerticalsFollowingAdapter;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.databinding.FragmentVerticalFollowingBinding;

import java.util.ArrayList;

public class VerticalFollowingFragment extends Fragment {
FragmentVerticalFollowingBinding binding;

    ArrayList<FollowingModel> list=new ArrayList<>();
    public VerticalFollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentVerticalFollowingBinding.inflate(inflater,container,false);


        binding.vsprogressbar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        VerticalsFollowingAdapter adapter=new VerticalsFollowingAdapter(getContext(),list);
        binding.followingrv.setLayoutManager(layoutManager);
        binding.followingrv.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FollowingModel followingModel=dataSnapshot.getValue(FollowingModel.class);
                    list.add(followingModel);
                }
                binding.vsprogressbar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return binding.getRoot();
    }
}