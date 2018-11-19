package com.f4pl0.ami.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.R;


public class SetupLocationFragmentText extends Fragment {

    EditText editLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_location_fragment_text, container, false);

        //Initialize components
        editLocation = fragmentView.findViewById(R.id.editLocation);

        //Disable editability
        editLocation.setKeyListener(null);

        //Set the location
        String location = this.getActivity().getPreferences(MainActivity.MODE_PRIVATE).getString("user.location", "user.location");
        editLocation.setText(location);
        return fragmentView;
    }

}
