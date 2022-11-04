package com.menga.Redeption.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.ActivityEditAccountBinding;
import com.squareup.picasso.Picasso;

import io.github.muddz.styleabletoast.StyleableToast;

public class EditAccountActivity extends AppCompatActivity {
    ActivityEditAccountBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        binding.editpersonaldetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccountActivity.this, EditPersonalDetailsActivity.class);
                startActivity(intent);
            }
        });

// this code for seting user profile when he vist this activity//
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Userdata userdata = snapshot.getValue(Userdata.class);
                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.userprofilepohoto);
                    binding.name.setText(userdata.getName());
                    binding.bio.setText(userdata.getBio());
                    binding.country.setText(userdata.getCountry());
                    binding.city.setText(userdata.getCity());
                    binding.birthday.setText(userdata.getBirthday());
                    binding.gender.setText(userdata.getGender());
                    binding.website.setText(userdata.getWebsite());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/// Update Profile //////// Line 76 to 123  ////////
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = binding.name.getText().toString(),
                        Bio = binding.bio.getText().toString(),
                        Country = binding.country.getText().toString(),
                        City = binding.city.getText().toString(),
                        Birthday = binding.birthday.getText().toString(),
                        Gender = binding.gender.getText().toString(),
                        Website = binding.website.getText().toString();


                database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            if (Name.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("Name Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                database.getReference().child("Users").child(auth.getUid()).child("name").setValue(Name);
                            }
                            if (Bio.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("Bio Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                database.getReference().child("Users").child(auth.getUid()).child("Bio").setValue(Bio);


                            }
                            if (Country.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("Country Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();

                                database.getReference().child("Users").child(auth.getUid()).child("Country").setValue(Country);


                            }
                            if (City.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("City Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                database.getReference().child("Users").child(auth.getUid()).child("City").setValue(City);


                            }
                            if (Gender.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("Gender Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(1)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                database.getReference().child("Users").child(auth.getUid()).child("Gender").setValue(Gender);


                            }
                            if (Website.length() > 0) {
                                new StyleableToast
                                        .Builder(getApplicationContext())
                                        .text("Website Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                database.getReference().child("Users").child(auth.getUid()).child("Website").setValue(Website);


                            }
                            if (Birthday.length() > 0) {
                                new StyleableToast.Builder(getApplicationContext())
                                        .text("Birthday Updated")
                                        .textColor(Color.WHITE)
                                        .cornerRadius(5)
                                        .backgroundColor(Color.GRAY)
                                        .show();
                                ;
                                database.getReference().child("Users").child(auth.getUid()).child("Birthday").setValue(Birthday);

                            }

                            //OUT OF THE ALL CONDITIONS/// <<< CODE HERE >>> WHAT WILL DONE AFEER>>//


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        });
        /// Update Profile ///// LINE 76 TO 123   ///////////


        //
        //  Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();

        binding.cmicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(EditAccountActivity.this)
                        .crop(1f,1f)
                        .maxResultSize(1728, 3840)
                        .start();
            }
        });
        binding.userprofilepohoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditAccountActivity.this)
                        .crop(1f,1f)
                        .maxResultSize(1728, 3840)
                        .start();
            }

        });


        // there we want to open user phone galery to uplod his or her pohoto on click change profile text//

        binding.changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here we want to do some activity so we use intent//
                ImagePicker.with(EditAccountActivity.this)
                        .crop(1f,1f)
                        .maxResultSize(1728, 3840)
                        .start();
            }
        });
        // this code for go back on profile frigment ///////////////////////////
    }


// we get all result who user perfom in startActivity intent//

    @Override
    // this code for start activty when user click on change profile pohoto
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // in this code if user do some action than take that image in Uri uri (becase image will come in Uri form so we declaire here Uri type variable//
        if (data.getData() != null) {
            Uri uri = data.getData(); //get that data and storre it in uri variable//
            // set that pohoto on profile
            binding.userprofilepohoto.setImageURI(uri);//bacause in this time that pohoto is in uri form so we set that pohoto in uri on profile//

            // make final type statment StrogeRefrence to get thet reference (because we are taking reference for storage so we take StrogeReference).storage is our FirebaseStroge variable.get reference is used to get reference .child is
            //is used to make sub class in firebase stroge .child(" name of that child who you want to make ").child is on time again is used to make that child subchild .chils("in this we take that user id bu using FirbaseAuth.getInstance.getUid()")//


            // here we are put that file who user choose in our This StrogeReference reference by using putFile//

            final StorageReference reference = storage.getReference().child("profile_pohoto").child(auth.getUid());
            new StyleableToast
                    .Builder(getApplicationContext())
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