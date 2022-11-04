package com.menga.Redeption.Frigments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.menga.Redeption.Activities.ChartActivity;
import com.menga.Redeption.Activities.EditAccountActivity;
import com.menga.Redeption.Activities.SetingsActivity;
import com.menga.Redeption.Adapters.FollowersAdapter;
import com.menga.Redeption.Adapters.FollowingAdapter;
import com.menga.Redeption.Adapters.MypostsAdapter;
import com.menga.Redeption.Bottom_sheets.MyspaceBottomsheetFragment;
import com.menga.Redeption.Models.FollowersModel;
import com.menga.Redeption.Models.FollowingModel;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;


public class ProfileFragment extends Fragment {
FragmentProfileBinding binding;
FirebaseDatabase database;
FirebaseAuth auth;
ArrayList<FollowersModel>list=new ArrayList<>();
ArrayList<FollowingModel>followingModelArrayList=new ArrayList<>();
ArrayList<PostModel>postModelArrayList=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();

        auth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.Post.setText("0");
        binding.chartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
            }
        });
        if (list.size()>10){
            binding.followerstextView.setVisibility(View.VISIBLE);
            binding.myfolowersrv.setVisibility(View.VISIBLE);

        }else {
            binding.followerstextView.setVisibility(View.GONE);
            binding.myfolowersrv.setVisibility(View.GONE);

        }
        //assign data//

/////////     >>>>>>>>>>>>>>>>      Here code is for showing following section on recylerview     <<<<<<<<<<<<<<<  <<<<<<<<<<  //////////////////////////////////


  ////////////>>>>>>>>>>>>>>>Icons and Buttons Binding and set Methods or functions<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<///////////////////////////
        binding.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.replace(R.id.conteiner, new FriendsFrigment());
                transaction2.commit();
            }
        });

        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.replace(R.id.conteiner, new FriendsFrigment());
                transaction2.commit();
            }
        });

        binding.editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditAccountActivity.class);
                startActivity(intent);

            }
        });
        // go to database and go to Users child and goto Uid child and take that single childe or we can say valu by using addListenerForSingleValueEvent//
        //when we get  any data from anywhere that data will be come in snapshot form//
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if this snapshot exits in database ( here we use that model who we make for store to userdata )than go to user database model (user database model name we decliered >>>>Userdata<<<)
                //and get snapshot valu snapshot.getValue(we declire that class where we want to set data);
                if (snapshot.exists()) {

                    Userdata userdata = snapshot.getValue(Userdata.class);
                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.profilepohoto);

                    // When we get any images or informations  from internet or database thaen we use Picassu android dependenci so add this depenci in in gradel file///
                    //picasso get and load img from userdata and , placeholder is use for default img if user not uploded any img thaen default img will show
                    // here we are saying picasso load imf from profilepohoto from userdata and place default img if there if there is a image then  set it into profile pohoto place in xml or layout//
                    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                    //here we get that user name from our firbase database//
                    binding.currentusername.setText(userdata.getName());
                    binding.proffesion.setText(userdata.getBio());
                        binding.website.setText(userdata.getWebsite());

                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Picasso.get().load(userdata.getBackground_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.backgroundlayout);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(auth.getUid()).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    long followerscount=(long)snapshot.getChildrenCount();
                    binding.Followers.setText(Long.toString(followerscount));

//                    if (followerscount<100){
//                        binding.starimg.setVisibility(View.GONE);
//
//                    }else if(followerscount>100){
//                            binding.starimg.setVisibility(View.VISIBLE);
//                            binding.starimg.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.silverstar));
//                        }else if (followerscount>500){
//                            binding.starimg.setVisibility(View.VISIBLE);
//                            binding.starimg.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bronzestar));
//                        }else if (followerscount>1000){
//                            binding.starimg.setVisibility(View.VISIBLE);
//                            binding.starimg.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.goldstar));
//                        }else if (followerscount>5000){
//                            binding.starimg.setVisibility(View.VISIBLE);
//                            binding.starimg.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rubistar));
//                        }

                }
                else {
                    binding.Followers.setText("0");
//                    binding.starimg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(auth.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long followingscount=(long)snapshot.getChildrenCount();
                    binding.Following.setText(Long.toString(followingscount));
                    if (followingscount>10){
                        binding.FollowingtextView.setVisibility(View.VISIBLE);
                        binding.followingrv.setVisibility(View.VISIBLE);
                        binding.View8.setVisibility(View.VISIBLE);


                    }else {
                        binding.FollowingtextView.setVisibility(View.GONE);
                        binding.followingrv.setVisibility(View.GONE);
                        binding.View8.setVisibility(View.GONE);
                    }
                }
                else{
                    binding.Following.setText("0");
                    binding.FollowingtextView.setVisibility(View.GONE);
                    binding.followingrv.setVisibility(View.GONE);
                    binding.View8.setVisibility(View.GONE);
                    binding.textView3.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        FollowersAdapter adapter=new FollowersAdapter(list,getContext());
        binding.myfolowersrv.setLayoutManager(layoutManager);
        binding.myfolowersrv.setAdapter(adapter);
        database.getReference().child("Users").child(auth.getUid()).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FollowersModel followersModel=dataSnapshot.getValue(FollowersModel.class);
                    list.add(followersModel);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager followinglayoutmannager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        followinglayoutmannager.setReverseLayout(true);
        followinglayoutmannager.setStackFromEnd(true);
        FollowingAdapter followingAdapter=new FollowingAdapter(followingModelArrayList,getContext());
        binding.followingrv.setLayoutManager(followinglayoutmannager);
        binding.followingrv.setAdapter(followingAdapter);
        database.getReference().child("Users").child(auth.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followingModelArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FollowingModel followingModel=dataSnapshot.getValue(FollowingModel.class);
                    followingModelArrayList.add(followingModel);
                }
                followingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


///////////////////////////////////////////////////Here are sec. of postsCount  to set text on Posts textview//////////////////////////////////////////////////////////////////////

///////////////////////My Posts////////My Posts/////////My Posts/////////My Posts/////My Posts////////////My Posts//////My Posts///////////////My Posts/////////My Posts////////////My Posts////////My Posts////My Posts

        MypostsAdapter mypostsAdapter =new MypostsAdapter(postModelArrayList,getContext());
        LinearLayoutManager mypostslayout=new LinearLayoutManager(getContext());
        mypostslayout.setReverseLayout(true);
        mypostslayout.setStackFromEnd(true);
        binding.postsrv.setAdapter(mypostsAdapter);
        binding.postsrv.setLayoutManager(mypostslayout);
        database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostModel postModel=dataSnapshot.getValue(PostModel.class);
                    if(postModel.getPostby().equals(FirebaseAuth.getInstance().getUid())){
                        postModelArrayList.add(postModel);
                        postModel.setPostid(dataSnapshot.getKey());
                        long postcount=(long)postModelArrayList.size();
                        binding.Post.setText(Long.toString(postcount));
                    }

                }
                mypostsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
