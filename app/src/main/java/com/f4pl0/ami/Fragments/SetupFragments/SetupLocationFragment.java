package com.f4pl0.ami.Fragments.SetupFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4pl0.ami.R;


public class SetupLocationFragment extends Fragment {

    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and store it to a variable
        View fragmentView = inflater.inflate(R.layout.fragment_setup_location, container, false);

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragment = new SetupLocationFragmentButton();
        transaction.replace(R.id.setuplocationFragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        return fragmentView;
    }
    public void nextStep(){
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragment = new SetupLocationFragmentText();
        transaction.replace(R.id.setuplocationFragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
