package com.f4pl0.ami.Fragments.SetupFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.f4pl0.ami.R;


public class SetupInterestsFragment extends Fragment {

    LinearLayout interestCategoryContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_interests, container, false);

        // Initialize components
        interestCategoryContainer = fragmentView.findViewById(R.id.interestCategoryContainerLyt);

        String[] ints = new String[5];
        ints[0] = "Programming";
        ints[1] = "Loling";
        ints[2] = "Football";
        ints[3] = "Boxing";
        ints[4] = "Whatever";
        for(int i=0; i < 10; i ++){
            FragmentManager fragmentManager = getFragmentManager ();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
            fragmentTransaction.add (R.id.interestCategoryContainerLyt, new SetupInterestsFragmentCategory("Category "+i,ints));
            fragmentTransaction.commit ();
        }


        return fragmentView;
    }

}
