package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentMypostbottomsheetFragmantBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.muddz.styleabletoast.StyleableToast;

public class MypostbottomsheetFragmant extends BottomSheetDialogFragment {
FragmentMypostbottomsheetFragmantBinding binding;


    public MypostbottomsheetFragmant() {
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
        binding=FragmentMypostbottomsheetFragmantBinding.inflate(inflater, container, false);
        Bundle bundle=getArguments();
        String postID=bundle.getString("postid");
        String postedBY=bundle.getString("postedby");

        binding.deletethispost.setOnClickListener(new View.OnClickListener() {
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
                                        if (snapshot.exists()){
                                            FirebaseDatabase.getInstance().getReference().child("Deleted_Posts").child(FirebaseAuth.getInstance().getUid()).push().setValue(snapshot.getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("posts").child(postID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            sweetAlertDialog.dismiss();
                                                            MypostbottomsheetFragmant.this.dismiss();
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
        FirebaseDatabase.getInstance().getReference().child("posts").child(postID).child("Comments_of").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.turnoffcomment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FirebaseDatabase.getInstance().getReference().child("posts").child(postID).child("Comments_of").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.turnoffcomment.setText("Turn on commenting");
                                    MypostbottomsheetFragmant.this.dismiss();
                                }
                            });
                        }
                    });

                }else{
                    binding.turnoffcomment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FirebaseDatabase.getInstance().getReference().child("posts").child(postID).child("Comments_of").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.turnoffcomment.setText("Turn on commenting");
                                    MypostbottomsheetFragmant.this.dismiss();
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