package com.menga.Redeption.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Models.BlockedusersModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.BlockeduersrvdesigenBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class blockedusersAdapter extends RecyclerView.Adapter<blockedusersAdapter.viewHolder>{
    ArrayList<BlockedusersModel>list;
    Context context;

    public blockedusersAdapter(ArrayList<BlockedusersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blockeduersrvdesigen,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
BlockedusersModel model=list.get(position);
        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getBlockedto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdata userdata=snapshot.getValue(Userdata.class);
                holder.binding.vsname.setText(userdata.getName());
                holder.binding.vsbio.setText(userdata.getBio());
                Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.vsimage);
                holder.binding.removebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Blocked_Users").child(model.getBlockedid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                new StyleableToast.Builder(context)
                                        .text("Now "+ userdata.getName() +" is Unblocked")
                                        .iconStart(R.drawable.unblock)
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                            }
                        });
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

    public class viewHolder extends RecyclerView.ViewHolder{
BlockeduersrvdesigenBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=BlockeduersrvdesigenBinding.bind(itemView);
        }
    }
}
