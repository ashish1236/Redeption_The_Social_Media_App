package com.menga.Redeption.Bottom_sheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.menga.Redeption.Models.Comment_Report_Model;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentCommentReportbottomsheetBinding;

import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;

public class CommentReportbottomsheetFragment extends BottomSheetDialogFragment {
FragmentCommentReportbottomsheetBinding binding;


    public CommentReportbottomsheetFragment() {
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
        binding=FragmentCommentReportbottomsheetBinding.inflate(inflater, container, false);
        Bundle bundle=getArguments();
        String postid = bundle.getString("Postid");
        String commentid = bundle.getString("Commentid");
        String commentby = bundle.getString("Commentby");


        binding.falseinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Comment_Report_Model comment_report_model = new Comment_Report_Model();
                comment_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                comment_report_model.setReport_Type("Wrong comment");
                comment_report_model.setReported_at(new Date().getTime());
                comment_report_model.setReported_Comment_By(commentby);
                comment_report_model.setReported_Comment_id(commentid);
                comment_report_model.setPostid_of_Rrported_comment(postid);
                comment_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_comments").child(commentid).push().setValue(comment_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        CommentReportbottomsheetFragment.this.dismiss();
                    }
                });

            }
        });
        binding.hatespeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment_Report_Model comment_report_model = new Comment_Report_Model();
                comment_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                comment_report_model.setReport_Type(getString(R.string.hate_speech));
                comment_report_model.setReported_at(new Date().getTime());
                comment_report_model.setReported_Comment_By(commentby);
                comment_report_model.setReported_Comment_id(commentid);
                comment_report_model.setPostid_of_Rrported_comment(postid);
                comment_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_comments").child(commentid).push().setValue(comment_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        CommentReportbottomsheetFragment.this.dismiss();
                    }
                });

            }

        });
        binding.scamorfrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment_Report_Model comment_report_model = new Comment_Report_Model();
                comment_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                comment_report_model.setReport_Type(getString(R.string.scam_or_fraud));
                comment_report_model.setReported_at(new Date().getTime());
                comment_report_model.setReported_Comment_By(commentby);
                comment_report_model.setReported_Comment_id(commentid);
                comment_report_model.setPostid_of_Rrported_comment(postid);
                comment_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_comments").child(commentid).push().setValue(comment_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        CommentReportbottomsheetFragment.this.dismiss();
                    }
                });

            }
        });
        binding.sexual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment_Report_Model comment_report_model = new Comment_Report_Model();
                comment_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                comment_report_model.setReport_Type(getString(R.string.nudity_or_sexual_content));
                comment_report_model.setReported_at(new Date().getTime());
                comment_report_model.setReported_Comment_By(commentby);
                comment_report_model.setReported_Comment_id(commentid);
                comment_report_model.setPostid_of_Rrported_comment(postid);
                comment_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_comments").child(commentid).push().setValue(comment_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        CommentReportbottomsheetFragment.this.dismiss();
                    }
                });

            }
        });

        binding.voilance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment_Report_Model comment_report_model = new Comment_Report_Model();
                comment_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                comment_report_model.setReport_Type(getString(R.string.violence));
                comment_report_model.setReported_at(new Date().getTime());
                comment_report_model.setReported_Comment_By(commentby);
                comment_report_model.setReported_Comment_id(commentid);
                comment_report_model.setPostid_of_Rrported_comment(postid);
                comment_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_comments").child(commentid).push().setValue(comment_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        CommentReportbottomsheetFragment.this.dismiss();
                    }
                });

            }
        });



        return binding.getRoot();
    }
}