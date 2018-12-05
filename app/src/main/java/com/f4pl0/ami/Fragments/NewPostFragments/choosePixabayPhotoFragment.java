package com.f4pl0.ami.Fragments.NewPostFragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class choosePixabayPhotoFragment extends Fragment {

    GridLayout gridPixabayPhotoLayout;
    EditText imageSearchEditText;
    private Target target;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_choose_pixabay_photo, container, false);

        //Init components
       target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {



            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        gridPixabayPhotoLayout = fragmentView.findViewById(R.id.gridPixabayPhotoLayout);
        imageSearchEditText = fragmentView.findViewById(R.id.imageSearchEditText);
        imageSearchEditText.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        imageSearchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    gridPixabayPhotoLayout.removeAllViews();
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String url = null;
                    try {
                        url = "https://pixabay.com/api/?key=10874794-852a16c26eee4fc62f5582ac7&q="+URLEncoder.encode(imageSearchEditText.getText().toString(), "utf-8")+"&image_type=photo";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray hits = jsonObject.getJSONArray("hits");
                                        for(int i=0;i < hits.length(); i++){
                                            String url = hits.getJSONObject(i).getString("webformatURL");
                                            //Picasso.with(getContext()).load(url).into(target);
                                            MyAsync obj = new MyAsync(url){

                                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                @Override
                                                protected void onPostExecute(Bitmap bmp) {
                                                    super.onPostExecute(bmp);

                                                    /*Bitmap bm = bmp;
                                                    LinearLayout layout = new LinearLayout(getApplicationContext());
                                                    layout.setLayoutParams(new AbsListView.LayoutParams(250, 250));
                                                    layout.setGravity(Gravity.CENTER);

                                                    ImageView imageView = new ImageView(getApplicationContext());
                                                    imageView.setLayoutParams(new AbsListView.LayoutParams(220, 220));
                                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                                    imageView.setImageBitmap(bm);

                                                    layout.addView(imageView);*/
                                                    final ImageButton imageButton = new ImageButton(getContext());
                                                    GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
                                                    //imageButton.getRootView().setLayoutParams(parem);
                                                    imageButton.getRootView().setLayoutParams(new AbsListView.LayoutParams(220, 220));
                                                    imageButton.setBackground(getResources().getDrawable( R.drawable.text_box_input_bg));
                                                    imageButton.setImageBitmap(bmp);
                                                    imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                    imageButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            final Dialog settingsDialog = new Dialog(getContext());
                                                            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                                            ImageView imageView = new ImageView(getContext());
                                                            imageView.setImageBitmap(((BitmapDrawable)imageButton.getDrawable()).getBitmap());
                                                            settingsDialog.addContentView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                            Button confirmBtn = new Button(getContext());
                                                            confirmBtn.setText("Use");
                                                            confirmBtn.setAllCaps(false);
                                                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    ((NewPostActivity)getActivity()).SetPostImage(((BitmapDrawable)imageButton.getDrawable()).getBitmap());
                                                                    settingsDialog.dismiss();
                                                                }
                                                            });
                                                            settingsDialog.addContentView(confirmBtn, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                            settingsDialog.show();
                                                        }
                                                    });
                                                    gridPixabayPhotoLayout.addView(imageButton);
                                                }
                                            };obj.execute();





                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Error occured.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);
                }
                return false;
            }
        });

        return fragmentView;
    }
    public class MyAsync extends AsyncTask<Void, Void, Bitmap>{
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
