package com.f4pl0.ami.Fragments.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.f4pl0.ami.R;

public class MenuSurroundsFragment extends Fragment {

    LinearLayout postsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu_surrounds, container, false);

        // Initialize the components
        postsContainer = fragmentView.findViewById(R.id.postsContainer);

        for(int i=0; i < 15; i++){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.postsContainer, new PostFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return fragmentView;
    }

}
