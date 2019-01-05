package com.f4pl0.ami;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

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
    MainActivity thisMainActivity;
    int currentFragmentNo = 0;

    @Override
    public void onBackPressed() {
        //do nothing lol
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisMainActivity = this;
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
                            finish();
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
        menuProfileFragment = new MenuProfileFragment(this);
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
        new ShowcaseView.Builder(this)
                //.setTarget(new ViewTarget(getId(), getActivity()))
                .setContentTitle("Welcome to Ami!")
                .setContentText("On this screen You can browse posts made by Your contacts, or surrounding people.")
                .withNewStyleShowcase()
                .singleShot(10)
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        new ShowcaseView.Builder(thisMainActivity)
                                .setTarget(new ViewTarget(bottomNavigationView.getId(), thisMainActivity))
                                .setContentTitle("Navigation")
                                .setContentText("Here You can navigate to different sections of the app.")
                                .withNewStyleShowcase()
                                .singleShot(11)
                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                    @Override
                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    }

                                    @Override
                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                    }
                                })
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
                .setStyle(R.style.AppTheme)
                .build();
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

        if (resultCode == RESULT_OK && data != null) {
            if(requestCode == 121){
                //CAMERA
                Log.d("CAMERA", "REQCODE 121");
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Intent intent = new Intent(this, NewPostActivity.class);
                intent.putExtra("TYPE", "3");
                intent.putExtra("img" , imageBitmap);
                startActivity(intent);
                Log.d("WIDTHxHEIGHT", imageBitmap.getWidth() + " x " + imageBitmap.getHeight());
            }else if(requestCode == 122) {
                //GALLERY
                try {
                    Uri filePath = data.getData();
                    Intent intent = new Intent(this, NewPostActivity.class);
                    intent.putExtra("TYPE", "2");

                    final String imageString = encodeToBase64(scaleBitmapContent(MediaStore.Images.Media.getBitmap(getContentResolver(), filePath)), Bitmap.CompressFormat.JPEG, 100);
                    intent.putExtra("img" , imageString);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Uri filePath = data.getData();
                if (menuProfileFragment.profilePicRequest) {
                    try {
                        Bitmap srcBmp, dstBmp;
                        srcBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        Log.d("SRCBITMAP", "Height: " + srcBmp.getHeight() + "  Width: " + srcBmp.getWidth());
                        if (srcBmp.getWidth() >= srcBmp.getHeight()) {
                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                                    0,
                                    srcBmp.getHeight(),
                                    srcBmp.getHeight()
                            );

                        } else {

                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    0,
                                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                                    srcBmp.getWidth(),
                                    srcBmp.getWidth()
                            );
                        }
                        showLoading("Uploading, please wait...");

                        //converting image to base64 string
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        scaleBitmapProfile(dstBmp).compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        //sending image to server
                        StringRequest request = new StringRequest(Request.Method.POST, "http://ami.earth/android/api/uploadImage.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                dismissLoading();
                                if (s.contains("ok")) {
                                    ReloadProfileFragment();
                                } else {
                                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                dismissLoading();
                                Toast.makeText(MainActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                                ;
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
                } else {
                    try {
                        Bitmap srcBmp, dstBmp;
                        srcBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        Log.d("SRCBITMAP", "Height: " + srcBmp.getHeight() + "  Width: " + srcBmp.getWidth());
                        dstBmp = scaleBitmapCover(srcBmp);

                        Log.d("DSTBITMAP", "Height: " + dstBmp.getHeight() + "  Width: " + dstBmp.getWidth());
                        showLoading("Uploading, please wait...");

                        //converting image to base64 string
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        dstBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        //sending image to server
                        StringRequest request = new StringRequest(Request.Method.POST, "http://ami.earth/android/api/uploadImage.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                dismissLoading();
                                if (s.contains("ok")) {
                                    ReloadProfileFragment();
                                } else {
                                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                dismissLoading();
                                Toast.makeText(MainActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                                ;
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
    }
    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    private Bitmap scaleBitmapProfile(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int maxWidth = 256;
        int maxHeight = 256;

        Log.v("Pictures", "Width and height are " + width + "--" + height);

        if (width > height) {
            // landscape
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
    private Bitmap scaleBitmapCover(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int maxWidth = 1024;
        int maxHeight = 1024;

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
    }private Bitmap scaleBitmapContent(Bitmap bm) {
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
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
    public void ReloadProfileFragment(){
        menuProfileFragment = new MenuProfileFragment(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        currentFragment = menuProfileFragment;
        transaction.replace(R.id.mainFragment, currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
