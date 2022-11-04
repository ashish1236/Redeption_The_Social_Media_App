package com.menga.Redeption.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.LikedusersAdapter;
import com.menga.Redeption.Models.LikedModel;
import com.menga.Redeption.databinding.ActivityLikesBinding;

import java.util.ArrayList;

public class LikesActivity extends AppCompatActivity {
ActivityLikesBinding binding;
Intent intent;
String postId;

ArrayList<LikedModel>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        postId=intent.getStringExtra("PostId");
        LikedusersAdapter adapter=new LikedusersAdapter(list,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.likedusersrv.setLayoutManager(layoutManager);
        binding.likedusersrv.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("posts").child(postId).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot datasnapshot:snapshot.getChildren()){
                    LikedModel likedModel=datasnapshot.getValue(LikedModel.class);
                    likedModel.setLikedid(datasnapshot.getKey());
                    list.add(likedModel);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*   this code is use for get data to fragmant to fragmant like intent;

         Bundle bundle=this.getArguments();
       String key= bundle.getString("postid");


       // this code is for send data//
        /*  Bundle bundle = new Bundle();
         bundle.putString("postid",model.getPostid()); // Put anything what you want

          AppCompatActivity appCompatActivity=(AppCompatActivity)view.getContext();

         likedusersFragment.setArguments(bundle);
         appCompatActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.modal_in,R.anim.modal_out).replace(R.id.conteiner, likedusersFragment).addToBackStack(null).commit();

     */

    }
}