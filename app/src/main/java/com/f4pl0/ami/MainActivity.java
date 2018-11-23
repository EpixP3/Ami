package com.f4pl0.ami;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise stuff
        progress = new ProgressDialog(this);
        progress.setTitle("Please wait a bit");
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        //Check if user is logged in, if not, go to "setup" activity
        final String session = getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).getString("SessionID","");
        if(!session.isEmpty()){
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
                                    getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).edit().putString("SessionID", "").commit();
                                    Intent myIntent = new Intent(MainActivity.this, SetupActivity.class);
                                    startActivity(myIntent);
                                    finish();
                                } else if (response.contains("valid")){
                                    setContentView(R.layout.activity_main);

                                    // CODE FOR INITIALIZATION AND EVERYTHING ELSE GOES HERE
                                    bottomNavigationView = findViewById(R.id.bottomNavigationView);
                                }else{
                                    Toast.makeText(MainActivity.this, "There was an error." , Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }catch(Exception e){
                                Toast.makeText(MainActivity.this, "There was an error." , Toast.LENGTH_SHORT).show();
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
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("sessionTXT", session);
                    return params;
                }
            };
            showLoading("Logging you in...");
            queue.add(postRequest);
        }else{
            dismissLoading();
            Intent myIntent = new Intent(this, SetupActivity.class);
            startActivity(myIntent);
            finish();
        }
    }


    public void showLoading(String message){
        progress.setMessage(message);
        progress.show();
    }
    public void dismissLoading(){
        progress.dismiss();
    }
}
