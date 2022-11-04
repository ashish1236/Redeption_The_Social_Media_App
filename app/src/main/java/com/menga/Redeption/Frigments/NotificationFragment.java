package com.menga.Redeption.Frigments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.NotificationAdapter;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
ArrayList<NotificationModel>notificationModelslist;
RecyclerView NotificationsrecyclerView;


    public NotificationFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_notification, container, false);


        NotificationsrecyclerView=view.findViewById(R.id.Notificans);
        notificationModelslist=new ArrayList<>();


        NotificationAdapter notificationAdapter=new NotificationAdapter(notificationModelslist,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        NotificationsrecyclerView.setLayoutManager(layoutManager);
        NotificationsrecyclerView.setAdapter(notificationAdapter);

        FirebaseDatabase.getInstance().getReference().child("Notifications").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationModelslist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NotificationModel notificationModel=dataSnapshot.getValue(NotificationModel.class);
                    notificationModel.setNotificationid(dataSnapshot.getKey());
                    notificationModelslist.add(notificationModel);

                }
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}