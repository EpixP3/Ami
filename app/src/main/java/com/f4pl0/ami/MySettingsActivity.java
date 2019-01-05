package com.f4pl0.ami;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MySettingsActivity extends AppCompatActivity {

    ImageButton backBtn;
    Switch notificationsSwitch;
    EditText phoneEditText;
    Button editPhoneBtn;
    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);

        session = getApplicationContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).getString("SessionID", "");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://ami.earth/android/api/getMyPhoneNumber.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.contains("invalid")){
                            phoneEditText.setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "There was an error connecting to the server.", Toast.LENGTH_SHORT).show();
                        finish();
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

        phoneEditText = findViewById(R.id.settingsPhoneEditText);

        backBtn = findViewById(R.id.settingsBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        notificationsSwitch.setChecked(getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).getBoolean("notificationsEnabled", false));
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).edit().putBoolean("notificationsEnabled" , isChecked).commit();
            }
        });
        editPhoneBtn = findViewById(R.id.settingsChangeNumberBtn);
        editPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO edit phone alert builder
            }
        });
    }
}
