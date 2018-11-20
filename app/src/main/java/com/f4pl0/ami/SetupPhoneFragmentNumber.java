package com.f4pl0.ami;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class SetupPhoneFragmentNumber extends Fragment {

    String number;
    TextView notCorrect;
    EditText editNumber;

    @SuppressLint("ValidFragment")
    public SetupPhoneFragmentNumber(String number){
        this.number = number;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_phone_fragment_number, container, false);

        //Initialize components
        notCorrect = fragmentView.findViewById(R.id.notCorrectPhoneTxt);
        editNumber = fragmentView.findViewById(R.id.editPhoneNumber);
        editNumber.setText(number);
        notCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "yet to be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        return fragmentView;
    }

}
