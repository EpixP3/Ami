package com.f4pl0.ami;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
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

import com.f4pl0.ami.Fragments.SetupAgeFragment;
import com.f4pl0.ami.Fragments.SetupContactsFragment;
import com.f4pl0.ami.Fragments.SetupContactsFragmentPermission;
import com.f4pl0.ami.Fragments.SetupInterestsFragment;
import com.f4pl0.ami.Fragments.SetupLocationFragment;
import com.f4pl0.ami.Fragments.SetupNameFragment;
import com.f4pl0.ami.Fragments.SetupOccupationFragment;
import com.f4pl0.ami.Fragments.SetupPhoneFragment;
import com.f4pl0.ami.Structures.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetupActivity extends FragmentActivity {

    String name = "";
    int age = 0;
    String occupation = "";
    String location = "";
    Contact[] contacts;
    String phoneNumber = "";

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
                        //Change fragment with a nice little animation
                        if(phoneNumber.length() > 0) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupInterestsFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                            break;
                        }else{
                            Toast.makeText(SetupActivity.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing lol
    }

    public void setName(String t) {
        name = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.name", name).commit();
    }

    public void setAge(int t) {
        age = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putInt("user.age", age).commit();
    }

    public void setOccupation(String t) {
        occupation = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.occupation", occupation).commit();
    }

    public void setLocation(String t) {
        location = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.location", location).commit();
        dismissLoading();
        nextBtn.setVisibility(View.VISIBLE);
    }

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }
    public void setPhoneNumber(String t){
        this.phoneNumber = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.phoneNumber", phoneNumber).commit();
    }



    public void setContactType(int ContactIndex, int Type) {
        this.contacts[ContactIndex].SetType(Type);
    }

    private void getLocation(double lat, double lng) {
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            for (int i = 0; i < addresses.size(); i++) {
                String city = addresses.get(i).getLocality();
                String country = addresses.get(0).getCountryName();
                if (city != null && country != null) {
                    String loc = city + ", " + country;
                    setLocation(loc);
                    ((SetupLocationFragment) fragment).nextStep();
                    break;
                }
            }
        }
    }

    int count = 0;
    private LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(final Location location) {
            if (location != null) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                getLocation(latitude, longitude);
                lm.removeUpdates(mLocationListener);
                dismissLoading();
            } else {
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
                showLoading("Getting your location...");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //showLoading("Getting your location...");
                    //getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Log.d("LOCATION_SERVICES","ListSize: "+lm.getAllProviders().size());
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
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
        try {
            mPhoneNumber = tMgr.getLine1Number();
        }catch (Exception e){}
        if(mPhoneNumber.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("We couldn't get Your phone number, please enter it for us.");
            // Set up the input
            final EditText input = new EditText(this);
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