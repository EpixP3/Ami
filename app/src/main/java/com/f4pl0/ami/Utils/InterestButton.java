package com.f4pl0.ami.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;

import java.util.HashMap;
import java.util.Map;

public class InterestButton extends android.support.v7.widget.AppCompatButton {

    private boolean activated = false;
    private String name = "";
    private boolean preview;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public InterestButton(Context context, String name, final boolean preview) {
        super(context);
        this.name = name;
        this.preview = preview;

        this.setAllCaps(false);
        this.setText(name);
        this.setTextColor(Color.WHITE);
        this.setTextSize(11);
        this.setBackground(getResources().getDrawable(R.drawable.interest_button_false));
        if(preview){
            this.setBackground(getResources().getDrawable(R.drawable.interest_button_true));
        }
        SetActivated(false);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SetActivated(!GetActivated());
            }
        });
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(preview){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Not interested?")
                            .setMessage("Do you really want remove "+GetName()+" interest?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {




                                    final String session = getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).getString("SessionID", "");
                                    //Check if storred session is valid
                                    //POST REQUEST TO THE SERVER
                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                    String url = "http://ami.earth/android/api/deleteInterest.php-----";
                                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            //Put the POST parameters to the request
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("session", session);
                                            params.put("interest", GetName());
                                            return params;
                                        }
                                    };
                                    queue.add(postRequest);




                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void SetActivated(boolean activated){
        if(!preview) {
            this.activated = activated;
            if (activated) {
                this.setBackground(getResources().getDrawable(R.drawable.interest_button_true));
                String inters = getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
                getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).edit().putString("user.interests", inters + "," + name).commit();
            } else {
                this.setBackground(getResources().getDrawable(R.drawable.interest_button_false));
                String inters = getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
                getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).edit().putString("user.interests", inters.replace("," + name, "")).commit();
            }
        }
    }
    public boolean GetActivated(){
        return this.activated;
    }
    public void SetName(String name){
        this.name = name;
        this.setText(name);
    }
    public String GetName(){
        return this.name;
    }
    public String GetNameIfActivated(){
        if(this.activated){
            return this.name;
        }else{
            return "";
        }
    }
}
