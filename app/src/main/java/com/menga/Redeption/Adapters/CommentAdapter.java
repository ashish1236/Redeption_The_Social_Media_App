package com.menga.Redeption.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Activities.UsersProfileActivity;
import com.menga.Redeption.Bottom_sheets.DeletecommentFragment;
import com.menga.Redeption.Models.CommentModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.CommentslayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder>{
    ArrayList<CommentModel>list;
    Context context;
    public CommentAdapter(ArrayList<CommentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.commentslayout,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CommentModel commentModel=list.get(position);
        holder.binding.searchname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid",commentModel.getCommentby());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.binding.searchuserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, UsersProfileActivity.class);
                intent.putExtra("usersid",commentModel.getCommentby());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
      String time = TimeAgo.using(commentModel.getTime());
        /* You can use java.util.Calendar.getInstance().getTimeInMillis()*/
        /* Also, with java 8, java.time.Instant.now().toEpochMilli() */
        holder.binding.cmtime.setText(time);



        FirebaseDatabase.getInstance().getReference().child("Users").child(commentModel.getCommentby()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(commentModel.getCommentby()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(commentModel.getCommentby()).addValueEventListener(new ValueEventListener() {
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





        FirebaseDatabase.getInstance().getReference().child("Users").child(commentModel.getCommentby()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Userdata userdata=snapshot.getValue(Userdata.class) ;
               holder.binding.searchname.setText(userdata.getName());
               Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.searchuserimg);
               holder.binding.comment.setText(commentModel.getCommentbody());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        ColorStateList oldcolor=holder.binding.cmtime.getTextColors();
//        FirebaseDatabase.getInstance().getReference().child("posts")
//                .child(commentModel.getPostid())
//                .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if (snapshot.exists()){
//                    holder.binding.firetext.setTextColor(ContextCompat.getColor(context,R.color.red));
//                    holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.liked,0,0);
//                    holder.binding.firetext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FirebaseDatabase.getInstance().getReference().child("posts")
//                                    .child(commentModel.getPostid())
//                                    .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//
//                                }
//                            });
//                        }
//                    });
//                }else {
//                    holder.binding.firetext.setTextColor(oldcolor);
//                    holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.like,0,0);
//                    holder.binding.firetext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FirebaseDatabase.getInstance().getReference().child("posts")
//                                    .child(commentModel.getPostid())
//                                    .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    NotificationModel notificationModel=new NotificationModel();
//                                    notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
//                                    notificationModel.setTime(new Date().getTime());
//                                    notificationModel.setPostid(commentModel.getPostid());
//                                    notificationModel.setPostedby(commentModel.getPostedby());
//                                    notificationModel.setNotificationtype("firestoke");
//
//                                    FirebaseDatabase.getInstance().getReference().child("Notifications").child(commentModel.getPostedby()).push().setValue(notificationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            holder.binding.firetext.setTextColor(ContextCompat.getColor(context,R.color.red));
//                                            holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.liked,0,0);
//                                        }
//                                    });
//
//
//                                }
//                            });
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.exists())
//                holder.binding.firetext.setTextColor(oldcolor);
//                holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.like,0,0);
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        FirebaseDatabase.getInstance().getReference().child("posts")
                .child(commentModel.getPostid())
                .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.firetext.setTextColor(ContextCompat.getColor(context,R.color.red));
                    holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.liked,0,0);
                    holder.binding.firetext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("posts")
                                    .child(commentModel.getPostid())
                                    .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                  holder.binding.firetext.setTextColor(oldcolor);
                                  holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.like,0,0);
                                }
                            });
                        }
                    });
                }else {
                    holder.binding.firetext.setTextColor(oldcolor);
                    holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.like,0,0);
                    holder.binding.firetext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("posts")
                                    .child(commentModel.getPostid())
                                    .child("comments").child(commentModel.getCommentid()).child("Firestokes").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    NotificationModel notificationModel=new NotificationModel();
                                    notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                    notificationModel.setTime(new Date().getTime());
                                    notificationModel.setPostid(commentModel.getPostid());
                                    notificationModel.setPostedby(commentModel.getPostedby());
                                    notificationModel.setNotificationtype("firestoke");

                                    FirebaseDatabase.getInstance().getReference().child("Notifications").child(commentModel.getPostedby()).push().setValue(notificationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.firetext.setTextColor(ContextCompat.getColor(context,R.color.red));
                                            holder.binding.firetext.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.liked,0,0);
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


        FirebaseDatabase.getInstance().getReference().child("posts")
                .child(commentModel.getPostid())
                .child("comments").child(commentModel.getCommentid()).child("Firestokes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  long firestokecount=(long) snapshot.getChildrenCount();
                  holder.binding.firetext.setText(Long.toString(firestokecount));
              }else{
                  holder.binding.firetext.setText("0");

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


holder.binding.commentlayout.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {

            Bundle bundel=new Bundle();
            bundel.putString("commentid", commentModel.getCommentid());
            bundel.putString("postid", commentModel.getPostid());
            bundel.putString("commentby",commentModel.getCommentby());
            DeletecommentFragment deletecommentFragment=new DeletecommentFragment();
            deletecommentFragment.setArguments(bundel);
            deletecommentFragment.show(((FragmentActivity)context).getSupportFragmentManager(),deletecommentFragment.getTag());


        return false;
    }
});

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
CommentslayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CommentslayoutBinding.bind(itemView);
        }
    }
}