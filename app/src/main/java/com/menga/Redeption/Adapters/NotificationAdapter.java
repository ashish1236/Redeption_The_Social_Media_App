package com.menga.Redeption.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Activities.CommentActivity;
import com.menga.Redeption.Activities.LikesActivity;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.NotificationsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {
    ArrayList<NotificationModel> list;
    Context context;
    public NotificationAdapter(ArrayList<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notifications,parent,false);
        return new viewHolder(view);
    }
// set data for all views here //
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


            NotificationModel notificationModel=list.get(position);
            String type=notificationModel.getNotificationtype();
            Boolean checkopen=notificationModel.isCheckopen();
           if (checkopen==true){
               holder.binding.notificationlayout.setBackground(ContextCompat.getDrawable(context,R.color.transparent));

           }else {
           }
        FirebaseDatabase.getInstance().getReference().child("Users").child(notificationModel.getNotificationby()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdata userdata=snapshot.getValue(Userdata.class);
                Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.userimage);
                holder.binding.name.setText(userdata.getName());
                String time= TimeAgo.using(notificationModel.getTime());
                holder.binding.time.setText(time);
                if (type.equals("firestoke")){
                    holder.binding.notification.setText(userdata.getName()+ "give firestoke on your post.");
                }else if (type.equals("comment")){
                    holder.binding.notification.setText(userdata.getName()+ "commented on your post.");
                }else if (type.equals("follow")){
                    holder.binding.notification.setText(userdata.getName()+ "started following you. ");
                }

                holder.binding.notificationlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (type.equals("comment")){
                            holder.binding.notificationlayout.setBackground(ContextCompat.getDrawable(context,R.color.transparent));
                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(notificationModel.getPostedby())
                                    .child(notificationModel.getNotificationid()).child("checkopen").setValue(true);
                            Userdata userdata = new Userdata();
                            Intent intent = new Intent(context, CommentActivity.class);
                            //THIS IS FOR SEND DATA IN NEXT ACTIVITY//
                            intent.putExtra("PostId",notificationModel.getPostid());
                            intent.putExtra("PostedBy",notificationModel.getPostedby());
                            intent.putExtra("userid", userdata.getUserID());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            ////////////////////////////////////////////////////
                            context.startActivity(intent);
                        }else if (type.equals("firestoke")){
                            holder.binding.notificationlayout.setBackground(ContextCompat.getDrawable(context,R.color.transparent));

                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(notificationModel.getPostedby())
                                    .child(notificationModel.getNotificationid()).child("checkopen").setValue(true);

                            Userdata userdata = new Userdata();
                            Intent intent = new Intent(context, LikesActivity.class);
                            //THIS IS FOR SEND DATA IN NEXT ACTIVITY//
                            intent.putExtra("PostId", notificationModel.getPostid());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }else if (type.equals("follow")){
                            holder.binding.notificationlayout.setBackground(ContextCompat.getDrawable(context,R.color.transparent));
                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(notificationModel.getNotifiacationfollowid())
                                    .child(notificationModel.getNotificationid()).child("checkopen").setValue(true);
                        }
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

// find your all items of layout here..//
    public class viewHolder extends RecyclerView.ViewHolder{
      NotificationsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=NotificationsBinding.bind(itemView);

        }
    }
}
