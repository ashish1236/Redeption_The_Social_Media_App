package com.menga.Redeption.Frigments;

import static com.menga.Redeption.Activities.UsersProfileActivity.USERID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.usersfollowingadapter;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.databinding.FragmentUserfollwingBinding;

import java.util.ArrayList;


public class userfollwingFragment extends Fragment {
FragmentUserfollwingBinding binding;
    ArrayList<FollowingModel> list=new ArrayList<>();
    public userfollwingFragment() {
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
        binding=FragmentUserfollwingBinding.inflate(inflater,container,false);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        usersfollowingadapter adapter=new usersfollowingadapter(list,getContext());
        binding.followingrv.setLayoutManager(layoutManager);
        binding.followingrv.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("Users").child(USERID).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
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