package com.f4pl0.ami.Fragments.MainFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.PostActivity;
import com.f4pl0.ami.R;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("ValidFragment")
public class PostFragment extends Fragment {
    private String title, content, image, posterName, posterLocation, posterImage;
    TextView posterNameTxt, posterLocationTxt, titleTxt, readMoreTxt;
    ImageView posterImageImg, imageImg;
    Bitmap postBitmap = null , profileBitmap = null;
    View fragmentView;

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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false);

        //Initialize components
        posterNameTxt = fragmentView.findViewById(R.id.post_nameTxt);
        posterLocationTxt = fragmentView.findViewById(R.id.post_locationTxt);
        posterImageImg = fragmentView.findViewById(R.id.postActivity_profileImg);
        titleTxt = fragmentView.findViewById(R.id.post_titleTxt);
        imageImg = fragmentView.findViewById(R.id.post_contentImg);
        readMoreTxt = fragmentView.findViewById(R.id.post_readmoreTxt);



        readMoreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                try {
                    intent.putExtra("contentImg", encodeToBase64(postBitmap, Bitmap.CompressFormat.JPEG, 100));
                }catch (Exception e){}
                try {
                    intent.putExtra("posterImg", encodeToBase64(profileBitmap, Bitmap.CompressFormat.JPEG, 100));
                }catch (Exception e){}
                intent.putExtra("contentTxt",content);
                intent.putExtra("posterName", posterName);
                intent.putExtra("posterLocation", posterLocation);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        imageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                try {
                    intent.putExtra("contentImg", saveToInternalStorage(postBitmap));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    intent.putExtra("posterImg", encodeToBase64(profileBitmap, Bitmap.CompressFormat.JPEG, 100));
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("contentTxt", content);
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
            imageImg.setImageBitmap(postBitmap);
        }else{
        if(image.length() > 5) {
            MenuProfileFragment.MyAsync obj = new MenuProfileFragment.MyAsync(image) {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                protected void onPostExecute(Bitmap bmp) {
                    super.onPostExecute(bmp);
                    postBitmap = bmp;
                    postBitmap = scaleBitmapProfile(postBitmap);
                    readMoreTxt.setVisibility(View.GONE);
                    imageImg.setImageBitmap(postBitmap);
                }
            };
            obj.execute();
        }else{
            imageImg.setVisibility(View.GONE);
            readMoreTxt.setVisibility(View.VISIBLE);
        }
        }
        if(profileBitmap != null){
            posterImageImg.setImageBitmap(profileBitmap);
        }else {
            if (posterImage.length() > 5) {
                MenuProfileFragment.MyAsync a = new MenuProfileFragment.MyAsync(posterImage) {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void onPostExecute(Bitmap bmp) {
                        super.onPostExecute(bmp);
                        profileBitmap = bmp;
                        profileBitmap = scaleBitmapContent(bmp);
                        posterImageImg.setImageBitmap(profileBitmap);
                    }
                };
                a.execute();
            }else{
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
        try {
            int width = bm.getWidth();
            int height = bm.getHeight();
            int maxWidth = 512;
            int maxHeight = 512;

            Log.v("Pictures", "Width and height are " + width + "--" + height);

            if (width > height) {
                // landscape-
                float ratio = (float) width / maxWidth;
                width = maxWidth;
                height = (int) (height / ratio);
            } else if (height > width) {
                // portrait
                float ratio = (float) height / maxHeight;
                height = maxHeight;
                width = (int) (width / ratio);
            } else {
                // square
                height = maxHeight;
                width = maxWidth;
            }

            Log.v("Pictures", "after scaling Width and heig-ht are " + width + "--" + height);

            bm = Bitmap.createScaledBitmap(bm, width, height, true);
            return bm;
        }catch(Exception e){
            return null;
        }
    }private Bitmap scaleBitmapProfile(Bitmap bm) {
        try {
            int width = bm.getWidth();
            int height = bm.getHeight();
            int maxWidth = 256;
            int maxHeight = 256;

            Log.v("Pictures", "Width and height are " + width + "--" + height);

            if (width > height) {
                // landscape-
                float ratio = (float) width / maxWidth;
                width = maxWidth;
                height = (int) (height / ratio);
            } else if (height > width) {
                // portrait
                float ratio = (float) height / maxHeight;
                height = maxHeight;
                width = (int) (width / ratio);
            } else {
                // square
                height = maxHeight;
                width = maxWidth;
            }

            Log.v("Pictures", "after scaling Width and height are " + width + "--" + height);

            bm = Bitmap.createScaledBitmap(bm, width, height, true);
            return bm;
        }catch(Exception e){
            return null;
        }
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    public void SetLLParams(LinearLayout.LayoutParams params){
        getView().setLayoutParams(params);
    }
}
