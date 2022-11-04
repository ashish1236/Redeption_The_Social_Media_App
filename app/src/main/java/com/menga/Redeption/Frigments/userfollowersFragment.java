package com.menga.Redeption.Frigments;

import static com.menga.Redeption.Activities.UsersProfileActivity.USERID;

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
import com.menga.Redeption.Adapters.userfollowersadapter;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.databinding.FragmentUserfollowersBinding;

import java.util.ArrayList;


public class userfollowersFragment extends Fragment {
FragmentUserfollowersBinding binding;
ArrayList<FollowersModel> list=new ArrayList<>();

    public userfollowersFragment() {
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
        binding=FragmentUserfollowersBinding.inflate(inflater, container, false);

        binding.vsprogressbar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        if (USERID.equals(FirebaseAuth.getInstance().getUid())){
//            Toast.makeText(getContext(), "your profile", Toast.LENGTH_SHORT).show();
            VerticalFollowersAdapter adapter=new VerticalFollowersAdapter(list,getContext());
            binding.vsrv.setLayoutManager(layoutManager);
            binding.vsrv.setAdapter(adapter);
            FirebaseDatabase.getInstance().getReference().child("Users").child(USERID).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
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

        }else {
//            Toast.makeText(getContext(), "usersprofile", Toast.LENGTH_SHORT).show();
            userfollowersadapter adapter=new userfollowersadapter(list,getContext());
            binding.vsrv.setLayoutManager(layoutManager);
            binding.vsrv.setAdapter(adapter);
            FirebaseDatabase.getInstance().getReference().child("Users").child(USERID).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
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

        }





        return binding.getRoot();
    }
}