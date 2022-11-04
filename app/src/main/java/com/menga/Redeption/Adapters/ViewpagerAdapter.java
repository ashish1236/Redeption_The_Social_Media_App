package com.menga.Redeption.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.menga.Redeption.Frigments.VerticalFollowersFragment;
import com.menga.Redeption.Frigments.VerticalFollowingFragment;

public class ViewpagerAdapter extends FragmentPagerAdapter {


    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new VerticalFollowersFragment();
            case 1:return new VerticalFollowingFragment();
            default:return new VerticalFollowersFragment();
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
            tittle="Followers";
        }
        else {
            tittle="Following";
        }
        return tittle;
    }
}
