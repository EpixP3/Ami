package com.f4pl0.ami.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupPhoneFragmentNumber;
import com.f4pl0.ami.SetupPhoneFragmentPermission;

public class SetupPhoneFragment extends Fragment {

    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_phone, container, false);

        //Change the fragment to button, and later on to the phone when user clicks the button
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment = new SetupPhoneFragmentPermission();
        transaction.replace(R.id.setupPhoneFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        return fragmentView;
    }
    public void changeFragment(String phoneNumber){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragment = new SetupPhoneFragmentNumber(phoneNumber);
        transaction.replace(R.id.setupPhoneFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
