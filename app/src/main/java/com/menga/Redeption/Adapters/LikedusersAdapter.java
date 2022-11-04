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
import com.menga.Redeption.Models.LikedModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.LikedusersrvdesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class LikedusersAdapter extends RecyclerView.Adapter<LikedusersAdapter.viewHolder> {
    ArrayList<LikedModel> list;
    Context context;

    public LikedusersAdapter(ArrayList<LikedModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.likedusersrvdesign, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        LikedModel model = list.get(position);
        holder.binding.likedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid",model.getLikedby());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getLikedby()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(model.getLikedby()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(model.getLikedby()).addValueEventListener(new ValueEventListener() {
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


        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getLikedby()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdata userdata = snapshot.getValue(Userdata.class);
                holder.binding.searchname.setText(userdata.getName());
                holder.binding.searchbio.setText(userdata.getBio());
                Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.searchuserimg);

                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getLikedby()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followingbuttonlayout));
                            holder.binding.followbtn.setText("Following");
                            holder.binding.followbtn.setTextColor(Color.GRAY);
                            holder.binding.followbtn.setEnabled(false);
                        }else {
                            holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FollowersModel followersModel=new FollowersModel();
                                    followersModel.setFollowedate(new Date().getTime());
                                    followersModel.setFollowedby(FirebaseAuth.getInstance().getUid());
                                    followersModel.setFollowedate(new Date().getTime());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(model.getLikedid()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(followersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FollowingModel followingModel=new FollowingModel();
                                            followingModel.setFollowingat(new Date().getTime());
                                            followingModel.setFollowingto(model.getLikedid());
                                            followingModel.setFollowingat(new Date().getTime());
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(model.getLikedid()).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        LikedusersrvdesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = LikedusersrvdesignBinding.bind(itemView);
        }
    }
}
