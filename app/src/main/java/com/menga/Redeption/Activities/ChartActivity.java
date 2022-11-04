package com.menga.Redeption.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.databinding.ActivityChartBinding;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
ActivityChartBinding binding;
BarData barData;
BarDataSet barDataSet;
ArrayList barentry=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
////
//        barentry=new ArrayList<>();
//        barentry.add(new BarEntry(1f,1));
//        barentry.add(new BarEntry(2f,2));
//        barentry.add(new BarEntry(3f,3));
//
//        barDataSet=new BarDataSet(barentry,"Data Set");
//        barData=new BarData(barDataSet);
//        binding.mypostchart.setData(barData);
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
//        binding.mypostchart.invalidate();

//code for set green data means followersdata// grean 1f //
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long followerscount=(long)snapshot.getChildrenCount();
                    barentry.add(new BarEntry(1f,0,followerscount));

                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }else {
                    barentry.add(new BarEntry(1f,0,0));

                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//code for set green data means followingd// yellow 2f //
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long followingscount=(long)snapshot.getChildrenCount();
                    barentry.add(new BarEntry(2f,0,followingscount));
                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }else {
                    barentry.add(new BarEntry(2f,0,0));
                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//code for set green data means spacefriends// red 3f //
        FirebaseDatabase.getInstance().getReference().child("Space_Center").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    long spacefriendcount=(long) snapshot.getChildrenCount();

                    barentry.add(new BarEntry(3f,spacefriendcount));
                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }else {
                    barentry.add(new BarEntry(3f,0,0));
                    barDataSet=new BarDataSet(barentry,"Data Set");
                    barData=new BarData(barDataSet);
                    binding.mypostchart.setData(barData);
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);
                    binding.mypostchart.invalidate();
                    binding.mypostchart.refreshDrawableState();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }


}