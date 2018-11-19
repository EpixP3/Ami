package com.f4pl0.ami;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.f4pl0.ami.Fragments.SetupAgeFragment;
import com.f4pl0.ami.Fragments.SetupContactsFragmentPermission;
import com.f4pl0.ami.Fragments.SetupLocationFragment;
import com.f4pl0.ami.Fragments.SetupNameFragment;
import com.f4pl0.ami.Fragments.SetupOccupationFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SetupActivity extends FragmentActivity {

    String name = "";
    int age = 0;
    String occupation = "";
    String location = "";
    ImageButton nextBtn;
    int currentStep = 0;
    Fragment fragment;

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
        nextBtn = findViewById(R.id.setupNextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check on which set-up step are we on, so we can change fragments
                switch (currentStep){
                    case 0:
                        //Check if user has entered the name
                        if(name.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupAgeFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        }else{
                            Toast.makeText(SetupActivity.this, "Please enter Your Name.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        //Check user's age
                        if(age > 0 && age < 120) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupOccupationFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        }else{
                            Toast.makeText(SetupActivity.this, "Please enter Your Age.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        //Check user's occupation
                        if(occupation.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupLocationFragment();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            nextBtn.setVisibility(View.INVISIBLE);
                            currentStep++;
                        }else{
                            Toast.makeText(SetupActivity.this, "Please enter Your Occupation.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        //Check user's location
                        if(location.length() > 0) {
                            //Change fragment with a nice little animation
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            fragment = new SetupContactsFragmentPermission();
                            transaction.replace(R.id.setupFragment, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            currentStep++;
                        }else{
                            Toast.makeText(SetupActivity.this, "Please enter Your Occupation.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        });
    }

    public void setName(String t){
        name = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.name",name).commit();
    }
    public void setAge(int t){
        age = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putInt("user.age", age).commit();
    }
    public void setOccupation(String t){
        occupation = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.occupation", occupation).commit();
    }
    public void setLocation(String t){
        location = t;
        getPreferences(MainActivity.MODE_PRIVATE).edit().putString("user.location", location).commit();
        nextBtn.setVisibility(View.VISIBLE);
    }
    private void getLocation(double lat, double lng){
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            for (int i=0; i < addresses.size(); i++){
                String city = addresses.get(i).getLocality();
                String country = addresses.get(0).getCountryName();
                if(city != null && country != null){
                    String loc = city + ", "+country;
                    setLocation(loc);
                    ((SetupLocationFragment) fragment).nextStep();
                    break;
                }
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    getLocation(latitude, longitude);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
