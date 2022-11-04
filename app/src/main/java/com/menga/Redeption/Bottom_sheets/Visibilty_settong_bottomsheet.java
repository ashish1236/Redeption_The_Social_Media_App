package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.databinding.FragmentVisibiltySettongBottomsheetBinding;


public class Visibilty_settong_bottomsheet extends BottomSheetDialogFragment {
        FragmentVisibiltySettongBottomsheetBinding binding;
    public Visibilty_settong_bottomsheet() {
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
        binding=FragmentVisibiltySettongBottomsheetBinding.inflate(inflater,container,false);


        FirebaseDatabase.getInstance().getReference().child("Visibilty_Whenonline").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.whenonline.setEnabled(false);
                    binding.never.setEnabled(true);
                    binding.always.setEnabled(true);
//                    binding.whenonline.setBackground(ContextCompat.getDrawable(getActivity(), R.color.gray2));
                    binding.whenonline.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.always.setEnabled(true);
                    binding.never.setEnabled(false);
                    binding.whenonline.setEnabled(true);
                    binding.never.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.always.setEnabled(false);
                    binding.never.setEnabled(true);
                    binding.whenonline.setEnabled(true);
                    binding.always.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.always.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Whenonline").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                   binding.always.setEnabled(false);
                        binding.never.setEnabled(true);
                        binding.whenonline.setEnabled(true);
                        binding.always.setBackgroundColor(Color.LTGRAY);
                        Visibilty_settong_bottomsheet.this.dismiss();
                    }
                });
            }
        });

        binding.never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Whenonline").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.never.setEnabled(false);
                        binding.always.setEnabled(true);
                        binding.whenonline.setEnabled(true);
                        binding.never.setBackgroundColor(Color.LTGRAY);
                        Visibilty_settong_bottomsheet.this.dismiss();
                    }
                });
            }
        });


        binding.whenonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Visibilty_Whenonline").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.whenonline.setEnabled(false);
                        binding.never.setEnabled(true);
                        binding.always.setEnabled(true);
                        binding.whenonline.setBackgroundColor(Color.LTGRAY);
                        Visibilty_settong_bottomsheet.this.dismiss();
                    }
                });

            }
        });



        return binding.getRoot();
    }
}