package com.f4pl0.ami.Fragments.SetupFragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f4pl0.ami.R;
import com.f4pl0.ami.Structures.InterestButton;

@SuppressLint("ValidFragment")
public class SetupInterestsFragmentCategory extends Fragment {

    LinearLayout interestContainer;
    String[] interests;
    String name;
    TextView catName;

    @SuppressLint("ValidFragment")
    public SetupInterestsFragmentCategory(String name, String[] interests){
        this.interests = interests;
        this.name = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_interests_fragment_category, container, false);

        //Initialise components
        interestContainer = fragmentView.findViewById(R.id.interestContainerLyt);
        catName = fragmentView.findViewById(R.id.categoryTxt);

        catName.setText(name);
        for(int i = 0; i < interests.length; i++){
            interestContainer.addView(new InterestButton(getContext(), interests[i]));
            TextView sample = new TextView(getContext());
            sample.setText("a");
            sample.setTextColor(Color.TRANSPARENT);
            interestContainer.addView(sample);
        }
        return fragmentView;
    }

}
