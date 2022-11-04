//package com.menga.friendspacefinal.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.menga.friendspacefinal.Models.StoryModel;
//import com.menga.friendspacefinal.Models.Userdata;
//import com.menga.friendspacefinal.Models.UsersStories;
//import com.menga.friendspacefinal.R;
//import com.menga.friendspacefinal.databinding.StoryRvdesigneBinding;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import omari.hamza.storyview.StoryView;
//import omari.hamza.storyview.callback.StoryClickListeners;
//import omari.hamza.storyview.model.MyStory;
//
//
//public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {
//
//ArrayList<StoryModel> list;
//Context context;
//
//    public StoryAdapter(ArrayList<StoryModel> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View view= LayoutInflater.from(context).inflate(R.layout.story_rvdesigne,parent,false);
//
//        return  new viewHolder(view);
//    }
//
//    @Override
//    //set data for all views//
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//      StoryModel storyModel=list.get(position);
//        FirebaseDatabase.getInstance().getReference().child("Users").child(storyModel.getStoryby()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    Userdata userdata=snapshot.getValue(Userdata.class);
//                    Picasso.get().load(userdata.getProfile_Pohoto()).placeholder(R.drawable.aptavatar).into(holder.binding.profie);
//                    holder.binding.username.setText(userdata.getName());
//
//                    holder.binding.storymodel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            ArrayList<MyStory> myStories = new ArrayList<>();
//
//                            for(UsersStories story: storyModel.getStories()){
//                                myStories.add(new MyStory(
//                                        story.getImage()
//                                ));
//                            }
//                            new StoryView.Builder(((AppCompatActivity)context).getSupportFragmentManager())
//                                    .setStoriesList(myStories) // Required
//                                    .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
//                                    .setTitleText(userdata.getName()) // Default is Hidden
//                                    .setSubtitleText(userdata.getBio()) // Default is Hidden
//                                    .setTitleLogoUrl(userdata.getProfile_Pohoto()) // Default is Hidden
//                                    .setStoryClickListeners(new StoryClickListeners() {
//                                        @Override
//                                        public void onDescriptionClickListener(int position) {
//                                            //your action
//                                        }
//
//                                        @Override
//                                        public void onTitleIconClickListener(int position) {
//                                            //your action
//                                        }
//                                    }) // Optional Listeners
//                                    .build() // Must be called before calling show method
//                                    .show();
//
//
//
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class viewHolder extends RecyclerView.ViewHolder {
//      StoryRvdesigneBinding  binding;
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//         binding=StoryRvdesigneBinding.bind(itemView);
//        }
//    }
//}
