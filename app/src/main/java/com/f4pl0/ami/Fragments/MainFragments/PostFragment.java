package com.f4pl0.ami.Fragments.MainFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.f4pl0.ami.PostActivity;
import com.f4pl0.ami.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("ValidFragment")
public class PostFragment extends Fragment {
    private String title, content, image, posterName, posterLocation, posterImage;
    TextView posterNameTxt, posterLocationTxt, titleTxt;
    ImageView posterImageImg, imageImg;
    Bitmap postBitmap = null , profileBitmap = null;

    public PostFragment(String title, String content, String image, String posterName, String posterLocation, String posterImage){
        this.title = title;
        this.content = content;
        this.image = image;
        this.posterName = posterName;
        this.posterLocation = posterLocation;
        this.posterImage = posterImage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_post, container, false);

        //Initialize components
        posterNameTxt = fragmentView.findViewById(R.id.post_nameTxt);
        posterLocationTxt = fragmentView.findViewById(R.id.post_locationTxt);
        posterImageImg = fragmentView.findViewById(R.id.postActivity_profileImg);
        titleTxt = fragmentView.findViewById(R.id.post_titleTxt);
        imageImg = fragmentView.findViewById(R.id.post_contentImg);
        imageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra("contentImg", profileBitmap);
                intent.putExtra("posterImg", postBitmap);
                intent.putExtra("contentTxt",content);
                intent.putExtra("posterName", posterName);
                intent.putExtra("posterLocation", posterLocation);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        //Set values
        posterNameTxt.setText(posterName);
        posterLocationTxt.setText(posterLocation);

        if(postBitmap != null){
            posterImageImg.setImageBitmap(postBitmap);
        }else{
        if(image.length() > 5) {
            MenuProfileFragment.MyAsync obj = new MenuProfileFragment.MyAsync(posterImage) {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                protected void onPostExecute(Bitmap bmp) {
                    super.onPostExecute(bmp);
                    postBitmap = bmp;
                    postBitmap = scaleBitmapContent(postBitmap);
                    posterImageImg.setImageBitmap(postBitmap);
                }
            };
            obj.execute();
        }else{
        }
        }
        if(profileBitmap != null){
            imageImg.setImageBitmap(profileBitmap);
        }else {
            if (image.length() > 5) {
                MenuProfileFragment.MyAsync a = new MenuProfileFragment.MyAsync(image) {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void onPostExecute(Bitmap bmp) {
                        super.onPostExecute(bmp);
                        profileBitmap = bmp;
                        profileBitmap = scaleBitmapProfile(bmp);
                        imageImg.setImageBitmap(profileBitmap);
                    }
                };
                a.execute();
            }else{
                imageImg.setVisibility(View.GONE);
            }
        }
        titleTxt.setText(title);

        return fragmentView;
    }

    public class MyAsync extends AsyncTask<Void, Void, Bitmap> {
        String src;
        public MyAsync(String src){
            this.src = src;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
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
