package com.f4pl0.ami.Fragments;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;

public class SetupLocationFragmentButton extends Fragment {

    ImageButton giveLocationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_location_fragment_button, container, false);

        // Initialize components
        giveLocationBtn = fragmentView.findViewById(R.id.giveLocationBtn);
        giveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });

        return fragmentView;
    }

}
