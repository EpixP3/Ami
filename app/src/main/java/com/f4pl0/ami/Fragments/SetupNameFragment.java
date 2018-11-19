package com.f4pl0.ami.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;


public class SetupNameFragment extends Fragment {

    EditText nameTxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the view and store it as a variable
        View fragmentView = inflater.inflate(R.layout.fragment_setup_name, container, false);

        //Initialize stuff
        nameTxt = fragmentView.findViewById(R.id.editName);
        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((SetupActivity) getActivity()).setName(s.toString());
            }
        });
        //Return inflated view
        return fragmentView;
    }
}
