package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.menga.Redeption.Models.SpaceModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentBootomsheetBinding;

import java.util.Date;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.muddz.styleabletoast.StyleableToast;


public class  BootomsheetFragment extends BottomSheetDialogFragment {
    FragmentBootomsheetBinding binding;

    public BootomsheetFragment() {
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
        binding = FragmentBootomsheetBinding.inflate(inflater, container, false);






        Bundle bundle = getArguments();
        String postID = bundle.getString("postid");
        String postedBY = bundle.getString("postedby");



        if (postedBY.equals(FirebaseAuth.getInstance().getUid())){
            binding.blockuser.setVisibility(View.GONE);
        }else{
            binding.blockuser.setVisibility(View.VISIBLE);
        }
        if (postedBY.equals(FirebaseAuth.getInstance().getUid())){
            binding.removethispost.setVisibility(View.GONE);
        }else{
//            binding.removethispost.setVisibility(View.VISIBLE);
        }

        if (postedBY.equals(FirebaseAuth.getInstance().getUid())){
            binding.unfollow.setVisibility(View.GONE);
        }else{
            binding.unfollow.setVisibility(View.VISIBLE);
        }

        if (postedBY.equals(FirebaseAuth.getInstance().getUid())){
            binding.addinspace.setVisibility(View.GONE);
        }else{
            binding.addinspace.setVisibility(View.VISIBLE);
        }


        FirebaseDatabase.getInstance().getReference().child("Space_Center").child(FirebaseAuth.getInstance().getUid()).child(postedBY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.addinspace.setText("Remove from space");
                    binding.addinspace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Space_Center").child(FirebaseAuth.getInstance().getUid()).child(postedBY).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.addinspace.setText(getActivity().getString(R.string.add_user_in_your_space));
                                    BootomsheetFragment.this.dismiss();
                                }
                            });
                        }
                    });
                }else {
                    binding.addinspace.setText("Add user in space");
                    binding.addinspace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SpaceModel spaceModel = new SpaceModel();
                            spaceModel.setSpaceddto(postedBY);
                            spaceModel.setSpacedat(new Date().getTime());
                            spaceModel.setSpacedby(FirebaseAuth.getInstance().getUid());
                            FirebaseDatabase.getInstance().getReference().child("Space_Center").child(FirebaseAuth.getInstance().getUid()).child(postedBY).setValue(spaceModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.addinspace.setText("Remove from space");
                                    new StyleableToast.Builder(getContext())
                                            .text("Now user is in your Space")
                                            .textColor(Color.WHITE)
                                            .backgroundColor(Color.GRAY)
                                            .cornerRadius(5)
                                            .iconStart(R.drawable.space)
                                            .show();
                                    BootomsheetFragment.this.dismiss();
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


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Blocked_Users").child(postedBY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.blockuser.setText(R.string.unblock);
                    binding.blockuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Userdata userdata = snapshot.getValue(Userdata.class);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Blocked_Users").child(postedBY).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    new StyleableToast.Builder(getContext())
                                            .text("Now" + userdata.getName() + "is Unblocked")
                                            .iconStart(R.drawable.unblock)
                                            .textColor(Color.WHITE)
                                            .cornerRadius(5)
                                            .backgroundColor(Color.GRAY)
                                            .show();
                                    BootomsheetFragment.this.dismiss();
                                }
                            });
                        }
                    });


                }else {
                    binding.blockuser.setText("Block user");
                    binding.blockuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BlockedusersModel blockedusersModel = new BlockedusersModel();
                            blockedusersModel.setBlockedto(postedBY);
                            blockedusersModel.setBlockedby(FirebaseAuth.getInstance().getUid());
                            blockedusersModel.setBlockedat(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Blocked_Users").child(postedBY).setValue(blockedusersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.blockuser.setText(R.string.unblock);
                                    BootomsheetFragment.this.dismiss();
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


     FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(postedBY).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists()){
                 binding.unfollow.setText("Following");
                 binding.unfollow.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(postedBY).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 FirebaseDatabase.getInstance().getReference().child("Users").child(postedBY).child("following").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void unused) {
                                         binding.unfollow.setText("Follow");
                                     }
                                 });
                             }
                         });
                     }
                 });
             }else {
                 binding.unfollow.setText("Follow");

                 binding.unfollow.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         FollowersModel model=new FollowersModel();
                         model.setFollowedby(FirebaseAuth.getInstance().getUid());
                         model.setFollowedate(new Date().getTime());
                         FirebaseDatabase.getInstance().getReference().child("Users").child(postedBY).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 FollowingModel followingModel=new FollowingModel();
                                 followingModel.setFollowingat(new Date().getTime());
                                 followingModel.setFollowingto(postedBY);
                                 FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").child(postedBY).setValue(followingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void unused) {
                                         NotificationModel notificationModel=new NotificationModel();
                                         notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                         notificationModel.setTime(new Date().getTime());
                                         notificationModel.setNotificationtype("follow");
                                         notificationModel.setNotifiacationfollowid(postedBY);
                                         FirebaseDatabase.getInstance().getReference().child("Notifications").child(postedBY).push().setValue(notificationModel);
                                         binding.unfollow.setText("Following");
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







        if (postedBY.equals(FirebaseAuth.getInstance().getUid())) {
            binding.deletepost.setVisibility(View.VISIBLE);
            binding.deletepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Don't worry you can easily Recover your deleted post in Setings")
                            .setConfirmButton("Yes,delete it!", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    FirebaseDatabase.getInstance().getReference().child("posts").child(postID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).push().setValue(snapshot.getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        FirebaseDatabase.getInstance().getReference().child("posts").child(postID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                sweetAlertDialog.dismiss();
                                                                BootomsheetFragment.this.dismiss();
                                                            }
                                                        });

                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            new StyleableToast.Builder(getContext())
                                                    .text(error.getMessage())
                                                    .textColor(Color.WHITE)
                                                    .backgroundColor(Color.GRAY)
                                                    .cornerRadius(5)
                                                    .iconStart(R.drawable.error)
                                                    .show();
                                        }
                                    });

                                }
                            })
                            .show();


                }
            });

        } else {
            binding.deletepost.setVisibility(View.GONE);
        }

        if (postedBY.equals(FirebaseAuth.getInstance().getUid())){
            binding.report.setVisibility(View.GONE);
        }else {
            binding.report.setVisibility(View.VISIBLE);
        }

        binding.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle reportpostid = new Bundle();
                reportpostid.putString("Postid", postID);
                Report_Bottomsheet report_bottomsheet = new Report_Bottomsheet();
                report_bottomsheet.setArguments(reportpostid);
                BootomsheetFragment.this.dismiss();
                report_bottomsheet.show(((FragmentActivity) getContext()).getSupportFragmentManager(), report_bottomsheet.getTag());

            }
        });

//binding.savepost.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Saved_Posts_Model saved_posts_model=new Saved_Posts_Model();
//        saved_posts_model.setPost_id(postID);
//        saved_posts_model.setSaved_by(FirebaseAuth.getInstance().getUid());
//        saved_posts_model.setSaved_at(new Date().getTime());
//        FirebaseDatabase.getInstance().getReference().child("Users").child("Saved_posts").setValue(saved_posts_model).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                BootomsheetFragment.this.dismiss();
//            }
//        });
//    }
//});





        return binding.getRoot();
    }
}