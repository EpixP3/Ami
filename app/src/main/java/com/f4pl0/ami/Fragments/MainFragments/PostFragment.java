package com.f4pl0.ami.Fragments.MainFragments;

import android.annotation.SuppressLint;
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
        posterImageImg = fragmentView.findViewById(R.id.profileProfileImg);
        titleTxt = fragmentView.findViewById(R.id.post_titleTxt);
        imageImg = fragmentView.findViewById(R.id.post_contentImg);

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
                    posterImageImg.setImageBitmap(bmp);
                }
            };
            obj.execute();
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
                        imageImg.setImageBitmap(bmp);
                    }
                };
                a.execute();
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
}
