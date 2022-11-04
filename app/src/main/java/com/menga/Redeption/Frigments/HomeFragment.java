package com.menga.Redeption.Frigments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iammert.library.readablebottombar.ReadableBottomBar;
import com.menga.Redeption.Activities.MainActivity;
import com.menga.Redeption.Adapters.PostAdapter;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.StoryModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.menga.friendspacefinal.Activities.Add_story_Activity;
//import com.menga.friendspacefinal.Adapters.StoryAdapter;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView storyrv;
    RecyclerView postrv;
    ArrayList<StoryModel> storylist;
    ArrayList<PostModel> postlist;
    SwipeRefreshLayout refreshLayout;
    ArrayList<Userdata>followingModelslist=new ArrayList<>();

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Userdata userdata=snapshot.getValue(Userdata.class);
                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadableBottomBar readableBottomBar = ((MainActivity) getContext()).findViewById(R.id.readableBottomBar);
                                                   readableBottomBar.selectItem(4);
                                                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                   transaction.replace(R.id.conteiner, new ProfileFragment());
                                                   transaction.commit();
            }
        });



//        storyrv = binding.storyRV;
//        storylist = new ArrayList<>();
//        StoryAdapter adapter = new StoryAdapter(storylist, getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        storyrv.setLayoutManager(linearLayoutManager);
//        storyrv.setNestedScrollingEnabled(false);
//        storyrv.setAdapter(adapter);
//FirebaseDatabase.getInstance().getReference().child("Stories").addListenerForSingleValueEvent(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        if (snapshot.exists()){
//            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                StoryModel storyModel=new StoryModel();
//                storyModel.setStoryby(dataSnapshot.getKey());
//                storyModel.setStoryat(dataSnapshot.child("time").getValue(long.class));
//                ArrayList<UsersStories>stories=new ArrayList<>();
//                for (DataSnapshot dataSnapshot1:dataSnapshot.child("Userstories").getChildren()){
//                    UsersStories usersStories=dataSnapshot1.getValue(UsersStories.class);
//                    stories.add(usersStories);
//                }
//                storyModel.setStories(stories);
//                storylist.add(storyModel);
//            }
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});








        postrv = binding.postsonhome;
        postlist = new ArrayList<>();
        PostAdapter postAdapter = new PostAdapter(postlist, getContext());
        LinearLayoutManager l=new LinearLayoutManager(getContext());
        l.setReverseLayout(true);
        l.setStackFromEnd(true);
        postrv.setLayoutManager(l);
        postrv.setNestedScrollingEnabled(false);
        postrv.setAdapter(postAdapter);
        binding.all.setTextColor(Color.BLUE);
        binding.fun.setTextColor(Color.BLACK);
        binding.qutes.setTextColor(Color.BLACK);
        binding.motivation.setTextColor(Color.BLACK);
        binding.bussiness.setTextColor(Color.BLACK);
        binding.education.setTextColor(Color.BLACK);
        binding.crupto.setTextColor(Color.BLACK);
        binding.fashion.setTextColor(Color.BLACK);

        database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    postModel.setPostid(dataSnapshot.getKey());
                    postlist.add(postModel);

                    database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                postlist.remove(postModel);
                                postAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
                refreshLayout.setRefreshing(false);

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//        FollowerssuggestionAdapter followsugadapter=new FollowerssuggestionAdapter(followingModelslist,getContext());
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        //>>allusers is Name of recylerer view /<<<<<<<</
//        binding.followsugrv.setAdapter(followsugadapter);
//        binding.followsugrv.setLayoutManager(layoutManager);
//        if (!followingModelslist.isEmpty()){
//            binding.textView22.setVisibility(View.VISIBLE);
//            binding.cut.setVisibility(View.VISIBLE);
//        }else {
//            binding.textView22.setVisibility(View.GONE);
//            binding.cut.setVisibility(View.GONE);
//
//        }
//        binding.cut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.followsugrv.setVisibility(View.GONE);
//                binding.cut.setVisibility(View.GONE);
//                binding.textView22.setVisibility(View.GONE);
//            }
//        });

