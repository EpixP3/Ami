package com.f4pl0.ami;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {
    String posterName, posterLocation, title, content;
    Bitmap posterImgBmp, contentImgBmp;
    TextView posterNameTxt, posterLocationTxt, titleTxt, contentTxt;
    ImageView posterImg, contentImg;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Init compontnts
        posterNameTxt = findViewById(R.id.postActivity_name);
        posterLocationTxt = findViewById(R.id.postActivity_location);
        titleTxt = findViewById(R.id.postActivity_title);
        contentTxt = findViewById(R.id.postActivity_contentTxt);
        posterImg = findViewById(R.id.postActivity_profileImg);
        contentImg = findViewById(R.id.postActivity_contentImg);
        backBtn = findViewById(R.id.postActivity_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Get data needed for the post
        Intent intent = getIntent();
        posterImgBmp = intent.getParcelableExtra("posterImg");
        contentImgBmp = intent.getParcelableExtra("contentImg");
        posterName =  intent.getStringExtra("posterName");
        title =  intent.getStringExtra("title");
        posterLocation =  intent.getStringExtra("posterLocation");
        content =  intent.getStringExtra("contentTxt");

        //Set the data to the views
        posterNameTxt.setText(posterName);
        posterImg.setImageBitmap(posterImgBmp);
        titleTxt.setText(title);
        contentTxt.setText(content);
        contentImg.setImageBitmap(contentImgBmp);
        posterLocationTxt.setText(posterLocation);
    }
}
