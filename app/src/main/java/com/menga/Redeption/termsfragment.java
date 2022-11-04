package com.menga.Redeption;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import com.menga.Redeption.databinding.FragmentFriendsFrigmentBinding;
import com.menga.Redeption.databinding.FragmentTermsfragmentBinding;

public class termsfragment extends Fragment {
FragmentTermsfragmentBinding binding;

    public termsfragment() {
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
        binding= FragmentTermsfragmentBinding.inflate(inflater, container, false);





        String file="file:///android_asset/terms.html";

        binding.web .getSettings().setJavaScriptEnabled(true);
        binding.web .setWebViewClient(new WebViewClient());
        binding.web .getSettings().setAllowContentAccess(true);
        binding.web .getSettings().setAllowFileAccess(true);
        binding.web .loadUrl(file);
        return binding.getRoot();

    }
}