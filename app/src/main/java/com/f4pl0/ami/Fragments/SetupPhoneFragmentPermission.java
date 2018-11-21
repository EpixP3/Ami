package com.f4pl0.ami.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;

public class SetupPhoneFragmentPermission extends Fragment {

    ImageButton getPhnePErmissionBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_phone_fragment_permission, container, false);

        //Initialise components
        getPhnePErmissionBtn = fragmentView.findViewById(R.id.setupPhonePermissionBtn);
        getPhnePErmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SetupActivity)getActivity()).getPhoneAndChangeFragment();
            }
        });
        return fragmentView;
    }
}
