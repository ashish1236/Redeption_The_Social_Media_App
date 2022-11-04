//package com.menga.friendspacefinal.Activities;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.drjacky.imagepicker.ImagePicker;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.menga.friendspacefinal.Models.StoryModel;
//import com.menga.friendspacefinal.Models.UsersStories;
//import com.menga.friendspacefinal.R;
//import com.menga.friendspacefinal.databinding.ActivityAddStoryBinding;
//
//import java.util.Date;
//
//public class Add_story_Activity extends AppCompatActivity {
//ActivityAddStoryBinding binding;
//Uri uri;
//ProgressDialog dialog;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding=ActivityAddStoryBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//
//
//        binding.addimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.textbody.setVisibility(View.GONE);
//                binding.pollcard.setVisibility(View.GONE);
//                binding.remove2.setVisibility(View.GONE);
//                binding.polltext.setText("");
//                binding.textbody.setText("");
//                ImagePicker.Companion.with(Add_story_Activity.this)
//                        .crop()
//                        .start();
//            }
//        });
//
//
//
//
//
//
//
//
//binding.text.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        binding.textbody.setVisibility(View.VISIBLE);
//        binding.imageView2.setVisibility(View.GONE);
//        binding.removeimg.setVisibility(View.GONE);
//        binding.pollcard.setVisibility(View.GONE);
//        binding.remove2.setVisibility(View.GONE);
//        binding.polltext.setText("");
//        uri=null;
//    }
//});
////
////        binding.pollbtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                binding.textbody.setVisibility(View.GONE);
////                binding.imageView2.setVisibility(View.GONE);
////                binding.removeimg.setVisibility(View.GONE);
////                binding.pollcard.setVisibility(View.VISIBLE);
////                binding.remove2.setVisibility(View.VISIBLE);
////                binding.textbody.setText("");
////                uri=null;
////
////
////            }
////        });
//
//        binding.removeimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.imageView2.setVisibility(View.GONE);
//                binding.removeimg.setVisibility(View.GONE);
//                uri=null;
//            }
//        });
////        binding.remove2.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                binding.textbody.setVisibility(View.GONE);
////                binding.imageView2.setVisibility(View.GONE);
////                binding.pollcard.setVisibility(View.GONE);
////                binding.remove2.setVisibility(View.GONE);
////                binding.polltext.setText("");
////            }
////        });
////
////
////        binding.polltext.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                String discription = binding.polltext.getText().toString();
////                if (!discription.isEmpty()) {
////                    binding.postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
////                    binding.postbtn.setEnabled(true);
////                } else {
////                    binding.postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
////                    binding.postbtn.setEnabled(false);
////                }
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////            }
////        });
////
////        binding.textbody.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                String discription = binding.textbody.getText().toString();
////                if (!discription.isEmpty()) {
////                    binding.postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
////                    binding.postbtn.setEnabled(true);
////                } else {
////                    binding.postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
////                    binding.postbtn.setEnabled(false);
////                }
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////            }
////        });
//
//
//        binding.backbtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//
//
//binding.postbtn.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        if (uri != null) {
//            final StorageReference reference = FirebaseStorage.getInstance().getReference().child("stories").child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime() + "");
//            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            StoryModel storyModel = new StoryModel();
//                            storyModel.setStoryat(new Date().getTime());
//                            FirebaseDatabase.getInstance().getReference().child("Stories").child(FirebaseAuth.getInstance().getUid()).child("time").setValue(storyModel.getStoryat()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    UsersStories usersStories=new UsersStories();
//                                    usersStories.setImage(uri.toString());
//                                    usersStories.setPollquestion(binding.polltext.getText().toString());
//                                    usersStories.setText(binding.textbody.getText().toString());
//                                    usersStories.setPostedat(new Date().getTime());
//                                    FirebaseDatabase.getInstance().getReference().child("Stories").child(FirebaseAuth.getInstance().getUid()).child("Userstories").push().setValue(usersStories).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//            });
////        }else {
////            StoryModel storyModel = new StoryModel();
////            storyModel.setStoryat(new Date().getTime());
////            FirebaseDatabase.getInstance().getReference().child("Stories").child(FirebaseAuth.getInstance().getUid()).child("time").setValue(storyModel.getStoryat()).addOnSuccessListener(new OnSuccessListener<Void>() {
////                @Override
////                public void onSuccess(Void unused) {
////                    UsersStories usersStories=new UsersStories();
////                    usersStories.setImage("");
////                    usersStories.setPollquestion(binding.polltext.getText().toString());
////                    usersStories.setText(binding.textbody.getText().toString());
////                    usersStories.setPostedat(new Date().getTime());
////                    FirebaseDatabase.getInstance().getReference().child("Stories").child(FirebaseAuth.getInstance().getUid()).child("Userstories").push().setValue(usersStories).addOnSuccessListener(new OnSuccessListener<Void>() {
////                        @Override
////                        public void onSuccess(Void unused) {
////                            dialog.dismiss();
////                            finish();
////                        }
////                    });
////                }
////            });
////
////        }
//
//
//
//    }
//});
//
//
//
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // in this code if user do some action than take that image in Uri uri (becase image will come in Uri form so we declaire here Uri type variable//
////      if (requestCode==5){
////          if (data!=null){
////              video = data.getData();
////              Toast.makeText(getContext(), "geted", Toast.LENGTH_SHORT).show();
////              binding.videoView.setVideoURI(video);
////              binding.videoView.start();
////
////          }else {
////              video= null;
////          }
////
////
////      }
//        if (data.getData() != null) {
//            uri = data.getData(); //get that data and storre it in uri variable//
//            binding.imageView2.setVisibility(View.VISIBLE);
//            binding.removeimg.setVisibility(View.VISIBLE);
////            binding.remove.setVisibility(View.VISIBLE);
//            binding.imageView2.setImageURI(uri);//bacause in this time that pohoto is in uri form so we set that pohoto in uri on profile//
//            binding.postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
//            binding.postbtn.setEnabled(true);
//
//
//        } else {
//            binding.imageView2.setVisibility(View.GONE);
//            binding.removeimg.setVisibility(View.GONE);
//            uri = null;
//        }
//
//
//    }
//}