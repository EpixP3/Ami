package com.f4pl0.ami.Fragments.MainFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.Fragments.NewPostFragments.choosePixabayPhotoFragment;
import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.f4pl0.ami.Utils.InterestButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MenuProfileFragment extends Fragment {

    String[] profileData;
    TextView profileNameTxt, profileAgeTxt, profileOccupationTxt, profileLocationTxt, profileQuoteTxt;
    ImageView profileProfileImg, profileCoverImg;
    public boolean profilePicRequest = true;
    LinearLayout profileInterestsLyt;

    public MenuProfileFragment(){
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate (Bundle savedInstanceState) {

        //#####################
        //###GET THE PROFILE###
        //#####################
        //Get the stored session
        super.onCreate(savedInstanceState);
        final String session = getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).getString("SessionID", "");
        //Check if storred session is valid
        //POST REQUEST TO THE SERVER
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://ami.earth/android/api/getProfile.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.contains("invalid")) {
                                //Session is invalid, go to setup and delete stored session
                                getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).edit().putString("SessionID", "").commit();
                                Intent myIntent = new Intent(getContext(), SetupActivity.class);
                                startActivity(myIntent);
                            } else {
                                profileData = response.split(",;,a");
                                setProfileAssets(profileData[0],profileData[1],profileData[2],profileData[3],profileData[5],profileData[6],profileData[7], profileData[4]);
                            }
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "There was an error connecting to the server.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //Put the POST parameters to the request
                Map<String, String> params = new HashMap<String, String>();
                params.put("sessionTXT", session);
                return params;
            }
        };
        queue.add(postRequest);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu_profile, container, false);

        //Initialize components
        profileNameTxt = fragmentView.findViewById(R.id.profileNameTxt);
        profileAgeTxt = fragmentView.findViewById(R.id.profileAgeTxt);
        profileOccupationTxt = fragmentView.findViewById(R.id.profileOccupationTxt);
        profileLocationTxt = fragmentView.findViewById(R.id.profileLocationTxt);
        profileQuoteTxt = fragmentView.findViewById(R.id.profileQuoteTxt);
        profileProfileImg = fragmentView.findViewById(R.id.profileProfileImg);
        profileCoverImg = fragmentView.findViewById(R.id.profileCoverImg);
        profileInterestsLyt = fragmentView.findViewById(R.id.profileInterestsLyt);

        profileProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePicRequest = true;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });

        profileCoverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePicRequest = false;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });

        if(profileData != null){
            setProfileAssets(profileData[0],profileData[1],profileData[2],profileData[3],profileData[5],profileData[6],profileData[7], profileData[4]);
        }

        return fragmentView;
    }
    public boolean isProfilePicRequest(){
        return profilePicRequest;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setProfileAssets(String name, String age, String occupation, String location, String profilePicture, String coverPicture, String quote, String interests){
        profileNameTxt.setText(name);
        profileAgeTxt.setText(age);
        profileOccupationTxt.setText(occupation);
        profileLocationTxt.setText(location);
        profileQuoteTxt.setText("\""+quote+"\"");

        String[] interestsArray = interests.split(",");
        for(int i = 0; i < interestsArray.length; i++){
            profileInterestsLyt.addView(new InterestButton(getContext(), interestsArray[i], true));
            TextView sample = new TextView(getContext());
            sample.setText("a");
            sample.setTextColor(Color.TRANSPARENT);
            profileInterestsLyt.addView(sample);
        }

        if(!profilePicture.contains("none")){
            MyAsync obj = new MyAsync(profilePicture){

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                protected void onPostExecute(Bitmap bmp) {
                    super.onPostExecute(bmp);
                    profileProfileImg.setImageBitmap(bmp);
                }
            };obj.execute();
        }
        if(!coverPicture.contains("none")){
            MyAsync obj = new MyAsync(coverPicture){

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                protected void onPostExecute(Bitmap bmp) {
                    super.onPostExecute(bmp);
                    profileCoverImg.setImageBitmap(bmp);
                }
            };obj.execute();


        }


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
