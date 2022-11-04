package com.menga.Redeption.Activities;

import static com.menga.Redeption.Frigments.AddPostFragment.pohoto;
import static com.menga.Redeption.Frigments.AddPostFragment.postbtn;
import static com.menga.Redeption.Frigments.AddPostFragment.remove;
import static com.menga.Redeption.Frigments.AddPostFragment.uri;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.menga.Redeption.R;

public class editpohoto extends AppCompatActivity {

    public  static Uri outputUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpohoto);

        Intent dsPhotoEditorIntent = new Intent(getApplicationContext(), DsPhotoEditorActivity.class);

        dsPhotoEditorIntent.setData(uri);

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "FriendSpace");

//        int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//
//        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);


        startActivityForResult(dsPhotoEditorIntent, 200);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 200:
                    outputUri = data.getData();
                    pohoto.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
              postbtn.setTextColor(getApplicationContext().getResources().getColor(R.color.maincolor));
              postbtn.setEnabled(true);
                    pohoto.setImageURI(outputUri);
                    finish();
                    break;

            }

        }else {
            finish();
        }

    }
}