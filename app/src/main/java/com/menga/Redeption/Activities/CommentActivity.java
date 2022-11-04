package com.menga.Redeption.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.CommentAdapter;
import com.menga.Redeption.Models.CommentModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityCommentBinding;

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {
    ActivityCommentBinding binding;
    Intent intent;
    String postId;
    String postedBy;
    FirebaseDatabase database;
   FirebaseAuth auth;
   ArrayList <CommentModel>list=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        intent = getIntent();
        postId = intent.getStringExtra("PostId");
        postedBy = intent.getStringExtra("PostedBy");
        binding.commentbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.sendbtn.setEnabled(false);
                binding.sendbtn.setBackground(getDrawable(R.drawable.baseline_send_black_24dp));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String comment = binding.commentbox.getText().toString();
                if (!comment.isEmpty()) {
                    binding.sendbtn.setEnabled(true);
                    binding.sendbtn.setBackground(getDrawable(R.drawable.send));
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommentModel commentModel = new CommentModel();
                commentModel.setCommentby(FirebaseAuth.getInstance().getUid());
                commentModel.setTime(new Date().getTime());
                commentModel.setPostedby(postedBy);
                commentModel.setPostid(postId);
                commentModel.setCommentbody(binding.commentbox.getText().toString());
                database.getReference().child("posts")
                        .child(postId)
                        .child("comments")
                        .push()
                        .setValue(commentModel);
                binding.commentbox.setText("");

                NotificationModel notificationModel=new NotificationModel();
                notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                notificationModel.setTime(new Date().getTime());
                notificationModel.setPostid(postId);
                notificationModel.setPostedby(postedBy);
                notificationModel.setNotificationtype("comment");
                FirebaseDatabase.getInstance().getReference().child("Notifications").child(postedBy).push().setValue(notificationModel);

            }
        });
        CommentAdapter commentAdapter = new CommentAdapter(list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commentsrv.setAdapter(commentAdapter);
        binding.commentsrv.setLayoutManager(layoutManager);
//        database.getReference().child("posts")
//                .child(postId)
//                .child("comments")
////                .addValueEventListener(new ValueEventListener() {
////            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
//                    commentModel.setCommentid(dataSnapshot.getKey());
//                    list.add(commentModel);
//                }
//                commentAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        database.getReference().child("posts")
                .child(postId)
                .child("comments")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CommentModel commentModel = snapshot.getValue(CommentModel.class);
                    commentModel.setCommentid(snapshot.getKey());
                    list.add(commentModel);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                database.getReference().child("posts")
                        .child(postId)
                        .child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                            commentModel.setCommentid(dataSnapshot.getKey());
                            list.add(commentModel);
                        }
                        commentAdapter.notifyDataSetChanged();
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


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}