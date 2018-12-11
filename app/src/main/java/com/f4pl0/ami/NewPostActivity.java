package com.f4pl0.ami;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.Fragments.MainFragments.MenuProfileFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostCategoryFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostContentTextFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostPicFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostShareFragment;
import com.f4pl0.ami.Fragments.NewPostFragments.NewPostTitleTextFragment;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {


    String session;
    ImageButton nextBtn;
    private int currentStep = 0;
    private String postTextContent = "";
    private String setupType = "";
    private String postTextTitle = "";
    private Bitmap postImage = null;
    private String interests = "";
    private boolean shareFamily=true, shareCloseFriends=true, shareAcq=true, shareLikeMinded=true;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //Get the stored session
        session = getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).getString("SessionID","");

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
                                    transaction.replace(R.id.newPostFragment, new NewPostShareFragment());
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    nextBtn.setImageDrawable(getResources().getDrawable(R.drawable.new_post_post));
                                    currentStep++;
                                }else{
                                    Toast.makeText(NewPostActivity.this, "Please select 1 or more interests.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:
                                String shares = "";
                                if(shareFamily){
                                    shares += "fam";
                                }if(shareCloseFriends){
                                    if(shares.length() > 0){
                                        shares += ",clo";
                                    }else{
                                        shares += "clo";
                                    }
                                }if(shareAcq){
                                    if(shares.length() > 0){
                                        shares += ",acq";
                                    }else{
                                        shares += "acq";
                                    }
                                }if(shareLikeMinded){
                                    if(shares.length() > 0){
                                        shares += ",lik";
                                    }else{
                                        shares += "lik";
                                    }
                                }
                                if(shares.length() >  0) {
                                    post(postTextTitle, postTextContent, postImage, interests, shares);
                                }else{
                                    Toast.makeText(NewPostActivity.this, "Please select at least 1 group to share with.", Toast.LENGTH_SHORT).show();
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
    public void SetShare(int group, boolean share){
        switch(group){
            case 0:
                this.shareFamily = share;
                break;
            case 1:
                this.shareCloseFriends = share;
                break;
            case 2:
                this.shareAcq = share;
                break;
            case 3:
                this.shareLikeMinded = share;
                break;
        }
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

    private void post(final String title, final String content, Bitmap photo, final String categories, final String shares){
        showLoading("Posting...");

        boolean flag = false;
        //converting image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            photo = scaleBitmapContent(photo);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }catch (Exception e){
            flag = true;
        }
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //sending image to server
        final boolean finalFlag = flag;
        StringRequest request = new StringRequest(Request.Method.POST, "http://ami.earth/android/api/NewPost.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                dismissLoading();
                if(s.contains("ok")){
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissLoading();
                Toast.makeText(getApplicationContext(), "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                if(finalFlag){
                    parameters.put("hasImg", "no");
                }else{
                    parameters.put("hasImg", "yes");
                }
                parameters.put("image", imageString);
                parameters.put("session", session);
                parameters.put("title", title);
                parameters.put("content", content);
                parameters.put("categories", categories);
                parameters.put("shares", shares);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(request);
    }
    private Bitmap scaleBitmapContent(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int maxWidth = 512;
        int maxHeight = 512;

        Log.v("Pictures", "Width and height are " + width + "--" + height);

        if (width > height) {
            // landscape-
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        } else if (height > width) {
            // portrait
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        } else {
            // square
            height = maxHeight;
            width = maxWidth;
        }

        Log.v("Pictures", "after scaling Width and height are " + width + "--" + height);

        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        return bm;
    }private Bitmap scaleBitmapProfile(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int maxWidth = 256;
        int maxHeight = 256;

        Log.v("Pictures", "Width and height are " + width + "--" + height);

        if (width > height) {
            // landscape-
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        } else if (height > width) {
            // portrait
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        } else {
            // square
            height = maxHeight;
            width = maxWidth;
        }

        Log.v("Pictures", "after scaling Width and height are " + width + "--" + height);

        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        return bm;
    }
}
