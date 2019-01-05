package com.f4pl0.ami.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
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
import com.f4pl0.ami.MyActivities.MyContactsActivity;
import com.f4pl0.ami.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class DetailedContactFragment extends Fragment {
    ImageView profileImg, typeImg;
    int type, id;
    String name, profileLink;
    TextView nameTxt;
    Button editButton, deleteButton;
    String session;
    LinearLayout.LayoutParams params;

    int temptype;


    public DetailedContactFragment(String name, String image, int type, int id, String session, LinearLayout.LayoutParams params){
        this.name = name;
        this.id = id;
        this.type = type;
        this.profileLink = image;
        this.session = session;
        this.params = params;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView = inflater.inflate(R.layout.fragment_detailed_contact, container, false);

        //Init components
        profileImg = fragmentView.findViewById(R.id.contactImage);
        typeImg = fragmentView.findViewById(R.id.contactTypeImg);
        nameTxt = fragmentView.findViewById(R.id.contactName);
        editButton = fragmentView.findViewById(R.id.editContactBtn);
        deleteButton = fragmentView.findViewById(R.id.deleteContactBtn);

        //Set values
        nameTxt.setText(name);
        switch (type){
            case 0:
                typeImg.setImageDrawable(getActivity().getDrawable(R.drawable.contact_acquaintance));
                break;
            case 1:
                typeImg.setImageDrawable(getActivity().getDrawable(R.drawable.contact_closefriend));
                break;
            case 2:
                typeImg.setImageDrawable(getActivity().getDrawable(R.drawable.contact_relative));
                break;
            case 3:
                typeImg.setImageDrawable(getActivity().getDrawable(R.drawable.contact_partner));
                break;
        }
        fragmentView.setLayoutParams(params);

        //Set profile image
        if(profileLink.length() > 5){
            MyAsync a = new MyAsync(profileLink) {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                protected void onPostExecute(Bitmap bmp) {
                    super.onPostExecute(bmp);
                    profileImg.setImageBitmap(bmp);
                }
            };
            a.execute();
        }
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileLink.length() > 5){
                    //TODO open profile
                    Toast.makeText(getContext(), "Yet to be implemented", Toast.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(fragmentView, "User has no Ami profile", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://ami.earth/android/api/deleteContact.php";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response.contains("invalid")) {
                                        //finish();
                                    }else if(response.contains("ok")){
                                        Intent intent = new Intent(getActivity(), MyContactsActivity.class);
                                        getActivity().startActivity(intent);
                                        Toast.makeText(getContext(), name+" removed.", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }

                                }catch(Exception e){
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        //Put the POST parameters to the request
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("sessionTXT", session);
                        params.put("contactID", ""+id);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temptype = type;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit "+name);

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.getRootView().setId(View.generateViewId());
                linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(height, height);

                final ImageButton acqBtn = new ImageButton(getContext());
                final ImageButton cloBtn = new ImageButton(getContext());
                final ImageButton relBtn = new ImageButton(getContext());
                final ImageButton parBtn = new ImageButton(getContext());

                acqBtn.setImageDrawable(getActivity().getDrawable(R.drawable.contact_acquaintance));
                cloBtn.setImageDrawable(getActivity().getDrawable(R.drawable.contact_closefriend));
                relBtn.setImageDrawable(getActivity().getDrawable(R.drawable.contact_relative));
                parBtn.setImageDrawable(getActivity().getDrawable(R.drawable.contact_partner));

                acqBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                cloBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                relBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                parBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                acqBtn.setBackgroundColor(Color.TRANSPARENT);
                cloBtn.setBackgroundColor(Color.TRANSPARENT);
                relBtn.setBackgroundColor(Color.TRANSPARENT);
                parBtn.setBackgroundColor(Color.TRANSPARENT);

                acqBtn.setLayoutParams(layoutParams);
                cloBtn.setLayoutParams(layoutParams);
                relBtn.setLayoutParams(layoutParams);
                parBtn.setLayoutParams(layoutParams);

                switch (temptype){
                    case 0:
                        acqBtn.setImageAlpha(255);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(100);
                        break;
                    case 1:
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(255);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(100);
                        break;
                    case 2:
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(255);
                        parBtn.setImageAlpha(100);
                        break;
                    case 3:
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(255);
                        break;
                }

                acqBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temptype = 0;
                        acqBtn.setImageAlpha(255);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(100);
                    }
                });
                cloBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temptype = 1;
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(255);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(100);
                    }
                });
                relBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temptype = 2;
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(255);
                        parBtn.setImageAlpha(100);
                    }
                });
                parBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temptype = 3;
                        acqBtn.setImageAlpha(100);
                        cloBtn.setImageAlpha(100);
                        relBtn.setImageAlpha(100);
                        parBtn.setImageAlpha(255);
                    }
                });

                linearLayout.addView(acqBtn);
                linearLayout.addView(cloBtn);
                linearLayout.addView(relBtn);
                linearLayout.addView(parBtn);

                builder.setView(linearLayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url ="http://ami.earth/android/api/editContact.php";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            if (response.contains("invalid")) {
                                                //finish();
                                            }else if(response.contains("ok")){
                                                Intent intent = new Intent(getActivity(), MyContactsActivity.class);
                                                getActivity().startActivity(intent);
                                                getActivity().finish();
                                            }

                                        }catch(Exception e){
                                        }
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                //Put the POST parameters to the request
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("sessionTXT", session);
                                params.put("contactID", ""+id);
                                params.put("type", ""+temptype);
                                return params;
                            }
                        };
                        queue.add(postRequest);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


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
