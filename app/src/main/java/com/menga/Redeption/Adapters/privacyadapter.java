package com.menga.Redeption.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.menga.Redeption.Frigments.VerticalFollowersFragment;
import com.menga.Redeption.Frigments.VerticalFollowingFragment;
import com.menga.Redeption.Privacyfragment;
import com.menga.Redeption.termsfragment;

public class privacyadapter extends FragmentPagerAdapter {
    public privacyadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new Privacyfragment();
            case 1:return new termsfragment();
            default:return new Privacyfragment();
        }
    }


    @Override
    public int getCount() {

        return 2;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tittle=null;
        if (position==0){
            tittle="Privacy Policy";
        }
        else {
            tittle="Terms & Conditions";
        }
        return tittle;
    }
}
