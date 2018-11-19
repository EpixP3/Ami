package com.f4pl0.ami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if user is registered, if not, go to "setup" activity
        boolean setup = getPreferences(MODE_PRIVATE).getBoolean("SetUp",false);
        if(setup){
            setContentView(R.layout.activity_main);
        }else{
            Intent myIntent = new Intent(this, SetupActivity.class);
            startActivity(myIntent);
            finish();
        }


    }
}
