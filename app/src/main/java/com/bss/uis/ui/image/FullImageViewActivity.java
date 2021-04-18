package com.bss.uis.ui.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bss.uis.R;
import com.bumptech.glide.Glide;

public class FullImageViewActivity extends AppCompatActivity {

    ImageView myImage, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        myImage = findViewById(R.id.image);
        back = findViewById(R.id.back);

        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .placeholder(R.color.codeGray)
                .into(myImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
    }
}