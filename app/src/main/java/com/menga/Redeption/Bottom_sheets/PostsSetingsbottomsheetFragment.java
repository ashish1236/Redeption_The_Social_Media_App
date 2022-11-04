package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentPostsSetingsbottomsheetBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class PostsSetingsbottomsheetFragment extends BottomSheetDialogFragment implements RadioGroup.OnCheckedChangeListener {
FragmentPostsSetingsbottomsheetBinding binding;
String status;

    public PostsSetingsbottomsheetFragment() {
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
        binding=FragmentPostsSetingsbottomsheetBinding.inflate(inflater,container,false);
        Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();
//        FirebaseDatabase.getInstance().getReference().child("Menga_comments_on").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    binding.choose.setsel
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })
        return binding.getRoot();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.oncommentsallpost:
                status="on";
                FirebaseDatabase.getInstance().getReference().child("Menga_comments_on").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast
                                .Builder(getContext())
                                .text("Comments are on for all posts")
                                .textColor(Color.WHITE)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        PostsSetingsbottomsheetFragment.this.dismiss();
                    }
                });

                break;

            case R.id.offcommentsallpost:
                status="of";
                FirebaseDatabase.getInstance().getReference().child("Menga_comments_of").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast
                                .Builder(getContext())
                                .text("Comments are of for all posts")
                                .textColor(Color.WHITE)
                                .cornerRadius(5)
                                .backgroundColor(Color.GRAY)
                                .show();
                        PostsSetingsbottomsheetFragment.this.dismiss();
                    }
                });
                break;

        }
    }
}