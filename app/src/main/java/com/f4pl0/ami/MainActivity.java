package com.f4pl0.ami;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.Fragments.MainFragments.MenuProfileFragment;
import com.f4pl0.ami.Fragments.MainFragments.MenuSurroundsFragment;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    BottomNavigationView bottomNavigationView;
    MenuSurroundsFragment menuSurroundsFragment;
    MenuProfileFragment menuProfileFragment;
    Fragment currentFragment;
    String session;
    int currentFragmentNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create a new progress dialog to show for loading
        progress = new ProgressDialog(this);
        progress.setTitle("Please wait a bit");
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        //Get the stored session
        session = getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).getString("SessionID","");
        // Check if user has a storred session
        if(!session.isEmpty()){
            //Check if storred session is valid
            //POST REQUEST TO THE SERVER
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://ami.earth/android/api/checkSession.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            dismissLoading();
                            try {
                                if (response.contains("invalid")) {
                                    //Session is invalid, go to setup and delete stored session
                                    getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).edit().putString("SessionID", "").commit();
                                    Intent myIntent = new Intent(MainActivity.this, SetupActivity.class);
                                    startActivity(myIntent);
                                    finish();
                                } else if (response.contains("valid")){
                                    //Session is valid, continue with loading of the main screen.
                                    setContentView(R.layout.activity_main);

                                    // CODE FOR INITIALIZATION AND EVERYTHING ELSE GOES HERE
                                    InitializeComponents();

                                }else{
                                    Toast.makeText(MainActivity.this, "There was an error." , Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }catch(Exception e){
                                Toast.makeText(MainActivity.this, "There was an error." , Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                finish();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "There was an error connecting to the server.", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    //Put the POST parameters to the request
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("sessionTXT", session);
                    return params;
                }
            };
            //Show the dialog and do the POST Server request
            showLoading("Loading...");
            queue.add(postRequest);
        }else{
            dismissLoading();
            Intent myIntent = new Intent(this, SetupActivity.class);
            startActivity(myIntent);
            finish();
        }
    }
    private void InitializeComponents(){
        //Method for initializing main components
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        menuSurroundsFragment = new MenuSurroundsFragment();
        menuProfileFragment = new MenuProfileFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch(menuItem.getItemId()){
                    case R.id.navigation_menu_surrounds:
                        if(currentFragmentNo == 0)return false;
                        currentFragment = menuSurroundsFragment;
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        currentFragmentNo = 0;
                        transaction.replace(R.id.mainFragment, currentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.navigation_menu_discover:
                        if(currentFragmentNo == 1)return false;
                        currentFragment = menuSurroundsFragment;
                        if(currentFragmentNo > 1){
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        }else{
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        }
                        currentFragmentNo = 1;
                        transaction.replace(R.id.mainFragment, currentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.navigation_menu_matching:
                        if(currentFragmentNo == 2)return false;
                        currentFragment = menuSurroundsFragment;
                        if(currentFragmentNo > 2){
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        }else{
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        }
                        currentFragmentNo = 2;
                        transaction.replace(R.id.mainFragment, currentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.navigation_menu_chats:
                        if(currentFragmentNo == 3)return false;
                        currentFragment = menuSurroundsFragment;
                        if(currentFragmentNo > 3){
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        }else{
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        }
                        currentFragmentNo = 3;
                        transaction.replace(R.id.mainFragment, currentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.navigation_menu_profile:
                        if(currentFragmentNo == 4)return false;
                        currentFragment = menuProfileFragment;
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        currentFragmentNo = 4;
                        transaction.replace(R.id.mainFragment, currentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                }

                return true;
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        currentFragment = menuSurroundsFragment;
        transaction.replace(R.id.mainFragment, currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void showLoading(String message){
        //Method for showing the loading progress dialog
        progress.setMessage(message);
        progress.show();
    }
    public void dismissLoading(){
        //Method for dismissing the possibly ongoing progress dialog
        progress.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            if(menuProfileFragment.profilePicRequest){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    showLoading("Uploading, please wait...");

                    //converting image to base64 string
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //sending image to server
                    StringRequest request = new StringRequest(Request.Method.POST, "http://ami.earth/android/api/uploadImage.php", new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            dismissLoading();
                            if(s.contains("ok")){
                                menuProfileFragment = new MenuProfileFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                currentFragment = menuProfileFragment;
                                transaction.replace(R.id.mainFragment, currentFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }else{
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            dismissLoading();
                            Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                        }
                    }) {
                        //adding parameters to send
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("image", imageString);
                            parameters.put("session", session);
                            parameters.put("type", "profile");
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Bitmap srcBmp, dstBmp;
                    srcBmp  = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    if (srcBmp.getWidth() >= srcBmp.getHeight()){

                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                                0,
                                srcBmp.getHeight(),
                                srcBmp.getHeight()
                        );

                    }else{

                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                0,
                                srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                                srcBmp.getWidth(),
                                srcBmp.getWidth()
                        );
                    }
                    showLoading("Uploading, please wait...");

                    //converting image to base64 string
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    getResizedBitmap(dstBmp, 512, 512).compress(Bitmap.CompressFormat.JPEG, 75, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //sending image to server
                    StringRequest request = new StringRequest(Request.Method.POST, "http://ami.earth/android/api/uploadImage.php", new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            dismissLoading();
                            if(s.contains("ok")){
                                menuProfileFragment = new MenuProfileFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                currentFragment = menuProfileFragment;
                                transaction.replace(R.id.mainFragment, currentFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }else{
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            dismissLoading();
                            Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                        }
                    }) {
                        //adding parameters to send
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("image", imageString);
                            parameters.put("session", session);
                            parameters.put("type", "cover");
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
    public void reloadProfileFragment(){
        menuProfileFragment = new MenuProfileFragment();
    }
}
