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
import com.menga.Redeption.Models.Posts_Report_Model;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentReportBottomsheetBinding;

import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;

public class Report_Bottomsheet extends BottomSheetDialogFragment {
    FragmentReportBottomsheetBinding binding;

    public Report_Bottomsheet() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportBottomsheetBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        String postid = bundle.getString("Postid");


        binding.dontlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type("I don't like this post");
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });


            }
        });
        binding.falseinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type("Wrong information");
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });

            }
        });
        binding.hatespeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type(getString(R.string.hate_speech));
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus(getString(R.string.pending));

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text(getString(R.string.reportfeedbacktoast))
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });
            }
        });
        binding.scamorfrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type(getString(R.string.scam_or_fraud));
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus(getString(R.string.pending));

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text(getString(R.string.reportfeedbacktoast))
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });
            }
        });
        binding.sexual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type(getString(R.string.nudity_or_sexual_content));
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus(getString(R.string.pending));

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text(getString(R.string.reportfeedbacktoast))
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });
            }
        });

        binding.voilance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts_Report_Model posts_report_model = new Posts_Report_Model();
                posts_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                posts_report_model.setPotid(postid);
                posts_report_model.setReport_Type(getString(R.string.violence));
                posts_report_model.setTime(new Date().getTime());
                posts_report_model.setStatus(getString(R.string.pending));

                FirebaseDatabase.getInstance().getReference().child("Reported_posts").child(postid).push().setValue(posts_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text(getString(R.string.reportfeedbacktoast))
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .iconStart(R.drawable.congrates)
                                .show();
                        Report_Bottomsheet.this.dismiss();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}