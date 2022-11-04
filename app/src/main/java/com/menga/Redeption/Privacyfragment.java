package com.menga.Redeption;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.menga.Redeption.databinding.FragmentPrivacyfragmentBinding;


public class Privacyfragment extends Fragment {
FragmentPrivacyfragmentBinding binding;

    public Privacyfragment() {
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
        binding=FragmentPrivacyfragmentBinding.inflate(inflater, container, false);

        String file="file:///android_asset/privacy.html";

       binding.web .getSettings().setJavaScriptEnabled(true);
        binding.web .setWebViewClient(new WebViewClient());
        binding.web .getSettings().setAllowContentAccess(true);
        binding.web .getSettings().setAllowFileAccess(true);
        binding.web .loadUrl(file);
        return binding.getRoot();

    }
}