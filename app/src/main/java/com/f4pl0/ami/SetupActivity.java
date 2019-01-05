package com.f4pl0.ami;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.Fragments.SetupFragments.SetupAgeFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupContactsFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupContactsFragmentPermission;
import com.f4pl0.ami.Fragments.SetupFragments.SetupDonateFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupInterestsFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupLocationFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupNameFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupOccupationFragment;
import com.f4pl0.ami.Fragments.SetupFragments.SetupPhoneFragment;
import com.f4pl0.ami.Utils.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SetupActivity extends FragmentActivity {

    //Store the setup values in these variables
    String name = "";
    int age = 0;
    String occupation = "";
    String location = "";
    Contact[] contacts;
    String phoneNumber = "";
    String interests = "";

    LocationManager lm;
    ImageButton nextBtn;
    int currentStep = 0;
    Fragment fragment;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //Set starting fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new SetupNameFragment();
        transaction.replace(R.id.setupFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //Initialize stuff
        getSharedPreferences("interests", Context.MODE_PRIVATE).edit().putString("user.interests", "").commit();
        progress = new ProgressDialog(this);
        progress.setTitle("Please wait a bit");
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        nextBtn = findViewById(R.id.setupNextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check on which set-up step are we on, so we can change fragments
                switch (currentStep) {
                    case 0:
                        //Check if user has entered the name
                        if (name.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupAgeFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        } else {
                            Toast.makeText(SetupActivity.this, "Please enter Your Name.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        //Check user's age
                        if (age > 0 && age < 120) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            //Change to occupation fragment
                            fragment = new SetupOccupationFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        } else {
                            Toast.makeText(SetupActivity.this, "Please enter Your Age.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        //Check user's occupation
                        if (occupation.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            //Change to location fragment
                            fragment = new SetupLocationFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            nextBtn.setVisibility(View.INVISIBLE);
                            currentStep++;
                        } else {
                            Toast.makeText(SetupActivity.this, "Please enter Your Occupation.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        //Check user's location
                        if (location.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            //Change to contacts fragment
                            fragment = new SetupContactsFragmentPermission();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            nextBtn.setVisibility(View.INVISIBLE);
                            currentStep++;
                        } else {
                            Toast.makeText(SetupActivity.this, "Please enter Your Occupation.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if(contacts.length > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            //Change to phone fragment
                            fragment = new SetupPhoneFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                            nextBtn.setVisibility(View.INVISIBLE);
                            break;
                        }
                    case 5:
                        //Change fragment with a nice little animation
                        if(phoneNumber.length() > 0) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            //Change to interests fragment
                            fragment = new SetupInterestsFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                            break;
                        }else{
                            Toast.makeText(SetupActivity.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    case 6:
                        // Get interests and store them
                        String inters = getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
                        if(inters.split(",").length > 3) {
                            setInterests(inters);
                            Log.d("INTERESTS", inters);
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupDonateFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        }else{
                            Toast.makeText(SetupActivity.this, "Please select at least 3 interests.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 7:
                        //Finish with the setup and upload data to the server.
                        showLoading("Finishing your registration...");
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url ="http://ami.earth/android/api/register.php";
                        //Setup the contacts JSON
                        final JSONArray contactsJSONArray = new JSONArray();
                        try {
                            for (int i=0; i < contacts.length; i++){
                                if(contacts[i].GetNumber().startsWith("+")){
                                    contactsJSONArray.put(new JSONObject()
                                            .put("name", contacts[i].GetName())
                                            .put("number", contacts[i].GetNumber().replace(" ", ""))
                                            .put("type", contacts[i].GetType()));
                                }else if(contacts[i].GetNumber().startsWith("0")){
                                    contactsJSONArray.put(new JSONObject()
                                            .put("name", contacts[i].GetName())
                                            .put("number", "+"+GetCountryZipCode()+contacts[i].GetNumber().substring(1).replace(" ", ""))
                                            .put("type", contacts[i].GetType()));
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //Set up the POST Server request for registering
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            String[] responseArr = response.split(":");
                                            if (responseArr.length == 2 && responseArr[0].contains("session")) {
                                                //If session exists, user has successfully registered, store that session, it is very important
                                                getApplicationContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).edit().putString("SessionID", responseArr[1]).commit();
                                                //Go to the main activity, that's where the all fun is happening
                                                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(myIntent);
                                                dismissLoading();
                                                finish();
                                            } else {
                                                dismissLoading();
                                                Toast.makeText(SetupActivity.this, "There was an error.", Toast.LENGTH_SHORT).show();
                                            }
                                        }catch(Exception e){
                                            Toast.makeText(SetupActivity.this, "There was an error." , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dismissLoading();
                                        Toast.makeText(SetupActivity.this, "There was an error.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                //Set all of the POST Request parameters
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("name", name);
                                params.put("age", ""+age);
                                params.put("occupation",occupation);
                                params.put("location", location);
                                params.put("contacts", contactsJSONArray.toString());
                                params.put("phone", phoneNumber.replace(" ", ""));
                                params.put("interests", interests.substring(1));
                                return params;
                            }
                        };
                        queue.add(postRequest);

                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing lol
    }

    public void setName(String t) {
        //Method for setting the age variable
        name = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.name", name).commit();
    }

    public void setAge(int t) {
        //Method for setting the age variable
        age = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putInt("user.age", age).commit();
    }

    public void setOccupation(String t) {
        //Method for setting the occupation variable
        occupation = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.occupation", occupation).commit();
    }

    public void setLocation(String t) {
        //Method for setting the location variable
        location = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.location", location).commit();
        dismissLoading();
        nextBtn.setVisibility(View.VISIBLE);
    }

    public void setContacts(Contact[] contacts) {
        //Method for setting the contacts variable
        this.contacts = contacts;
    }
    public String GetCountryZipCode(){
        //Method that returns country call code based on SIM service country ID
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }
    public void setPhoneNumber(String t){
        //Method for setting the phoneNumber variable
        this.phoneNumber = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.phoneNumber", phoneNumber).commit();
    }

    public void setInterests(String interest){
        //Method for setting the interests variable
        this.interests = interest;
    }

    public void setContactType(int ContactIndex, int Type) {
        //Method for setting the contact type of a contact
        this.contacts[ContactIndex].SetType(Type);
    }

    private void getLocation(double lat, double lng) {
        boolean flag = false;
        while(true) {
            //Method for locating the user's city and state based on coordinates
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null) {
                if (addresses.size() > 0) {
                    for (int i = 0; i < addresses.size(); i++) {
                         String city = addresses.get(i).getLocality();
                        if(city == null) {
                            city = addresses.get(i).getAddressLine(0).split(",")[2].substring(1);
                        }
                        String country = addresses.get(0).getCountryName();
                        if (city != null && country != null) {
                            //Store city and state in a variable and go to next step
                            String loc = city + ", " + country;
                            setLocation(loc);
                            ((SetupLocationFragment) fragment).nextStep();
                            dismissLoading();
                            flag = true;
                            break;
                        }
                    }
                }
            } else {
            }
            if(flag){
                break;
            }
        }
    }

    int count = 0;
    //LocationListener for getting the user's location
    private LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(final Location location) {
            //If a location is got, check if it's null
            if (location != null) {
                //Extract the coordinates from it
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                lm.removeUpdates(mLocationListener);
                getLocation(latitude, longitude);
                dismissLoading();
            } else {
                //Try all of the possible methods for location getting
                count++;
                switch (count) {
                    case 1:
                        lm.removeUpdates(mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                        break;
                    case 2:
                        lm.removeUpdates(mLocationListener);
                        lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, mLocationListener);
                        break;
                    case 3:
                        //All of the methods are a failure, android is broken
                        Toast.makeText(SetupActivity.this, "Couldn't get Your location. Please try again later.", Toast.LENGTH_SHORT).show();
                        lm.removeUpdates(mLocationListener);
                        count = 0;
                        break;
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showLoading("Getting your location...");
                    //showLoading("Getting your location...");
                    //getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


                    Log.d("LOCATION_SERVICES","ListSize: "+lm.getAllProviders().size());
                    try {
                        lm.requestLocationUpdates(lm.getBestProvider(new Criteria(), true), 0, 0, mLocationListener);
                    }catch (IllegalArgumentException e){
                        dismissLoading();
                    }
                    return;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.e("PERMISSION", "PERMISSION FOR LOCATION DENIED");
                }
                break;
            }
            case 2:
                //contacts
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ArrayList<Contact> contacts = new ArrayList<>();
                    ContentResolver cr = this.getContentResolver();
                    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                            null, null, null, null);

                    if ((cur != null ? cur.getCount() : 0) > 0) {
                        while (cur != null && cur.moveToNext()) {
                            String id = cur.getString(
                                    cur.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cur.getString(cur.getColumnIndex(
                                    ContactsContract.Contacts.DISPLAY_NAME));
                            if (cur.getInt(cur.getColumnIndex(
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                Cursor pCur = cr.query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                        new String[]{id}, null);
                                while (pCur.moveToNext()) {
                                    String phoneNo = pCur.getString(pCur.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    contacts.add(new Contact(name, phoneNo));
                                }
                                pCur.close();
                            }
                        }
                    }
                    if(cur!=null){
                        cur.close();
                    }
                    showContactsFragment(contacts.toArray(new Contact[contacts.size()]));
                }else {
                    Log.e("PERMISSION", "PERMISSION FOR CONTACTS NOT GRANTED");
                }
                break;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void showContactsFragment(Contact[] contacts){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragment = new SetupContactsFragment(contacts);
        setContacts(contacts);
        transaction.replace(R.id.setupFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        nextBtn.setVisibility(View.VISIBLE);
        dismissLoading();
    }
    public void showLoading(String message){
        progress.setMessage(message);
        progress.show();
    }
    public void dismissLoading(){
        progress.dismiss();
    }
    public void getPhoneAndChangeFragment(){

        //Get the number
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = "";
        try
        {
            mPhoneNumber =tMgr.getLine1Number();
        }
        catch(Exception ex)
        {
        }

        if(mPhoneNumber.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("We couldn't get Your phone number, please enter it for us.");
            // Set up the input
            final EditText input = new EditText(this);
            input.setText("+"+GetCountryZipCode());
            input.setInputType(InputType.TYPE_CLASS_PHONE);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Change the fragment with the phone
                    nextBtn.setVisibility(View.VISIBLE);
                    setPhoneNumber(input.getText().toString());
                    ((SetupPhoneFragment) fragment).changeFragment(input.getText().toString());
                }
            });

            builder.show();
        }else {
            //Change the fragment with the phone
            nextBtn.setVisibility(View.VISIBLE);
            setPhoneNumber(mPhoneNumber);
            ((SetupPhoneFragment) fragment).changeFragment(mPhoneNumber);
        }
    }

}
