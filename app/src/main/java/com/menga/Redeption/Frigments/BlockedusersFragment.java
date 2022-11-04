package com.menga.Redeption.Frigments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.blockedusersAdapter;
import com.menga.Redeption.Models.BlockedusersModel;
import com.menga.Redeption.databinding.FragmentBlockedusersBinding;

import java.util.ArrayList;

public class BlockedusersFragment extends BottomSheetDialogFragment {
FragmentBlockedusersBinding binding;
  ArrayList<BlockedusersModel>list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;

    public BlockedusersFragment() {
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
        // Inflate the layout for this fragment
        binding= FragmentBlockedusersBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        blockedusersAdapter adapter=new blockedusersAdapter(list,getContext());
        binding.bolckeduesrsrv.setLayoutManager(layoutManager);
        binding.bolckeduesrsrv.setAdapter(adapter);

        if (list.isEmpty()){
            binding.emtytext.setVisibility(View.VISIBLE);
        }

        database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    BlockedusersModel model=snapshot.getValue(BlockedusersModel.class);
                    model.setBlockedid(snapshot.getKey());
                    list.add(model);
                    binding.emtytext.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            BlockedusersModel model=dataSnapshot.getValue(BlockedusersModel.class);
                            model.setBlockedid(dataSnapshot.getKey());
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}