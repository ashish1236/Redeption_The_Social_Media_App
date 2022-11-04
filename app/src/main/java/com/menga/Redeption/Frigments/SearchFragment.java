package com.menga.Redeption.Frigments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menga.Redeption.Adapters.UsersAdapter;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentSearchBinding;

import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;

public class SearchFragment extends Fragment {
FragmentSearchBinding binding;

FirebaseAuth auth;
FirebaseDatabase database;
ArrayList<Userdata>list=new ArrayList<>();
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =FragmentSearchBinding.inflate(inflater,container,false);
        binding.searchprogressbar.setVisibility(View.VISIBLE);


        UsersAdapter adapter=new UsersAdapter(getContext(),list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        //>>allusers is Name of recylerer view /<<<<<<<</
       binding.allusers.setAdapter(adapter);
        binding.allusers.setLayoutManager(layoutManager);
        try {
            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return true;
                }
                private void filter(String newText) {
                    ArrayList<Userdata>filterdlist=new ArrayList<>();
                    for (Userdata item: list){
                        if (item.getName().toLowerCase().contains(newText.toLowerCase()))
                        {
                            filterdlist.add(item);
                        }
                    }
                    if (filterdlist.isEmpty()){
                        new StyleableToast.Builder(getContext()).text("Not found").backgroundColor(Color.GRAY);
                    }else{
                        adapter.filterList(filterdlist);
                    }
                }
            });

        }catch (Exception e){

        }
        finally {
            database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot datasnapshot:snapshot.getChildren()) {
                        Userdata userdata=datasnapshot.getValue(Userdata.class);
                        userdata.setUserID(datasnapshot.getKey());
                        if (!datasnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                            list.add(userdata);
                        }

                    }

                    adapter.notifyDataSetChanged();
                    binding.searchprogressbar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    new StyleableToast.Builder(getContext())
                            .text(error.getMessage())
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .cornerRadius(5)
                            .iconStart(R.drawable.error)
                            .show();
                }
            });

        }


        return  binding.getRoot();
    }



}