//        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                followingModelslist.clear();
//                for (DataSnapshot datasnapshot:snapshot.getChildren()) {
//                    Userdata userdata=datasnapshot.getValue(Userdata.class);
//                    userdata.setUserID(datasnapshot.getKey());
//                    followingModelslist.add(userdata);
//                    if (datasnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
//                        followingModelslist.remove(userdata);
//                        followsugadapter.notifyDataSetChanged();
//
//                    }
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(userdata.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()){
//////                                    Toast.makeText(getContext(), "+++++", Toast.LENGTH_SHORT).show();
//                                followingModelslist.remove(userdata);
//                                followsugadapter.notifyDataSetChanged();
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }
//
//                followsugadapter.notifyDataSetChanged();
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                new StyleableToast.Builder(getContext())
//                        .text(error.getMessage())
//                        .textColor(Color.WHITE)
//                        .backgroundColor(Color.GRAY)
//                        .cornerRadius(5)
//                        .iconStart(R.drawable.error)
//                        .show();
//            }
//        });


// here are the code for refresh recylerview//
        refreshLayout = binding.refresh;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);
                binding.all.setTextColor(Color.BLUE);
                binding.fun.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);

                            postlist.add(postModel);

                            database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        postlist.remove(postModel);
                                        postAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            postModel.setPostid(dataSnapshot.getKey());
                        }
                        refreshLayout.setRefreshing(false);

                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });








//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                postlist.remove(viewHolder.getAbsoluteAdapterPosition());
//                adapter.notifyDataSetChanged();
//
//            }
//        }).attachToRecyclerView(postrv);

        binding.fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLUE);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Fun mantra")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        binding.qutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLUE);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Quotes")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.motivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLUE);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Motivation")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.bussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLUE);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Business")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLUE);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Education")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.crupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLUE);
                binding.fashion.setTextColor(Color.BLACK);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Crypto & Stocks")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fun.setTextColor(Color.BLACK);
                binding.all.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLUE);

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                postrv.setLayoutManager(layoutManager);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            if (postModel.getPosttype().equals("Fashion")) {
                                postlist.add(postModel);
                                database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            postlist.remove(postModel);
                                            postAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                postModel.setPostid(dataSnapshot.getKey());
                            }

                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        binding.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postrv = binding.postsonhome;
                postlist = new ArrayList<>();
                PostAdapter postAdapter = new PostAdapter(postlist, getContext());
                LinearLayoutManager l=new LinearLayoutManager(getContext());
                l.setReverseLayout(true);
                l.setStackFromEnd(true);
                postrv.setLayoutManager(l);
                postrv.setNestedScrollingEnabled(false);
                postrv.setAdapter(postAdapter);
                binding.all.setTextColor(Color.BLUE);
                binding.fun.setTextColor(Color.BLACK);
                binding.qutes.setTextColor(Color.BLACK);
                binding.motivation.setTextColor(Color.BLACK);
                binding.bussiness.setTextColor(Color.BLACK);
                binding.education.setTextColor(Color.BLACK);
                binding.crupto.setTextColor(Color.BLACK);
                binding.fashion.setTextColor(Color.BLACK);

                database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PostModel postModel = dataSnapshot.getValue(PostModel.class);
                            postModel.setPostid(dataSnapshot.getKey());
                            postlist.add(postModel);
                            database.getReference().child("Users").child(auth.getUid()).child("Blocked_Users").child(postModel.getPostby()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        postlist.remove(postModel);
                                        postAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        }
                        refreshLayout.setRefreshing(false);

                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });


        /////////////////////////////////////////////for default posts///////////////////////////////////////////////////////////////////


//binding.addstory.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Intent intent=new Intent(getContext(), Add_story_Activity.class);
//        startActivity(intent);
//    }
//});
        return binding.getRoot();
    }
}