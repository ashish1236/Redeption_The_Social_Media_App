package com.menga.Redeption.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.DeletedpostAdapter;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.databinding.ActivityDeletedpostsBinding;

import java.util.ArrayList;

public class Deletedposts extends AppCompatActivity {
    ActivityDeletedpostsBinding binding;

    ArrayList<PostModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeletedpostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DeletedpostAdapter deletedpostAdapter = new DeletedpostAdapter(list, Deletedposts.this);
        LinearLayoutManager deletedpostlayout = new LinearLayoutManager(Deletedposts.this);
        deletedpostlayout.setReverseLayout(true);
        deletedpostlayout.setStackFromEnd(true);
        binding.deletedrv.setAdapter(deletedpostAdapter);
        binding.deletedrv.setLayoutManager(deletedpostlayout);
        FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    postModel.setPostid(dataSnapshot.getKey());
                    list.add(postModel);
                }
                deletedpostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.backbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAbsoluteAdapterPosition());
                deletedpostAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(binding.deletedrv);

    }
}