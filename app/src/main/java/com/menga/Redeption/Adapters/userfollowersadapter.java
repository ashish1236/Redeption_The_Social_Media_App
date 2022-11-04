package com.menga.Redeption.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Activities.UsersProfileActivity;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.UsersfollowersverticalsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class userfollowersadapter extends RecyclerView.Adapter<userfollowersadapter.viewHolder> {

    ArrayList<FollowersModel>list;
    //make context//
    Context context;
    public userfollowersadapter(ArrayList<FollowersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public userfollowersadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.usersfollowersverticals,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userfollowersadapter.viewHolder holder, int position) {

//        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scroolview);
//        holder.itemView.setAnimation(animation);
        FollowersModel model=list.get(position);
        if (model.getFollowedby().equals(FirebaseAuth.getInstance().getUid())){
            holder.binding.removebtn.setVisibility(View.GONE);
        }else {
            holder.binding.removebtn.setVisibility(View.VISIBLE);
        }

        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowedby()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(model.getFollowedby()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(model.getFollowedby()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            holder.binding.online.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    holder.binding.online.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowedby()).child("followers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.removebtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followingbuttonlayout));
                    holder.binding.removebtn.setText("Following");
                    holder.binding.removebtn.setTextColor(Color.GRAY);
                    holder.binding.removebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowedby()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowedby()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.removebtn.setText("Follow");
                                            holder.binding.removebtn.setTextColor(Color.GRAY);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else {
                    holder.binding.removebtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followbtnlayout));
                    holder.binding.removebtn.setText("Follow");
                    holder.binding.removebtn.setTextColor(Color.GRAY);
                    holder.binding.removebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FollowersModel fmodel=new FollowersModel();
                            fmodel.setFollowedby(FirebaseAuth.getInstance().getUid());
                            fmodel.setFollowedate(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowedby()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FollowingModel followingModel=new FollowingModel();
                                    followingModel.setFollowingat(new Date().getTime());
                                    followingModel.setFollowingto(model.getFollowedby());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowedby()).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            NotificationModel notificationModel=new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setNotificationtype("follow");
                                            notificationModel.setNotifiacationfollowid(model.getFollowedby());
                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(model.getFollowedby()).push().setValue(notificationModel);
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

            }
        });




        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getFollowedby())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Userdata userdata=snapshot.getValue(Userdata.class);
                        Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.vsimage);
                        holder.binding.vsname.setText(userdata.getName());
                        holder.binding.vsbio.setText(userdata.getBio());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.binding.followerslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid", model.getFollowedby());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        UsersfollowersverticalsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=UsersfollowersverticalsBinding.bind(itemView);
        }
    }
}
