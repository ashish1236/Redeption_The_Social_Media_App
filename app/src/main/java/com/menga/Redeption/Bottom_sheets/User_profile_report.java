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
import com.menga.Redeption.Models.User_Profile_Report_Model;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentUserProfileReportBinding;

import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;

public class User_profile_report extends BottomSheetDialogFragment {
FragmentUserProfileReportBinding binding;
    public User_profile_report() {
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
        binding=FragmentUserProfileReportBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        String Userid = bundle.getString("userid");

        binding.fakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               User_Profile_Report_Model user_profile_report_model = new User_Profile_Report_Model();
                user_profile_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                user_profile_report_model.setProfileid(Userid);
                user_profile_report_model.setReport_Type(getString(R.string.fake_account));
                user_profile_report_model.setTime(new Date().getTime());
                user_profile_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_users").child(Userid).push().setValue(user_profile_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        User_profile_report.this.dismiss();
                    }
                });
            }
        });

        binding.harassmentOrBullying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_Profile_Report_Model user_profile_report_model = new User_Profile_Report_Model();
                user_profile_report_model.setReportedby(FirebaseAuth.getInstance().getUid());
                user_profile_report_model.setProfileid(Userid);
                user_profile_report_model.setReport_Type(getString(R.string.harassment_or_bullying));
                user_profile_report_model.setTime(new Date().getTime());
                user_profile_report_model.setStatus("Pending");

                FirebaseDatabase.getInstance().getReference().child("Reported_users").child(Userid).push().setValue(user_profile_report_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new StyleableToast.Builder(getContext())
                                .text("Thanks for reporting we will take action soon")
                                .backgroundColor(Color.GRAY)
                                .cornerRadius(5)
                                .show();
                        User_profile_report.this.dismiss();
                    }
                });
            }
        });
        return binding.getRoot();
    }
}