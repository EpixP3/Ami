package com.f4pl0.ami.Fragments.SetupFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;


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

        //Disable editability
        editNumber.setKeyListener(null);

        editNumber.setText(number);
        notCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Please enter Your number");
                final EditText input = new EditText(getContext());
                input.setText("+"+((SetupActivity)getActivity()).GetCountryZipCode());
                input.setText(number);
                input.setGravity(EditText.TEXT_ALIGNMENT_CENTER);
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        number = input.getText().toString();
                        ((SetupActivity)getActivity()).setPhoneNumber(number);
                        editNumber.setText(number);
                    }
                });

                builder.show();
            }
        });

        return fragmentView;
    }

}
