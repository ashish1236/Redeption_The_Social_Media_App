package com.menga.Redeption.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.AllfollowersBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.viewHolder> {
    //make array list//  ArrayList<PASS HERE YOUR Model>
    ArrayList<FollowersModel> list;
    //make context//
    Context context;

    public FollowersAdapter(ArrayList<FollowersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //after this create constracter of your adapter//
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate your view here who maked you//LayoutInflater.from(context).inflate(pass here your layout ,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.allfollowers, parent, false);
        //retun your viewholder//
        return new viewHolder(view);
        //after this goto onbindviewHolder and set your data//
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scroolview);
        holder.itemView.setAnimation(animation);
        FollowersModel model = list.get(position);

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

        //here profileimg  is taken from viewHolder   ImageView profileimg;// profileimg=itemView.findViewById(R.id.profilepohoto);
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getFollowedby())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Userdata userdata = snapshot.getValue(Userdata.class);
                        Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.profilepohoto);
                        holder.binding.follwersname.setText(userdata.getName());
// here all data will set on vertical frigment////


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

    //First make a viewHolder class and extend it recyclervieholder//
    public class viewHolder extends RecyclerView.ViewHolder {
        // get all view of your adapter by using find view by id//
        AllfollowersBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AllfollowersBinding.bind(itemView);

        }
    }
}
