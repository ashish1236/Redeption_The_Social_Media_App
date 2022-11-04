package com.menga.Redeption.Frigments;


import static com.menga.Redeption.Activities.editpohoto.outputUri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.iammert.library.readablebottombar.ReadableBottomBar;
import com.menga.Redeption.Activities.MainActivity;
import com.menga.Redeption.Activities.editpohoto;
import com.menga.Redeption.Models.NotificationModel;
import com.menga.Redeption.Models.PostModel;
import com.menga.Redeption.Models.Userdata;
import com.menga.Redeption.R;
import com.menga.Redeption.databinding.FragmentAddPostBinding;
import com.squareup.picasso.Picasso;

import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;


public class AddPostFragment extends Fragment {
    FragmentAddPostBinding binding;
   public static Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    BitmapDrawable drawable;
    Bitmap bitmap;
    public static ImageView pohoto;
    ArrayAdapter<String> spinneradapter;
    public static ImageView remove;
    public static Button postbtn;
    String[] posttype = {"Select post category", "All", "Fun mantra", "Quotes", "Motivation", "Business", "Education", "Crypto & Stocks", "Fashion"};

    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        pohoto=binding.pohoto;
        remove=binding.remove;
        postbtn=binding.postbtn;


//
//                binding.pohoto.setVisibility(View.VISIBLE);
//                binding.remove.setVisibility(View.VISIBLE);
//                binding.pohoto.setImageURI(outputUri);//bacause in this time that pohoto is in uri form so we set that pohoto in uri on profile//
//                binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.maincolor));
//                binding.tip.setVisibility(View.GONE);
//                binding.tip2.setVisibility(View.GONE);
//                binding.bulb.setVisibility(View.GONE);
//                binding.postbtn.setEnabled(true);
//
//
//        }
//        catch (Exception e){
//
//        }

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Posting");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        database.getReference().child("Users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Userdata userdata = snapshot.getValue(Userdata.class);
                            Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(binding.userprofile);
                            binding.username.setText(userdata.getName());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.postdiscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String discription = binding.postdiscription.getText().toString();
                if (!discription.isEmpty()) {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.blue));

                    binding.postbtn.setEnabled(true);
                } else {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.postbtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pollcard.setVisibility(View.GONE);
                binding.polltext.setText("");
                binding.postdiscription.setVisibility(View.VISIBLE);
//                binding.videoView.setVisibility(View.GONE);
//                binding.vremove.setVisibility(View.GONE);

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 5);
                ImagePicker.with(AddPostFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();



                // in fragment or activity
            }
        });
     /*   binding.addimgtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddPostFragment.this)
                       .crop()
                        .start();
            }
        });
*/

        ///////////filter////////////////



// Code for spinner//
        spinneradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, posttype);
        binding.spinner.setAdapter(spinneradapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.polltext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String discription = binding.polltext.getText().toString();
                if (!discription.isEmpty()) {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.blue));
                    binding.postbtn.setEnabled(true);
                } else {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.postbtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.pollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                binding.postdiscription.setVisibility(View.GONE);
                binding.pohoto.setVisibility(View.GONE);
                binding.pollcard.setVisibility(View.VISIBLE);
                binding.remove2.setVisibility(View.VISIBLE);
                binding.remove.setVisibility(View.GONE);
//                binding.videoView.setVisibility(View.GONE);
//                binding.vremove.setVisibility(View.GONE);

            }
        });
        binding.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pohoto.setVisibility(View.GONE);
                binding.pollcard.setVisibility(View.GONE);
                binding.remove2.setVisibility(View.GONE);
                binding.remove.setVisibility(View.GONE);
                binding.polltext.setText("");
//                binding.videoView.setVisibility(View.GONE);
//                binding.vremove.setVisibility(View.GONE);
                binding.postdiscription.setVisibility(View.VISIBLE);

            }
        });
        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pohoto.setVisibility(View.GONE);
                binding.remove.setVisibility(View.GONE);
                outputUri =null;
                String discription2=binding.postdiscription.getText().toString();
                if (!discription2.isEmpty()) {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.blue));
                    binding.postbtn.setEnabled(true);
                } else {
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.postbtn.setEnabled(false);
                }

            }
        });
        binding.remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.postdiscription.setVisibility(View.VISIBLE);
                binding.pollcard.setVisibility(View.GONE);
                binding.polltext.setText("");
                binding.remove2.setVisibility(View.GONE);
            }
        });
//        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//            }
//        });


//        binding.vremove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.vremove.setVisibility(View.GONE);
//                binding.pohoto.setVisibility(View.GONE);
//                binding.pollcard.setVisibility(View.GONE);
//                binding.remove2.setVisibility(View.GONE);
//                binding.remove.setVisibility(View.GONE);
//                binding.videoView.setVisibility(View.GONE);
//
//            }
//        });
//        binding.video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new StyleableToast
//                        .Builder(getContext())
//                        .text("We are working on this ")
//                        .textColor(Color.WHITE)
//                        .cornerRadius(5)
//                        .backgroundColor(Color.GRAY)
//                        .show();
////                binding.tip.setVisibility(View.GONE);
////                binding.tip2.setVisibility(View.GONE);
////                binding.bulb.setVisibility(View.GONE);
//////                new VideoPicker.Builder(getActivity())
//////                        .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
//////                        .directory(VideoPicker.Directory.DEFAULT)
//////                        .extension(VideoPicker.Extension.MP4)
//////                        .enableDebuggingMode(true)
//////                        .build();
////                Intent intent = new Intent();
////                intent.setType("video/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(intent, 5);
////
////                binding.pohoto.setVisibility(View.GONE);
////                binding.pollcard.setVisibility(View.GONE);
////                binding.remove2.setVisibility(View.GONE);
////                binding.remove.setVisibility(View.GONE);
////                binding.vremove.setVisibility(View.VISIBLE);
////                binding.videoView.setVisibility(View.VISIBLE);
//
//            }
//        });




        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
