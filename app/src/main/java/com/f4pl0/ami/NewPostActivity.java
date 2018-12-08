package com.f4pl0.ami;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.f4pl0.ami.Fragments.NewPostFragments.NewPostCategoryFragment;
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
    private String interests = "";
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        progress = new ProgressDialog(this);
        progress.setTitle("Please wait a bit");
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
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
                            case 2:
                                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                transaction.replace(R.id.newPostFragment, new NewPostCategoryFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                                currentStep++;
                                break;
                            case 3:
                                String interestsarr = getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
                                if(interestsarr.length() > 0) {
                                    interests = interestsarr.substring(1);
                                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                    transaction.replace(R.id.newPostFragment, new NewPostCategoryFragment());
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    currentStep++;
                                }else{
                                    Toast.makeText(NewPostActivity.this, "Please select 1 or more interests.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        break;
                }
            }
        });
    }
    //getContext().getSharedPreferences("interests",Context.MODE_PRIVATE).edit().putString("user.interests", "").commit();
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
    public void showLoading(String message){
        progress.setMessage(message);
        progress.show();
    }
    public void dismissLoading(){
        progress.dismiss();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
