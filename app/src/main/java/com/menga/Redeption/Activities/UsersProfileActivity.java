package com.menga.Redeption.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.FollowersAdapter;
import com.menga.Redeption.Adapters.FollowingAdapter;
import com.menga.Redeption.Adapters.UserspostsAdapter;
import com.menga.Redeption.Bottom_sheets.UsersProfilebottomsheetFragment;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityUsersProfileBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class UsersProfileActivity extends AppCompatActivity {
    ActivityUsersProfileBinding binding;
    Intent intent;
    ArrayList<FollowersModel> followersModelArrayList = new ArrayList<>();
    ArrayList<FollowingModel> followingModelArrayList = new ArrayList<>();
    ArrayList<PostModel>postModelArrayList=new ArrayList<>();
    public static String USERID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        String UserId = intent.getStringExtra("usersid");
        USERID=intent.getStringExtra("usersid");

        ///here code for following or follow buttun/////
        FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("following").child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.followingbuttonlayout));
                    binding.followbtn.setText("Following");
                    binding.followbtn.setTextColor(Color.GRAY);
                    binding.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.followbtn.setText("Follow");
                            binding.followbtn.setTextColor(Color.GRAY);
                            binding.followbtn.setEnabled(false);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(UserId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue();
                                }
                            });
                        }
                    });

                } else {
                    binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.followingbuttonlayout));
                    binding.followbtn.setText("Follow");
                    binding.followbtn.setTextColor(Color.GRAY);
                    binding.followbtn.setEnabled(true);
                    binding.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FollowersModel model = new FollowersModel();
                            model.setFollowedate(new Date().getTime());
                            model.setFollowedby(FirebaseAuth.getInstance().getUid());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FollowingModel followingModel = new FollowingModel();
                                    followingModel.setFollowingat(new Date().getTime());
                                    followingModel.setFollowingto(UserId);
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(UserId).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            NotificationModel notificationModel=new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setNotificationtype("follow");
                                            notificationModel.setNotifiacationfollowid(UserId);
                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(UserId).push().setValue(notificationModel);

                                        }
                                    });
                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new StyleableToast.Builder(getApplicationContext())
                        .backgroundColor(Color.GRAY)
                        .text(error.getMessage())
                        .iconStart(R.drawable.error)
                        .textColor(Color.WHITE)
                        .show();

            }
        });



        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.online.setVisibility(View.VISIBLE);

                }else {
                   binding.online.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (!UserId.equals(FirebaseAuth.getInstance().getUid())){
            binding.followbtn.setVisibility(View.VISIBLE);
        }else {
            binding.followbtn.setVisibility(View.GONE);

        }
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    long followerscount=(long)snapshot.getChildrenCount();
                    binding.Followers.setText(Long.toString(followerscount));
//                    if (followerscount<100){
//                        binding.starimg.setVisibility(View.GONE);
//                    }
//                    if (followerscount>100){
//                        binding.starimg.setVisibility(View.VISIBLE);
//                        binding.starimg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.silverstar));
//                    }else if (followerscount>500){
//                        binding.starimg.setVisibility(View.VISIBLE);
//                        binding.starimg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bronzestar));
//                    }else if (followerscount>1000){
//                        binding.starimg.setVisibility(View.VISIBLE);
//                        binding.starimg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.goldstar));
//                    }else if (followerscount>5000){
//                        binding.starimg.setVisibility(View.VISIBLE);
//                        binding.starimg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rubistar));
//                    }
                    if (followerscount>10){
                        binding.followerstextView.setVisibility(View.VISIBLE);
                        binding.myfolowersrv.setVisibility(View.VISIBLE);


                    }else {
                        binding.followerstextView.setVisibility(View.GONE);
                        binding.myfolowersrv.setVisibility(View.GONE);

                    }

                }
                else {
                    binding.Followers.setText("0");
                    binding.followerstextView.setVisibility(View.GONE);
                    binding.myfolowersrv.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long followingscount=(long)snapshot.getChildrenCount();
                    binding.Following.setText(Long.toString(followingscount));
                    if (followingscount>10){
                        binding.FollowingtextView.setVisibility(View.VISIBLE);
                        binding.followingrv.setVisibility(View.VISIBLE);
                        binding.View8.setVisibility(View.VISIBLE);
                    }else {
                        binding.FollowingtextView.setVisibility(View.GONE);
                        binding.followingrv.setVisibility(View.GONE);
                        binding.View8.setVisibility(View.GONE);
                    }
                }
                else{
                    binding.Following.setText("0");
                    binding.FollowingtextView.setVisibility(View.GONE);
                    binding.followingrv.setVisibility(View.GONE);
                    binding.View8.setVisibility(View.GONE);
                    binding.textView3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdata userdata = snapshot.getValue(Userdata.class);
                binding.currentusername.setText(userdata.getName());
                binding.proffesion.setText(userdata.getBio());
                    binding.website.setText(userdata.getWebsite());
                Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.profilepohoto);
                Picasso.get().load(userdata.getBackground_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.backgroundlayout);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FollowersAdapter followersAdapter = new FollowersAdapter(followersModelArrayList, getApplicationContext());
        LinearLayoutManager follwerlayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        follwerlayout.setStackFromEnd(true);
        follwerlayout.setReverseLayout(true);
        binding.myfolowersrv.setAdapter(followersAdapter);
        binding.myfolowersrv.setLayoutManager(follwerlayout);
        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followersModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FollowersModel followersModel = dataSnapshot.getValue(FollowersModel.class);
                    followersModelArrayList.add(followersModel);
                }
                followersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new StyleableToast.Builder(getApplicationContext())
                        .backgroundColor(Color.GRAY)
                        .text(error.getMessage())
                        .iconStart(R.drawable.error)
                        .textColor(Color.WHITE)
                        .show();

            }
        });


        FollowingAdapter followingAdapter = new FollowingAdapter(followingModelArrayList, getApplicationContext());
        LinearLayoutManager follinglayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        follinglayout.setStackFromEnd(true);
        follinglayout.setReverseLayout(true);
        binding.followingrv.setAdapter(followingAdapter);
        binding.followingrv.setLayoutManager(follinglayout);

        FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followingModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FollowingModel followingModel = dataSnapshot.getValue(FollowingModel.class);
                    followingModelArrayList.add(followingModel);
                }
                followingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new StyleableToast.Builder(getApplicationContext())
                        .backgroundColor(Color.GRAY)
                        .text(error.getMessage())
                        .iconStart(R.drawable.error)
                        .textColor(Color.WHITE)
                        .show();

            }
        });



