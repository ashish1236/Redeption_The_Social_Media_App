package com.menga.Redeption.Adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.menga.Redeption.Activities.CommentActivity;
import com.menga.Redeption.Activities.LikesActivity;
import com.menga.Redeption.Bottom_sheets.MypostbottomsheetFragmant;
import com.menga.Redeption.Models.LikedModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.Saved_Posts_Model;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.MypostsrvdesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;

// create public class viewHolder  and extend it to RecyclerView.ViewHolder implement method
public class MypostsAdapter extends RecyclerView.Adapter<MypostsAdapter.viewHolder>{
    ArrayList<PostModel>list;
    Context context;
    public MypostsAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mypostsrvdesign,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModel model=list.get(position);
//        try {
//            if (model.getVideo().equals("")) {
//                holder.binding.videoView.setVisibility(View.GONE);
//            } else {
//                holder.binding.postimage.setVisibility(View.GONE);
//                holder.binding.pollcard.setVisibility(View.GONE);
//                holder.binding.videoView.setVisibility(View.VISIBLE);
//                holder.binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        mediaPlayer.setLooping(true);
//                    }
//                });
//                holder.binding.videoView.setVideoURI(Uri.parse(model.getVideo()));
//                holder.binding.videoView.start();
//            }
//        } catch (Exception e) {
//        } finally {
//
        if (model.getPostimage().equals("")) {
            holder.binding.postimage.setVisibility(View.GONE);
        } else {
            holder.binding.postimage.setVisibility(View.VISIBLE);
            Picasso.get().load(model.getPostimage()).placeholder(R.drawable.aptavatar).into(holder.binding.postimage);
        }
        if (model.getPostdiscription().equals("")) {
            holder.binding.posttext.setVisibility(View.GONE);
        } else {
            holder.binding.posttext.setVisibility(View.VISIBLE);
            holder.binding.posttext.setText(model.getPostdiscription());
        }

            if (model.getPollquestion().equals("")) {
                holder.binding.pollcard.setVisibility(View.GONE);

            } else {
                holder.binding.postimage.setVisibility(View.GONE);
                holder.binding.posttext.setVisibility(View.GONE);
                holder.binding.pollcard.setVisibility(View.VISIBLE);
                holder.binding.polltext.setText(model.getPollquestion());


                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.binding.yes.setEnabled(false);
                            holder.binding.no.setEnabled(false);
                            holder.binding.yes.setTextColor(ContextCompat.getColor(context, R.color.blue));
                            holder.binding.yes.setBackground(ContextCompat.getDrawable(context,R.drawable.pollclickedlayout));
                            FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        long yes = (long) snapshot.getChildrenCount();
                                        holder.binding.yes.setText(yes + " people hits yes ");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("No_count").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        long no = (long) snapshot.getChildrenCount();
                                        holder.binding.no.setText(no + " people hits no ");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {

                            FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("No_count").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        holder.binding.yes.setEnabled(false);
                                        holder.binding.no.setEnabled(false);
                                        holder.binding.no.setBackground(ContextCompat.getDrawable(context,R.drawable.pollclickedlayout));
                                        holder.binding.no.setTextColor(ContextCompat.getColor(context, R.color.blue));

                                        FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    long yes = (long) snapshot.getChildrenCount();
                                                    holder.binding.yes.setText(yes + " people hits yes ");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("No_count").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    long no = (long) snapshot.getChildrenCount();
                                                    holder.binding.no.setText(no + " people hit no ");


                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    } else {
                                        holder.binding.yes.setEnabled(true);
                                        holder.binding.no.setEnabled(true);
                                        holder.binding.yes.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        holder.binding.yes.setEnabled(false);
                                                        holder.binding.no.setEnabled(false);
                                                    }
                                                });
                                            }
                                        });
                                        holder.binding.no.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("No_count").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        holder.binding.yes.setEnabled(false);
                                                        holder.binding.no.setEnabled(false);
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


