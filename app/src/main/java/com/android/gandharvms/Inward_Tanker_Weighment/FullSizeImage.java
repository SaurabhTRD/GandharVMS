package com.android.gandharvms.Inward_Tanker_Weighment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.squareup.picasso.Picasso;

public class FullSizeImage extends AppCompatActivity {

    ImageView fullSizeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_image);

        fullSizeImageView = findViewById(R.id.full_size_image_view);

        // Get the image URL from the intent
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Load the image using Picasso or any other image loading library
        Picasso.get().load(imageUrl).into(fullSizeImageView);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);
        finish();
    }
}