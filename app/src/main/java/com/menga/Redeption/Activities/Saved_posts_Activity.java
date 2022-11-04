package com.menga.Redeption.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.savedpostsAdapter;
import com.menga.Redeption.Models.Saved_Posts_Model;
import com.menga.Redeption.databinding.ActivitySavedPostsBinding;

import java.util.ArrayList;

public class Saved_posts_Activity extends AppCompatActivity {
ActivitySavedPostsBinding binding;
    ArrayList<Saved_Posts_Model> savedpostslist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySavedPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        savedpostsAdapter savedpostsAdapter=new savedpostsAdapter(savedpostslist,getApplicationContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
       binding.savedpostrv.setAdapter(savedpostsAdapter);
        binding.savedpostrv.setLayoutManager(layoutManager);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Saved_posts").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedpostslist.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Saved_Posts_Model saved_posts_model=dataSnapshot.getValue(Saved_Posts_Model.class);
                    saved_posts_model.setSnap_id(dataSnapshot.getKey());
                    savedpostslist.add(saved_posts_model);
                }
                binding.progressBar.setVisibility(View.GONE);
                savedpostsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }


}