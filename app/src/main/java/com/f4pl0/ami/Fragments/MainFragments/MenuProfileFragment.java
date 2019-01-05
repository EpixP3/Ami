package com.f4pl0.ami.Fragments.MainFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.MyActivities.MyContactsActivity;
import com.f4pl0.ami.MyActivities.MyPostsActivity;
import com.f4pl0.ami.MySettingsActivity;
import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.f4pl0.ami.Utils.InterestButton;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@SuppressLint("ValidFragment")
public class MenuProfileFragment extends Fragment {

    String[] profileData;
    TextView profileNameTxt, profileAgeTxt, profileOccupationTxt, profileLocationTxt, profileQuoteTxt;
    ImageView profileProfileImg, profileCoverImg;
    public boolean profilePicRequest = true;
    LinearLayout profileInterestsLyt;
    TextView contactsTxt, highfivesTxt,postsTxt, settingsTxt;
    String session;
    MainActivity mainActivity;

    public MenuProfileFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate (Bundle savedInstanceState) {

        //#####################
        //###GET THE PROFILE###
        //#####################

        //Get the stored session
        super.onCreate(savedInstanceState);
        session = getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).getString("SessionID", "");
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
        profileProfileImg = fragmentView.findViewById(R.id.postActivity_profileImg);
        profileCoverImg = fragmentView.findViewById(R.id.profileCoverImg);
        profileInterestsLyt = fragmentView.findViewById(R.id.profileInterestsLyt);
        contactsTxt = fragmentView.findViewById(R.id.profile_contactsTxt);
        highfivesTxt = fragmentView.findViewById(R.id.profile_highFivesTxt);
        postsTxt = fragmentView.findViewById(R.id.profile_postsTxt);
        settingsTxt = fragmentView.findViewById(R.id.profile_settingsTxt);

        //##############################
        //###### TUTORIAL ##############
        //##############################
        new ShowcaseView.Builder(getActivity())
                //.setTarget(new ViewTarget(getId(), getActivity()))
                .setContentTitle("This is Your Profile")
                .setContentText("Here Your can customize anything You want")
                .withNewStyleShowcase()
                .singleShot(1)
                .setStyle(R.style.AppTheme)
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        new ShowcaseView.Builder(getActivity())
                                .setTarget(new ViewTarget(profileProfileImg.getId(), getActivity()))
                                .setContentTitle("This is Your Profile Picture")
                                .setContentText("Tap and hold it to change it!")
                                .withNewStyleShowcase()
                                .singleShot(2)
                                .setStyle(R.style.AppTheme)
                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                    @Override
                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                        new ShowcaseView.Builder(getActivity())
                                                .setTarget(new ViewTarget(profileCoverImg.getId(), getActivity()))
                                                .setContentTitle("This is Your Cover Picture")
                                                .setContentText("Tap and hold it to change it!")
                                                .withNewStyleShowcase()
                                                .singleShot(3)
                                                .setStyle(R.style.AppTheme)
                                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                                    @Override
                                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                                    }

                                                    @Override
                                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                                        new ShowcaseView.Builder(getActivity())
                                                                .setTarget(new ViewTarget(profileNameTxt.getId(), getActivity()))
                                                                .setContentTitle("This is Your Name")
                                                                .setContentText("Tap and- hold it to change it!")
                                                                .withNewStyleShowcase()
                                                                .singleShot(4)
                                                                .setStyle(R.style.AppTheme)
                                                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                                                    @Override
                                                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                                                    }

                                                                    @Override
                                                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                                                        new ShowcaseView.Builder(getActivity())
                                                                                .setTarget(new ViewTarget(profileOccupationTxt.getId(), getActivity()))
                                                                                .setContentTitle("Theese are Your descriptive texts")
                                                                                .setContentText("Tap and hold them to change your occupation and age!")
                                                                                .withNewStyleShowcase()
                                                                                .singleShot(5)
                                                                                .setStyle(R.style.AppTheme)
                                                                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                                                                    @Override
                                                                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                                                                    }

                                                                                    @Override
                                                                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                                                                        new ShowcaseView.Builder(getActivity())
                                                                                                .setTarget(new ViewTarget(profileInterestsLyt.getId(), getActivity()))
                                                                                                .setContentTitle("Theese are Your Interests")
                                                                                                .singleShot(6)
                                                                                                .setContentText("Tap and hold them to delete, or add a new one!")
                                                                                                .withNewStyleShowcase()
                                                                                                .setStyle(R.style.AppTheme)
                                                                                                .build();
                                                                                    }

                                                                                    @Override
                                                                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                                                                    }

                                                                                    @Override
                                                                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                                                                    }
                                                                                })
                                                                                .build();
                                                                    }

                                                                    @Override
                                                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                                                    }

                                                                    @Override
                                                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                                                    }
                                                                })
                                                                .build();
                                                    }

                                                    @Override
                                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                                    }

                                                    @Override
                                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                                    }
                                                })
                                                .build();
                                    }

                                    @Override
                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                    }
                                })
                                .build();
                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                    }
                })
                .build();


        contactsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyContactsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        highfivesTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LAUNCH MY HIGH FIVES ACTIVITY
            }
        });
        postsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPostsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        settingsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MySettingsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        profileProfileImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                profilePicRequest = true;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
                return false;
            }
        });

        profileCoverImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                profilePicRequest = false;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
                return false;
            }
        });

        profileNameTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change your name");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                input.setText(profileNameTxt.getText());
                builder.setView(input);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MakeChangeRequest("name", input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return false;
            }
        });
        profileAgeTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change your age");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL);
                input.setText(profileAgeTxt.getText());
                builder.setView(input);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MakeChangeRequest("age", input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return false;
            }
        });
        profileOccupationTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change your occupation");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                input.setText(profileOccupationTxt.getText());
                builder.setView(input);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MakeChangeRequest("occupation", input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return false;
            }
        });
        profileQuoteTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change your quote");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                input.setText(profileQuoteTxt.getText().toString().substring(1, profileQuoteTxt.getText().toString().length()-2));
                builder.setView(input);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MakeChangeRequest("quote", input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return false;
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
    private void MakeChangeRequest(final String type, final String content){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://ami.earth/android/api/changeProfileContent.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("ok")){
                             mainActivity.ReloadProfileFragment();
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
                params.put("session", session);
                params.put("type", type);
                params.put("content", content);
                return params;
            }
        };
        queue.add(postRequest);
    }
    public static class MyAsync extends AsyncTask<Void, Void, Bitmap> {
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
