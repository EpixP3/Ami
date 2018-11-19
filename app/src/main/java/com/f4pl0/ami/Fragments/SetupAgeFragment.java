package com.f4pl0.ami.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;


public class SetupAgeFragment extends Fragment {

    TextView meetTxt;
    EditText editAge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Fragment and store it in a View
        View fragmentView = inflater.inflate(R.layout.fragment_setup_age, container, false);

        // Initialize components
        meetTxt = fragmentView.findViewById(R.id.meetTxt);
        editAge = fragmentView.findViewById(R.id.editAge);
        editAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((SetupActivity) getActivity()).setAge(Integer.parseInt(s.toString()));
            }
        });

        //Get the name and set the text
        String name = this.getActivity().getPreferences(MainActivity.MODE_PRIVATE).getString("user.name", "user.name");
        meetTxt.setText(meetTxt.getText().toString().replace("Name", name));
        //return the inflated view
        return fragmentView;
    }

}
