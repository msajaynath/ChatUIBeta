package com.chatui.ms.chatui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gun0912.tedpicker.ImagePickerActivity;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import utilities.BitmapDecoder;
import utilities.ChatMessage;

public class NewGroupAddActivity extends AppCompatActivity {

    private static final int INTENT_REQUEST_GET_IMAGES = 20;
    private ImageView emojiButton;
    private EmojiconEditText emojiconEditText;
    private View rootView;
    private EmojIconActions emojIcon;
    private ImageView profileImage;
    private LinearLayout upHome;
    private LinearLayout upNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rootView = findViewById(R.id.root_view);
        upHome = (LinearLayout) findViewById(R.id.upHome);
        upNext = (LinearLayout) findViewById(R.id.nextButton);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        emojiButton = (ImageView) findViewById(R.id.kayboardswitch);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojiEditText);
        emojIcon=new EmojIconActions(this,rootView,emojiconEditText,emojiButton);
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard","open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard","close");
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(NewGroupAddActivity.this, ImagePickerActivity.class);
                startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);
            }
        });
        upHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(NewGroupAddActivity.this,NewGroupAddMemberActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            for(Uri uri:image_uris)
            {
                try {
                    Uri newUri=Uri.fromFile(new File(uri.getPath()));

                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), newUri);

                    Bitmap bitmap = BitmapDecoder.getThumbnail(newUri, getApplicationContext());
                    profileImage.setImageBitmap(bitmap);

                }
                catch (Exception e)
                {
                    Log.d("devuu",e.toString());
                }

            }


            // Log.d("devuu",image_uris.get(0).getPath()) ;
            //do something
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
