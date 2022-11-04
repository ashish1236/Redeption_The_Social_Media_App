package com.menga.Redeption.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.DeletedpostrvdesignBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.muddz.styleabletoast.StyleableToast;

public class DeletedpostAdapter extends RecyclerView.Adapter<DeletedpostAdapter.viewHolder> {
    ArrayList<PostModel> list;
    Context context;
BitmapDrawable drawable;
Bitmap bitmap;
    public DeletedpostAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.deletedpostrvdesign, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModel model = list.get(position);
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
                holder.binding.pollcard.setVisibility(View.VISIBLE);
                holder.binding.postimage.setVisibility(View.GONE);
                holder.binding.posttext.setVisibility(View.GONE);
                holder.binding.polltext.setText(model.getPollquestion());
                FirebaseDatabase.getInstance().getReference().child("Polls_posts").child(model.getPostid()).child("Yes_count").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.binding.yes.setEnabled(false);
                            holder.binding.no.setEnabled(false);
                            holder.binding.yes.setTextColor(ContextCompat.getColor(context, R.color.blue));
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


            FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(model.getPostby()).child(model.getPostid()).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        long commentscount = (long) snapshot.getChildrenCount();
                        holder.binding.comment.setText(Long.toString(commentscount));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(model.getPostby()).child(model.getPostid()).child("likes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        long likes = (long) snapshot.getChildrenCount();
                        holder.binding.likes.setText(Long.toString(likes));
                    } else {
                        holder.binding.likes.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostby()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Userdata userdata = snapshot.getValue(Userdata.class);
                    String time = TimeAgo.using(model.getPostAt());
                    holder.binding.posttime.setText(time);
                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.userprofile);
                    holder.binding.username.setText(userdata.getName());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            holder.binding.permadel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("You want Permanently delete your post")
                            .setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                  /*      FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostby()).child(model.getPostid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                new StyleableToast.Builder(context)
                                        .text("Post Permanently deleted")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                            }
                        });
                    */
                                    FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }).show();
                }
            });
            holder.binding.restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("You want Republish your deleted post ")
                            .setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                FirebaseDatabase.getInstance().getReference().child("posts").push().setValue(snapshot.getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).child(model.getPostid()).removeValue();
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                });

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .show();

                }
            });
            holder.binding.downlod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FileOutputStream outputStream = null;
                    drawable = (BitmapDrawable) holder.binding.postimage.getDrawable();
                    bitmap = drawable.getBitmap();
                    File internalstroage = Environment.getExternalStorageDirectory();
                    File directory = new File(internalstroage.getAbsolutePath() + "/FriendSpace");
                    //mkdir is use for create directory//
                    directory.mkdir();
                    //this is used for set name of file format in stroage//
                    String filename = String.format("%d.jpg", System.currentTimeMillis());
                    File outputfile = new File(directory, filename);
                    new StyleableToast.Builder(context)
                            .text("Image saved")
                            .backgroundColor(Color.GRAY)
                            .cornerRadius(5)
                            .iconStart(R.drawable.congrates)
                            .show();

                    try {
                        outputStream = new FileOutputStream(outputfile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(outputfile));
                        context.sendBroadcast(intent);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });


        }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        DeletedpostrvdesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DeletedpostrvdesignBinding.bind(itemView);
        }
    }
}