binding.a.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});

UserspostsAdapter postAdapter =new UserspostsAdapter(postModelArrayList,getApplicationContext());
        LinearLayoutManager mypostslayout=new LinearLayoutManager(getApplicationContext());
        mypostslayout.setReverseLayout(true);
        mypostslayout.setStackFromEnd(true);
        binding.postsrv.setAdapter(postAdapter);
        binding.postsrv.setLayoutManager(mypostslayout);
       FirebaseDatabase.getInstance().getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostModel postModel=dataSnapshot.getValue(PostModel.class);
                    if(postModel.getPostby().equals(UserId)){
                        postModelArrayList.add(postModel);
                        postModel.setPostid(dataSnapshot.getKey());
                        long postcount=(long)postModelArrayList.size();
                        binding.Post.setText(Long.toString(postcount));
                    }

                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
///here code for following or follow buttun/////
binding.settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("userid",UserId);
     UsersProfilebottomsheetFragment usersprofileBottomsheetFragment=new UsersProfilebottomsheetFragment();
        usersprofileBottomsheetFragment.setArguments(bundle);
        usersprofileBottomsheetFragment.show(getSupportFragmentManager(),usersprofileBottomsheetFragment.getTag());

    }
});

try {
    binding.a.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent=new Intent(UsersProfileActivity.this, new Usersfriendactivity().getClass());
           startActivity(intent);
        }
    });

    binding.c.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(UsersProfileActivity.this, new Usersfriendactivity().getClass());
            startActivity(intent);
        }
    });
}catch (Exception e){
}finally {

}



        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }





}