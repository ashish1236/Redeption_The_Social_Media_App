package com.menga.Redeption.Bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.MyspaceAdapter;
import com.menga.Redeption.Models.SpaceModel;
import com.menga.Redeption.databinding.FragmentMyspaceBottomsheetBinding;

import java.util.ArrayList;

public class MyspaceBottomsheetFragment extends BottomSheetDialogFragment {
FragmentMyspaceBottomsheetBinding binding;
ArrayList<SpaceModel>list=new ArrayList<>();


    public MyspaceBottomsheetFragment() {
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
        binding=FragmentMyspaceBottomsheetBinding.inflate(inflater, container, false);



        MyspaceAdapter adapter=new MyspaceAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.myspacerv.setAdapter(adapter);
        binding.myspacerv.setLayoutManager(layoutManager);

        if (list.isEmpty()){
            binding.empty.setVisibility(View.VISIBLE);
        }

        FirebaseDatabase.getInstance().getReference().child("Space_Center").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
            SpaceModel spaceModel=dataSnapshot.getValue(SpaceModel.class);
            spaceModel.setSpaceid(dataSnapshot.getKey());
            list.add(spaceModel);
            binding.empty.setVisibility(View.GONE);


        }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}