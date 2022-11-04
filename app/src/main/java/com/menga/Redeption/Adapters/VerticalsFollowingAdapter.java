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
import com.menga.Redeption.databinding.VerticalfollowinglayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class VerticalsFollowingAdapter extends RecyclerView.Adapter<VerticalsFollowingAdapter.viewHolder> {
    Context context;
    ArrayList<FollowingModel>list;

    public VerticalsFollowingAdapter(Context context, ArrayList<FollowingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.verticalfollowinglayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FollowingModel model=list.get(position);
        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(model.getFollowingto()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(model.getFollowingto()).addValueEventListener(new ValueEventListener() {
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


        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.followinbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followingbuttonlayout));
                    holder.binding.followinbtn.setText("Following");
                    holder.binding.followinbtn.setTextColor(Color.GRAY);
                    holder.binding.followinbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowingto()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.followinbtn.setText("Follow");
                                            holder.binding.followinbtn.setTextColor(Color.GRAY);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else {
                    holder.binding.followinbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followbtnlayout));
                    holder.binding.followinbtn.setText("Follow");
                    holder.binding.followinbtn.setTextColor(Color.GRAY);
                    holder.binding.followinbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FollowersModel fmodel=new FollowersModel();
                            fmodel.setFollowedby(FirebaseAuth.getInstance().getUid());
                            fmodel.setFollowedate(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FollowingModel followingModel=new FollowingModel();
                                    followingModel.setFollowingat(new Date().getTime());
                                    followingModel.setFollowingto(model.getFollowingto());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowingto()).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            NotificationModel notificationModel=new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setNotificationtype("follow");
                                            notificationModel.setNotifiacationfollowid(model.getFollowingto());
                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(model.getFollowingto()).push().setValue(notificationModel);
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



//        holder.binding.followinbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getFollowingto()).removeValue();
//                FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowingto()).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                    }
//                });
//            }
//        });



        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getFollowingto())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Userdata userdata=snapshot.getValue(Userdata.class);
                        Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.vgimage);
                        holder.binding.vgname.setText(userdata.getName());
                        holder.binding.vgbio.setText(userdata.getBio());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.followinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid", model.getFollowingto());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        VerticalfollowinglayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=VerticalfollowinglayoutBinding.bind(itemView);
        }
    }
}
