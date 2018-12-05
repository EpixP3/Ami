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

        String[] Kunst = new String[11];
        Kunst[0] = "Design";
        Kunst[1] = "Mode / Fashion";
        Kunst[2] = "Fotografie";
        Kunst[3] = "Wohnen / Deko";
        Kunst[4] = "Architektur";
        Kunst[5] = "Street Art";
        Kunst[6] = "Malerei";
        Kunst[7] = "Theater";
        Kunst[8] = "Tattoos / Piercing";
        Kunst[9] = "Tanz";
        Kunst[10] = "Gesang";
        String[] Technologie = new String[8];
        Technologie[0] = "Computer";
        Technologie[1] = "Smartphones / Tablets";
        Technologie[2] = "Software / Apps";
        Technologie[3] = "Hardware";
        Technologie[4] = "Wearables";
        Technologie[5] = "Programmieren";
        Technologie[6] = "Neue Technologien";
        Technologie[7] = "Futurismus";

        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.add (R.id.interestCategoryContainerLyt, new SetupInterestsFragmentCategory("Kunst",Kunst));
        fragmentTransaction.commit ();


        return fragmentView;
    }

}
