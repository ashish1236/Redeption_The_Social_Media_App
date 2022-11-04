package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Models.BlockedusersModel;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentUsersProfilebottomsheetBinding;

import java.util.Date;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class UsersProfilebottomsheetFragment extends BottomSheetDialogFragment {
    FragmentUsersProfilebottomsheetBinding binding;

    public UsersProfilebottomsheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsersProfilebottomsheetBinding.inflate(inflater, container, false);


        Bundle bundle = this.getArguments();
        String Userid = bundle.getString("userid");
        if (!Userid.equals(FirebaseAuth.getInstance().getUid())){
            binding.unfollow.setVisibility(View.VISIBLE);
        }else {
            binding.unfollow.setVisibility(View.GONE);

        }


        if (Userid.equals(FirebaseAuth.getInstance().getUid())){
            binding.blockuser.setVisibility(View.GONE);
            binding.report.setVisibility(View.GONE);

        }else {
            binding.blockuser.setVisibility(View.VISIBLE);
            binding.report.setVisibility(View.VISIBLE);


        }
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Blocked_Users").child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.blockuser.setText(R.string.unblock);
                    binding.blockuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Userdata userdata = snapshot.getValue(Userdata.class);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Blocked_Users").child(Userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.blockuser.setText(R.string.block);
                                    new StyleableToast.Builder(getContext())
                                            .text("Now" + userdata.getName() + "is Unblocked")
                                            .iconStart(R.drawable.unblock)
                                            .textColor(Color.WHITE)
                                            .cornerRadius(5)
                                            .backgroundColor(Color.GRAY)
                                            .show();
                                    UsersProfilebottomsheetFragment.this.dismiss();
                                }
                            });
                        }
                    });
                } else {
                    binding.blockuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BlockedusersModel blockedusersModel = new BlockedusersModel();
                            blockedusersModel.setBlockedto(Userid);
                            blockedusersModel.setBlockedby(FirebaseAuth.getInstance().getUid());
                            blockedusersModel.setBlockedat(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Blocked_Users").child(Userid).setValue(blockedusersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.blockuser.setText(R.string.unblock);
                                    UsersProfilebottomsheetFragment.this.dismiss();
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


        binding.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle reporteduserid = new Bundle();
                reporteduserid.putString("userid", Userid);
                User_profile_report user_profile_report = new User_profile_report();
                user_profile_report.setArguments(reporteduserid);
                UsersProfilebottomsheetFragment.this.dismiss();
                user_profile_report.show(((FragmentActivity) getContext()).getSupportFragmentManager(), user_profile_report.getTag());
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.unfollow.setText(getString(R.string.unfollow));
                    binding.unfollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(Userid).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Userid).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    binding.unfollow.setText(getText(R.string.follow));
                                }
                            });

                        }
                    });
                } else {
                    binding.unfollow.setText("Follow");
                    binding.unfollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FollowersModel model = new FollowersModel();
                            model.setFollowedate(new Date().getTime());
                            model.setFollowedby(FirebaseAuth.getInstance().getUid());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Userid).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FollowingModel followingModel = new FollowingModel();
                                    followingModel.setFollowingat(new Date().getTime());
                                    followingModel.setFollowingto(Userid);
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(Userid).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            NotificationModel notificationModel=new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setNotificationtype("follow");
                                            notificationModel.setNotifiacationfollowid(Userid);
                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(Userid).push().setValue(notificationModel);

                                            binding.unfollow.setText(getText(R.string.unfollow));
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
        return binding.getRoot();
    }
}