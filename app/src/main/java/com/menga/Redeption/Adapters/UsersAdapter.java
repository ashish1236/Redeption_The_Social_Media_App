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
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.Activities.UsersProfileActivity;
import com.menga.Redeption.databinding.UsersBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {
 Context context;
    ArrayList<Userdata> list;
    public UsersAdapter(Context context, ArrayList<Userdata> list) {
        this.context = context;
        this.list = list;
    }
public void filterList(ArrayList<Userdata>filterlist){
        list=filterlist;
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.users,parent,false);
        return new  viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Userdata userdata= list.get(position);
        holder.binding.searchbio.setText(userdata.getBio());
        holder.binding.searchname.setText(userdata.getName());

        FirebaseDatabase.getInstance().getReference().child("Users").child(userdata.getUserID()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(userdata.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(userdata.getUserID()).addValueEventListener(new ValueEventListener() {
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


        Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.searchuserimg);
///here code for following or follow buttun/////
        FirebaseDatabase.getInstance().getReference().child("Users").child(userdata.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followingbuttonlayout));
                    holder.binding.followbtn.setText("Following");
                    holder.binding.followbtn.setTextColor(Color.GRAY);
                    holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(userdata.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(userdata.getUserID()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.followbtn.setText("Follow");
                                            holder.binding.followbtn.setTextColor(Color.GRAY);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else {
                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followbtnlayout));
                    holder.binding.followbtn.setText("Follow");
                    holder.binding.followbtn.setTextColor(Color.GRAY);
                    holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FollowersModel model=new FollowersModel();
                            model.setFollowedby(FirebaseAuth.getInstance().getUid());
                            model.setFollowedate(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(userdata.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FollowingModel followingModel=new FollowingModel();
                                    followingModel.setFollowingat(new Date().getTime());
                                    followingModel.setFollowingto(userdata.getUserID());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(userdata.getUserID()).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            NotificationModel notificationModel=new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setNotificationtype("follow");
                                            notificationModel.setNotifiacationfollowid(userdata.getUserID());
                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(userdata.getUserID()).push().setValue(notificationModel);
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
///here code for following or follow buttun/////


        holder.binding.userslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid",userdata.getUserID() );
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
UsersBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=UsersBinding.bind(itemView);
        }
    }
}
