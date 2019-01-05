package com.f4pl0.ami.MyActivities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.f4pl0.ami.Utils.Contact;
import com.f4pl0.ami.Utils.DetailedContactFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyContactsActivity extends AppCompatActivity {

    ImageButton backBtn;
    String session;
    LinearLayout contactsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        contactsContainer = findViewById(R.id.contactsContainer);

        session = getApplicationContext().getSharedPreferences("shared",MODE_PRIVATE).getString("SessionID","");

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://ami.earth/android/api/getMyContacts.php";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response.contains("invalid")) {
                                        //finish();
                                    }
                                    JSONObject contactsobj = new JSONObject(response);
                                    JSONArray contacts = contactsobj.getJSONArray("contacts");
                                    for(int i = 0; i < contacts.length(); i+=2){

                                        LinearLayout row = new LinearLayout(getApplicationContext());
                                        row.setOrientation(LinearLayout.HORIZONTAL);
                                        row.setId(View.generateViewId());
                                        contactsContainer.addView(row);

                                            try {
                                                JSONObject contact = contacts.getJSONObject(i );
                                                String contactName = contact.getString("name");
                                                int contactID = Integer.parseInt(contact.getString("id"));
                                                int contactType = contact.getInt("type");
                                                String contactImage = contact.getString("image");

                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(contactsContainer.getWidth()/2 - 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                params.gravity = Gravity.LEFT;
                                                params.bottomMargin = 5;
                                                params.rightMargin = 3;

                                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                                DetailedContactFragment detailedContactFragment = new DetailedContactFragment(contactName, contactImage, contactType, contactID, session, params);

                                                transaction.add(row.getId(), detailedContactFragment);
                                                transaction.commit();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        try {
                                            JSONObject contact = contacts.getJSONObject(i +1);
                                            String contactName = contact.getString("name");
                                            int contactID = Integer.parseInt(contact.getString("id"));
                                            int contactType = contact.getInt("type");
                                            String contactImage = contact.getString("image");

                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(contactsContainer.getWidth()/2 - 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            params.gravity = Gravity.RIGHT;
                                            params.bottomMargin = 5;
                                            params.leftMargin = 2;

                                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                            DetailedContactFragment detailedContactFragment = new DetailedContactFragment(contactName, contactImage, contactType, contactID, session, params);

                                            transaction.add(row.getId(), detailedContactFragment);
                                            transaction.commit();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        }

                                }catch(Exception e){
                                    finish();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
                queue.add(postRequest);




        backBtn = findViewById(R.id.contactsBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
