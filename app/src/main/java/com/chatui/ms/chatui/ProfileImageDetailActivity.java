package com.chatui.ms.chatui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class ProfileImageDetailActivity extends AppCompatActivity {

    ImageView bitmapImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bitmapImage= (ImageView) findViewById(R.id.imageZoom);
        Intent intent =getIntent();
        if(intent.hasExtra("Image"))
        {

            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("Image"),0,getIntent().getByteArrayExtra("Image").length);



            bitmapImage.setImageBitmap(b);

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