//                        holder.binding.yes.setEnabled(true);
//                        holder.binding.no.setEnabled(true);
//                        holder.binding.yes.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        holder.binding.yes.setEnabled(false);
//                                        holder.binding.no.setEnabled(false);
//                                    }
//                                });
//                            }
//                        });
//                        holder.binding.no.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("No_count").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        holder.binding.yes.setEnabled(false);
//                                        holder.binding.no.setEnabled(false);
//                                    }
//                                });
//                            }
//                        });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }


        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostby()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.binding.online.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Visibilty_Always").child(model.getPostby()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                holder.binding.online.setVisibility(View.VISIBLE);
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("Visibilty_Never").child(model.getPostby()).addValueEventListener(new ValueEventListener() {
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



        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>code for delete post<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

            holder.binding.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("postid", model.getPostid());
                    bundle.putString("postedby", model.getPostby());
                    MypostbottomsheetFragmant mypostbottomsheetFragmant = new MypostbottomsheetFragmant();
                    mypostbottomsheetFragmant.setArguments(bundle);
                    mypostbottomsheetFragmant.show(((FragmentActivity) context).getSupportFragmentManager(), mypostbottomsheetFragmant.getTag());
                }
            });


            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^>code for delete post^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//


            //here data for showing my posts //////////////////////////
            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostby()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Userdata userdata = snapshot.getValue(Userdata.class);
                    String time = TimeAgo.using(model.getPostAt());
                    holder.binding.posttime.setText(time);
                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.userprofile);
                    holder.binding.username.setText(userdata.getName());
                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("likes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                long likes = (long) snapshot.getChildrenCount();
                                holder.binding.firestokecount.setText(Long.toString(likes));
                            } else {
                                holder.binding.firestokecount.setText("0");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("comments").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                long commentscount = (long) snapshot.getChildrenCount();
                                holder.binding.commentcount.setText(Long.toString(commentscount));
                            } else {
                                holder.binding.commentcount.setText("0");
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

            ColorStateList oldcolr = holder.binding.comment.getTextColors();
            FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("likes").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.binding.likes.setTextColor(Color.RED);
                        holder.binding.likes.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.liked, 0, 0);
                        holder.binding.likes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.binding.likes.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like, 0, 0);
                                holder.binding.likes.setTextColor(oldcolr);
                                FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("likes").child(FirebaseAuth.getInstance().getUid()).removeValue();
                            }
                        });
                    } else {
                        holder.binding.likes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LikedModel likedModel = new LikedModel();
                                likedModel.setLikedby(FirebaseAuth.getInstance().getUid());
                                likedModel.setLikedat(new Date().getTime());
                                holder.binding.likes.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.liked, 0, 0);
                                holder.binding.likes.setTextColor(Color.RED);
                                FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("likes").child(FirebaseAuth.getInstance().getUid()).setValue(likedModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        NotificationModel notificationModel = new NotificationModel();
                                        notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                        notificationModel.setTime(new Date().getTime());
                                        notificationModel.setPostid(model.getPostid());
                                        notificationModel.setPostedby(model.getPostby());
                                        notificationModel.setNotificationtype("firestoke");
                                        FirebaseDatabase.getInstance().getReference().child("Notifications").child(model.getPostby()).push().setValue(notificationModel);

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


            holder.binding.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("Comments_of").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                new StyleableToast.Builder(context).text("Comments are off for this post")
                                        .text("Comments are off for this post")
                                        .textColor(Color.WHITE)
                                        .iconStart(R.drawable.email)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                            } else {

                                Userdata userdata = new Userdata();
                                Intent intent = new Intent(context, CommentActivity.class);
                                //THIS IS FOR SEND DATA IN NEXT ACTIVITY//
                                intent.putExtra("PostId", model.getPostid());
                                intent.putExtra("PostedBy", model.getPostby());
                                intent.putExtra("userid", userdata.getUserID());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                ////////////////////////////////////////////////////
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            holder.binding.commentslayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostid()).child("Comments_of").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                new StyleableToast.Builder(context).text("Comments are off for this post")
                                        .text("Comments are off for this post")
                                        .textColor(Color.WHITE)
                                        .iconStart(R.drawable.email)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                            } else {

                                Userdata userdata = new Userdata();
                                Intent intent = new Intent(context, CommentActivity.class);
                                //THIS IS FOR SEND DATA IN NEXT ACTIVITY//
                                intent.putExtra("PostId", model.getPostid());
                                intent.putExtra("PostedBy", model.getPostby());
                                intent.putExtra("userid", userdata.getUserID());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                ////////////////////////////////////////////////////
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

            holder.binding.otherliked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Userdata userdata = new Userdata();
                    Intent intent = new Intent(context, LikesActivity.class);
                    //THIS IS FOR SEND DATA IN NEXT ACTIVITY//
                    intent.putExtra("PostId", model.getPostid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            ColorStateList oldcolor = holder.binding.comment.getTextColors();


            FirebaseDatabase.getInstance().getReference().child("Saved_posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.binding.save.setText("Unsave");
                        holder.binding.save.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.save, 0, 0);
                        holder.binding.save.setTextColor(ContextCompat.getColor(context, R.color.blue));

                        holder.binding.save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseDatabase.getInstance().getReference().child("Saved_posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.binding.save.setText("save");
                                        holder.binding.save.setTextColor(oldcolor);
                                        holder.binding.save.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unsave, 0, 0);
                                    }
                                });

                            }
                        });

                    } else {
                        holder.binding.save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Saved_Posts_Model saved_posts_model = new Saved_Posts_Model();
                                saved_posts_model.setPost_id(model.getPostid());
                                saved_posts_model.setPostedby(model.getPostby());
                                saved_posts_model.setSaved_by(FirebaseAuth.getInstance().getUid());
                                saved_posts_model.setSaved_at(new Date().getTime());
                                FirebaseDatabase.getInstance().getReference().child("Saved_posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).setValue(saved_posts_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.binding.save.setText("Unsave");
                                        holder.binding.save.setTextColor(ContextCompat.getColor(context, R.color.blue));

                                        holder.binding.save.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.save, 0, 0);
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
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
MypostsrvdesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=MypostsrvdesignBinding.bind(itemView);
        }
    }
}
