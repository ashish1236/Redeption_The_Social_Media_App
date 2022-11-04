package com.menga.Redeption.Bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.menga.Redeption.databinding.FragmentDeletecommentBinding;

public class DeletecommentFragment extends BottomSheetDialogFragment {
FragmentDeletecommentBinding binding;


    public DeletecommentFragment() {
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
        Bundle bundle=getArguments();
       String commentId= bundle.getString("commentid");
        String postId= bundle.getString("postid");
        String commentBy= bundle.getString("commentby");


        binding=FragmentDeletecommentBinding.inflate(inflater,container,false);
if (commentBy.equals(FirebaseAuth.getInstance().getUid())){
    binding.deletecomment.setVisibility(View.VISIBLE);
    binding.cancel.setVisibility(View.VISIBLE);
    binding.report.setVisibility(View.GONE);
}else {
    binding.cancel.setVisibility(View.GONE);
    binding.deletecomment.setVisibility(View.GONE);
    binding.report.setVisibility(View.VISIBLE);

}

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletecommentFragment.this.dismiss();
            }
        });


        binding.deletecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("posts").child(postId).child("comments").child(commentId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DeletecommentFragment.this.dismiss();
                    }
                });
            }
        });



        binding.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundlereport=new Bundle();
                bundlereport.putString("Postid",postId);
                bundlereport.putString("Commentid",commentId);
                bundlereport.putString("Commentby",commentBy);
                CommentReportbottomsheetFragment commentReportbottomsheetFragment=new CommentReportbottomsheetFragment();
                commentReportbottomsheetFragment.setArguments(bundlereport);
                commentReportbottomsheetFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(),commentReportbottomsheetFragment.getTag());
            }
        });
        return binding.getRoot();
    }
}