binding.settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(getContext(), SetingsActivity.class);
        startActivity(intent);
    }
});

binding.cmbg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ImagePicker.with(ProfileFragment.this)
                .crop(16f,9f)	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(1);
    }

});


binding.ppcm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ImagePicker.with(ProfileFragment.this)
                .crop(1f,1f)
                .maxResultSize(1728,3840)
                .start(2);
    }
});
binding.spacecenter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        MyspaceBottomsheetFragment bottomsheetFragment=new MyspaceBottomsheetFragment();
       bottomsheetFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(),bottomsheetFragment.getTag());
    }
});


        return binding.getRoot();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            // in this code if user do some action than take that image in Uri uri (becase image will come in Uri form so we declaire here Uri type variable//
            if (data.getData() != null)
            {
                Uri uri = data.getData(); //get that data and storre it in uri variable//
                // set that pohoto on profile
                binding.backgroundlayout.setImageURI(uri);//bacause in this time that pohoto is in uri form so we set that pohoto in uri on profile//

                // make final type statment StrogeRefrence to get thet reference (because we are taking reference for storage so we take StrogeReference).storage is our FirebaseStroge variable.get reference is used to get reference .child is
                //is used to make sub class in firebase stroge .child(" name of that child who you want to make ").child is on time again is used to make that child subchild .chils("in this we take that user id bu using FirbaseAuth.getInstance.getUid()")//


                // here we are put that file who user choose in our This StrogeReference reference by using putFile//

                final StorageReference reference = FirebaseStorage.getInstance().getReference().child("background_pohoto").child(FirebaseAuth.getInstance().getUid());

                // here we are put that file who user choose in our This StrogeReference reference by using putFile//

                // uri= that vareabel who wwe declaire for taking image//
                // after this we set addonsucceslistiner (new On SuccessListener)on our reference//
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                database.getReference().child("Users").child(auth.getUid()).child("background_Pohoto").setValue(uri.toString());

                            }
                        });
                        // in this code first get database than get that database reference of Users child and than get that user id child and make there Profile_pohoto new child and set value this valu and store that in string//
                    }
                });
            }
        }
        if (requestCode==2){
            if (data.getData() != null) {
                Uri uri = data.getData(); //get that data and storre it in uri variable//
                // set that pohoto on profile
                binding.profilepohoto.setImageURI(uri);//bacause in this time that pohoto is in uri form so we set that pohoto in uri on profile//

                // make final type statment StrogeRefrence to get thet reference (because we are taking reference for storage so we take StrogeReference).storage is our FirebaseStroge variable.get reference is used to get reference .child is
                //is used to make sub class in firebase stroge .child(" name of that child who you want to make ").child is on time again is used to make that child subchild .chils("in this we take that user id bu using FirbaseAuth.getInstance.getUid()")//


                // here we are put that file who user choose in our This StrogeReference reference by using putFile//

               final StorageReference reference=FirebaseStorage.getInstance().getReference().child("profile_pohoto").child(auth.getUid());
                new StyleableToast
                        .Builder(getContext())
                        .text("Profile Pohoto Updated")
                        .textColor(Color.WHITE)
                        .cornerRadius(5)
                        .backgroundColor(Color.GRAY)
                        .show();
                // here we are put that file who user choose in our This StrogeReference reference by using putFile//

                // uri= that vareabel who wwe declaire for taking image//
                // after this we set addonsucceslistiner (new On SuccessListener)on our reference//
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                database.getReference().child("Users").child(auth.getUid()).child("profile_Pohoto").setValue(uri.toString());

                            }
                        });
                        // in this code first get database than get that database reference of Users child and than get that user id child and make there Profile_pohoto new child and set value this valu and store that in string//
                    }
                });
            }

        }




    }
}