package com.menga.Redeption.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.VerticalfollowerslayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VerticalFollowersAdapter extends RecyclerView.Adapter<VerticalFollowersAdapter.viewHolder>{

    ArrayList<FollowersModel>list;
    //make context//
    Context context;

    public VerticalFollowersAdapter(ArrayList<FollowersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.verticalfollowerslayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

//        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scroolview);
//        holder.itemView.setAnimation(animation);
        FollowersModel model=list.get(position);
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

        holder.binding.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("followers").child(model.getFollowedby()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Users").child(model.getFollowedby()).child("following").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.binding.removebtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.followbtnlayout));
                        holder.binding.removebtn.setTextColor(ContextCompat.getColor(context,R.color.black));
                        holder.binding.removebtn.setBackgroundTintMode(null);
                        holder.binding.removebtn.setEnabled(false);
                        holder.binding.removebtn.setText("Removed");
                    }
                });

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
        VerticalfollowerslayoutBinding binding;
      public viewHolder(@NonNull View itemView) {
          super(itemView);
          binding=VerticalfollowerslayoutBinding.bind(itemView);
      }
  }
}
