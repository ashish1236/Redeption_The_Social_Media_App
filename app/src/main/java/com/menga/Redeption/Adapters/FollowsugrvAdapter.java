package com.menga.Redeption.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FollowsugrvdesignBinding;

import java.util.ArrayList;

public class FollowsugrvAdapter extends RecyclerView.Adapter<FollowsugrvAdapter.viewHolder>{
    ArrayList<FollowingModel>list;
    Context context;

    public FollowsugrvAdapter(ArrayList<FollowingModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.followsugrvdesign,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FollowingModel model = list.get(position);
        //        FirebaseDatabase.getInstance().getReference().child("Users")
//                .child(model.getFollowingto())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Userdata userdata=snapshot.getValue(Userdata.class);
//                        Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.profilepohoto);
//                        holder.binding.follwersname.setText(userdata.getName());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

//        here code for following or follow buttun/////
//        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followingbuttonlayout));
//                    holder.binding.followbtn.setText("Following");
//                    holder.binding.followbtn.setTextColor(Color.GRAY);
//                    holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowingto()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            holder.binding.followbtn.setText("Follow");
//                                            holder.binding.followbtn.setTextColor(Color.GRAY);
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//                else {
//                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followbtnlayout));
//                    holder.binding.followbtn.setText("Follow");
//                    holder.binding.followbtn.setTextColor(Color.GRAY);
//                    holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FollowersModel fmodel=new FollowersModel();
//                            fmodel.setFollowedby(FirebaseAuth.getInstance().getUid());
//                            fmodel.setFollowedate(new Date().getTime());
//                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    FollowingModel followingModel=new FollowingModel();
//                                    followingModel.setFollowingat(new Date().getTime());
//                                    followingModel.setFollowingto(model.getFollowingto());
//
//                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowingto()).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            NotificationModel notificationModel=new NotificationModel();
//                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
//                                            notificationModel.setTime(new Date().getTime());
//                                            notificationModel.setNotificationtype("follow");
//                                            notificationModel.setNotifiacationfollowid(model.getFollowingto());
//                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(model.getFollowingto()).push().setValue(notificationModel);
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
/////here code for following or follow buttun/////
//
//        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("online").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    holder.binding.online.setVisibility(View.VISIBLE);
//                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(model.getFollowingto()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()){
//                                holder.binding.online.setVisibility(View.VISIBLE);
//                            }else {
//                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(model.getFollowingto()).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if (snapshot.exists()){
//                                            holder.binding.online.setVisibility(View.GONE);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }else {
//                    holder.binding.online.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        FollowsugrvdesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=FollowsugrvdesignBinding.bind(itemView);
        }
    }
}
