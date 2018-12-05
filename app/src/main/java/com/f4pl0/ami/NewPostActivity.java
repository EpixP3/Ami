package com.f4pl0.ami;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.f4pl0.ami.Fragments.NewPostFragments.NewPostContentTextFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostPicFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostTitleTextFragment;

public class NewPostActivity extends AppCompatActivity {

    ImageButton nextBtn;
    private int currentStep = 0;
    private String postTextContent = "";
    private String setupType = "";
    private String postTextTitle = "";
    private Bitmap postImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //Initialize Components
        Intent intent = getIntent();
        setupType = intent.getStringExtra("TYPE");
        switch (setupType){
            case "1":
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.newPostFragment, new NewPostContentTextFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }

        nextBtn = findViewById(R.id.newPostNextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (setupType){
                    case "1":
                        switch (currentStep){
                            case 0:
                                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                transaction.replace(R.id.newPostFragment, new NewPostTitleTextFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                                currentStep++;
                                break;
                            case 1:
                                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                transaction.replace(R.id.newPostFragment, new NewPostPicFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                                currentStep++;
                                break;
                        }
                        break;
                }
            }
        });

    }
    public void SetPostContent(String postTextContent){
        this.postTextContent = postTextContent;
    }
    public void SetPostTitle(String postTextTitle){
        this.postTextTitle = postTextTitle;
    }
    public void SetPostImage(Bitmap postImage){
        this.postImage = postImage;
        //TODO NEXT STEP
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