//                   if (video==null){
                if (outputUri != null) {
                    final StorageReference reference = storage.getReference().child("posts").child(auth.getUid()).child(new Date().getTime() + "");
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    PostModel postModel = new PostModel();
                                    postModel.setPostimage(outputUri.toString());
                                    postModel.setPostby(auth.getUid());
                                    postModel.setPostdiscription(binding.postdiscription.getText().toString());
                                    postModel.setPostAt(new Date().getTime());
                                    postModel.setPosttype(binding.spinner.getSelectedItem().toString());
                                    postModel.setPollquestion(binding.polltext.getText().toString());
                                    postModel.setVideo("");
                                    database.getReference().child("posts").push().setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            ReadableBottomBar readableBottomBar = ((MainActivity) getContext()).findViewById(R.id.readableBottomBar);
                                            readableBottomBar.selectItem(0);
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.conteiner, new HomeFragment());
                                            transaction.commit();


                                            NotificationModel notificationModel = new NotificationModel();
                                            notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setTime(new Date().getTime());
                                            notificationModel.setPostid(postModel.getPostid());
                                            notificationModel.setPostedby(postModel.getPostby());
                                            notificationModel.setNotificationtype("new_post");

                                            FirebaseDatabase.getInstance().getReference().child("Notifications").child(postModel.getPostby()).push().setValue(notificationModel);

                                            new StyleableToast
                                                    .Builder(getContext())
                                                    .text("Congrates")
                                                    .textColor(Color.WHITE)
                                                    .cornerRadius(5)
                                                    .backgroundColor(Color.GRAY)
                                                    .show();
                                        }
                                    });

                                }
                            });
                        }
                    });
                } else {
                    PostModel postModel = new PostModel();
                    postModel.setPostby(auth.getUid());
                    postModel.setPostimage("");
                    postModel.setPostdiscription(binding.postdiscription.getText().toString());
                    postModel.setPostAt(new Date().getTime());
                    postModel.setPosttype(binding.spinner.getSelectedItem().toString());
                    postModel.setPollquestion(binding.polltext.getText().toString());
                    postModel.setVideo("");
                    database.getReference().child("posts").push().setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            ReadableBottomBar readableBottomBar = ((MainActivity) getContext()).findViewById(R.id.readableBottomBar);
                            readableBottomBar.selectItem(0);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.conteiner, new HomeFragment());
                            transaction.commit();


                            new StyleableToast
                                    .Builder(getContext())
                                    .text("Congrates")
                                    .textColor(Color.WHITE)
                                    .cornerRadius(5)
                                    .backgroundColor(Color.GRAY)
                                    .show();

                        }
                    });
                }
//                  else {
//                           final StorageReference reference = storage.getReference().child("posts").child(auth.getUid()).child(new Date().getTime() + "");
//                           reference.putFile(video).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                               @Override
//                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                   reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                       @Override
//                                       public void onSuccess(Uri uri) {
//                                           PostModel postModel = new PostModel();
//                                           postModel.setPostimage("");
//                                           postModel.setPostby(auth.getUid());
//                                           postModel.setPostdiscription(binding.postdiscription.getText().toString());
//                                           postModel.setPostAt(new Date().getTime());
//                                           postModel.setPosttype(binding.spinner.getSelectedItem().toString());
//                                           postModel.setPollquestion(binding.polltext.getText().toString());
//                                           postModel.setVideo(uri.toString());
//                                           database.getReference().child("posts").push().setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                               @Override
//                                               public void onSuccess(Void unused) {
//                                                   dialog.dismiss();
//                                                   ReadableBottomBar readableBottomBar = ((MainActivity) getContext()).findViewById(R.id.readableBottomBar);
//                                                   readableBottomBar.selectItem(0);
//                                                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                                                   transaction.replace(R.id.conteiner, new HomeFragment());
//                                                   transaction.commit();
//                                                   NotificationModel notificationModel = new NotificationModel();
//                                                   notificationModel.setNotificationby(FirebaseAuth.getInstance().getUid());
//                                                   notificationModel.setTime(new Date().getTime());
//                                                   notificationModel.setPostid(postModel.getPostid());
//                                                   notificationModel.setPostedby(postModel.getPostby());
//                                                   notificationModel.setNotificationtype("new_post");
//
//                                                   FirebaseDatabase.getInstance().getReference().child("Notifications").child(postModel.getPostby()).push().setValue(notificationModel);
//
//                                                   new StyleableToast
//                                                           .Builder(getContext())
//                                                           .text("Congrates")
//                                                           .textColor(Color.WHITE)
//                                                           .cornerRadius(5)
//                                                           .backgroundColor(Color.GRAY)
//                                                           .show();
//                                               }
//                                           });
//
//                                       }
//                                   });
//                               }
//                           });
//                   }


            }


        });



        return binding.getRoot();

    }




    // this code for start activty when user click on change profile pohoto
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


    if (data.getData() != null) {
        uri = data.getData();
        Intent dsPhotoEditorIntent = new Intent(getActivity(), editpohoto.class);
        startActivity(dsPhotoEditorIntent);
//        Intent dsPhotoEditorIntent = new Intent(getActivity(), DsPhotoEditorActivity.class);
//        dsPhotoEditorIntent.setData(uri);
//        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "FriendSpace");
//        startActivityForResult(dsPhotoEditorIntent, 200);
//
    } else {
        uri = null;
    }




//            // handle the result uri as you want, such as display it in an imageView;
//
//
////
//
//        }
//
}







}

